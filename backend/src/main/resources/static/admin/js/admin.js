import drinkTableRow from "./drinks/component/drinkTableRow.js";
import {
  getRequest,
  putRequest,
  postRequest,
  deleteRequest
} from "./utils/methodFetches.js";
import drinkTableColumns from "./drinks/component/drinkTableColumns.js";

const $drinksTable = document.querySelector("#drinksTable");
const testData = {
  data: [{
    id: "1000",
    name: "나봄마시면한방에가는술1",
    imageUrl: "대충이미지",
    englishName: "nabomKiierBOOM1",
    category: {
      id: "1",
      name: "nabomKiller"
    },
    alcoholByVolume: 99.9
  }, {
    id: "1001",
    name: "나봄마시면한방에가는술2",
    imageUrl: "대충이미지",
    englishName: "nabomKiierBOOM2",
    category: {
      id: "1",
      name: "nabomKiller"
    },
    alcoholByVolume: 99.9
  }, {
    id: "1002",
    name: "나봄마시면한방에가는술3",
    imageUrl: "대충이미지",
    englishName: "nabomKiierBOOM3",
    category: {
      id: "1",
      name: "nabomKiller"
    },
    alcoholByVolume: 99.9
  }, {
    id: "1003",
    name: "나봄마시면한방에가는술4",
    imageUrl: "대충이미지",
    englishName: "nabomKiierBOOM4",
    category: {
      id: "1",
      name: "nabomKiller"
    },
    alcoholByVolume: 99.9
  }
  ],
  pageInfo: {
    currentPage: 1,
    lastPage: 1,
    countPerPage: 50
  }
}

let drinkStore = testData.data;

window.onload = (async () => {
  console.log("admin.js :: onload")
  renderDrinksTable();
  addEditButtonsEvent();

  const response = await getRequest("/admin/drinks")
  if (response.data) {
    drinkStore = response.data
    renderDrinksTable()
  }
})

// test data loading

function renderDrinksTable(data = drinkStore) {
  console.log('admin.js :: renderDrinksTable() :: parameter');
  console.log(data);
  console.log('============================================');
  $drinksTable.querySelector('tbody').innerHTML = '';
  data.forEach((element) => {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('afterbegin',
        drinkTableRow(element)
    )
  })
}

function addEditButtonsEvent() {
  const $tableBody = document.querySelector(
      "main #drinksTable tbody");

  $tableBody.addEventListener('click',
      event => drinkTableUpdateEvent(event)
  )

  $tableBody.addEventListener('click',
      event => drinkTableDeleteEvent(event)
  )
}

function drinkTableUpdateEvent(event) {
  console.log("asd")
  console.log(event.target.classList)
  if (!(event.target.classList.contains("updateButton")
      || event.target.classList.contains("normalButton"))) {
    return;
  }

  // update API call

  const $parentRow = event.target.closest(".drinkTableRow");
  const drinkId = $parentRow.querySelector(".drinkId").innerText;
  const drink = drinkStore.find(ele => ele.id === drinkId);

  console.log("drinkTableUpdateEvent ::: drink")
  console.log(drink)

  if (event.target.classList.contains("updateButton")) {
    $parentRow.innerHTML = drinkTableColumns(drink);
  } else {
    $parentRow.innerHTML = drinkTableColumns(drink, "update");
  }
}

function drinkTableDeleteEvent(event) {
  if (!event.target.classList.contains("deleteButton")) {
    return;
  }

  // delete API call

  const $parentRow = event.target.closest(".drinkTableRow");
  const drinkId = $parentRow.querySelector(".drinkId").innerText;
  drinkStore.splice(drinkStore.findIndex(ele => ele.id === drinkId), 1);
  console.log("drinkTableUpdateEvent ::: drink id")
  console.log(drinkId)

  renderDrinksTable();
}


