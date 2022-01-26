 let sock = new SockJS('/portfolio');

let stomp = Stomp.over(sock);
stomp.connect({}, function (frame) {
  console.log('Connected: ' + frame);
  stomp.subscribe('/topic/greeting', function (greeting) {
      console.log(JSON.parse(greeting.body).content);
  });
  stomp.send("/app/greeting", {}, JSON.stringify({'name': "Lukas"}));
});

async function postData(url = '', data = {}) {
  const response = await fetch(url, {
    method: 'POST',
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
  return response;
}

$(".add-item").click(() =>  {
    postData(`/api/add?name=test data&done=false`);
});

$(".del-item").click(async (e) =>  {
    let id  = $(e.target).closest("tr").data("id");
    let t   = await postData(`/api/delete?id=${id}`);
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

$(".save-item").click(async (e) =>  {
    let id   = $(e.target).closest("tr").data("id");
    let name = $(e.target).closest("tr").find("td.name > span").text();
    let done = $(e.target).closest("tr").find("td.done > span").text();

    if(validateDone && validateId && validateName) {
        let t = await postData(`/api/update?name=${name}&done=${done}&id=${id}`);
        console.log(t);
    }
});