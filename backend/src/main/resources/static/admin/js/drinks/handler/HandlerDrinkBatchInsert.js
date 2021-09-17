import {postRequest} from "../../utils/methodFetches.js";
import {$$} from "../../utils/querySelector.js";
import {getDrinkDetail, renderDrinksTable} from "../view/drinkTable.js";

export const HandlerDrinkBatchInsert = ({target}, drinks) => {
  batchInsertEvent(target, drinks)
}

async function batchInsertEvent(target,drinks) {
  if (!target.classList.contains("saveButton")) {
    return;
  }

  const $drinkRows = $$("#batchInsert tbody .drinkTableRow");
  const requestBodies = nodeListToValidRequest($drinkRows);
  await showSpinnerUntilSuccessRequest(requestBodies);
  clearBatchInsertTable();

  await renderDrinksTable(drinks);
}

function nodeListToValidRequest(nodeList){
  const notValidRequestBodies = Array.from(nodeList).map($row => getDrinkDetail($row));
  return notValidRequestBodies.filter(ele => !ele.isValidProperties());
}

async function showSpinnerUntilSuccessRequest(requestBodies){
  const spinner = document.querySelector(".batchInsertSpinner")
  spinner.classList.remove("hide");

  await postRequest(`/admin/drinks`, requestBodies);
  spinner.classList.add("hide");
}

function clearBatchInsertTable(){
  $$("#batchInsert .drinkTableRow td input")
  .forEach(input => input.value = "");
}