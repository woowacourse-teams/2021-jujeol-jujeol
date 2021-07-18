export default class DrinkRequest {
  // Todo: category 추가되면 수정
  constructor(id, name, englishName, alcoholByVolume, imageUrl, category) {
    if (!validProperties([name, imageUrl, alcoholByVolume, category])){
      return false;
    }
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.category = {id: 1, name: "맥주"};
  }
}

function validProperties(nonNullList) {
  let isNullOrEmptyFlag = true;

  nonNullList.forEach(ele => {
    if (isNullOrEmpty(ele)) {
      isNullOrEmptyFlag = false;
    }
  })

  return isNullOrEmptyFlag;
}

function isNullOrEmpty(object) {
  return object === undefined || object === null || object === ""
}
