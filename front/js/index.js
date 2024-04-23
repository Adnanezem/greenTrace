

function loadPage() {
    $(document).ready(function() {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`, function() {
            isConnected();
        });
    });
}

function isConnected() {
    console.log(document)
    const profilNav = document.querySelector('#profilNav')
    const loginNav = document.querySelector('#loginNav')
    const logoutNav = document.querySelector('#logoutNav');
    console.log('elem profil: ', profilNav)
    console.log('elem login: ', loginNav)
    if(sessionStorage.getItem("jwt") === null) {
        profilNav.style.display = 'none';
        logout.style.display = 'none';
        loginNav.style.display = 'flex';
    } else { // on est connecté
        profilNav.style.display = 'flex';
        logout.style.display = 'flex';
        loginNav.style.display = 'none';
    }
}

loadPage();