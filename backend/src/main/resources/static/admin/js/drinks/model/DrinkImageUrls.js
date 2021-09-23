export default class DrinkImageUrls {
  constructor(drinksResponse) {
    this.small = drinksResponse.smallImageFilePath;
    this.medium = drinksResponse.mediumImageFilePath;
    this.large = drinksResponse.largeImageFilePath;
  }

}