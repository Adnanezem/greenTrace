var CARBON_BACKEND_ENDPOINT = 'https://192.168.75.79/back_test/carbon/';

function loadPage() {
    $(document).ready(function () {

        var page = window.location.search.substring(1).split('=')[1] || 'accueil';
        $('#content').load(`../html/${page}.html`, function () {
            isConnected();
            $.getScript('https://cdn.plot.ly/plotly-latest.min.js', function () {
                if (page === 'profil') {
                    loadCarbonHistory();
                }
            });
        });
    });
}

function serverError(comment) {
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
    div.innerHTML = 'Erreur: ' + comment;
    document.body.appendChild(div);

    setTimeout(function () {
        div.remove();
    }
        , 5000);
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
                throw new Error("Erreur lors de la récupération de l'historique de l'utilisateur.")
            }
        }
    }).then(async json => {
        if (json !== undefined) {
            const avgHist = document.querySelector('#histAvg');
            const bilanQuotidienDiv = document.querySelector('#bilanCO2Result');
            const avgBilan = json.historique.historique;
            const avgBilanTxt = document.createTextNode("Moyenne hebdomadaire: " + avgBilan + "g de carbone.");
            avgHist.appendChild(avgBilanTxt);
            const currentDate = new Date();
            previousDate = getPreviousSevenDays(currentDate);
            histTab = document.querySelector("#userHistTab tbody");
            carbon_history = await getHistoryDetail(currentDate, previousDate, bilanQuotidienDiv, histTab);
            data_avg_carbon_print = await getAvgUsersCarbonPrint();
            drawCarbonHistoryChart(carbon_history, data_avg_carbon_print);
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
            if (response.status !== 404) {
                throw new Error("Erreur lors de la récupération des détails de l'historique de l'utilisateur.");
            }
        }
    }).then(json => {
        return json;
    }).catch(err => {
        serverError(err);
    });
}

async function getHistoryDetail(currentDate, previousDate, bilanQuotidienDiv, histTab) {
    let carbon_hist = [];
    for (const date of previousDate) {
        const formattedDate = formatToSQLDate(date);
        const row = document.createElement("tr");
        const col1 = document.createElement("td");
        col1.textContent = formattedDate;
        const col2 = document.createElement("td");

        try {
            const res = await loadCarbonHistoryDetail(formattedDate);
            let finalRes = 0;
            res.journees.forEach(elem => {
                finalRes += elem.resultat;
            });
            col2.textContent = finalRes;
            if (date.toISOString() === currentDate.toISOString()) {
                bilanQuotidienDiv.innerHTML = "Votre résultat quotidien est : " + finalRes + " g de CO<sub>2</sub>.";
            }
            carbon_hist.push({
                date: formattedDate,
                result: finalRes
            });
        } catch (err) {
            col2.textContent = "Pas de bilan pour cette date.";
            if (date.toISOString() === currentDate.toISOString()) {
                bilanQuotidienDiv.textContent = "Vous n'avez pas réalisé de bilan carbone aujourd'hui.";
            }
            carbon_hist.push({
                date: formattedDate,
                result: 0
            });
        }

        row.appendChild(col1);
        row.appendChild(col2);
        histTab.appendChild(row);
    }
    return carbon_hist;
}

function getPreviousSevenDays(startDate) {
    const dates = [];
    const baseDate = new Date(startDate);

    for (let i = 0; i < 7; i++) {
        const date = new Date(baseDate);
        date.setDate(baseDate.getDate() - i);
        dates.push(date);
    }
    return dates;
}

function getAvgUsersCarbonPrint() {
    const headers = new Headers();
    const login = sessionStorage.getItem("U-Login");
    headers.append("Authorization", sessionStorage.getItem("jwt"));
    headers.append("U-Login", login);
    return fetch(CARBON_BACKEND_ENDPOINT + 'average', {
        method: 'GET',
        headers: headers,
    }).then(response => {
        if (response.ok) {
            console.log('Response: ', response);
            return response.json();
        } else {
            console.log('Response: ', response);
            if (response.status !== 404) {
                throw new Error("Erreur lors de la récupération de la moyenne de consommation carbone.");
            }
        }
    }).then(json => {
        return json;
    }).catch(err => {
        serverError(err);
    });
}

function formatToSQLDate(date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
}

function drawCarbonHistoryChart(data, avg_carbon_print) {
    const dates = data.map(item => item.date);
    const values = data.map(item => item.result);
    const avg = avg_carbon_print.historique.historique;

    const trace = {
        x: dates,
        y: values,
        type: 'scatter',
        mode: 'lines+markers',
        name: 'Votre empreinte',
        marker: {
            color: 'green'
        }
    };

    const averageTrace = {
        x: [dates[0], dates[dates.length - 1]],
        y: [avg, avg],
        type: 'scatter',
        mode: 'lines',
        name: 'Moyenne du site',
        line: {
            color: 'red',
            width: 2,
            dash: 'dot'
        }
    };

    const layout = {
        title: 'Historique des émissions carbones',
        xaxis: {
            title: 'Date'
        },
        yaxis: {
            title: 'Émission (g de CO2)'
        },
        autosize: true,
        margin: {
            l: 50,
            r: 50,
            b: 100,
            t: 100,
            pad: 4
        }
    };

    const config = {
        responsive: true
    };

    Plotly.newPlot('carbonHistoryChart', [trace, averageTrace], layout, config);
}

loadPage();