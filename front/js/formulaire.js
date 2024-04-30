var COMPUTE_FORM_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/compute';

import { toFrench, toEnglish } from './dictionnary.js';

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
        processingDiv.style.transition = 'opacity 0.5s';
        processingDiv.textContent = 'Requête en cours de traitement...';
        document.body.appendChild(processingDiv);
    }
    processingDiv.style.opacity = show ? '1' : '0';

    if (!show) {
        setTimeout(function() {
            processingDiv.remove();
        }
        , 500);
    }
}

function ErrorMessage(comment, server = true) {
    toggleProcessingMessage(false);
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
    if (server) {
        div.innerHTML = 'Erreur serveur: ' + comment;
    } else {
        div.innerHTML = 'Erreur: ' + comment;
    }
    document.body.appendChild(div);

    setTimeout(function() {
        div.remove();
    }
    , 5000);
}

function SuccessMessage(comment) {
    toggleProcessingMessage(false);
    var div = document.createElement('div');
    div.style.backgroundColor = 'green';
    div.style.color = 'white';
    div.style.position = 'fixed';
    div.style.top = '0';
    div.style.left = '0';
    div.style.width = '100%';
    div.style.zIndex = '1000';
    div.style.padding = '10px';
    div.style.textAlign = 'center';
    div.innerHTML = 'Success: ' + comment;
    document.body.appendChild(div);

    setTimeout(function() {
        div.remove();
    }
    , 5000);
}


function closeForm(form) {
    form.style.transition = 'transform 0.5s ease, opacity 0.5s ease';
    form.style.transform = 'translateY(-100%)';
    form.style.opacity = '0';
    setTimeout(() => {
        form.remove();
    }, 1000);
}

