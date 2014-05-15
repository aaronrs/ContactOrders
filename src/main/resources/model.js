var contacts, currentContact;

var orders, currentOrder;

function newContact(obj) {
    var contact = Object.create(obj);
    contact.fullName = function() {
        return this.name.first + " " + this.name.last;
    };
    contact.fullAddress = function() {
        return this.address.number + " " + this.address.address1 + " " + this.address.town;
    };
    contact.validate = function() {

    }
    return contact;
}

function newOrder(obj) {
    return Object.create(obj);
}

