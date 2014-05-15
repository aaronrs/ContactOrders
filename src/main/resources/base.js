function buildTable(name, values, titles) {
    var header = document.getElementsByTagName('thead')[0];
    createHeader(header, titles);
    var bodyFragment = document.createDocumentFragment(), tr;

    for (var index in values) {
        tr = document.createElement('tr');
        tr.setAttribute("data-index", index);
        for (var row in values[index]) {
            buildRow(tr, values[index][row]);
        }
        bodyFragment.appendChild(tr);
    }
    var body = document.getElementsByTagName('tbody')[0];
    var range = new Range();
    range.selectNodeContents(body);
    range.deleteContents();
    body.appendChild(bodyFragment.cloneNode(true));
}

function createHeader(header, titles) {
    var fragment = document.createDocumentFragment();
    var tr = document.createElement('tr');

    for (var index in titles) {
        createHeaderTitle(tr, titles[index][0], titles[index][1]);
    }
    fragment.appendChild(tr);

    var range = new Range();
    range.selectNodeContents(header);
    range.deleteContents();
    header.appendChild(fragment.cloneNode(true));
}

function createHeaderTitle(tr, name, sort) {
    var th = document.createElement('th');
    if (sort) {
        var sortDesc = document.createTextNode('\u25BC');
        var span1 = document.createElement('span');
        span1.setAttribute("direction", "1");
        span1.setAttribute("sortName", 'sort' + name);
        span1.appendChild(sortDesc);
        th.appendChild(span1);
    }
    var newChild2 = document.createTextNode(name);
    th.appendChild(newChild2);

    if (sort) {
        var sortAsc = document.createTextNode('\u25B2');
        var span2 = document.createElement('span');
        span2.appendChild(sortAsc);
        span2.setAttribute("direction", "-1");
        span2.setAttribute("sortName", 'sort' + name);
        th.appendChild(span2);
    }

    tr.appendChild(th);
}

function buildRow(tr, value) {
    var td = document.createElement('td');
    td.appendChild(document.createTextNode(value));
    tr.appendChild(td);
}

function buildAddRow(row) {
    row.setAttribute("data-index", '-1');
    var td = document.createElement('td');
    td.colSpan = 3;
    td.appendChild(document.createTextNode("-> Add Contact"));
    row.appendChild(td);
    return row;
}

function sortContactTable(val, direction) {
    contacts.sort(function (x, y) {
        return direction * x[val].localeCompare(y[val]);
    });
    buildContactTable();
}

function tableSelect() {
    document.getElementById("mytable").onclick = function (e) {

        var target = e.target;
        if (target.nodeName == "SPAN") {
            sortContactTable(target.getAttribute('sortName'), target.getAttribute('direction'))
        } else {
            document.getElementById('contact_form').style.display = 'block';
            document.getElementById('order_form').style.display = 'none';
            document.getElementById('showContactBtn').disabled = true;
            var index = target.parentNode.getAttribute("data-index");
            if (index >= 0) {
                contactTable(contacts[index]);
                document.getElementById('updateButton').disabled = false;
                document.getElementById('deleteButton').disabled = false;
                document.getElementById('saveButton').disabled = true;
                document.getElementById('showOrdersBtn').disabled = false;
            } else {
                clearContactTable();
                document.getElementById('updateButton').disabled = true;
                document.getElementById('deleteButton').disabled = true;
                document.getElementById('saveButton').disabled = false;
                document.getElementById('showOrdersBtn').disabled = true;
            }
            displayLightbox();
        }
    };
}

// lightbox 1

function displayLightbox() {
    document.getElementById('light').style.display = 'block';
    document.getElementById('fade').style.display = 'block';
}
function closeLightbox() {
    document.getElementById('light').style.display = 'none';
    document.getElementById('fade').style.display = 'none';
}

function showOrderForm() {
    buildOrderTable();
    tabDisplay('order_form', 'contact_form', 'showOrdersBtn', 'showContactBtn');
}

function tabDisplay(tab1, tab2, btn1, btn2) {
    document.getElementById(tab1).style.display = 'block';
    document.getElementById(tab2).style.display = 'none';
    document.getElementById(btn1).disabled = true;
    document.getElementById(btn2).disabled = false;

}

function init() {
    tableSelect();
    requestContacts();
    buildContactTable();
}