import categoryDropdown from './categoryDropdown.js';

export default (categories) => {
  return `<tr class="drinkTableRow">
      <td class="col-2 drinkName"><input class="form-control form-control-sm" placeholder="이름" type="text"></td>
      <td class="col-2 drinkEnglishName"><input class="form-control form-control-sm" placeholder="영어이름" type="text"></td>
      <td class="col-1 drinkAbv"><input class="form-control form-control-sm" placeholder="도수" type="text"></td>
      <td class="col-3 drinkImageFilePath"><input class="form-control form-control-sm" placeholder="이미지 파일 경로" type="file" alt="image-input" accept="image/png, image/jpeg"></td>
      <td class="col-3 drinkDescription"><textarea class="modal-description form-control form-control-sm" placeholder="상세 설명" type="text"></textarea></td>
      <td class="col-1 needValidate drinkCategory">${categoryDropdown(categories)}</td>
    </tr>`;
};
