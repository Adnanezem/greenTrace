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
                        let clonedCard = card.cloneNode(true); // Clone the card
                        clonedCard.querySelector('button').textContent = 'Modifier'; // Change the button text in the cloned card
                        cardListUser.appendChild(clonedCard); // Append the cloned card to the user's card list
                    });
                    div.appendChild(button);

                    card.appendChild(div);

                    cardListNew.appendChild(card);
                });
            });
        });
}

console.log('formulaire.js loaded');

// Call the function to generate the cards
generateCardsFromJson();