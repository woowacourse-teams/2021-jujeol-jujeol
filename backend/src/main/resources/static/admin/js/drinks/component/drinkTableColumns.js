import drinkUpdateButton from "./drinkUpdateButton.js";

export default function (drink, state) {
  const component = (text, state) => {
    if(state === 'update'){
      return `<input className="form-control form-control-sm" placeholder="${text}" type="text">`
    }
    return text;
  }
  console.log("update Button ::: state");
  console.log(state);

  return `<td class="col-1 drinkId">${drink.id}</td>
          <td class="col-2 drinkName">${component(drink.name, state)}</td>
          <td class="col-2 drinkEnglishName">${component(drink.englishName, state)}</td>
          <td class="col-1 drinkAbv">${component(drink.alcoholByVolume, state)}</td>
          <td class="col-3 drinkImageFilePath">${component(drink.imageUrl, state)}</td>
          <td class="col-1 drinkCategory">${component(drink.category.name, state)}</td>
          <td class="col-1 updateButtonWrapper text-center">
              ${drinkUpdateButton(state)}
          </td>
          <td class="col-1 deleteButtonWrapper text-center">
            <button type="button" class="deleteButton btn btn-sm btn-outline-danger">삭제</button>
          </td>`
}