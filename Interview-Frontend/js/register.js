async function registerUser() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(
        "http://localhost:8080/users",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: name,
                email: email,
                password: password
            })
        }
    );

    if (response.ok) {
        document.getElementById("msg").innerText =
            "✅ Registration Successful";
    } 
    else {
        document.getElementById("msg").innerText =
            "❌ Registration Failed";
    }
}