function blameBackEnd() {
    console.log('blameBackEnd');
    //Create a div in bad flashy color
    var div = document.createElement('div');
    div.style.backgroundColor = 'red';
    div.style.color = 'white';
    div.style.position = 'fixed';
    div.style.top = '0';
    div.style.left = '0';
    div.style.width = '100%';
    div.style.height = '100%';
    div.style.zIndex = '1000';
    div.style.padding = '10px';
    div.style.textAlign = 'center';
    div.innerHTML = 'Erreur serveur, allez taper sur le développeur back-end';
    document.body.appendChild(div);

    // Fill in the space with advertising
    var advertising = document.createElement('div');
    advertising.style.backgroundColor = 'white';
    advertising.style.color = 'black';
    advertising.style.position = 'fixed';
    advertising.style.top = '50%';
    advertising.style.left = '50%';
    advertising.style.width = '100%';
    advertising.style.height = '20%';
    advertising.style.transform = 'translate(-50%, -50%)';
    advertising.style.zIndex = '1000';
    advertising.style.padding = '10px';
    advertising.style.textAlign = 'center';
    advertising.innerHTML = 'Achetez nos produits!';
    //make the advert flash
    setInterval(function() {
        if (advertising.style.backgroundColor === 'white') {
            advertising.style.backgroundColor = 'black';
            advertising.style.color = 'white';
        } else {
            advertising.style.backgroundColor = 'white';
            advertising.style.color = 'black';
        }
    }, 500);

    document.body.appendChild(advertising);

    

    // Add a button to close the div
    var button = document.createElement('button');
    button.style.position = 'fixed';
    button.style.top = '0';
    button.style.right = '0';
    button.style.zIndex = '1001';
    button.innerHTML = 'X';
    button.addEventListener('click', function() {
        div.remove();
        advertising.remove();
        button.remove();
    });
    document.body.appendChild(button);

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



// contact.js
document.querySelector('.contactForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var inputName1 = document.getElementById('inputName1').value;
    var inputName2 = document.getElementById('inputName2').value;
    var inputEmail4 = document.getElementById('inputEmail4').value;
    var inputCity = document.getElementById('inputCity').value;
    var inputPhone = document.getElementById('inputPhone').value;
    var inputMessage = document.getElementById('inputMessage').value;
    var exampleFormControlTextarea1 = document.getElementById('exampleFormControlTextarea1').value;

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

    console.log(data);

    //store the data in the local storage, for automatic filling of the form
    localStorage.setItem('contactData', JSON.stringify(data));

    // Call the function to blame the back-end
    blameBackEnd();

});