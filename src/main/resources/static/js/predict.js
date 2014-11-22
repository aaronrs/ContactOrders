var xmlHttp = new XMLHttpRequest();
xmlHttp.open("GET", "/products/list", false);
xmlHttp.send();
var productList = JSON.parse(xmlHttp.responseText);

productList = JSON.parse('[{"":""}]');

var form = document.getElementById("theForm");
var resultsDiv = document.getElementById("results");
var searchField = form.search;

// first, position the results:
var node = searchField;
var x = 0;
var y = 0;
while (node != null) {
    x += node.offsetLeft;
    y += node.offsetTop;
    node = node.offsetParent;
}
resultsDiv.style.left = x + "px";
resultsDiv.style.top = (y + 20) + "px";

// now, attach the keyup handler to the search field:
searchField.onkeyup = function () {
    var txt = this.value.toLowerCase();
    if (txt.length == 0) return;

    var txtRE = new RegExp("(" + txt + ")", "ig");
    // now...do we have any matches?
    var top = 0;
    for (var s = 0; s < productList.length; ++s) {
        var srch = productList[s];
        if (srch.name.toLowerCase().indexOf(txt) >= 0) {
            srch = srch.name.replace(txtRE, "<span>$1</span>");
            var div = document.createElement("div");
            div.innerHTML = srch;
            div.onclick = function () {
                searchField.value = this.innerHTML.replace(/\<\/?span\>/ig, "");
                resultsDiv.style.display = "none";
            };
            div.style.top = top + "px";
            top += 20;
            resultsDiv.appendChild(div);
            resultsDiv.style.display = "block";
        }
    }
}
searchField.onkeydown = function () {
    while (resultsDiv.removeChild(resultsDiv.firstChild) != null);
    resultsDiv.style.display = "none";
}
