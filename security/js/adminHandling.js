    let today = new Date();
    let day = String(today.getDate()).padStart(2, '0');
    let month = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    let year = today.getFullYear();

    today = year + '-' + month + '-' + day;

    // Set the min attribute of the date input to today's date
    document.getElementById("deadline").min = today;

let url = 'http://localhost:8585/tasks/admin/add';
const myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");
myHeaders.append('Authorization', 'Bearer ' + sessionStorage.getItem('token'));

let requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
    // body:"",
};

let renderTasks = function (){
    requestOptions.method='GET';
    if (requestOptions.hasOwnProperty('body')) {
        // Delete the body property
        delete requestOptions.body;
    }
    fetch("http://localhost:8585/tasks/admin/mainPage", requestOptions)
.then((response) => response.json())
.then((result) => {
    taskList.innerHTML ='';
    result.forEach((element )=> {
        console.log(element)
        putTask(element,element['done']);
    });
})
.catch((error) => window.location.href="login.html");

}

let putTask = function (task,isDone){
    let color = '';
    if(isDone)
        color = '&#x2705;';
    taskDocument = `
    <tr">
        <th class="task-id-${task['id']}">${task['id']}</th>
        <th class="task-name-${task['id']}">${task['name']}</th>
        <th class="deadline-${task['id']}">${task['deadLine'].substring(0,10)}</th>
        <td class="actions">
            <button class="btn btn-primary btn-sm actionsButton"  data-toggle="modal" data-target="#exampleModal" id="editTask" onclick="editTask(${task['id']})">Edit</button>
            <button class="btn btn-danger actionsButton btn-sm" onclick="deleteTask(${task['id']})">Delete</button>
            <span style="font-size:25px">${color}</span>
        </td>
    </tr>`
    taskList.innerHTML += taskDocument;
}

let deleteTask = function(id){
    requestOptions.method='DELETE'
    let url = `http://localhost:8585/tasks/admin/delete/${id}`;
    fetch(url, requestOptions)
    .then((response) => {
    console.log("deleted");
    
    renderTasks();
})
.catch((error) => console.error(error));
}

let editTask = function(id){
    let name = document.querySelector(`.task-name-${id}`).innerHTML;
    let deadline = document.querySelector(`.deadline-${id}`).innerHTML;
    let newName = document.querySelector("#newName");
    let newDeadline = document.querySelector("#newDeadline");
    newName.value = name;
    newDeadline.value = deadline;
    $('#exampleModal').modal('show');
        $('#editButton').off('click').click(function(event){
            event.preventDefault();
            let url =  `http://localhost:8585/tasks/admin/edit/${id}`;
            makeTransaction(newName, newDeadline , url , 'PUT')
        });
}

let addButton = document.querySelector(".addButton");

    addButton.addEventListener("click", function(event) {
        event.preventDefault();
    });

function addTask(){
    let newName = document.querySelector("#taskName");
    let newDeadline = document.querySelector("#deadline");
    let url =  `http://localhost:8585/tasks/admin/add`;
    makeTransaction(newName, newDeadline , url ,'POST' );
    newName.value='';
    newDeadline.value ='';

}


function makeTransaction(newName , newDeadline  ,url , method) {
    if (newName.value && newDeadline.value){
        requestOptions.method=method;
        let data ={
            name:newName.value,
            deadLine:newDeadline.value,
        }
        requestOptions.body= JSON.stringify(data);
        fetch(url, requestOptions)
            .then((response) => {
            console.log("added");
            renderTasks();
        }).catch((error) => console.error(error));
        $('#exampleModal').modal('hide');
    }
    else{
        alert('Please enter valid task name and deadline.'); //this line
    }
}



renderTasks();