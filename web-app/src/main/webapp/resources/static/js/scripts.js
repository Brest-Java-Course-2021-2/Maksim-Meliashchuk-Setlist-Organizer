function secondsToTime() {
    var array = document.getElementsByName("timeDuration");
    array.forEach(function(timeDuration) {
            var e = timeDuration.innerText;
            var h = Math.floor(e / 3600).toString().padStart(2,'0'),
                m = Math.floor(e % 3600 / 60).toString().padStart(2,'0'),
                s = Math.floor(e % 60).toString().padStart(2,'0');
            var time;
            h == '00' ? time = m + ':' + s : time = h + ':' + m + ':' + s;
            time == '00:00:00' || time == '00:00' ? timeDuration.innerText = '' : timeDuration.innerText = time;
    })
}

function durationToMinutesAndSeconds() {
    var e = document.getElementById("field_track_duration").value,
        m = Math.floor(e % 3600 / 60),
        s = Math.floor(e % 60);
    document.getElementById("track_duration_minutes").value = m;
    document.getElementById("track_duration_seconds").value = s;
}

function durationToSeconds() {
    var m = document.getElementById("track_duration_minutes").value;
    var s = document.getElementById("track_duration_seconds").value;
    var e = +m * 60 + +s;
    document.getElementById("field_track_duration").value = e
}


