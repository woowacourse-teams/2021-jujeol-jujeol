import drinkUpdateButton from "./drinkUpdateButton.js";
import categoryDropdown from "./categoryDropdown.js";

const component = (text, state) => {
  if(state === 'update'){
    return `<input class="form-control form-control-sm" placeholder="${text}" value="${text}" type="text">`
  }
  return text;
}

const categoryComponent = (text, state, categories) => {
  if(state === 'update'){
    return categoryDropdown(categories)
  }

  return text;
}

export default function (drink, state, categories) {
  return `<td class="col-1 drinkId">${drink.id}</td>
          <td class="col-2 needValidate drinkName">${component(drink.name, state)}</td>
          <td class="col-2 drinkEnglishName">${component(drink.englishName, state)}</td>
          <td class="col-1 needValidate drinkAbv">${component(drink.alcoholByVolume, state)}</td>
          <td class="col-3 needValidate drinkImageFilePath">${component(drink.imageUrl, state)}</td>
          <td class="col-1 needValidate drinkCategory">${categoryComponent(drink.category.name, state, categories)}</td>
          <td class="col-1 updateButtonWrapper text-center">
              ${drinkUpdateButton(state)}
          </td>
          <td class="col-1 deleteButtonWrapper text-center">
            <button type="button" class="deleteButton btn btn-sm btn-outline-danger">삭제</button>
          </td>`
}