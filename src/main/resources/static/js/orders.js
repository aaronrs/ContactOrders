function buildProductDropdown() {
    var data = requestProducts();

    var dropdown = document.getElementById("productOptions");
    var opt = document.createElement('option');
    opt.value = -1;
    opt.innerHTML = "Select Product";
    dropdown.appendChild(opt);
    for (var index in data) {

        opt = document.createElement('option');
        var product = data[index];

        opt.value = product.id;
        opt.innerHTML = product.name;
        dropdown.appendChild(opt);
    }
}

function buildOrderTable() {

    var contactId = document.getElementById("contactId").value;
    if (contactId == -1) return;

    var data = requestOrders(contactId);

    var values = [];
    for (var index in data) {
        var order = data[index];
        values[index] = [order.name, order.description.substring(0, 20) + " ...", order.category, order.day + "/" + order.month + "/" + order.year, order.amount];
    }

    var titles = [['Name', false],['Description', false],['Category', false],['Date', true],['Amount', false]];

    buildTable('ordersTable', values, titles);
}

function sortOrderTable(val1, val2, direction){
    contacts.sort(function(x,y) {
        return direction * x[val1][val2].localeCompare(y[val1][val2]);
    });
    buildOrderTable();
}

// Order functions

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
// AJAX

function saveOrder() {
    var select = document.getElementById("productOptions");
    var productId = select.options[select.selectedIndex].value;
    if (productId == -1) return;
    var date = document.getElementById("orderDate").valueAsDate;
    var data = "productId=" + productId +
        "&year=" + date.getFullYear() +
        "&month=" + date.getMonth() +
        "&day=" + date.getDate() +
        "&amount=" + document.getElementById("orderAmount").value +
        "";
    var id = document.getElementById("contactId").value;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "../contacts/" + id + "/orders", false);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(data);
    buildOrderTable();
}

function requestOrders(id) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "../contacts/" + id + "/orders", false);
    xmlHttp.send();
    var jsonList = JSON.parse(xmlHttp.responseText);
    var orderList = [];
    for (var i in jsonList) {
        orderList[i] = newObject(jsonList[i]);
    }
    return orderList;
}

function requestProducts() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "../contacts/products", false);
    xmlHttp.send();
    var jsonList = JSON.parse(xmlHttp.responseText);
    var productList = [];
    for (var i in jsonList) {
        productList[i] = newObject(jsonList[i]);
    }
    return productList;
}

function newObject(obj) {
    return Object.create(obj);
}

function init() {
    tableSelect();
//    buildTestTable();
    requestContacts();
    buildTable();
}