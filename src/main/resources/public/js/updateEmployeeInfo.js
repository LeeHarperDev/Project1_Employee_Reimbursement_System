import CookieProvider from "./classes/CookieProvider.js";

let employee = null;

function init() {
    let updateInfoForm = document.getElementById("updateInfoForm");

    assignDefaultValues();
    updateInfoForm.addEventListener("submit", sendUpdateInfoRequest);
}

async function assignDefaultValues() {
    CookieProvider.parseCookies();
    let employeeId = CookieProvider.getCookieValue("employeeId");

    let res = await fetch(`/api/employees/${employeeId}`);
    let data = await res.json();

    employee = data;

    document.getElementById("employeeFirstName").value = data.fname;
    document.getElementById("employeeLastName").value = data.lname;
    document.getElementById("employeeGender").value = data.gender;
    document.getElementById("employeeAddressFirstLine").value = data.address.firstLine;
    document.getElementById("employeeAddressSecondLine").value = data.address.secondLine;
    document.getElementById("employeeAddressCity").value = data.address.city;
    document.getElementById("employeeAddressState").value = data.address.state;
    document.getElementById("employeeAddressZip").value = data.address.zip;
}

async function sendUpdateInfoRequest(event) {
    CookieProvider.parseCookies();
    let employeeId = CookieProvider.getCookieValue("employeeId");

    event.preventDefault();
    console.log("SUCCESS");
    console.log(event.target.employeeFirstName.value);
    
    let data = {
        id: employee.id,
        fname: event.target.employeeFirstName.value,
        lname: event.target.employeeLastName.value,
        gender: event.target.employeeGender.value,
        address: {
            id: employee.address.id,
            firstLine: event.target.employeeAddressFirstLine.value,
            secondLine: event.target.employeeAddressSecondLine.value,
            city: event.target.employeeAddressCity.value,
            state: event.target.employeeAddressState.value,
            zip: event.target.employeeAddressZip.value,
        }
    }

    fetch(`/api/employees/${employeeId}`, {
        method: "PATCH",    
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (res.status === 200) {
            console.log("An employee was successfully added to the database.");
            window.location = "/profile";
        } else {
            throw new Error("An error occurred with updating the employee profile.");
        }
    }).catch(err => {
        console.log(err.message);
    });
}

window.addEventListener("load", init);