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

    const body = JSON.stringify({
      name: name,
      englishName: englishName,
      alcoholByVclume: alcoholByVolume,
      description: description,
      categoryKey: categoryKey,
    })

    this.formData.append('body', body)
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
