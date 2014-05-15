
function buildOrderTable() {
    var data = requestOrders(document.getElementById("contactId").value);

    var fragment = document.createDocumentFragment(), tr;

    var values = [];
    for (var index in data) {
        var order = data[index];
        values[index] = [order.reference, order.category, order.name, order.day + "/" + order.month + "/" + order.year];
    }

    for (var index in data) {
        tr = document.createElement('tr');
        tr.setAttribute("data-index", index);
        var order = data[index];
        buildRow(tr, order.reference);
        buildRow(tr, order.category);
        buildRow(tr, order.name);
        buildRow(tr, order.day + "/" + order.month + "/" + order.year);
        fragment.appendChild(tr);
    }
    var body = document.getElementsByTagName('tbody')[0];
    var range = new Range();
    range.selectNodeContents(body);
    range.deleteContents();
    body.appendChild(fragment.cloneNode(true));
}

function sortOrderTable(val1, val2, direction){
    contacts.sort(function(x,y) {
        return direction * x[val1][val2].localeCompare(y[val1][val2]);
    });
    buildOrderTable();
}

// Order functions

function saveOrder() {
    if (validateOrder()) {

        var fields = ["year","month","day","reference","category","name","description"];
        var data = "contactId=-1";
        for (var i in fields) {
            data += "&" + fields[i] + "=" + document.getElementById(fields[i]).value;
        }
        save(data);
    }
}

function deleteOrder() {
    var len = contacts.length;
    var selectedId = parseInt(document.getElementById("contactId").value);
    for (var i=0; i < len; i++) {
        if (contacts[i].id == selectedId) {
            contacts.splice(i, 1);
            break;
        }
    }
    buildTable();
}

function validateOrder() {
    if (document.getElementById("reference").value == "") return false;
    if (document.getElementById("category").value == "") return false;
    if (document.getElementById("name").value == "") return false;
    if (document.getElementById("description").value == "") return false;
    return true;
}

// AJAX

function requestOrders(id) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8787/contacts/" + id + "/orders", false);
    xmlHttp.send();
    var jsonList = JSON.parse(xmlHttp.responseText);
    var orderList = [];
    for (var i in jsonList) {
        orderList[i] = newOrder(jsonList[i]);
    }
    return orderList;
}

function save(data) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "http://localhost:8787/contacts", false);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(data);
}

function newOrder(obj) {
    return Object.create(obj);
}

function init() {
    tableSelect();
//    buildTestTable();
    requestContacts();
    buildTable();
}