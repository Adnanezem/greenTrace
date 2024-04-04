console.log('login.js loaded');
// login.js
var log_in = true;

var CONTACT_BACKEND_ENDPOINT = 'http://192.168.75.79/back_test/contact';
var LOGIN_BACKEND_ENDPOINT = 'http://192.168.75.79/back_test/login';
var SIGNUP_BACKEND_ENDPOINT = 'http://192.168.75.79/back_test/signup';


function serverError(comment) {
    var div = document.createElement('div');
    div.style.backgroundColor = 'red';
    div.style.color = 'white';
    div.style.position = 'fixed';
    div.style.top = '0';
    div.style.left = '0';
    div.style.width = '100%';
    div.style.zIndex = '1000';
    div.style.padding = '10px';
    div.style.textAlign = 'center';
    div.innerHTML = 'Erreur: ' + comment;
    document.body.appendChild(div);

    setTimeout(function() {
        div.remove();
    }
    , 5000);
}

function toggleProcessingMessage(show) {

    console.log('Processing message toggled');
    let processingDiv = document.getElementById('processingMessage');
    if (!processingDiv) {
        processingDiv = document.createElement('div');
        processingDiv.id = 'processingMessage';
        processingDiv.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
        processingDiv.style.color = 'white';
        processingDiv.style.position = 'fixed';
        processingDiv.style.top = '0';
        processingDiv.style.left = '0';
        processingDiv.style.width = '100%';
        processingDiv.style.height = '100%';
        processingDiv.style.zIndex = '1001';
        processingDiv.style.display = 'flex';
        processingDiv.style.justifyContent = 'center';
        processingDiv.style.alignItems = 'center';
        processingDiv.style.fontSize = '20px';
        processingDiv.textContent = 'Processing your request...';
        document.body.appendChild(processingDiv);
    }
    processingDiv.style.display = show ? 'flex' : 'none';

    console.log('Processing message toggled');
}

//if loginData is stored in the local storage, fill the form with the data
function fillForm() {
    var data = JSON.parse(localStorage.getItem('loginData'));
    if (data) {
        document.getElementById('si-username').value = data.login;
        document.getElementById('si-password').value = data.password;
    }
}

function buttonEventSetup() {

    // When "login-btn" submit input is clicked
    document.getElementById('login-btn').addEventListener('click', function() {
        // Get username and password inputs
        var login = document.getElementById('si-username').value;
        var password = document.getElementById('si-password').value;
        console.log('Username: ', login);
        console.log('Password: ', password);

        // Create a json object with the data
        var data = {
            login: login,
            password: password
        };

        console.log('Data: ', data);
        // Store data in local storage
        localStorage.setItem('loginData', JSON.stringify(data))

        // Show processing message
        toggleProcessingMessage(true);

        // Now send the data to the backend using fetch
        fetch(LOGIN_BACKEND_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            // Hide processing message
            toggleProcessingMessage(false);
            if (data.success) {
                console.log("??????????????????????????????????????????????????????????????????????????MOUETTE")
                //window.location.href = './';
            } else {
                alert('Invalid username or password');
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            serverError('Impossible de se connecter');
            // Hide processing message
            toggleProcessingMessage(false);
            // Empty the password input
            document.getElementById('si-password').value = '';
            //stay on the same page
            return;
        });
    });

    // When "signup-btn" submit input is clicked
    document.getElementById('signup-btn').addEventListener('click', function() {
        var login = document.getElementById('su-login').value;
        var fname = document.getElementById('su-username').value;
        var lname = document.getElementById('su-userlastname').value;
        var email = document.getElementById('su-email').value;
        var password = document.getElementById('su-password').value;

        var data = {
            login: login,
            fname: fname,
            lname: lname,
            email: email,
            password: password
        };

        console.log('Data: ', data);

        localStorage.setItem('signupData', JSON.stringify(data));

        toggleProcessingMessage(true);

        fetch(SIGNUP_BACKEND_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            toggleProcessingMessage(false);
            if (data.success) {
                //window.location.href = './';
            } else {
                alert('Signup failed. Please try again.');
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            serverError('Impossible de s\'inscrire');
            toggleProcessingMessage(false);
            document.getElementById('su-password').value = '';
        });
    });
}

// Call the function to fill the form
fillForm();

// Call the function to setup button events
buttonEventSetup();


