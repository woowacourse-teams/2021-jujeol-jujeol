export default class DrinkRequest {
  constructor(id, name, englishName, alcoholByVolume, imageUrl, categoryKey) {
    if (!validProperties([name, imageUrl, alcoholByVolume, categoryKey])){
      return false;
    }
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.categoryKey = categoryKey;
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
