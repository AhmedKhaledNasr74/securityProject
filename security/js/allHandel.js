function logout(){
    if(sessionStorage.getItem("token"))
        sessionStorage.clear();
    window.location.href="login.html";
}