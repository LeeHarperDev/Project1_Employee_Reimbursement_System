import CookieProvider from "./classes/CookieProvider.js";

async function init() {
    CookieProvider.parseCookies();

    let employeeId = CookieProvider.getCookieValue("employeeId");
    let res = await fetch("http://localhost:8080/api/employees/" + employeeId);
    let data = await res.json();

    document.getElementById("employeeFullName").innerText = `${data.fname} ${data.lname}`;

    document.getElementById("employeeFirstName").innerText = data.fname;
    document.getElementById("employeeLastName").innerText = data.lname;
    document.getElementById("employeeGender").innerText = data.gender;
    document.getElementById("employeeId").innerText = data.id;

    document.getElementById("employeeAddressFirstLine").innerText = data.address.firstLine;
    document.getElementById("employeeAddressSecondLine").innerText = data.address.secondLine;
    document.getElementById("employeeAddressCity").innerText = data.address.city;
    document.getElementById("employeeAddressState").innerText = data.address.state;
    document.getElementById("employeeAddressZip").innerText = data.address.zip;
}

window.onload = init;