var COMPUTE_FORM_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/compute';

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
        processingDiv.textContent = 'Processing your request...';
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

function serverError(comment) {
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
    div.innerHTML = 'Erreur serveur: ' + comment;
    document.body.appendChild(div);

    setTimeout(function() {
        div.remove();
    }
    , 5000);
}

function serverSuccess(comment) {
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


// function to generate the field form from a json file
function generateFormFromJson(card, modify = false) {

    //console.log('generateFormFromJson:');
    //console.log(card);
    //console.log('modify: ' + modify);


    
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

        console.log(field);

        // We switch on the field type
        switch (field.type) {
            case 'number input':
                // We create an input element
                let number_input = document.createElement('input');
                number_input.type = 'number';
                number_input.name = field.name;
                number_input.placeholder = field.unit;
                if (modify) {
                    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
                    let cardIndex = cardSelection.length - 1;
                    if (cardSelection[cardIndex][field.name]) {
                        number_input.value = cardSelection[cardIndex][field.name];
                    }
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
                    optionElement.textContent = option;
                    select.appendChild(optionElement);
                });

                if (modify) {
                    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
                    let cardIndex = cardSelection.length - 1;
                    if (cardSelection[cardIndex][field.name]) {
                        select.value = cardSelection[cardIndex][field.name];
                    }
                }

                fieldDiv.appendChild(select);
                break;
            case 'time':
                // We create an input element
                let time_input = document.createElement('input');
                time_input.type = 'time';
                time_input.name = field.name;

                if (modify) {
                    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
                    let cardIndex = cardSelection.length - 1;
                    if (cardSelection[cardIndex][field.name]) {
                        time_input.value = cardSelection[cardIndex][field.name];
                    }
                }

                fieldDiv.appendChild(time_input);
                break;
            case 'color input':
                // We create an input element
                let color_input = document.createElement('input');
                color_input.type = 'color';
                color_input.name = field.name;

                if (modify) {
                    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
                    let cardIndex = cardSelection.length - 1;
                    if (cardSelection[cardIndex][field.name]) {
                        color_input.value = cardSelection[cardIndex][field.name];
                    }
                }

                fieldDiv.appendChild(color_input);
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
            form.style.transition = 'transform 0.5s ease, opacity 0.5s ease';
            form.style.transform = 'translateY(-100%)';
            form.style.opacity = '0';
            setTimeout(() => {
                form.remove();
            }, 1000);
            document.removeEventListener('keydown', closeForm);
        }
    }

    document.addEventListener('keydown', closeForm);

    // If in modify mode, we remove the last card from the cardSelection
    if (modify) {
        let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
        cardSelection.pop();
        localStorage.setItem('cardSelection', JSON.stringify(cardSelection));
    }

    // We add an event listener to the form (not modify)
    formElement.addEventListener('submit', function(event) {
        event.preventDefault();

        //Check if the form is filled
        if (!check_form_filled(form)) {
            return;
        }

        // check the card
        //console.log("Card: ", card);
        let data = {};
        data.category = card.category;
        card.fields.forEach(field => {
            data[field.name] = formElement.querySelector('[name="' + field.name + '"]').value;
        });
        //console.log('submit Data:');
        //console.log(data);
        //console.log('----');

        // Load the "cardSelection" from localStorage
        let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];

        // Add the new card to the "cardSelection"
        cardSelection.push(data);

        // Have the form slide and fade out
        form.style.transition = 'transform 1s ease, opacity 1s ease';
        form.style.transform = 'translateY(-100%)';
        form.style.opacity = '0';

        // After a delay, we remove the form
        setTimeout(() => {
            form.remove();
        }, 1000);

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
    let pass = true;
    fields.forEach(field => {
        if (field.value === '') {
            pass = false;
            alert('Please fill in all the fields');
        }
    });

    //check if the distances are rational in the case of "distance traveled"
    let distance = form.querySelector('[name="distance traveled"]');
    if (distance) {
        if (distance.value < 1) {
            pass = false;
            alert('Please enter a positive distance');
        }
        //limit to 100000 km
        if (distance.value > 100000) {
            pass = false;
            alert('Please enter a distance less than 100000 km');
        }
    }

    return pass;
}

