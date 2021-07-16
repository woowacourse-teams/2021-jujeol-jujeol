export default function (state) {
  console.log(state);
  const stateDetail = {
    update:{type : "updateButton", cssClass : "btn-outline-success", text : "완료"},
    default:{type : "normalButton", cssClass : "btn-outline-primary", text : "수정"}
  }

  const data = stateDetail[state] ? stateDetail[state] : stateDetail.default;
  
  return `<button type="button" 
                  class="${data.type} btn btn-sm ${data.cssClass}
                  btn-outline-primary">${data.text}</button>`
}