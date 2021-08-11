import Drink from "../model/Drink.js";
import drinkTableRow from "../component/drinkTableRow.js";
import {getRequest} from "../../utils/methodFetches.js";
import {$} from "../../utils/querySelector.js";
import DrinkRequest from "../dto/DrinkRequest.js";

export const renderDrinksTable = async (drinks) => {
  const response = await getRequest("/admin/drinks");

  if (response.data) {
    const drinkArray = convertRequestToDrinkArray(response.data);
    drinks.setDrinks(drinkArray);
  }

  const $drinksTable = $("#drinksTable");
  $drinksTable.querySelector('tbody').innerHTML = '';

  drinks.getDrinks().forEach((element) => {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('afterbegin',
        drinkTableRow(element)
    )
  })
}

function convertRequestToDrinkArray(drinkRequests) {
  return drinkRequests.map(ele =>
      new Drink(
          ele.id,
          ele.name,
          ele.englishName,
          ele.alcoholByVolume,
          ele.imageUrl,
          ele.category
      )
  )
}

export const getDrinkDetail = ($row, id) => {
  const name = $row.querySelector(".drinkName input").value
  const englishName = $row.querySelector(".drinkEnglishName input").value
  const alcoholByVolume = $row.querySelector(".drinkAbv input").value
  const imagePath = $row.querySelector(".drinkImageFilePath input").value
  const categoryKey = $row.querySelector(".categoryKey").value

  return new DrinkRequest(id, name, englishName, alcoholByVolume, imagePath, categoryKey);
}
