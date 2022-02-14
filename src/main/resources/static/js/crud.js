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

function error(code = 0) {
	let msg = "";
	switch(code) {
		case 200: // Do nothing
			break;
		case 403:
			msg = "You probably don't have the authority to do that.";
			break;
		case 404:
			msg = "Invalid URL.";
			break;
		default:
			msg = "Unknown error";
			break;
	}

	let parent = $(".alert-danger");

	if(!parent.length) {
		$("h3").after(`<div class="alert alert-danger" role="alert"></div>`);
	}

	$(".alert-danger").text(msg);
}

async function postData(url = "", data = {}) {
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

$(".add-item").click(async () => {
	let name = $("#todo-text").val();
	let done = $("#todo-done").is(":checked") ? "true" : "false";

	if(name !== "") {
		let t = await postData(`/api/add?name=${name}&done=${done}`);
		if (t.status === 200) {
			$("#todo-text").text("");
		} else {
			error(t.status);
		}
	}
	
});

$("tbody").on("click", ".del-item", async (e) => {
	let id = $(e.target).closest("tr").data("id");
	let t = await postData(`/api/delete?id=${id}`);

	if (t.status === 200) {
		// Display error or something if this is not the case
	} else {
		error(t.status);
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

		if (t.status === 200) {
			// Display error or something if this is not the case
		} else {
			error(t.status);
		}
	}
});

$.ajaxSetup({
	beforeSend : function(xhr, settings) {
		if(settings.type === "POST" || settings.type === "PUT" || settings.type === "DELETE") {
			if(!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
				console.log("happens")
				xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get("XSRF-TOKEN"));
			}
		}
	}
});

$.get("/auth/user", function(data) {
	console.log(data);
	$("#user").text(data.name);
	$("#avatar").attr("src", data.avatar);
}).fail(function() {
	$(".alert-danger").value("reeeeeeeeeeeeee");
});

function logout() {
    $.post("/logout", function() {
        location.reload();
    })
}