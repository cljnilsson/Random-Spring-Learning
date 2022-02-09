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
				<td class="text-end">
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
	console.log(Cookies.get("XSRF-TOKEN"))
	const response = await fetch(url, {
		method: "POST",
		cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
		headers: {
			"Content-Type": "application/json",
			"X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")
		},
		body: JSON.stringify(data),
	});
	return response;
}

$(".add-item").click(() => {
	let name = $("#todo-text").val();
	let done = $("#todo-done").is(":checked") ? "true" : "false";

	if(name !== "") {
		postData(`/api/add?name=${name}&done=${done}`);
		$("#todo-text").text("");
	}
	
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
	let row 	= $(e.target).closest("tr");
	let id 		= $(row).data("id");
	let name 	= $(row).find("td.name").text();
	let done 	= $(row).find("td.done").text();

	if (validateDone && validateId && validateName) {
		let t = await postData(
			`/api/update?name=${name}&done=${done}&id=${id}`
		);
	}
});

$.ajaxSetup({
	beforeSend : function(xhr, settings) {
		console.log(1)
		if(settings.type === "POST" || settings.type === "PUT" || settings.type === "DELETE") {
			console.log(2);
			if(!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
				console.log("happens")
				xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get("XSRF-TOKEN"));
			}
		}
	}
});

$.get("/auth/user", function(data) {
	$("#user").text(data.name);
	$("#avatar").attr("src", data.avatar);
});

function logout() {
    $.post("/logout", function() {
        location.reload();
    })
}