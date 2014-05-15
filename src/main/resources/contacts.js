var contacts;

function buildContactTable() {
    var data = contacts.slice(0);

    var values = [];
    for (var index in data) {
        var contact = data[index];
        values[index] = [contact.fullName, contact.fullAddress, contact.address.telephone];
    }

    var titles = [['Name', true],['Address', true],['Tel', false]];

    buildTable('contactsTable', values, titles);
}

// Contacts Detail

function contactTable(contact) {
    document.getElementById("contactId").value = contact.id;
    document.getElementById("firstname").value = contact.name.first;
    document.getElementById("surname").value = contact.name.last;
    document.getElementById("housenumber").value = contact.address.number;
    document.getElementById("houseName").value = contact.address.houseName;
    document.getElementById("address").value = contact.address.address1;
    document.getElementById("town").value = contact.address.town;
    document.getElementById("county").value = contact.address.county;
    document.getElementById("postcode").value = contact.address.postcode;
    document.getElementById("telephone").value = contact.address.telephone;
}

function clearContactTable() {
    document.getElementById("contactId").value = "-1";
    document.getElementById("firstname").value = "";
    document.getElementById("surname").value = "";
    document.getElementById("housenumber").value = "";
    document.getElementById("houseName").value = "";
    document.getElementById("address").value = "";
    document.getElementById("town").value = "";
    document.getElementById("county").value = "";
    document.getElementById("postcode").value = "";
    document.getElementById("telephone").value = "";
}

function showContactForm() {
    tabDisplay('contact_form', 'order_form', 'showContactBtn', 'showOrdersBtn');
}

// Contact functions

function findContact() {
    var len = contacts.length;
    var searchValue = parseInt(document.getElementById("search").value);
    var index;
    for (index = 0; index < len; index++) {
        if (contacts[index].name.first == searchValue ||
            contacts[index].name.last == searchValue ||
            contacts[index].address.houseName == searchValue ||
            contacts[index].address.address1 == searchValue
            ) break;
    }
    contactTable(contacts[index]);
    document.getElementById('updateButton').disabled = false;
    document.getElementById('deleteButton').disabled = false;
    document.getElementById('saveButton').disabled = true;
    displayLightbox();
}

function updateContact() {
    if (validateContact()) {
        var data = "contactId=" + document.getElementById("contactId").value +
            "&first=" + document.getElementById("firstname").value +
            "&last=" + document.getElementById("surname").value +
            "&number=" + document.getElementById("housenumber").value +
            "&houseName=" + document.getElementById("houseName").value +
            "&address1=" + document.getElementById("address").value +
            "&town=" + document.getElementById("town").value +
            "&county=" + document.getElementById("county").value +
            "&postcode=" + document.getElementById("postcode").value +
            "&telephone=" + document.getElementById("telephone").value;

        saveContact(data);
        requestContacts();
        buildContactTable();
    }
}

function saveContact() {
    if (validateContact()) {

        var data = "contactId=-1" +
            "&first=" + document.getElementById("firstname").value +
            "&last=" + document.getElementById("surname").value +
            "&number=" + document.getElementById("housenumber").value +
            "&houseName=" + document.getElementById("houseName").value +
            "&address1=" + document.getElementById("address").value +
            "&town=" + document.getElementById("town").value +
            "&county=" + document.getElementById("county").value +
            "&postcode=" + document.getElementById("postcode").value +
            "&telephone=" + document.getElementById("telephone").value;

        saveContact(data);

        requestContacts();
        buildContactTable();
    }
}

function deleteContact() {
    var len = contacts.length;
    var selectedId = parseInt(document.getElementById("contactId").value);
    for (var i = 0; i < len; i++) {
        if (contacts[i].id == selectedId) {
            contacts.splice(i, 1);
            break;
        }
    }
    buildContactTable();
}

function validateContact() {
    if (document.getElementById("firstname").value == "") return false;
    if (document.getElementById("surname").value == "") return false;
    if (document.getElementById("housenumber").value == "" && document.getElementById("houseName").value == "") return false;
    if (document.getElementById("address").value == "") return false;
    if (document.getElementById("town").value == "") return false;
    if (document.getElementById("county").value == "") return false;
    if (document.getElementById("postcode").value == "") return false;
    return true;
}

// AJAX

function requestContacts() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8787/contacts", false);
    xmlHttp.send();
    var jsonList = JSON.parse(xmlHttp.responseText);
    var contactsList = [];
    for (var i in jsonList) {
        contactsList[i] = newContact(jsonList[i]);
    }
    contacts = contactsList;
}

function saveContact(data) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "http://localhost:8787/contacts", false);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(data);
}

function newContact(obj) {
    var contact = Object.create(obj);
    contact.fullName = function () {
        return this.name.first + " " + this.name.last;
    };
    contact.fullAddress = function () {
        return this.address.number + " " + this.address.address1 + " " + this.address.town;
    };
    contact.sortName = function () {
        return this.name.last + this.name.first;
    };
    contact.sortAddress = function () {
        return this.address.town + this.address.address1 + this.address.number;
    };
    contact.fullName = contact.fullName();
    contact.fullAddress = contact.fullAddress();
    contact.sortName = contact.sortName();
    contact.sortAddress = contact.sortAddress();
    return contact;
}
