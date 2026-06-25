async function loadQuestions() {

    const token = localStorage.getItem("token");

    const response = await fetch(
        "http://localhost:8080/questions?topic=Java&difficulty=EASY",
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );

    const data = await response.json();

    let output = "";

    data.forEach(question => {
        output += `
            <h3>Question ID: ${question.id}</h3>
            <p><b>Question:</b> ${question.questionText}</p>
            <p><b>Topic:</b> ${question.topic}</p>
            <p><b>Difficulty:</b> ${question.difficulty}</p>
            <p><b>Expected Answer:</b> ${question.expectedAnswer}</p>
            <button onclick="window.location.href='edit-question.html?id=${question.id}'">
                Edit
            </button>
            <button onclick="deleteQuestion(${question.id})">
                Delete
            </button>

            <hr>
        `;
    });

    document.getElementById("result").innerHTML = output;
}
loadQuestions();
async function addQuestion() {

    const token = localStorage.getItem("token");

    const questionText =
        document.getElementById("questionText").value;

    const topic =
        document.getElementById("topic").value;

    const difficulty =
        document.getElementById("difficulty").value;

    const expectedAnswer =
        document.getElementById("expectedAnswer").value;

    const response = await fetch(
        "http://localhost:8080/questions",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                questionText: questionText,
                topic: topic,
                difficulty: difficulty,
                expectedAnswer: expectedAnswer
            })
        }
    );

    const data = await response.json();
    console.log(data);
}
async function deleteQuestion(id) {

    const token = localStorage.getItem("token");

    await fetch(
        `http://localhost:8080/questions/${id}`,
        {
            method: "DELETE",
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );
    loadQuestions();
}