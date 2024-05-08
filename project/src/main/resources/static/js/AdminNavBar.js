document.addEventListener("DOMContentLoaded", function() {
    fetch("AdminNavBar.html")
    .then(response => response.text())
    .then(data => {
        document.getElementById("AdminNavBar").innerHTML = data;
    });
});