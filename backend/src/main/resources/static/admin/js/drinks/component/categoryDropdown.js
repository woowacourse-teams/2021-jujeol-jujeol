export default function (categories, category = { key : false }) {
  return `<select class="categoryKey">
    ${categories.map(({ name, key }) => `<option value=${key}${ category.key === key ? ' selected' : ''}>${name}</option>`).join('')}
  </select>`
}