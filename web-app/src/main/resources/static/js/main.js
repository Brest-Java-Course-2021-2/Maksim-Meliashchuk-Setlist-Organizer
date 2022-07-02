let sock = new SockJS('http://localhost:8099/ws');

let client = Stomp.over(sock);

client.connect({}, (frame) => {
	console.log("Frame is: " + frame);
	client.subscribe('/topic/repertoire', (payload) => {
	var e = document.getElementById("toastNotice");
    var toast = new bootstrap.Toast(e, option);
    var body = document.getElementById("t-body")
    body.innerHTML = payload.body
    toast.show();
	});
});