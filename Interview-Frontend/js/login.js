async function login() {
    try {
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const response = await fetch(
            "http://localhost:8080/users/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }
        );
        if(response.ok){
            const data = await response.json();
            localStorage.setItem("token", data.token);
            localStorage.setItem("role", data.role);
            document.getElementById("error").innerText = "";
            window.location.href = "dashboard.html";
        }
        else{
            document.getElementById("error").innerText ="Invalid Email or Password";
            document.getElementById("email").value = "";
            document.getElementById("password").value = "";
        }
    } catch(error) {
        console.error(error);
        alert("Error: " + error);
    }
}