

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
        profilNav.display = 'none';
        loginNav.display = 'flex';
    } else { // on est connecté
        profilNav.display = 'flex';
        loginNav.hidden = 'none';
    }
}

loadPage();