function secondsToTime() {
    var array = document.getElementsByName("timeDuration");
    array.forEach(function(timeDuration) {
            var e = Math.floor(timeDuration.innerText / 1000);
            var h = Math.floor(e / 3600).toString().padStart(2,'0'),
                m = Math.floor(e % 3600 / 60).toString().padStart(2,'0'),
                s = Math.floor(e % 60).toString().padStart(2,'0');
            var time;
            h == '00' ? time = m + ':' + s : time = h + ':' + m + ':' + s;
            time == '00:00:00' || time == '00:00' ? timeDuration.innerText = '' : timeDuration.innerText = time;
    })
}

function durationToMinutesAndSeconds() {
    var e = Math.floor(document.getElementById("field_track_duration").value / 1000),
        m = Math.floor(e % 3600 / 60),
        s = Math.floor(e % 60);
    if (m > 0) {
        document.getElementById("track_duration_minutes").value = m;
    }
    if (s > 0) {
       document.getElementById("track_duration_seconds").value = s;
    }
}

function durationToSeconds() {
    var m = Math.abs(document.getElementById("track_duration_minutes").value);
    var s = Math.abs(document.getElementById("track_duration_seconds").value);
    var e = (+m * 60 + +s) * 1000;
    document.getElementById("field_track_duration").value = e
}

function dateFormat() {
    var options = {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
        };
    var array = document.getElementsByName("releaseDate");
    array.forEach(function(releaseDate) {
    if (releaseDate.innerText != '') {
        releaseDate.innerText = new Date(releaseDate.innerText).toLocaleString("en", options);
    }
    })
}

function actionOnSubmitExportExcel()
{
    var e = document.getElementById("dataBaseTablesExportExcel");
    var formAction = e.options[e.selectedIndex].value;
    document.exportExcel.action = formAction;
}

function actionOnSubmitImportExcel()
{
    var e = document.getElementById("dataBaseTablesImportExcel");
    var formAction = e.options[e.selectedIndex].value;
    document.importExcel.action = formAction;
}

function actionOnSubmitExportXml()
{
    var e = document.getElementById("dataBaseTablesExportXml");
    var formAction = e.options[e.selectedIndex].value;
    document.exportXml.action = formAction;
}


