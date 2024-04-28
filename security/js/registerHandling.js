let registerButton = document.querySelector("#registerButton");
registerButton.addEventListener("click", function(event) {
    event.preventDefault();
    let usernameInput = document.querySelector("#username").value;
    let passwordInput = document.querySelector("#password").value;
    const data = {
        name:usernameInput,
        password:passwordInput
    }

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");   

    const raw = JSON.stringify(data);

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8585/auth/signUp", requestOptions)
    .then((response) => {
        if(response.status>=400 && response.status<=499 )
            throw new Error("Authentication failed");
        else 
            window.location.href="login.html";
    })
    .catch((error) => console.error(error));

});