async function loadHistory() {

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    let url = "http://localhost:8080/interview/my-history";
    if(role === "ADMIN"){
        url = "http://localhost:8080/interview/all-history";
    }
    const response = await fetch(
       url,
       {
            headers: {
            Authorization: "Bearer " + token
            }
        }
    );

    const data = await response.json();
    console.log(data);

    let grouped = {};

    data.forEach(session => {
        if (!grouped[session.userEmail]) {
            grouped[session.userEmail] = [];
        }
        grouped[session.userEmail].push(session);
    });

    let html = "";

    Object.keys(grouped).forEach(email => {

        html += `
            <h2>👤 ${email}</h2>

            <table border="1" cellpadding="10">
                <tr>
                    <th>Topic</th>
                    <th>Difficulty</th>
                    <th>Score</th>
                </tr>
        `;

        grouped[email].forEach(session => {
            html += `
                <tr>
                    <td>${session.topic}</td>
                    <td>${session.difficulty}</td>
                    <td>${session.score}</td>
                </tr>
            `;
        });

        html += `</table><br><br>`;
    });

    document.getElementById("history").innerHTML = html;
}

loadHistory();