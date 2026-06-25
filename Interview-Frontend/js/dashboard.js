const token = localStorage.getItem("token");
if (!token) {
    alert("Please Login First");
    window.location.href = "login.html";
}
async function loadProfile() {
    const profileDiv = document.getElementById("profile");
    if (profileDiv.innerHTML !== "") {
        profileDiv.innerHTML = "";
        return;
    }
    const token = localStorage.getItem("token");
    console.log("Token =", token);

    const response = await fetch(
        "http://localhost:8080/users/me/details",
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );

    console.log("Status =", response.status);

    const data = await response.json();
    document.getElementById("profile").innerHTML = `
        <div style="
        border:1px solid #ccc;
        padding:15px;
        margin-top:10px;
        border-radius:10px;
        width:300px;
        ">
        <h3>👤 User Details</h3>
        <p><b>ID:</b> ${data.id}</p>
        <p><b>Name:</b> ${data.name}</p>
        <p><b>Email:</b> ${data.email}</p>
        </div>
    `;
}
function logout() {
    localStorage.removeItem("token");
    alert("Logged Out");
    window.location.href = "login.html";
}
async function loadStats() {
    const dashboardDiv = document.getElementById("dashboard");
    if (dashboardDiv.innerHTML !== "") {
        dashboardDiv.innerHTML = "";
        return;
    }
    const token = localStorage.getItem("token");

    const response = await fetch(
        "http://localhost:8080/interview/dashboard",
        {
            headers: {
                "Authorization": "Bearer " + token
            }
        }
    );

    const data = await response.json();

    dashboardDiv.innerHTML = `
        <h3>📈 Dashboard</h3>

        <p><b>Total Interviews:</b> ${data.totalInterviews}</p>

        <p><b>Average Score:</b> ${data.averageScore}</p>

        <p><b>Performance:</b> ${data.performance}</p>

        ${
            data.weakArea
            ? `<p><b>Weak Area:</b> ${data.weakArea}</p>`
            : ""
        }
    `;
}