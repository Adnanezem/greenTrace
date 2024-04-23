
var COMPUTE_FORM_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/compute';


// function to generate the field form from a json file
function generateFormFromJson(card, modify = false) {
    
    // We create a floating div to contain the form
    let form = document.createElement('div');
    form.className = 'floating_form';

    // We create a form element
    let formElement = document.createElement('form');
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

    // We loop through the fields
    card.fields.forEach(field => {
        // We create a div element to contain the field
        let fieldDiv = document.createElement('div');
        fieldDiv.className = 'form_field';
        fields.appendChild(fieldDiv);

        // We create a label element
        let label = document.createElement('label');
        label.textContent = field.name;
        fieldDiv.appendChild(label);

        // We switch on the field type
        switch (field.type) {
            case 'number input':
                // We create an input element
                let number_input = document.createElement('input');
                number_input.type = 'number';
                number_input.name = field.name;
                number_input.placeholder = field.unit;
                fieldDiv.appendChild(number_input);
                break;
            case 'scrolllist':
                // We create a select element
                let select = document.createElement('select');
                select.name = field.name;
                field.options.forEach(option => {
                    let optionElement = document.createElement('option');
                    optionElement.value = option;
                    optionElement.textContent = option;
                    select.appendChild(optionElement);
                });
                fieldDiv.appendChild(select);
                break;
            case 'time':
                // We create an input element
                let time_input = document.createElement('input');
                time_input.type = 'time';
                time_input.name = field.name;
                fieldDiv.appendChild(time_input);
                break;
            default:
                console.error('Unknown field type: ' + field.type);
                break;
        }


    });

    // We create a button element
    let button = document.createElement('button');
    button.textContent = 'Envoyer';
    formElement.appendChild(button);

    // We append the form to the body
    document.body.appendChild(form);

    // If user presses the "esc" key, we close the form, and remove the event listener
    let closeForm = function(event) {
        if (event.key === 'Escape') {
            form.remove();
            document.removeEventListener('keydown', closeForm);
        }
    }

    document.addEventListener('keydown', closeForm);

    // We add an event listener to the form
    formElement.addEventListener('submit', function(event) {
        event.preventDefault();

        //Check if the form is filled
        if (!check_form_filled(form)) {
            alert('Please fill all the fields');
            return;
        }

        // check the type of the card
        console.log(card.type);
        let data = {};
        card.fields.forEach(field => {
            data[field.name] = formElement.querySelector('[name="' + field.name + '"]').value;
        });
        console.log(data);

        // Load the "cardSelection" from localStorage
        let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];

        // Add the new card to the "cardSelection"
        cardSelection.push(data);

        // We close the form
        form.remove();

        // We display a success message
        let successMessage = document.createElement('div');
        successMessage.textContent = 'Formulaire envoyé avec succès';
        document.body.appendChild(successMessage);
        setTimeout(() => {
            successMessage.remove();
        }, 3000);

        let cardListUser = document.getElementById('cardListUser'); // Get the user's card list

        // Clone the card and change the button text to "Modifier"
        let cardElement = document.createElement('div');
        cardElement.className = 'card';

        let div = document.createElement('div');

        let h3 = document.createElement('h3');
        h3.textContent = card.name;
        div.appendChild(h3);

        let p = document.createElement('p');
        p.textContent = card.description;
        div.appendChild(p);

        let button = document.createElement('button');
        button.textContent = 'Modifier';
        button.addEventListener('click', function() {
            console.log('Modifier button clicked');
            // Open the form
            generateFormFromJson(card, true);
        });
        div.appendChild(button);

        cardElement.appendChild(div);

        cardListUser.appendChild(cardElement);

        // Save the card to localStorage
        localStorage.setItem('cardSelection', JSON.stringify(cardSelection));
    });
}

// Function to check if the form is filled
function check_form_filled(form) {
    let fields = form.querySelectorAll('input');
    let filled = true;
    fields.forEach(field => {
        if (field.value === '') {
            filled = false;
        }
    });
    return filled;
}

// Function to generate the cards from a json file
function generateCardsFromJson() {
    fetch('../json/cards.json')
        .then(response => response.json())
        .then(data => {
            let cardListNew = document.getElementById('cardListNew');
            let cardListUser = document.getElementById('cardListUser'); // Get the user's card list
            ['transport', 'repas', 'loisirs'].forEach(category => {
                data[category].forEach(item => {
                    let card = document.createElement('div');
                    card.className = 'card';

                    let img = document.createElement('img');
                    img.src = item.image.icon;
                    img.alt = item.image.alt;
                    card.appendChild(img);

                    let div = document.createElement('div');

                    let h3 = document.createElement('h3');
                    h3.textContent = item.name;
                    div.appendChild(h3);

                    let p = document.createElement('p');
                    p.textContent = item.description;
                    div.appendChild(p);

                    let button = document.createElement('button');
                    button.textContent = 'Ajouter';
                    button.addEventListener('click', function() { // Add event listener
                        console.log('Ajouter button clicked');
                        // Open the form
                        generateFormFromJson(item); // Generate the form
                    });
                    div.appendChild(button);

                    card.appendChild(div);

                    cardListNew.appendChild(card);
                });
            });
        });
}

// Function to load the saved cards from localStorage
function loadSavedCards() {
    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
    cardSelection.forEach(card => {
        let cardElement = document.createElement('div');
        cardElement.className = 'card';

        let div = document.createElement('div');

        let h3 = document.createElement('h3');
        h3.textContent = card.name;
        div.appendChild(h3);

        let p = document.createElement('p');
        p.textContent = card.description;
        div.appendChild(p);

        let button = document.createElement('button');
        button.textContent = 'Modifier';
        button.addEventListener('click', function() {
            console.log('Modifier button clicked');
            // Open the form
            generateFormFromJson(card, true);
        });
        div.appendChild(button);

        cardElement.appendChild(div);

        cardListUser.appendChild(cardElement);
    });
}

// Function to save a card to localStorage
function saveCard(card) {
    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
    cardSelection.push(card);
    localStorage.setItem('cardSelection', JSON.stringify(cardSelection));
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
            alert('Please add at least one card');
            return;
        }
        //display the card list
        console.log(cardSelection);

        //display a success message
        let successMessage = document.createElement('div');
        successMessage.textContent = 'Cartes envoyées avec succès';
        document.body.appendChild(successMessage);
        setTimeout(() => {
            successMessage.remove();
        }, 3000);

    });
}

console.log('formulaire.js loaded');

function sendFormData(data) {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    fetch(COMPUTE_FORM_BACKEND_ENDPOINT, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data),
    })
    .then(response => {
        if (response.ok) {
            console.log('Response: ', response);
            return response.json();
        } else {
            console.log('Response: ', response);
            // Hide processing message
            toggleProcessingMessage(false);
            //stay on the same page
            throw new Error("Erreur lors de l\'envoie du formulaire.")
        }
    }).then(json =>  {
        return json;
    }).catch(err => {
        serverError(err);
    });
}

// Call the function to generate the cards
generateCardsFromJson();

// Call the function to load the saved cards
loadSavedCards();

// Call the function to make the "send" button work
sendForm();