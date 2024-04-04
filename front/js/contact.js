console.log('contact.js loaded');

// Define the backend endpoint
var BACKEND_ENDPOINT = 'http://192.168.75.79/back_test/contact';

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
    div.innerHTML = 'Erreur serveur: ' + comment;
    document.body.appendChild(div);

    setTimeout(function() {
        div.remove();
    }
    , 5000);
}

function toggleProcessingMessage(show) {
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
}



//function to fill the form with the data stored in the local storage
function fillForm() {
    var data = JSON.parse(localStorage.getItem('contactData'));
    if (data) {
        document.getElementById('inputName1').value = data.inputName1;
        document.getElementById('inputName2').value = data.inputName2;
        document.getElementById('inputEmail4').value = data.inputEmail4;
        document.getElementById('inputCity').value = data.inputCity;
        document.getElementById('inputPhone').value = data.inputPhone;
    }
}

// Call the function to fill the form
fillForm();

document.querySelector('.contactForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var inputName1 = document.getElementById('inputName1').value;
    var inputName2 = document.getElementById('inputName2').value;
    var inputEmail4 = document.getElementById('inputEmail4').value;
    var inputCity = document.getElementById('inputCity').value;
    var inputPhone = document.getElementById('inputPhone').value;
    var inputMessage = document.getElementById('inputMessage').value;
    var exampleFormControlTextarea1 = document.getElementById('exampleFormControlTextarea1').value;

    // Email validation
    var emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (!inputEmail4.match(emailPattern)) {
        alert('Please enter a valid email address');
        return;
    }

    // Assuming console logs are for debugging purposes, and you've checked the data
    console.log('Prénom: ', inputName1);
    console.log('Nom: ', inputName2);
    console.log('Email: ', inputEmail4);
    console.log('Ville: ', inputCity);
    console.log('Tél: ', inputPhone);
    console.log('Sujet: ', inputMessage);
    console.log('Message: ', exampleFormControlTextarea1);

    // Create a json object with the data
    var data = {
        inputName1: inputName1,
        inputName2: inputName2,
        inputEmail4: inputEmail4,
        inputCity: inputCity,
        inputPhone: inputPhone,
        inputMessage: inputMessage,
        exampleFormControlTextarea1: exampleFormControlTextarea1
    };

    // Store the data in the local storage for automatic filling of the form
    localStorage.setItem('contactData', JSON.stringify(data));

    // Show processing message
    toggleProcessingMessage(true);

    // Now send the data to the backend using fetch
    fetch(BACKEND_ENDPOINT, {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json()) // assuming the server responds with json
    .then(data => {
        console.log('Success:', data);
        alert('Form successfully submitted');
        // Hide processing message
        toggleProcessingMessage(false);
    })
    .catch((error) => {
        console.error('Error:', error);
        serverError('Impossible de soumettre le formulaire');
        // Hide processing message
        toggleProcessingMessage(false);
    });
});
