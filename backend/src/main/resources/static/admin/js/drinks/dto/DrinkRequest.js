export default class DrinkRequest {
  constructor(id, name, englishName, alcoholByVolume, imageUrl, description, categoryKey) {
    if (!isValidProperties([name, imageUrl, alcoholByVolume, categoryKey, description])){
      throw new Error("요청 parameter 중 null이거나 빈 값이 있습니다.");
    }
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.description = description;
    this.categoryKey = categoryKey;
  }
}

function isValidProperties(properties) {
  return properties.every((property) => !isNullOrEmpty(property));
}

function isNullOrEmpty(object) {
  return object === undefined || object === null
}
