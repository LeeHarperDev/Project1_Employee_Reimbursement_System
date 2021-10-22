async function init() {
    let res = await fetch("http://localhost:8080/api/employees");
    let data = await res.json();
    let employeeListElement = document.getElementById("#employeeList");

    console.log(data);

    for (datum of data) {
        const employeeListElement = document.getElementById("employeeList");
        console.log(datum);
        const parent = document.createElement("div");
        parent.classList.add("employee_card")

        const employeeImage = document.createElement("img");
        employeeImage.src = "/images/employees/employee_placeholder.png"

        const employeeHeader = document.createElement("h2");
        const employeeHeaderText = document.createTextNode(`${datum.fname} ${datum.lname}`);

        const employeeProfileButton = document.createElement("a");
        employeeProfileButton.classList.add("anchorBtn");
        employeeProfileButton.href = `/admin/employees/${datum.id}`
        const employeeProfileText = document.createTextNode("View Profile");

        employeeProfileButton.appendChild(employeeProfileText);

        employeeHeader.appendChild(employeeHeaderText);
        parent.appendChild(employeeImage);
        parent.appendChild(employeeHeader);
        parent.appendChild(employeeProfileButton);
        employeeListElement.appendChild(parent);
    }
}

window.onload = init;