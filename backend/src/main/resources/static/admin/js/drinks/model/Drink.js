export default class Drink {
  constructor(id, name, englishName = "", alcoholByVolume, imageUrl, category) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.category = category;
    this.onUpdate = false;
  }

  turnUpdateState = () => {
    this.onUpdate = true;
  }
}