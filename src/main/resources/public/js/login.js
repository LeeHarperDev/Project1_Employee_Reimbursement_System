
document.getElementById("loginForm").addEventListener("submit", (event) => {
    event.preventDefault();
    
    let data = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    }

    fetch("/api/users/login", {
        method: "POST",    
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (res.status === 200) {
            document.location = "/dashboard";
        } else {
            throw new Error("Sorry, the account credentials you provided are invalid. Please try again.");
        }
    }).catch(err => {
        console.log(err.message);
    });
});