

function loadPage() {
    $(document).ready(function() {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`);
        isConnected();
    });
}

function isConnected() {
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

loadPage();