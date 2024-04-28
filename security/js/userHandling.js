

const myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");
myHeaders.append('Authorization', 'Bearer ' + localStorage.getItem('token'));

let requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
    // body:"",
};

let renderTasks = function ( ){
    requestOptions.method='GET';
    if (requestOptions.hasOwnProperty('body')) {
        delete requestOptions.body;
    }
    fetch("http://localhost:8585/tasks/user/mainPage", requestOptions)
.then((response) => response.json())
.then((result) => {
    taskList.innerHTML ='';
    result.forEach((element,index )=> {
        console.log(element)
        putTask(element);
    });
})
.catch((error) => window.location.href='login.html');

}

let putTask = function (task,index){
    taskDocument = `
    <tr>
        <th class="task-id-${task['id']}">${task['id']}</th>
        <th class="task-name-${task['id']}">${task['name']}</th>
        <th class="deadline-${task['id']}">${task['deadLine'].substring(0,10)}</th>
        <th class="actions">
                <button class="btn btn-primary actionsButton" onclick="markTask(${task['id']})">Done ?</button>
        </th>
    </tr>`
    taskList.innerHTML += taskDocument;
}

function markTask(id){
    requestOptions.method='PUT';
    url = `http://localhost:8585/tasks/user/markDone/${id}`;
    fetch(url, requestOptions)
    .then((response) => renderTasks())
    .catch((error) => console.error(error));
    
}

renderTasks();