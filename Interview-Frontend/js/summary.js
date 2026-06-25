async function loadSummary() {

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");
    let url = "http://localhost:8080/interview/summary";
    if(role === "ADMIN"){
        url = "http://localhost:8080/interview/all-summary";
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
    if(role === "ADMIN"){

        let html = "";

        data.forEach(user => {

            html += `
            <div style="
                border:1px solid #ccc;
                padding:20px;
                border-radius:10px;
                width:350px;
                margin-bottom:20px;
                box-shadow:2px 2px 10px rgba(0,0,0,0.1);
            ">

                <h2>👤 ${user.userEmail}</h2>

                <p><b>Total Interviews:</b> ${user.totalInterviews}</p>

                <p><b>Highest Score:</b> ${user.highestScore}</p>

                <p><b>Lowest Score:</b> ${user.lowestScore}</p>

                <p><b>Average Score:</b> ${user.averageScore}</p>

                <hr>

                <p style="
                    font-size:18px;
                    font-weight:bold;
                    color:blue;
                ">
                    ${user.performance}
                </p>

            </div>
            `;
        });

       document.getElementById("summary").innerHTML = html;
       return;
    }
    document.getElementById("summary").innerHTML = `
        <div style="
            border:1px solid #ccc;
            padding:20px;
            border-radius:10px;
            width:350px;
            box-shadow:2px 2px 10px rgba(0,0,0,0.1);
        ">

            <h2>📊 Summary Report</h2>

            <p><b>Total Interviews:</b> ${data.totalInterviews}</p>

            <p><b>Highest Score:</b> ${data.highestScore}</p>

            <p><b>Lowest Score:</b> ${data.lowestScore}</p>

            <p><b>Average Score:</b> ${data.averageScore}</p>

            <hr>

            <p style="
                font-size:18px;
                font-weight:bold;
                color:blue;
            ">
                ${data.performance}
            </p>

        </div>
    `;
}

loadSummary();