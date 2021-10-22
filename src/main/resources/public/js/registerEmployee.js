
document.getElementById("registerForm").addEventListener("submit", (event) => {
    event.preventDefault();
    
    let data = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        employee: {
            fname: document.getElementById("fname").value,
            lname: document.getElementById("lname").value,
            gender: document.getElementById("gender").value,
            address: {
                firstLine: document.getElementById("addressLineOne").value,
                secondLine: document.getElementById("addressLineTwo").value,
                city: document.getElementById("city").value,
                state: document.getElementById("state").value,
                zip: document.getElementById("zip").value,
            }
        }
    }

    console.log(data);

    fetch("/api/users", {
        method: "POST",    
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (res.status === 201) {
            console.log("An employee was successfully added to the database.");
            window.location = "/dashboard";
        } else {
            throw new Error("Sorry, the account credentials you provided are invalid. Please try again.");
        }
    }).catch(err => {
        console.log(err.message);
    });
});