//function to generate the card's div
function generateCardDiv(title, description, background_icon, background_alt, button_text, button_function) {
    //create a div element
    let card = document.createElement('div');
    card.className = 'card';
    //create an img element
    let img = document.createElement('img');
    img.src = background_icon;
    img.alt = background_alt;
    //create a div element
    let cardContent = document.createElement('div');
    cardContent.className = 'card-content';
    //create a h3 element
    let cardTitle = document.createElement('h3');
    cardTitle.textContent = title;
    //create a p element
    let cardDescription = document.createElement('p');
    cardDescription.textContent = description;
    //create a button element
    let cardButton = document.createElement('button');
    cardButton.textContent = button_text;
    cardButton.addEventListener('click', button_function);
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
                    let card = generateCardDiv(item.name, item.description, item.image.icon, item.image.alt, 'Remplir', function() {
                        console.log('Remplir button clicked');
                        console.log(item);
                        console.log('category: ' + item.category);
                        // Open the form
                        generateFormFromJson(item);
                    }
                    );
                    cardListNew.appendChild(card);
                });
            });
        });
}

// Function to load the saved cards from localStorage
function loadSavedCards() {
    // Show processing message
    //toggleProcessingMessage(true);

    // Load the "cardSelection" from localStorage
    let cardSelection = JSON.parse(localStorage.getItem('cardSelection')) || [];
    console.log('cardSelection:');
    console.log(cardSelection);
    console.log('----');
    cardSelection.forEach(card => {
        // Create the cards using the data from the localStorage and generateCardDiv
        /*let carddiv = generateCardDiv(card.name, card.description, 'images/transport.png', 'Transport', 'Modifier', function() {
            console.log('Modifier button clicked');
            // Open the form
            generateFormFromJson(carddiv, true);
        }
        );
        let cardListUser = document.getElementById('cardListUser');
        cardListUser.appendChild(card);*/

        let cardListUser = document.getElementById('cardListUser'); // Get the user's card list

        // Clone the card and change the button text to "Modifier"
        let cardElement = document.createElement('div');
        cardElement.className = 'card';

        let div = document.createElement('div');

        let h3 = document.createElement('h3');
        h3.textContent = card.category;
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

    // Save the cardSelection to localStorage
    localStorage.setItem('cardSelection', JSON.stringify(cardSelection));

    // Display the card list
    console.log(cardSelection);

    // Hide processing message
    //toggleProcessingMessage(false);
}

console.log('formulaire.js loaded');

function sendFormData(formData) {
    console.log('sendFormData:');
    const data = {
        "form" : formData,
        "U-Login" : sessionStorage.getItem("U-Login"),
    }
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    headers.append("U-Login", sessionStorage.getItem("U-Login"));
    console.log('data', data);
    fetch(COMPUTE_FORM_BACKEND_ENDPOINT, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(data),
    })
    .then(response => {
        if (response.ok) {
            console.log('Response: ', response);
            //success message
            serverSuccess('Carbon footprint calculated successfully');
            return response.json();
        } else {
            console.log('Response: ', response);
            //stay on the same page
            throw new Error("Erreur lors de l\'envoie du formulaire.")
        }
    }).then(json =>  {
        console.log(json);
        return json;
    }).catch(err => {

        serverError(err);
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
            alert('Please add at least one card');
            return;
        }
        //display the card list
        console.log(cardSelection);

        // Show processing message
        toggleProcessingMessage(true);

        //send the card list to the server
        sendFormData(cardSelection);

        // Clear the card list
        localStorage.setItem('cardSelection', JSON.stringify([]));

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