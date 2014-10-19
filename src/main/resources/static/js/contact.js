$(document).ready(function () {

    var wrapper = $(".input_fields_wrap"); //Fields wrapper
    var add_button = $(".add_field_button"); //Add button ID

    var x = 0; //initlal text box count

    $(add_button).click(function (e) { //on add input button click
        x++;
        if (x > 0) {
            $("#saveOrderButton").show();
        }
        e.preventDefault();
        var val = $("#product option:selected").attr("value");
        var txt = $("#product option:selected").text();

        var d = new Date();
        var monthNum = d.getMonth() + 1;
        var month = monthNum < 10 ? "0" + monthNum : monthNum;
        var dayNum = d.getDate();
        var day = dayNum < 10 ? "0" + dayNum : dayNum;

        var output = d.getFullYear() + "-" + month + "-" + day;

        $(wrapper).append(
                '<div>' +
                '<input type="hidden" name="productId" value="' + val + '"/>' +
                '<input style="width:300px;" type="text" name="name_' + val + '" value="' + txt + '"/>' +
                '<input type="number" placeholder="Amount" class="amount" name="amount_' + val + '" value="1"/>' +
                'Delivery: <input id="delivery" type="text" placeholder="Date" class="date" name="delivery_' + val + '" value="' + output + '"/>' +
                '<a href="#" class="remove_field">X</a>' +
                '</div>'
        );

    });

    $(wrapper).on("click", ".remove_field", function (e) { //user click on remove text
        x--;
        if (x = 0) {
            $("#saveOrderButton").hide();
        }
        e.preventDefault();
        $(this).parent('div').remove();
    })
});

