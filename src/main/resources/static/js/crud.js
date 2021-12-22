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