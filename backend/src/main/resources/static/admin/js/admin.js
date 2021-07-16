import drinkTableRow from "./drinks/component/drinkTableRow.js";
import {
  getRequest,
  putRequest,
  postRequest,
  deleteRequest
} from "./utils/methodFetches.js";

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

window.onload = (async () => {
  console.log("admin.js :: onload")
  //renderDrinksTable(testData.data);

  const response = await getRequest("/admin/drinks")
  if (response.data) {
    renderDrinksTable(response.data)
  }
})

// test data loading

function renderDrinksTable(data) {
  console.log('admin.js :: renderDrinksTable() :: parameter');
  console.log(data);
  console.log('============================================');
  data.forEach((element) => {
    $drinksTable.querySelector('tbody').insertAdjacentHTML('afterbegin',
        drinkTableRow(element, 'update')
    )
  })
}