// function to generate the field form from a json file
async function generateFormFromJson(cardJson, modify = false) {

    // make the promise
    return new Promise((resolve, reject) => {

        //console.log('generateFormFromJson:');
        //console.log(cardJson);
        //console.log('----');

        // We create a floating div to contain the form
        let form = document.createElement('div');
        form.className = 'floating_form';

        // We create a form element
        let formElement = document.createElement('form');

        // Have the form slide and fade in
        form.style.transition = 'transform 0.5s ease, opacity 0.5s ease';
        form.style.transform = 'translateY(-100%)';
        form.style.opacity = '0';

        // After a delay, we have the form slide and fade in
        setTimeout(() => {
            form.style.transform = 'translateY(0)';
            form.style.opacity = '1';
        }, 100);
        
        form.appendChild(formElement);

        // We create a fieldset element
        let fieldset = document.createElement('fieldset');
        formElement.appendChild(fieldset);

        // We create a legend element
        let legend = document.createElement('legend');
        legend.textContent = 'Formulaire';
        fieldset.appendChild(legend);

        // We create a div element to contain the fields
        let fields = document.createElement('div');
        fields.className = 'form_field_container';
        fieldset.appendChild(fields);

        let data = null;

        if (modify) {

            // We get the data from the saved card
            let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
            //if cardSelection is empty, we set data to null
            if (cardSelection.length > 0) {
                // if the card is already saved, we get the data
                let index = cardSelection.findIndex(card => card.id === cardJson.id);
                if (index !== -1) {
                    data = cardSelection[index].data;
                }
            }

            // Add a Delete button
            let deleteButton = document.createElement('button');
            deleteButton.textContent = 'Supprimer';
            deleteButton.type = 'button';
            formElement.appendChild(deleteButton);

            // Add an event listener to the Delete button
            deleteButton.addEventListener('click', function() {
                // We remove the card from the saved cards
                let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
                let index = cardSelection.findIndex(card => card.id === cardJson.id);
                if (index !== -1) {
                    cardSelection.splice(index, 1);
                    localStorage.setItem('cardSelection', JSON.stringify(cardSelection));
                }

                // We remove the card from the DOM
                let cardListUser = document.getElementById('cardListUser');
                let cards = cardListUser.querySelectorAll('.card');
                cards.forEach(card => {
                    if (card.querySelector('h3').textContent === cardJson.name) {
                        card.remove();
                    }
                });

                // We close the form
                closeForm(form);

                // We display a success message
                let successMessage = document.createElement('div');
                successMessage.textContent = 'Carte supprimée avec succès';
                document.body.appendChild(successMessage);
                setTimeout(() => {
                    successMessage.remove();
                }, 3000);
            });
        }

        // We loop through the fields
        cardJson.fields.forEach(field => {
            // We create a div element to contain the field
            let fieldDiv = document.createElement('div');
            fieldDiv.className = 'form_field';
            fields.appendChild(fieldDiv);

            // We create a label element
            let label = document.createElement('label');
            label.textContent = toFrench(field.name);
            fieldDiv.appendChild(label);

            //console.log(field);

            // We switch on the field type
            switch (field.type) {
                case 'number input':
                    // We create an input element
                    let number_input = document.createElement('input');
                    number_input.type = 'number';
                    number_input.name = field.name;
                    number_input.placeholder = field.unit;
                    // Do not go under 1
                    number_input.min = 1;
                    if (modify && data) {
                        number_input.value = data[field.name];
                    }
                    fieldDiv.appendChild(number_input);
                    break;
                case 'scrolllist':
                    // We create a select element
                    let select = document.createElement('select');
                    select.name = field.name;
                    field.options.forEach(option => {
                        let optionElement = document.createElement('option');
                        optionElement.value = option;
                        optionElement.textContent = toFrench(option);
                        select.appendChild(optionElement);
                    });

                    if (modify && data) {
                        select.value = data[field.name];
                    }

                    fieldDiv.appendChild(select);
                    break;
                case 'time input':
                    // Create 3 number input elements for hours, minutes and seconds
                    let time_input = document.createElement('div');
                    time_input.className = 'time_input';

                    let hours = document.createElement('input');
                    hours.type = 'number';
                    hours.name = field.name + '_hours';
                    hours.min = 0;
                    hours.max = 23;
                    hours.style.width = '50px';
                    time_input.appendChild(hours);

                    let minutes = document.createElement('input');
                    minutes.type = 'number';
                    minutes.name = field.name + '_minutes';
                    minutes.min = 0;
                    minutes.max = 59;
                    minutes.style.width = '50px';
                    time_input.appendChild(minutes);

                    let seconds = document.createElement('input');
                    seconds.type = 'number';
                    seconds.name = field.name + '_seconds';
                    seconds.min = 0;
                    seconds.max = 59;
                    seconds.style.width = '50px';
                    time_input.appendChild(seconds);

                    if (modify && data) {
                        let time = data[field.name].split(':');
                        hours.value = time[0];
                        minutes.value = time[1];
                        seconds.value = time[2];
                    }

                    fieldDiv.appendChild(time_input);
                    break;
                case 'color input':
                    // We create an input element
                    let color_input = document.createElement('input');
                    color_input.type = 'color';
                    color_input.name = field.name;

                    if (modify && data) {
                        color_input.value = data[field.name];
                    }

                    fieldDiv.appendChild(color_input);
                    break;
                case 'percentage input':
                    //add a slider between 0 and 100
                    let percentage_input = document.createElement('input');
                    percentage_input.type = 'range';
                    percentage_input.name = field.name;
                    percentage_input.min = 0;
                    percentage_input.max = 100;
                    percentage_input.step = 1;
                    if (modify && data) {
                        percentage_input.value = data[field.name];
                    }

                    //add a number input to display the value of the slider
                    let percentage_number = document.createElement('input');
                    percentage_number.type = 'number';
                    percentage_number.name = field.name;
                    percentage_number.min = 0;
                    percentage_number.max = 100;
                    percentage_number.value = 50;
                    percentage_number.step = 1;
                    percentage_number.style.width = '50px';
                    if (modify && data) {
                        percentage_number.value = data[field.name];
                    }

                    //add an event listener to the slider to update the number input
                    percentage_input.addEventListener('input', function() {
                        percentage_number.value = percentage_input.value;
                    }
                    );

                    //add an event listener to the number input to update the slider
                    percentage_number.addEventListener('input', function() {
                        percentage_input.value = percentage_number.value;
                    }
                    );

                    fieldDiv.appendChild(percentage_input);
                    fieldDiv.appendChild(percentage_number);
                    break;
                default:
                    console.error('Unknown field type: ' + field.type);
                    break;
            }
        });

        

        // We create a button element
        let button = document.createElement('button');
        formElement.appendChild(button);
        // if modify is true, we set the button text to "Modifier", else we set it to "Envoyer"
        button.textContent = modify ? 'Modifier' : 'Envoyer';
        button.type = 'submit';

        // Add a cancel button
        let cancel = document.createElement('button');
        cancel.textContent = 'Annuler';
        cancel.type = 'button';
        formElement.appendChild(cancel);

        // We append the form to the body
        document.body.appendChild(form);

        // If cancel button is pressed, or escape key is pressed, we close the form
        cancel.addEventListener('click', function() {
            closeForm(form);
        });
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeForm(form);
            }
        });

        // We add an event listener to the form if the "Envoyer" button is clicked
        formElement.addEventListener('submit', function(event) {
            event.preventDefault();
        
            //Check if the form is filled
            if (check_form_filled(form)) {
                // We create a json with the data
                let data = {};

                data.category = cardJson.category;
                data.type = cardJson.name;
                cardJson.fields.forEach(field => {
                    switch (field.type) {
                        case 'number input':
                            data[field.name] = form.querySelector('[name="' + field.name + '"]').value;
                            break;
                        case 'scrolllist':
                            data[field.name] = form.querySelector('[name="' + field.name + '"]').value;
                            break;
                        case 'time input':
                            let hours = form.querySelector('[name="' + field.name + '_hours"]').value;
                            let minutes = form.querySelector('[name="' + field.name + '_minutes"]').value;
                            let seconds = form.querySelector('[name="' + field.name + '_seconds"]').value;
                            data[field.name] = hours + ':' + minutes + ':' + seconds;
                            break;
                        case 'color input':
                            data[field.name] = form.querySelector('[name="' + field.name + '"]').value;
                            break;
                        default:
                            console.error('Unknown field type: ' + field.type);
                            break;
                    }
                });
            
                // We close the form
                closeForm(form);
            
                // We display a success message
                let successMessage = document.createElement('div');
                successMessage.textContent = 'Formulaire envoyé avec succès';
                document.body.appendChild(successMessage);
                setTimeout(() => {
                    successMessage.remove();
                }, 3000);
            
                resolve({complete: true, data: data}); // Resolve the promise here
            }

            
        });

    });


}

