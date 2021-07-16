export default function (state) {
  console.log(state);
  const stateDetail = {
    update:{cssClass : 'btn-outline-success', text : '완료'},
    default:{cssClass : 'btn-outline-primary', text : '수정'}
  }

  const data = stateDetail[state] ? stateDetail[state] : stateDetail.default;
  console.log(data);
  return `<button type="button" 
                  class="btn btn-sm ${data.cssClass}
                  btn-outline-primary">${data.text}</button>`
}