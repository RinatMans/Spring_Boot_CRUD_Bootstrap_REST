getUsers()

function getUsers() {
    fetch("/api/users").then(res => {
        res.json().then(users => {
            if (users.length > 0) {
                var temp = "";
                users.forEach((user) => {
                    temp += "<tr>";
                    temp += "<td>" + user.id + "</td>";
                    temp += "<td>" + user.firstname + "</td>";
                    temp += "<td>" + user.lastname + "</td>";
                    temp += "<td>" + user.age + "</td>";
                    temp += "<td>" + user.email + "</td>";
                    temp += "<td>" + user.roles.map(roleUser => roleUser.role) + "</td>";
                    temp += "<td>" + "<button onclick='editUserById(" + user.id + ")' class='btn btn-info edit-btn' " +
                        "data-toggle='modal' data-target='#editModal'>Edit</button>" + "</td>";
                    temp += "<td>" + "<button onclick='deleteUserById(" + user.id + ")' class='btn btn-danger' " +
                        "data-toggle='modal' data-target='#deleteModal'>Delete</button>" + "</td>";
                    temp += "</tr>";
                });
                document.getElementById("userTable").innerHTML = temp;
            }
        });
    });
}

function newUser() {
    let firstname = document.getElementById("newFirstname").value;
    let lastname = document.getElementById("newLastname").value;
    let age = document.getElementById("newAge").value;
    let email = document.getElementById("newEmail").value;
    let password = document.getElementById("newPassword").value;
    let role = getRoles(Array.from(document.getElementById('newRole').selectedOptions)
        .map(role => role.value));
    if (firstname != "" && lastname != "" && age > 0 && email != "" && role.length > 0) {
        fetch("/api/users", {
            method: "POST", headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify({
                firstname: firstname,
                lastname: lastname,
                age: age,
                email: email,
                password: password,
                roles: role
            })
        }).then(() => {
            getUsers();
        })
    }
}

function getRoles(list) {
    let roles = [];
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    return roles;
}

function editUserById(id) {
    fetch("/api/users/" + id, {
        method: 'GET', dataType: 'JSON'})
        .then(res => {
            res.json().then(user => {
                $('#editID').val(user.id)
                $('#editFirstname').val(user.firstname)
                $('#editLastname').val(user.lastname)
                $('#editAge').val(user.age)
                $('#editEmail').val(user.email)
                user.roles.map(role => {
                    $('#editRole').append('<option id="' + role.id + '" name="' + role.role + '">' +
                        role.role + '</option>')
                })
            })
        })

}

function editUser() {
    let id = document.getElementById("editID").value;
    let firstname = document.getElementById("editFirstname").value;
    let lastname = document.getElementById("editLastname").value;
    let age = document.getElementById("editAge").value;
    let email = document.getElementById("editEmail").value;
    let password = document.getElementById("editPassword").value;
    let role = getRoles(Array.from(document.getElementById('editRole').selectedOptions)
        .map(role => role.value));
    if (firstname != "" && lastname != "" && age > 0 && email != "" && role.length > 0) {
      fetch("/api/users/"+id, {
          method: "PATCH", headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8' },
          body: JSON.stringify({
              firstname:firstname,
              lastname: lastname,
              age: age,
              email: email,
              password: password,
              roles: role
          })
      }).then(() => {
          getUsers();
          $('#editModal').modal('hide');
        })
    }

}

function deleteUserById(id) {
    fetch("/api/users/" + id, {
        method: 'GET', dataType: 'JSON'
    })
        .then(res => {
            res.json().then(user => {
                $('#deleteID').val(user.id)
                $('#deleteFirstname').val(user.firstname)
                $('#deleteLastname').val(user.lastname)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                $('#deletePassword').val(user.password)
                user.roles.map(role => {
                    $('#deleteRole').append('<option id="' + role.id + '" name="' + role.role + '">' +
                        role.role + '</option>')
                })
            })
        })

}

function deleteUser() {
    fetch("/api/users/"+($('#deleteID').val()), {method:"DELETE"})
        .then(() => {
            getUsers();
            $('#deleteModal').modal('hide');
        })
}