export default class DrinkRequest {
  constructor(id, name, englishName, alcoholByVolume, imageUrl, description, categoryKey) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.description = description;
    this.categoryKey = categoryKey;
  }

  isValidProperties() {
    return ![this.name, this.imageUrl, this.alcoholByVolume, this.categoryKey, this.description]
      .every((property) => !isNullOrEmpty(property));
  }
}

function isNullOrEmpty(object) {
  return object === undefined || object === null || object === ""
}
