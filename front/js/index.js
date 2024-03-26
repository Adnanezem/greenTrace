$(document).ready(function() {

    var page = window.location.search.substring(1).split('=')[1] || 'accueil';
    $('#content').load(`./html/${page}.html`);
});