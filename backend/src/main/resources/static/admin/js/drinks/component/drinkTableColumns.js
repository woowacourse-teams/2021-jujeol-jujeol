import drinkUpdateButton from "./drinkUpdateButton.js";
import categoryDropdown from "./categoryDropdown.js";

const component = (text, state) => {
  if(state === 'update'){
    return `<input class="form-control form-control-sm" placeholder="${text}" value="${text}" type="text">`
  }
  return `<p>${text}</p>`;
}

const textarea = (text, state) => {
  if(state === 'update'){
    return `<textarea class="form-control form-control-sm" placeholder="${text}" type="text">${text}</textarea>`
  }
  return `<pre>${text}</pre>`;
}

const categoryComponent = (drink, state, categories) => {
  if(state === 'update'){
    return categoryDropdown(categories, drink.category)
  }
  return drink.category.name;
}

export default function (drink, state, categories) {
  return `<td class="drinkId"><pre>${drink.id}</pre></td>
          <td class="needValidate drinkName">${component(drink.name, state)}</td>
          <td class="drinkEnglishName">${component(drink.englishName, state)}</td>
          <td class="needValidate drinkAbv">${component(drink.alcoholByVolume, state)}</td>
          <td class="needValidate drinkImageFilePath">${component(drink.imageUrl, state)}</td>
          <td class="needValidate drinkDescription">${textarea(drink.description, state)}</td>
          <td class="needValidate drinkCategory">${categoryComponent(drink, state, categories)}</td>
          <td class="updateButtonWrapper text-center">
              ${drinkUpdateButton(state)}
          </td>
          <td class="deleteButtonWrapper text-center">
            <button type="button" class="deleteButton btn btn-sm btn-outline-danger">삭제</button>
          </td>`
}
