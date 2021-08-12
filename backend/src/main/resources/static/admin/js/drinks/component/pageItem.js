export default (pageText, destination) => {
  return `<li class="page-item"><a class="page-link" href="?page=${destination}">${pageText}</a></li>`
}
