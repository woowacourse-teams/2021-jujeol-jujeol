import {postRequest} from "../../utils/methodFetches.js";
import {$$} from "../../utils/querySelector.js";
import {renderDrinksTable, getDrinkDetail} from "../view/drinkTable.js";
import {isEmptyObject} from "../../utils/validator.js";

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
  // NodeList to Array
  const notValidRequestBodies = Array.prototype.slice.call(nodeList).map(
      $row => getDrinkDetail($row))

  return notValidRequestBodies.filter(ele => !isEmptyObject(ele));
}

async function showSpinnerUntilSuccessRequest(requestBodies){
  const spinner = document.querySelector(".batchInsertSpinner")
  spinner.classList.remove("hide");
  await postRequest(`/admin/drinks`, requestBodies)
  spinner.classList.add("hide");
}

function clearBatchInsertTable(){
  $$("#batchInsert .drinkTableRow td input")
  .forEach(input => input.value = "");
}