

function loadPage() {
    $(document).ready(function() {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`);
    });
}

function isConnected() {
    const profilNav = document.querySelector('#profilNav')
    const loginNav = document.querySelector('#loginNav')
    if(sessionStorage.getItem("jwt") === null) {
        profilNav.hidden = true;
        loginNav.hidden = false;
    } else { // on est connecté
        profilNav.hidden = false;
        loginNav.hidden = true;
    }
}

loadPage();