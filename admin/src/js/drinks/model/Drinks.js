export default class Drinks {
  constructor() {
    this.drinks = [];
  }

  findById(id){
    return this.drinks.find(ele => ele.id === Number(id));
  }

  anyOnUpdate(){
    return this.drinks.findIndex(ele => ele.onUpdate === true) !== -1
  }

  getDrinks() {
    return this.drinks;
  }

  setDrinks(drinks) {
    this.drinks = drinks;
  }
}
