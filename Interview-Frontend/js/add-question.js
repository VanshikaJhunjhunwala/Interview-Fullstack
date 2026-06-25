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

    if (!questionText || !topic || !difficulty || !expectedAnswer) {
        document.getElementById("formMessage").innerText ="Please fill all the fields";
        return;
    }

    const response = await fetch(
        "http://localhost:8080/questions",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                questionText,
                topic,
                difficulty,
                expectedAnswer
            })
        }
    );
    if(response.ok){
        document.getElementById("formMessage").style.color = "green";
        document.getElementById("formMessage").innerText ="Question Added Successfully";
        document.getElementById("questionText").value = "";
        document.getElementById("topic").value = "";
        document.getElementById("difficulty").value = "";
        document.getElementById("expectedAnswer").value = "";
    }
    else{
        const errorMessage = await response.text();
        document.getElementById("formMessage").style.color = "red";
        document.getElementById("formMessage").innerText =errorMessage;
        document.getElementById("questionText").value = "";
    }
}
async function checkQuestion() {

    const questionText =
        document.getElementById("questionText").value;

    if (!questionText) {
        return;
    }

    const token = localStorage.getItem("token");

    const response = await fetch(
        `http://localhost:8080/questions/exists?questionText=${encodeURIComponent(questionText)}`,
        {
            headers: {
                Authorization: "Bearer " + token
            }
        }
    );

    const exists = await response.json();

    if (exists) {

        document.getElementById("questionError").style.color = "red";

        document.getElementById("questionError").innerText =
            "Question Already Exists";

        document.getElementById("questionText").value = "";
    }
    else{
        document.getElementById("message").innerText = "";
    }
}