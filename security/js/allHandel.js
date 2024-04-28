function logout(){
    if(localStorage.getItem("token"))
        localStorage.clear();
    window.location.href="login.html";
}