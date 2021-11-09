$(document).ready(function(){
    $(".up,.down,.top,.bottom").click(function(){
        var row = $(this).parents("tr:first");
        if ($(this).is(".up")) {
            row.insertBefore(row.prev());
        } else if ($(this).is(".down")) {
            row.insertAfter(row.next());
        } else if ($(this).is(".top")) {
            row.insertBefore($("table tr:first"));
        }else {
            row.insertAfter($("table tr:last"));
        }
    });
});