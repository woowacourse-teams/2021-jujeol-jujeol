import DrinkImageUrls from "./DrinkImageUrls.js";

export default class Drink {
  constructor(id, name, englishName = "", alcoholByVolume, imageUrls, description, category) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrls = new DrinkImageUrls(imageUrls);
    this.description = description;
    this.category = category;
    this.onUpdate = false;
  }

  turnUpdateState = () => {
    this.onUpdate = true;
  }
}