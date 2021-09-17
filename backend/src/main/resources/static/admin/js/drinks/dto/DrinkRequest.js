export default class DrinkRequest {
  constructor(id, name, englishName, alcoholByVolume, imageFile, description, categoryKey) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.alcoholByVolume = alcoholByVolume;
    this.imageFile = imageFile;
    this.description = description;
    this.categoryKey = categoryKey

    this.formData = new FormData();

    this.formData.append('name', name)
    this.formData.append('englishName', englishName)
    this.formData.append('alcoholByVolume', alcoholByVolume)
    this.formData.append("description", description)
    this.formData.append("categoryKey", categoryKey)
    this.formData.append('image', imageFile[0])
  }

  isValidProperties() {
    return ![this.name, this.imageFile, this.alcoholByVolume, this.categoryKey, this.description]
      .every((property) => !isNullOrEmpty(property));
  }

  getFormData(){
    return this.formData;
  }
}

function isNullOrEmpty(object) {
  return object === undefined || object === null || object === ""
}
