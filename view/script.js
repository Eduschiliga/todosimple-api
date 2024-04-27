const url = "http://localhost:8080/task/user/1";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(tasks) {
    let table = `
        <thead>
            <th scope="col">#Id</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
            <th scope="col">User Id</th>
        </thead>
    `;

    for (let task of tasks) {
        table += `
            <tr>
                <td>${task.id}</td>
                <td>${task.description}</td>
                <td>${task.user?.username || "N/A"}</td>
                <td>${task.user?.id || "N/A"}</td>
            </tr>
        `;
    }

    document.getElementById("tasks").innerHTML = table;
}

async function getApi(url) {
    const response = await fetch(url, {method: "GET"});

    let data = await response.json();

    if(response) {
        hideLoader();
    }
    show(data);
}

getApi(url);