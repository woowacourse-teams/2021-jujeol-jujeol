import {getRequest, putRequestWithFormData} from "../../utils/methodFetches.js";
import drinkTableColumns from "../component/drinkTableColumns.js";
import {getDrinkDetail, renderDrinksTable} from "../view/drinkTable.js";
import {isEmptyObject} from "../../utils/validator.js";

export const handlerDrinkUpdate = ({target}, drinks) => {
  const drinkId = target.closest(".drinkTableRow").querySelector(".drinkId").innerText;
  const drink = drinks.findById(drinkId);

  invokeInputModeEvent(target, drinks, drink)
  drinkUpdateEvent(target, drinks, drink)
}

async function invokeInputModeEvent(target, drinks, drink) {
  if (!target.classList.contains("normalButton")) {
    return;
  }

  if (drinks.anyOnUpdate()) {
    alert("동시에 두 개의 행을 수정할 수 없습니다.")
    return;
  }
  drink.turnUpdateState();

  const $parentRow = target.closest(".drinkTableRow");
  const result = await getRequest('/categories');
  $parentRow.innerHTML = drinkTableColumns(drink, "update", result.data);
}

async function drinkUpdateEvent(target, drinks, drink) {
  if (!target.classList.contains("updateButton")) {
    return;
  }

  const $parentRow = target.closest(".drinkTableRow");
  const requestBody = getDrinkDetail($parentRow, drink.id);

  if (isEmptyObject(requestBody)) {
    alert("필수 입력 항목을 채워주세요.");
    return;
  }

  await putRequestWithFormData(`/admin/drinks/${drink.id}`, [requestBody]);
  renderDrinksTable(drinks);
}
