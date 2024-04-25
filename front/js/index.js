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
function updateNavSpacing() {
    const links = document.querySelectorAll('.nav-items');
    const visibleLinks = Array.from(links).filter(link => link.style.visibility !== 'hidden');

    visibleLinks.forEach(link => {
        link.style.flex = `1 0 ${100 / visibleLinks.length}%`;
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
    updateNavSpacing();
}

function loadCarbonHistory() {
    const headers = new Headers();
    const login = sessionStorage.getItem("U-Login");
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    headers.append("U-Login", login);
    fetch(CARBON_BACKEND_ENDPOINT + login + "/history", {
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