// Function to check if the form is filled
function check_form_filled(form) {
    let fields = form.querySelectorAll('input');
    let pass = true;
    fields.forEach(field => {
        if (field.value === '') {
            pass = false;
        }
    });

    if (!pass) {
        alert('Veuillez remplir tous les champs');
    }

    //check if the distances are rational in the case of "distance traveled"
    let distance = form.querySelector('[name="distance traveled"]');
    if (distance) {
        if (distance.value < 1) {
            pass = false;
            alert('Entrez une distance supérieure à 0 km');
        }
        //limit to 100000 km
        if (distance.value > 100000) {
            pass = false;
            alert('Entrez une distance inférieure à 100000 km');
        }
    }

    return pass;
}

function addNewCard(cardJson) {
    // Create a random ID
    let cardID = Math.random().toString(36).substring(7);
    //console.log('cardID: ' + cardID);

    // Replace the "Ajouter" of the cardJson description with "Modifier"
    cardJson.description = cardJson.description.replace('Ajouter', 'Modifier');

    // Add the ID to cardJson
    cardJson.id = cardID;


    //console.log('cardJson:');
    //console.log(cardJson);
    //console.log('----');

    // Create a form from the cardJson
    generateFormFromJson(cardJson).then(form => {

        //console.log('form:');
        //console.log(form);
        //console.log('----');

        // If the form is complete, we add the card to the user's card list
        if (form.complete) {

            // Create a json with infos: the data, the cardJSON, and the ID
            let savedCard = {
                data: form.data,
                cardJson: cardJson,
                id: cardID
            };

            // Save the card
            saveCard(savedCard);

            // Create a card element
            let cardElement = generateCardDiv(cardJson, false);

            cardListUser.appendChild(cardElement);

            // Display a success message
            SuccessMessage('Carte ajoutée avec succès!');
        }
    }
    );

}

//Function to update a card in the saved data
function saveCard(savedCard) {
    // Load the "cardSelection" from localStorage
    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];

    // If the card already exists, we remove it
    let index = cardSelection.findIndex(card => card.id === savedCard.id);
    if (index !== -1) {
        cardSelection.splice(index, 1);
    }

    // Add the updated card to the "cardSelection"
    cardSelection.push(savedCard);

    // Save the card to localStorage
    localStorage.setItem('cardSelection', JSON.stringify(cardSelection));
}

