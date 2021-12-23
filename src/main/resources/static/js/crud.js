async function postData(url = '', data = {}) {
  const response = await fetch(url, {
    method: 'POST',
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
}

$(".add-item").click(() =>  {
    console.log("baaaaaaaaaaa");
    postData(`/api/add?name=test data&done=false`);
});

$(".del-item").click(() =>  {
    console.log("baaaaaaaaaaa2");
});

function validateId(id) {
    return !isNaN(id) && id >= 0;
}

function validateName(name) {
    return name !== ""; // Should probably do a more detailed check with min length + any number of whitespace
}

function validateDone(done) {
    return done === "false" || done === "true";
}

$(".save-item").click((e) =>  {
    let id   =  $(e.target).closest("tr").data("id");
    let name = $(e.target).closest("tr").find("td.name > span").text();
    let done = $(e.target).closest("tr").find("td.done > span").text();

    if(validateDone && validateId && validateName) {
        postData(`/api/update?name=${name}&done=${done}&id=${id}`);
    }
});