// login.js
var log_in = true;

//if loginData is stored in the local storage, fill the form with the data
function fillForm() {
    var data = JSON.parse(localStorage.getItem('loginData'));
    if (data) {
        document.getElementById('si-username').value = data.si_username;
        document.getElementById('si-password').value = data.si_password;
    }
}


// If user presses button of id "sign-up-btn", switch log_in to false
document.getElementById('sign-up-btn').addEventListener('click', function() {
    log_in = false;
    console.log(log_in);
});

//If user presses button of id "sign-in-btn", switch log_in to true
document.getElementById('sign-in-btn').addEventListener('click', function() {
    log_in = true;
    console.log(log_in);
});


//When "login-btn" submit input is clicked
document.getElementById('login-btn').addEventListener('click', function() {
    //get password input "si-username" and "si-password"
    var si_username = document.getElementById('si-username').value;
    var si_password = document.getElementById('si-password').value;
    console.log('Username: ', si_username);
    console.log('Password: ', si_password);

    // Create a json object with the data
    var data = {
        si_username: si_username,
        si_password: si_password
    };

    console.log('Data: ', data);
    //store data in local storage
    localStorage.setItem('loginData', JSON.stringify(data));

    // If all elements are filled, redirect to the home page
    if (si_username && si_password) {
        window.location.href = './';
    } else {
        alert('Please fill in all the fields');
    }
});


//When "signup-btn" submit input is clicked
document.getElementById('signup-btn').addEventListener('click', function() {
    //get password input "su-username", "su-password" and "su-email"
    var su_username = document.getElementById('su-username').value;
    var su_password = document.getElementById('su-password').value;
    var su_email = document.getElementById('su-email').value;
    console.log('Username: ', su_username);
    console.log('Password: ', su_password);
    console.log('Email: ', su_email);

    // Create a json object with the data
    var data = {
        su_username: su_username,
        su_password: su_password,
        su_email: su_email
    };

    console.log('Data: ', data);

    // Email validation
    var emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (!su_email.match(emailPattern)) {
        alert('Please enter a valid email address');
        //empty the email input
        document.getElementById('su-email').value = '';
        return;
    } else {
        // If all elements are filled, redirect to the home page
        if (su_username && su_password && su_email) {
            window.location.href = './';
        } else {
            alert('Please fill in all the fields');
            return;
        }
    }
});