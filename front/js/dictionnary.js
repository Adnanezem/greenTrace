// Create a dictionnary for multi-language support

var dictionary = {
    "fr": {
        "distance traveled": "Distance parcourue",
        "drive mode": "Mode de conduite",
        "town": "Urbain",
        "sport": "Sportive",
        "highway": "Autoroute",
        "vehicle type": "Type de véhicule",
        "sedan": "Berline",
        "SUV": "SUV",
        "break": "Break",
        "compact": "Compact",
        "fuel type": "Type de carburant",
        "diesel": "Diesel",
        "gasoline": "Essence",
        "electric": "Électrique",
        "hybrid": "Hybride",
        "LPG": "GPL",
        "hydrogen": "Hydrogène",
        "city": "Ville",
        "mountain": "Montagne",
        "road": "Route",
        "intercity": "Interurbain",
        "school": "Scolaire",
        "tourism": "Touristique",
        "flight class": "Classe de vol",
        "economy": "Économique",
        "business": "Affaires",
        "first": "Première",
        "short haul": "Court-courrier",
        "medium haul": "Moyen-courrier",
        "long haul": "Long-courrier",
        "airplane color": "Couleur de l'avion",
        "meal type": "Type de repas",
        "breakfast": "Petit déjeuner",
        "brunch": "Brunch",
        "lunch": "Déjeuner",
        "dinner": "Dîner",
        "snack": "Snack",
        "restaurant type": "Type de restaurant",
        "fast food": "Restauration rapide",
        "traditional": "Traditionnel",
        "gourmet": "Gastronomique",
        "vegan": "Végan",
        "vegetarian": "Végétarien",
        "number of people": "Nombre de personnes",
        "delivery mode": "Mode de livraison",
        "bike": "Vélo",
        "car": "Voiture",
        "scooter": "Scooter",
        "walking": "À pied",
        "delivery time": "Temps de livraison",
        "museum type": "Type de musée",
        "art": "Art",
        "history": "Histoire",
        "science": "Science",
        "technology": "Technologie",
        "natural history": "Histoire naturelle",
        "time spent": "Temps passé",
        "park type": "Type de parc",
        "botanical": "Botanique",
        "national": "National",
        "regional": "Régional",
        "urban": "Urbain",
        "occupancy": "Occupation",
    }
};


function toFrench(text) {
    //return the french of the text
    console.log("Translation of " + text + " : " + dictionary["fr"][text]);
    return dictionary["fr"][text];
}

function toEnglish(text) {
    console.log("Translation of " + text + " : " + dictionary["fr"][text]);
    //return the english of the text
    for (var key in dictionary["fr"]) {
        if (dictionary["fr"][key] == text) {
            return key;
        }
    }
    return text;
}

export { toFrench, toEnglish };
