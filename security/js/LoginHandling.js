let url = 'http://localhost:8585/auth/signIn';


let submitLogin = document.querySelector(".submitLogin");
submitLogin.addEventListener("click", function(event) {
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

    fetch("http://localhost:8585/auth/signIn", requestOptions)
    .then((response) => {
        if(response.status>=400 && response.status<=499 ){
            throw new Error("wrong data");
        }
        return response.text()
    })
    .then((result) => {
        localStorage.setItem('token', result); // Store the token in local storage
        // Decode and extract data from the JWT token
        return decodedToken = parseJwt(result);
    }).then((decodedToken) =>{
        if(decodedToken.role=="ROLE_ADMIN")
            window.location.href="index.html";
        else
            window.location.href="user.html";
} )
    .catch((error) => console.error(error));

});

// Function to parse JWT token and extract payload data
function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    let data = JSON.parse(jsonPayload).sub;
    let splitedData = data.split(' ');
    let tokenParts = {
        id:splitedData[0],
        email:splitedData[1],
        role:splitedData[2]
    }
    return tokenParts;
}


    // fetch(url, {
    //     method: 'POST',
    //     body: JSON.stringify(data),
    //     headers: {
    //         'Content-Type': 'application/json',
    //     },
    //     redirect: "follow"
        
    // })
    //     .then(function(response) {
    //         console.log(response);
    //         localStorage.setItem('token', response);
    //     })
    //     .catch(function(error) {
    //         console.log('Error occurred:', error);
    //     });