//function to generate the card's div
function generateCardDiv(cardJson, isPlaceholder = true) {

    //create a div element
    let card = document.createElement('div');
    card.className = 'card';

    //create an img element
    let img = document.createElement('img');
    img.src = cardJson.image.icon;
    img.alt = cardJson.image.alt;

    //create a div element
    let cardContent = document.createElement('div');
    cardContent.className = 'card-content';

    //create a h3 element
    let cardTitle = document.createElement('h3');
    cardTitle.textContent = cardJson.name;

    //create a p element
    let cardDescription = document.createElement('p');
    cardDescription.textContent = cardJson.description;

    //create a button element
    let cardButton = document.createElement('button');

    //Depending on the button text, we add an event listener to the button
    if (isPlaceholder) {
        cardButton.textContent = "Ajouter";
        // call addNewCard function
        cardButton.addEventListener('click', function() {
            addNewCard(cardJson);
        });
        //disable the card if disabled is true
        if (cardJson.disabled) {
            cardButton.disabled = true;
            //add a gray filter to the card
            card.style.filter = 'grayscale(100%)';

            //add a h1 element to the card, vertically and horizontally centered
            let disabledText = document.createElement('h1');
            disabledText.style.position = 'absolute';
            disabledText.style.top = '70%';
            disabledText.style.left = '50%';
            disabledText.style.transform = 'translate(-50%, -50%)';

            //set the text of the h1 element to "Indisponible"
            disabledText.textContent = 'Indisponible';
            card.appendChild(disabledText);
        }
    } else {
        cardButton.textContent = "Modifier";
        // call generateFormFromJson function
        cardButton.addEventListener('click', function() {
            generateFormFromJson(cardJson, true).then(form => {
                //console.log('Modify button, opening form:');
                //console.log(form);
                //console.log('----');
                if (form.complete) {
                    // Create a json with infos: the data, the cardJSON, and the ID
                    let savedCard = {
                        data: form.data,
                        cardJson: cardJson,
                        id: cardJson.id
                    };

                    // Save the card
                    saveCard(savedCard);

                    // Display a success message
                    SuccessMessage('Carte modifiée avec succès!');
                }
            });
        });
    }

    //append the elements to the card
    card.appendChild(img);
    cardContent.appendChild(cardTitle);
    cardContent.appendChild(cardDescription);
    cardContent.appendChild(cardButton);
    card.appendChild(cardContent);
    //return the card
    return card;
}

// Function to generate the cards from a json file
function generateCardsFromJson() {
    fetch('../json/cards.json')
        .then(response => response.json())
        .then(data => {
            let cardListNew = document.getElementById('cardListNew');
            ['transport', 'repas', 'loisirs'].forEach(category => {
                data[category].forEach(item => {
                    let card = generateCardDiv(item, true);
                    cardListNew.appendChild(card);
                });
            });
        });
}

// Function to load the saved cards from localStorage
function loadSavedCards() {
    //console.log('loadSavedCards:');
    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
    let cardListUser = document.getElementById('cardListUser');
    cardSelection.forEach(savedCard => {
        let card = generateCardDiv(savedCard.cardJson, false);
        cardListUser.appendChild(card);
    });
    //console.log('----');
}

function sendFormData(formData) {
    //console.log('sendFormData:');
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    const token = sessionStorage.getItem("jwt");
    let endpoint = COMPUTE_FORM_BACKEND_ENDPOINT;
    if(token) {
        headers.append("Authorization", sessionStorage.getItem("jwt"));
        endpoint += "/connect";
    } else {
        endpoint += "/unconnect";
    }
    headers.append("U-Login", sessionStorage.getItem("U-Login"));
    fetch(endpoint, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(formData),
    })
    .then(response => {
        if (response.ok) {
            //success message
            SuccessMessage('Emmission de CO2 calculée avec succès!');
            return response.json();
        } else {
            //console.log('Response: ', response);
            //stay on the same page
            throw new Error("Erreur lors de l\'envoie du formulaire.")
        }
    }).then(json =>  {
        //console.log(json);
        return json;
    }).catch(err => {

        ErrorMessage(err);
    });
}

//Function to make the "send" button work
function sendForm() {
    //get button of id "sendCards"
    let sendButton = document.getElementById('sendCards');
    //add event listener to the button
    sendButton.addEventListener('click', function() {
        //get the card list in the localStorage
        let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
        //check if the card list is empty
        if (cardSelection.length === 0) {
            alert('Aucune carte à envoyer!');
            return;
        }
        //display the card list
        //console.log("cardSelection: ",cardSelection);

        // Show processing message
        toggleProcessingMessage(true);
        let dataToSend = {
            login: sessionStorage.getItem("U-Login"),
            form: []
        };

        cardSelection.forEach(elem => {
            dataToSend.form.push(elem.data);
        });
        //send the card list to the server
        sendFormData(dataToSend);

        // Clear the card list in the DOM (but keep the title) by moving the cards away
        let cardListUser = document.getElementById('cardListUser');
        let cards = cardListUser.querySelectorAll('.card');
        cards.forEach(card => {
            card.style.transition = 'transform 5s ease';
            card.style.transform = 'translateY(-1000%)';
        });
        // Delete the cards after a delay
        setTimeout(() => {
            cards.forEach(card => {
                card.remove();
            });
        }, 5000);

        // Clear the card list in localStorage
        localStorage.setItem('cardSelection', JSON.stringify([]));

        // Hide processing message
        //toggleProcessingMessage(false);

        // Display a success message
        //serverSuccess('Carbon footprint calculated successfully');

        // Redirect to the home page
        //window.location.href = './';

    });
}

// Call the function to generate the cards
generateCardsFromJson();

// Call the function to load the saved cards
loadSavedCards();

// Call the function to make the "send" button work
sendForm();