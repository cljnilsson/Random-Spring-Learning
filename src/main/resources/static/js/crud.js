let sock = new SockJS("/portfolio");
let stomp = Stomp.over(sock);

stomp.connect({}, function (frame) {
	console.log("Connected: " + frame);

	stomp.subscribe("/todo/greeting", function (greeting) {
		console.log(JSON.parse(greeting.body).content);
	});

	stomp.subscribe("/todo/sub/delete", function (greeting) {
		let id = JSON.parse(greeting.body).id;
		let found = $(`tr[data-id=${id}]`);
		found.remove();
	});

	stomp.subscribe("/todo/sub/add", function (greeting) {
		let added = JSON.parse(greeting.body);
		let body = $("tbody");
		$(body).append(`
			<tr data-id="${added.id}">
				<td class="align-middle name" contenteditable="true">${added.name}</td>
				<td class="align-middle done" contenteditable="true">${added.done}</td>
				<td>
					<button class="btn btn-large btn-primary del-item">-</button>
					<button class="btn btn-large btn-primary save-item">save</button>
				</td>
			</tr>
		`);
	});

	stomp.subscribe("/todo/sub/update", function (greeting) {
		let body = JSON.parse(greeting.body);
		let id = body.id;
		let found = $(`tr[data-id=${id}]`);
		console.log(found);
		$(found).find(".name").text(body.name);
		$(found).find(".done").text(body.done);
	});

	stomp.send("/app/greeting", {}, JSON.stringify({ name: "Lukas" }));
});

async function postData(url = "", data = {}) {
	const response = await fetch(url, {
		method: "POST",
		cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data),
	});
	return response;
}

$(".add-item").click(() => {
	postData(`/api/add?name=test data&done=false`);
});

$("tbody").on("click", ".del-item", async (e) => {
	let id = $(e.target).closest("tr").data("id");
	let t = await postData(`/api/delete?id=${id}`);

	if (t.status === 200) {
		// Display error or something if this is not the case
	}
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

$("tbody").on("click", ".save-item", async (e) => {
	let id = $(e.target).closest("tr").data("id");
	let name = $(e.target).closest("tr").find("td.name").text();
	console.log($(e.target).closest("tr"))
	let done = $(e.target).closest("tr").find("td.done").text();
	console.log(done);

	if (validateDone && validateId && validateName) {
		let t = await postData(
			`/api/update?name=${name}&done=${done}&id=${id}`
		);
	}
});
