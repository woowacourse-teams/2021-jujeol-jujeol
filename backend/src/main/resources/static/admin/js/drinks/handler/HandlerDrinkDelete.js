import {deleteRequest} from "../../utils/methodFetches.js";
import {renderDrinksTable} from "../view/drinkTable.js";

export const handlerDrinkDelete = ({target}, drinks) => {
  drinkTableDeleteEvent(target, drinks);
}

async function drinkTableDeleteEvent(target, drinks) {
  if (!target.classList.contains("deleteButton")) {
    return;
  }

  const $parentRow = target.closest(".drinkTableRow");
  const drinkId = $parentRow.querySelector(".drinkId").innerText;

  await deleteRequest(`/admin/drinks/${drinkId}`);
  renderDrinksTable(drinks);
}