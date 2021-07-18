import drinkTableColumns from "./drinkTableColumns.js";

export default (drink, state) => {
  return `<tr class="drinkTableRow ${state}">
              ${drinkTableColumns(drink, state)}
          </tr>`
}
