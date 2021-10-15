import Drink from "../model/Drink.js";
import drinkTableRow from "../component/drinkTableRow.js";
import {getRequest} from "../../utils/methodFetches.js";
import {$} from "../../utils/querySelector.js";
import DrinkRequest from "../dto/DrinkRequest.js";
import drinkInsertModalRow from "../component/drinkInsertModalRow.js";
import pageItemSelected from "../component/pageItemSelected.js";
import pageItem from "../component/pageItem.js";

export const renderDrinksTable = async (drinks) => {
  const currentPage = new URLSearchParams(window.location.search).get("page");

  const response = await getRequest(`/admin/drinks?page=${ currentPage ?? 1}`);
  
  const { lastPage } = response.pageInfo;
  renderDrinkPaginationButton(currentPage ?? 1, lastPage);

  const drinkArray = convertRequestToDrinkArray(response.data);
  drinks.setDrinks(drinkArray);
  
  const $drinksTable = $("#drinksTable");
  $drinksTable.querySelector('tbody').innerHTML = '';

  drinks.getDrinks().forEach((element) => {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('beforeend',
        drinkTableRow(element)
    )
  })
}

export const renderDrinksBatchInsertModal = async () => {
  const $drinksTable = $("#dataInsertModal");
  $drinksTable.querySelector('tbody').innerHTML = '';

  const result = await getRequest('/categories');

  const BATCH_SIZE = 10;
  for (let i = 0; i < BATCH_SIZE; i++) {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('afterbegin',
        drinkInsertModalRow(result.data)
    )
  }
}

export const getDrinkDetail = ($row, id) => {
  const name = $row.querySelector(".drinkName input").value
  const englishName = $row.querySelector(".drinkEnglishName input").value
  const alcoholByVolume = $row.querySelector(".drinkAbv input").value
  const images = $row.querySelector(".drinkImageFilePath input").files
  const description = $row.querySelector(".drinkDescription textarea").value
  const categoryKey = $row.querySelector(".categoryKey").value

  return new DrinkRequest(id, name, englishName, alcoholByVolume, images, description, categoryKey);
}

function convertRequestToDrinkArray(drinkResponse) {
  return drinkResponse.map(ele =>
      new Drink(
          ele.id,
          ele.name,
          ele.englishName,
          ele.alcoholByVolume,
          ele.imageUrl,
          ele.description,
          ele.category
      )
  )
}

function renderDrinkPaginationButton (currentPage, lastPage) {
  const $drinkPagination = $("#drinkPagination");
  $drinkPagination.innerHTML = '';
  currentPage = Number(currentPage);
  
  const offset = 5;
  const previousNumber = currentPage - offset > 0 ? currentPage - offset : 1;
  const nextNumber = currentPage + offset < lastPage ? currentPage + offset : lastPage;

  const start = (currentPage - offset) > 1 ? (currentPage - offset) : 1;
  const last = (currentPage + offset) < lastPage ? (currentPage + offset) : lastPage;

  $drinkPagination.insertAdjacentHTML('beforeend', pageItem("previous", previousNumber))
  for ( let i = start; i <= last; i++ ) {
    $drinkPagination.insertAdjacentHTML('beforeend',
        i === currentPage ?
            pageItemSelected(i) :
            pageItem(i, i)
    )
  }
  $drinkPagination.insertAdjacentHTML('beforeend', pageItem("next", nextNumber))
}
