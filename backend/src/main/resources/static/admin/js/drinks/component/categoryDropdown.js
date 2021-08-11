export default function (categories) {
  return `<select class="categoryKey">
    ${categories.map(({ name, key}) => `<option value=${key}>${name}</option>`).join()}
  </select>`
}