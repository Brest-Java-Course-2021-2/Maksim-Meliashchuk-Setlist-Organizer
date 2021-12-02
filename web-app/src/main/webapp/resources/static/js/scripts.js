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
