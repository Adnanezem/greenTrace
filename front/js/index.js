var CARBON_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/';

function loadPage() {
    $(document).ready(function () {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`, function () {
            isConnected();
            if (page === 'profil') {
                loadCarbonHistory();
            }
        });
    });
}

function isConnected() {
    console.log(document)
    const profilNav = document.querySelector('#profilNav')
    const loginNav = document.querySelector('#loginNav')
    if (sessionStorage.getItem("jwt") === null) {
        profilNav.style.display = 'none';
        loginNav.style.display = 'flex';
    } else { // on est connecté
        profilNav.style.display = 'flex';
        loginNav.style.display = 'none';
    }
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
            if (response.status === 404) {
                const bilanSection = document.querySelector('#historiqueBilans')
                const noContentTxt = document.createTextNode("Vous n'avez pas de données disponible.");
                bilanSection.appendChild(noContentTxt);
            } else {
                console.log('Response: ', response);
                toggleProcessingMessage(false);
                throw new Error("Erreur lors de la récupération de l'historique de l'utilisateur.")
            }
        }
    }).then(json => {
        if(json !== undefined) {
            const bilanSection = document.querySelector('#historiqueBilans')
            console.log(json);
            const avgBilan = json.historique.historique;
            const avgBilanTxt = document.createTextNode("Moyenne hebdomadaire: " + avgBilan);
            bilanSection.appendChild(avgBilanTxt);
            const currentDate = new Date();
            console.log('currentDate: ', currentDate);
            previousDate = getPreviousSevenDays(currentDate);
            histTab = document.createElement("table");
            histTab.id = "userHistTab";
            console.log('previousDate: ', previousDate);
            previousDate.forEach(async date => {
                const formattedDate = date.toLocaleDateString('fr-FR', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric'
                });
                const res = await loadCarbonHistoryDetail(formattedDate);
                const row = document.createElement("tr");
    
                const col1 = document.createElement("td");
                col1.textContent = formattedDate;
    
                const col2 = document.createElement("td");
                col2.textContent = res.resultat;
    
                row.appendChild(col1);
                row.appendChild(col2);
                histTab.appendChild(row)
            });
            bilanSection.appendChild(histTab);
        }
    }).catch(err => {
        serverError(err);
    });
}

function loadCarbonHistoryDetail(date) {
    const headers = new Headers();
    const login = sessionStorage.getItem("U-Login");
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    headers.append("U-Login", login);
    return fetch(CARBON_BACKEND_ENDPOINT + login + "/history/" + date, {
        method: 'GET',
        headers: headers,
    }).then(response => {
        if (response.ok) {
            console.log('Response: ', response);
            return response.json();
        } else {
            console.log('Response: ', response);
            toggleProcessingMessage(false);
            throw new Error("Erreur lors de la récupération des détail de l'historique de l'utilisateur.")
        }
    }).then(json => {
        console.log(json);
        return json;
    }).catch(err => {
        serverError(err);
    });
}

function getPreviousSevenDays(startDate) {
    const dates = [];
    const date = new Date(startDate);

    for (let i = 0; i < 7; i++) {
        dates.push(new Date(date.getDate() - i));
    }
    return dates;
}

loadPage();