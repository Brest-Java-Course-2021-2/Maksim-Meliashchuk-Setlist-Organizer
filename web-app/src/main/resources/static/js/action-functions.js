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