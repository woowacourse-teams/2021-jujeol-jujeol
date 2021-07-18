import Drinks from "./drinks/model/Drinks.js";
import drinkTableRow from "./drinks/component/drinkTableRow.js";
import {
  getRequest,
  putRequest,
  postRequest,
  deleteRequest
} from "./utils/methodFetches.js";
import drinkTableColumns from "./drinks/component/drinkTableColumns.js";
import Drinks from "./drinks/model/Drinks";

const $drinksTable = document.querySelector("#drinksTable");

const init = () => {
  const drinks = new Drinks();
  console.log("admin.js :: onload");

  addEditButtonsEvent(drinks);
  addSaveButtonEvent(drinks);

  renderDrinksTable(drinks);
}
// 현재 구조의 문제점 : 상태관리를 통해서가 아니라 로우하게 다루고 있음
// fetches는 상테와 연관되게 하고, 렌더와 분리

window.addEventListener('DOMContentLoaded', init);

async function renderDrinksTable(drinks) {
  const response = await getRequest("/admin/drinks")
  if (response.data) {
    drinks.setDrinks(response.data);
  }

  console.log('admin.js :: renderDrinksTable() :: parameter');
  $drinksTable.querySelector('tbody').innerHTML = '';
  drinks.getDrinks().forEach((element) => {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('afterbegin',
        drinkTableRow(element)
    )
  })
}

function addEditButtonsEvent(drinks) {
  const $tableBody = document.querySelector(
      "main #drinksTable tbody");

  $tableBody.addEventListener('click',
      event => drinkTableUpdateEvent(event, drinks)
  )

  $tableBody.addEventListener('click',
      event => drinkTableDeleteEvent(event, drinks)
  )
}

function addSaveButtonEvent() {
  const $tableBody = document.querySelector(
      ".modal .modal-footer");

  $tableBody.addEventListener('click',
      event => batchInsertEvent(event)
  )
}

async function drinkTableUpdateEvent(event, drinks) {
  if (!(event.target.classList.contains("updateButton")
      || event.target.classList.contains("normalButton"))) {
    return;
  }

  const $parentRow = event.target.closest(".drinkTableRow");
  const drinkId = $parentRow.querySelector(".drinkId").innerText;
  const drink = drinks.getDrinks().find(ele => ele.id == drinkId);

  console.log("drinkTableUpdateEvent ::: drinkStore")
  console.log(drinkStore)
  console.log("drinkTableUpdateEvent ::: drink")
  console.log(drink)

  if (event.target.classList.contains("updateButton")) {
    const requestBody = getDrinkDetailWithoutId($parentRow);
    if (!validDrinkRequest(requestBody)) {
      alert("필수 항목을 모두 작성해주세요.");
      return false;
    }

    requestBody.id = drinkId;

    console.log("drinkTableUpdateEvent ::: requestBody")
    console.log(requestBody);
    await putRequest(`/admin/drinks/${drinkId}`, requestBody);
    await renderDrinksTable(drinks);
  } else {
    if (document.querySelector("#drinksTable tbody tr .updateButton")) {
      alert("동시에 두 개의 행을 수정할 수  없습니다.")
      return;
    }

    $parentRow.innerHTML = drinkTableColumns(drink, "update");
  }
}

function getDrinkDetailWithoutId($row) {
  const name = $row.querySelector(".drinkName input").value
  const englishName = $row.querySelector(".drinkEnglishName input").value
  const alcoholByVolume = $row.querySelector(".drinkAbv input").value
  const imagePath = $row.querySelector(".drinkImageFilePath input").value
  const category = $row.querySelector(".drinkCategory input").value

  //Todo : Category 정책 정해지면 category 하드코딩 수정
  return {
    name: name,
    englishName: englishName,
    alcoholByVolume: alcoholByVolume,
    imagePath: imagePath,
    category: {id: 1, name: "맥주"}
  }
}

async function drinkTableDeleteEvent(event) {
  if (!event.target.classList.contains("deleteButton")) {
    return;
  }

  const $parentRow = event.target.closest(".drinkTableRow");
  const drinkId = $parentRow.querySelector(".drinkId").innerText;

  await deleteRequest(`/admin/drinks/${drinkId}`);
  renderDrinksTable();
}

async function batchInsertEvent(event) {
  if (!event.target.classList.contains("saveButton")) {
    return;
  }

  const $drinkRows = document.querySelectorAll(
      "#batchInsert tbody .drinkTableRow");
  console.log($drinkRows);
  console.log($drinkRows);

  //convert LodeList to Array
  const notValidRequestBodies = Array.prototype.slice.call($drinkRows).map(
      $row => getDrinkDetailWithoutId($row))
  const requestBodies = notValidRequestBodies.filter(
      request => validDrinkRequest(request))

  console.log("batchInsertEvent ::: requestBodies")
  console.log(requestBodies)
  //api call

  const spinner = document.querySelector(".batchInsertSpinner")
  spinner.classList.remove("hide");
  await postRequest(`/admin/drinks`, requestBodies)
  spinner.classList.add("hide");
  document.querySelectorAll("#batchInsert .drinkTableRow td input")
    .forEach(input => input.value = "");
  await renderDrinksTable();

}

function validDrinkRequest(request) {
  const requireNonNull = [
    request.category,
    request.name,
    request.imagePath,
    request.alcoholByVolume
  ]

  let isNullOrEmptyFlag = true;

  requireNonNull.forEach(ele => {
    if (isNullOrEmpty(ele)) {
      isNullOrEmptyFlag = false;
    }
  })

  return isNullOrEmptyFlag;
}

function isNullOrEmpty(object) {
  return object === undefined || object === null || object === ""
}

