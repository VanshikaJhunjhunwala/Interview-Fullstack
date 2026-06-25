const params = new URLSearchParams(window.location.search);

const id = params.get("id");

loadQuestion();

async function loadQuestion() {

    const token = localStorage.getItem("token");

    const response = await fetch(
        `http://localhost:8080/questions/${id}`,
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );

    const data = await response.json();

    document.getElementById("questionText").value =
        data.questionText;

    document.getElementById("topic").value =
        data.topic;

    document.getElementById("difficulty").value =
        data.difficulty;

    document.getElementById("expectedAnswer").value =
        data.expectedAnswer;
}

async function updateQuestion() {

    const token = localStorage.getItem("token");

    const response = await fetch(
        `http://localhost:8080/questions/${id}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + token
            },
            body: JSON.stringify({
                questionText:
                    document.getElementById("questionText").value,

                topic:
                    document.getElementById("topic").value,

                difficulty:
                    document.getElementById("difficulty").value,

                expectedAnswer:
                    document.getElementById("expectedAnswer").value
            })
        }
    );

    await response.json();
    window.location.href = "questions.html";
}