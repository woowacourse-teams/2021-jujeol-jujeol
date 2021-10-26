import Drinks from './drinks/model/Drinks.js';
import { handlerDrinkUpdate } from './drinks/handler/HandlerDrinkUpdate.js';
import { handlerDrinkDelete } from './drinks/handler/HandlerDrinkDelete.js';
import { renderDrinksBatchInsertModal, renderDrinksTable } from './drinks/view/drinkTable.js';
import { HandlerDrinkBatchInsert } from './drinks/handler/HandlerDrinkBatchInsert.js';
import { $ } from './utils/querySelector.js';
import { ACCESS_TOKEN_KEY } from './constants.js';

const init = () => {
  const drinks = new Drinks();

  addEventListener(drinks);
  renderDrinksTable(drinks);
  renderDrinksBatchInsertModal();
};

window.addEventListener('DOMContentLoaded', init);

function addEventListener(drinks) {
  addDashboardEventListener(drinks);
  addBatchInsertModalEventListener(drinks);
}

function addDashboardEventListener(drinks) {
  const drinksTableBody = $('main #drinksTable tbody');

  drinksTableBody.addEventListener('click', (event) => handlerDrinkUpdate(event, drinks));

  drinksTableBody.addEventListener('click', (event) => handlerDrinkDelete(event, drinks));
}

function addBatchInsertModalEventListener(drinks) {
  const batchInsertFooter = $('.modal .modal-footer');

  batchInsertFooter.addEventListener('click', (event) => HandlerDrinkBatchInsert(event, drinks));
}

const $signOutButton = document.querySelector('#signout-button');

$signOutButton.addEventListener('click', () => {
  location.href = '/login.html';

  localStorage.removeItem(ACCESS_TOKEN_KEY);
});
