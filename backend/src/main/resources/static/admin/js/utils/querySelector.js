export const $ = (selector) => {
  return document.querySelector(selector);
}

export const $$ = (selector) => {
  return document.querySelectorAll(selector);
}
