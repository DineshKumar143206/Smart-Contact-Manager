console.log("picture preview");

document
  .querySelector("#pic")
  .addEventListener("change", function (event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      document
        .querySelector("#preview")
        .setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);
  });
