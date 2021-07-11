class PageTable {
    constructor(app) {
        this._app = app;
    }

    async show() {
        let html = await fetch("pages/table.html");
        let htmlContent = "";
        if (html.ok) {
            htmlContent = await html.text();
        }
        let urlParams = new URLSearchParams(window.location.search);
        let tisch_id = urlParams.get('tisch_id');
        this._app.setPageContent(htmlContent);
        this.setClickListener();
        this.openTableOverview(tisch_id);
    }

    setClickListener = () => {
        document.getElementById("table_close").addEventListener("click", () => {
            location.hash = "/service";
        });
    }

    //Übersicht eines bestimmten Tisches anzeigen (Page 2)
    openTableOverview = (tisch_id) => {
        console.log("Showing Table " + tisch_id);
        document.getElementById("table_number").innerHTML = "Tisch " + tisch_id; //Überschrift an Tisch ID anpassen
        //fetch("./resources/tisch.json").then( //Test ohne MySQL Backend
        fetch("./api/getTisch?id="+tisch_id).then( //Tisch von Spring Endpoint abrufen
            response => {return response.json();} //Response zu JSON parsen
        ).then(
            data => {
                let container = document.getElementById("table_spots");
                container.innerHTML = ""; //Container leeren
                data.plaetze.forEach(spot => { //Für jeden Platz ein Element hinzufügen
                    this.addSpotElement(spot, tisch_id, container); //Platz generieren und hinzufpgen
                });
                Helper.addDummyElement(container); //Dummy Element für Justify hinzufügen
            }
        )
    }

    //Neuen Platz auf Tischübersicht generieren
    addSpotElement = (spot, tisch_id, container) => {
        let template = document.getElementById("spot_template"); //Platz Template auswählen
        let newSpotElement = template.content.cloneNode(true); //Template Inhalt kopieren
        newSpotElement.querySelectorAll("div")[1].textContent = spot.name==""?"Frei":spot.name; //Spot mit Name oder "Frei" beschriften
        container.appendChild(newSpotElement); //Neuen Platz hinzufügen
        let spotElement = container.lastElementChild;
        fetch("./resources/kunde"+spot.id+".json").then( //Test ohne MySQL Backend
    //    fetch("./api/getKundeByPlatzId?id="+spot.id).then( //Tisch von Spring Endpoint abrufen
            result => {return result.json();} //Response zu JSON parsen
        ).then(
            customer => {
                spotElement.addEventListener("click", () => { //Click Event zum Bestellen hinzufügen
                    location.href = "?spot_id=" + spot.id + "&tisch_id=" + tisch_id + "&spot_name=" + spot.name + "&kunde_id=" + customer.id + "#/orderView";
                    //openOrderPage(spot.id, tisch_id, spot.name, customer.id);
                });
                //Farbe basierend auf Füllstand des jeweiligen Platzes
                let lastOrder = spot.bestellungen[spot.bestellungen.length - 1]; //Letzte Bestellung des Tisches ermitteln
                let lastMeasurement = lastOrder.messpunkte[lastOrder.messpunkte.length - 1]; //Letzten Messwert der Bestellung ermitteln

                if(lastMeasurement.fuellstand > 0.3) { //Mehr als 30% --> Grün
                    spotElement.style.backgroundColor = "green";
                } else if(lastMeasurement.fuellstand <= 0.3 && spot.name != "") { //30% oder weniger --> Gelb
                    spotElement.style.backgroundColor = "yellow";
                }
                if(lastMeasurement.fuellstand <= 0.1 && spot.name != "") { //10% oder weniger --> Rot
                    spotElement.style.backgroundColor = "red";
                }
            }
        )
    }
}