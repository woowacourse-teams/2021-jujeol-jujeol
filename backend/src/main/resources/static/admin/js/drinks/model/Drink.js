export default class Drink {
  // Todo : 카테고리 생성 시 리팩토링
  constructor(id, name, englishName = "", alcoholByVolume, imageUrl, category) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageUrl = imageUrl;
    this.category = {id: 1, name: "맥주"};
    this.onUpdate = false;
  }

  turnUpdateState = () => {
    this.onUpdate = true;
  }
}