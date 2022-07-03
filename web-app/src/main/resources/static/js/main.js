let sock = new SockJS('http://localhost:8099/ws');

let client = Stomp.over(sock);

client.connect({}, (frame) => {
	console.log("Frame is: " + frame);
	var path = window.location.pathname;
    var page = path.split("/").pop();
	client.subscribe('/topic/repertoire', (payload) => {
	    if (page == "bands" || page == "repertoire") {
                console.log("Reloaded page name is: " + page);
                updateContent();
            }
	    var e = document.getElementById("toastNotice");
        var toast = new bootstrap.Toast(e, option);
        var body = document.getElementById("t-body")
        body.innerHTML = payload.body
        toast.show();
	});
});

function updateContent()
{
    $("#reloaded-content").load(window.location.href + " #reloaded-content", function() {
        secondsToTime();
        dateFormat();
    });
}