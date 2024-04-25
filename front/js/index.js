var CARBON_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/';

function loadPage() {
    $(document).ready(function() {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`, function() {
            isConnected();
            if(page === 'profil') {
                loadCarbonHistory();
            }
        });
    });
}

function isConnected() {
    console.log(document)
    const profilNav = document.querySelector('#profilNav')
    const loginNav = document.querySelector('#loginNav')
    if(sessionStorage.getItem("jwt") === null) {
        profilNav.style.display = 'none';
        loginNav.style.display = 'flex';
    } else { // on est connecté
        profilNav.style.display = 'flex';
        loginNav.style.display = 'none';
    }
}

function loadCarbonHistory() {
    const headers = new Headers();
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    headers.append("U-Login", sessionStorage.getItem("U-Login"));
    fetch(CARBON_BACKEND_ENDPOINT, {
        method: 'GET',
        headers: headers,
    }).then(response => {
        if (response.ok) {
            console.log('Response: ', response);
            return response.json();
        } else {
            console.log('Response: ', response);
            // Hide processing message
            toggleProcessingMessage(false);
            //stay on the same page
            throw new Error("Erreur lors de la déconnexion.")
        }
    }).then(json => {
        const bilanSection = document.querySelector('#historiqueBilans')
        // document.createElement
        console.log(json);
    }).catch(err => {
        serverError(err);
    });
}

loadPage();