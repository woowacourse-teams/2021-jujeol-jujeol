import drinkUpdateButton from "./drinkUpdateButton.js";

export default function (drink, state) {
  const component = (text, state) => {
    if(state === 'update'){
      return `<input class="form-control form-control-sm" placeholder="${text}" type="text">`
    }
    return text;
  }
  console.log("update Button ::: state");
  console.log(state);

  //Todo : category 정책 정해지면 category의 ID와 name을 찾아서 보내도록 수정
  return `<td class="col-1 drinkId">${drink.id}</td>
          <td class="col-2 needValidate drinkName">${component(drink.name, state)}</td>
          <td class="col-2 drinkEnglishName">${component(drink.englishName, state)}</td>
          <td class="col-1 needValidate drinkAbv">${component(drink.alcoholByVolume, state)}</td>
          <td class="col-3 needValidate drinkImageFilePath">${component(drink.imageUrl, state)}</td>
          <td class="col-1 needValidate drinkCategory"><input type="hidden" class="drinkCategoryId">${component(drink.category.name, state)}</td>
          <td class="col-1 updateButtonWrapper text-center">
              ${drinkUpdateButton(state)}
          </td>
          <td class="col-1 deleteButtonWrapper text-center">
            <button type="button" class="deleteButton btn btn-sm btn-outline-danger">삭제</button>
          </td>`
}