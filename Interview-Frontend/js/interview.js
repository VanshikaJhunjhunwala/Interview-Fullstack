let askedQuestions = [];
let currentQuestionNumber = 0;
let totalQuestions = 0;
let totalScore = 0;
let attemptedQuestions = 0;
let currentQuestionId=null;
async function startInterview() {

    await saveCurrentAnswer();
    document.getElementById("answer").disabled=true;
    document.querySelector("button[onclick='nextQuestion()']").disabled=true;
    document.querySelector("button[onclick='startInterview()']").disabled=true;
    let finalScore = 0;

    if (attemptedQuestions > 0) {
        finalScore = Math.round(totalScore / attemptedQuestions);
    }
    const token = localStorage.getItem("token");
await fetch(
    "http://localhost:8080/interview/submit",
    {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            topic: document.getElementById("topic").value,
            difficulty: document.getElementById("difficulty").value,
            score: finalScore,
            attemptedQuestions: attemptedQuestions,
            totalQuestions: totalQuestions
        })
    }
);

    document.getElementById("result").innerHTML = `
        <div style="
            border:1px solid #ddd;
            padding:15px;
            margin-top:20px;
            border-radius:10px;
            width:400px;
        ">
            <h2>🎯 Interview Result</h2>

            <p><b>Topic:</b>
                ${document.getElementById("topic").value}
            </p>

            <p><b>Difficulty:</b>
                ${document.getElementById("difficulty").value}
            </p>

            <p><b>Questions Attempted:</b>
                ${attemptedQuestions}
            </p>

            <p><b>Total Questions:</b>
                ${totalQuestions}
            </p>

            <p><b>Average Score:</b>
                ${finalScore}
            </p>
        </div>
    `;
}
async function loadQuestion() {

    const topic =
        document.getElementById("topic").value;

    const difficulty =
        document.getElementById("difficulty").value;

    if (!topic || !difficulty) {
        alert("Enter topic and difficulty first");
        return;
    }

    const token = localStorage.getItem("token");

    const response = await fetch(
        `http://localhost:8080/questions/random?topic=${topic}&difficulty=${difficulty}`,
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );

    const data = await response.json();
    if (totalQuestions === 0) {
        await loadQuestionCount();
    }
    if (askedQuestions.includes(data.id)) {
        if (askedQuestions.length >= totalQuestions) {
            await startInterview();
            return;
        }
        loadQuestion();
        return;
    }
    askedQuestions.push(data.id);
    currentQuestionNumber++;
    document.getElementById("counter").innerText ="Question " + currentQuestionNumber;
    document.getElementById("question").innerText =data.questionText;
    currentQuestionId=data.id;
}
async function nextQuestion() {

    await saveCurrentAnswer();

    document.getElementById("answer").value = "";

    await loadQuestion();
}
async function loadQuestionCount() {
    const topic =document.getElementById("topic").value;
    const difficulty =document.getElementById("difficulty").value;
    const token = localStorage.getItem("token");
    const response = await fetch(
        `http://localhost:8080/questions/count?topic=${topic}&difficulty=${difficulty}`,
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );
    totalQuestions = await response.json();
}
async function saveCurrentAnswer() {
    console.log("saveCurrentAnswer called");
    const token = localStorage.getItem("token");

    const topic = document.getElementById("topic").value;
    const difficulty = document.getElementById("difficulty").value;
    const answer = document.getElementById("answer").value;

    if (!answer.trim()) {
        return;
    }

    const response = await fetch(
        "http://localhost:8080/interview/start",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                topic,
                difficulty,
                answer,
                questionId:currentQuestionId
            })
        }
    );
    console.log("Response Status =", response.status);
    const data = await response.json();
    console.log(data);
    totalScore += data.score;
    attemptedQuestions++;

    return data;
}