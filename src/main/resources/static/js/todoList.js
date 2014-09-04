function showTodos() {
    buildTodosTable();
    document.getElementById('todoList').style.display = 'block';
    document.getElementById('contact_form').style.display = 'none';
    document.getElementById('order_form').style.display = 'none';
    document.getElementById('showContactBtn').disabled = false;

    clearContactTable();
    document.getElementById('updateButton').disabled = true;
    document.getElementById('deleteButton').disabled = true;
    document.getElementById('saveButton').disabled = true;
    document.getElementById('showOrdersBtn').disabled = true;
    document.getElementById('showContactBtn').disabled = true;
    document.getElementById('showTodosBtn').disabled = true;

    displayLightbox();
}

function buildTodosTable() {

//    var contactId = document.getElementById("contactId").value;
//    if (contactId == -1) return;
//
    var data = requestTodos();

    var values = [];
    for (var index in data) {
        var todo = data[index];
        values[index] = [todo.name, todo.start, todo.end, todo.notes];
    }

    var titles = [['Name', false],['Start', false],['End', false],['Notes', false]];

    buildTable('todosTable', values, titles);
}

function requestTodos() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "../contacts/todo", false);
    xmlHttp.send();
    var json = "[{\"name\" : \"Fred\",\"date\" : \"01-02-2013\",\"end\" : \"01-02-2013\",\"notes\" : \"ABC\"}]"
    var jsonList = JSON.parse(json);
//    var jsonList = JSON.parse(xmlHttp.responseText);
    var todoList = [];
    for (var i in jsonList) {
        todoList[i] = newObject(jsonList[i]);
    }
    return todoList;
}

