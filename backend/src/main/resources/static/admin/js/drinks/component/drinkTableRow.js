import drinkUpdateButton from "./drinkUpdateButton.js";

export default function (drink, state) {
  console.log(drink);
  return `<tr class="drinkTableRow">
            <td class="drinkId">${drink.id}</td>
            <td class="drinkName">${drink.name}</td>
            <td class="drinkEnglishName">${drink.englishName}</td>
            <td class="drinkAbv">${drink.alcoholByVolume}</td>
            <td class="drinkImageFilePath">${drink.imageUrl}</td>
            <td class="drinkCategory">${drink.category.name}</td>
            <td class="col7 updateButton text-center">
                ${drinkUpdateButton(state)}
            </td>
            <td class="col8 deleteButton text-center">
              <button type="button" class="btn btn-sm btn-outline-danger">삭제</button>
            </td>
          </tr>`
}
