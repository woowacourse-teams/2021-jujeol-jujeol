export default class Drink {
  constructor(id, name, englishName = "", alcoholByVolume, imageUrl, description, category) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.description = description;
    this.category = category;
    this.onUpdate = false;
  }

  turnUpdateState = () => {
    this.onUpdate = true;
  }
}