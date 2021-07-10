class PageOrder {
    
    spot_id;
    tisch_id;
    kunde_id;
    spot_name;
    
    constructor(app) {
        this._app = app;
    }

    async show() {
        let html = await fetch("pages/order.html");
        let htmlContent = "";
        if (html.ok) {
            htmlContent = await html.text();
        }

        let urlParams = new URLSearchParams(window.location.search);
        this.spot_id = urlParams.get('spot_id');
        this.tisch_id = urlParams.get('tisch_id');
        this.kunde_id = urlParams.get('kunde_id');
        this.spot_name = urlParams.get('spot_name');
        this._app.setPageContent(htmlContent);
        this.setClickListener();
        this.openOrderPage(this.spot_id, this.tisch_id, this.spot_name, this.kunde_id);
    }

    setClickListener = () => {
        document.getElementById("spot_cancel").addEventListener("click", () => {
            location.href = "?tisch_id=" + this.tisch_id + "#/tableView";
        });

        document.getElementById("button_done").addEventListener("click", () => {
            this.guestDone();
        });

        document.getElementById("button_new").addEventListener("click", () => {
            this.newGuest();
        });

        document.getElementById("button_newGuestSubmit").addEventListener("click", () => {
            this.newGuestSubmit();
        });
    }

    //Übersicht über Platz / Getränke anzeigen (Page 3)
    openOrderPage = (platzId, tisch_id, name, kunde_id) => {
        //showPage(2);//Platz / Getränkeübersicht öffnen
        if(name == "") { //Titel mit Name / "Frei" beschriften
            document.getElementById("name").innerHTML = "Frei";
            document.getElementById("button_new").style.display = "block";
            document.getElementById("button_done").style.display = "none";
        } else {
            document.getElementById("name").innerHTML = name;
            document.getElementById("button_done").style.display = "block";
            document.getElementById("button_new").style.display = "none";
        } 
        document.getElementById("kunde_id").innerHTML = kunde_id;
        document.getElementById("platz_id").innerHTML = platzId;
        document.getElementById("tablenr").innerHTML = "T"+tisch_id; //Tisch ID anzeigen
        //fetch("./resources/getraenke.json").then(
        fetch("./api/getGetraenke").then( //Getränkeliste im Spring Endpoint abrufen
            response => {return response.json();} //Response zu JSON parsen
        ).then(
            data => {
                let container = document.getElementById("spot_drinks"); //Container wählen
                container.innerHTML = ""; //Container leeren
                data.forEach(drink => { //Für jedes Getränk im Backend
                    this.addDrinkElement(drink, container, platzId); //Getränk Tile erzeugen und hinzufügen
                });
                Helper.addDummyElement(container); //Dummy Element für Justify hinzufügen
            }
        )
    }

    //Neues Getränk anlegen und hinzufügen
    addDrinkElement = (drink, container, platz_id) => {
        let template = document.getElementById("drink_template"); //Drink Template auswählen
        let newDrinkElement = template.content.cloneNode(true); //Template Inhalt kopieren
        newDrinkElement.querySelectorAll("div")[1].textContent = drink.name + " " + drink.groesse/1000; //Getränk Element beschriften
        container.appendChild(newDrinkElement); //Neues Getränk hinzufpgen
        container.lastElementChild.addEventListener("click", () => { //Click Event zu Getränk hinzufügen
            //Backend neue Bestellung anlegen
            (async () => {
                const rawResponse = await fetch('./api/createBestellung', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({platz_id : platz_id, getraenk_id : drink.id})
                });
                const content = await rawResponse.json();
                location.href = "?tisch_id=" + this.tisch_id + "#/tableView";
                //showPage(1);//Bestellseite schließen wenn Getränk ausgewählt wurde
            })();
        });
    }

    //Input für neuen Gast öffnen
    newGuest = () => {
        document.getElementById("name_input").style.display = "block";
        let input = document.getElementById("customer_name");
        input.focus(); //Cursor auf Input Feld setzen
        input.select();
    }

    //Neuen Gast anlegen
    newGuestSubmit = () => {
        let input = document.getElementById("name_input").querySelectorAll("input")[0];
        let input_name = input.value;
        if(input.value.length < 2 ) {
            input.value = "";
            input.placeholder = "Bitte Namen angeben";
            return;
        }
        document.getElementById("name").innerHTML = input_name;
        //Backend neuen Kunden anlegen
        (async () => {
            const rawResponse = await fetch('./api/createKunde', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({platz_id : document.getElementById("platz_id").innerHTML, name:input_name})
            });
            const content = await rawResponse.json();
        })();
        document.getElementById("name_input").style.display = "none";
        document.getElementById("button_done").style.display = "block";
        document.getElementById("button_new").style.display = "none";
    }

    //Gast beenden
    guestDone = () => {
        document.getElementById("name").innerHTML = "Frei";
        document.getElementById("button_done").style.display = "none";
        document.getElementById("button_new").style.display = "block";
        //Backend User beenden
        (async () => {
            const rawResponse = await fetch('./api/setKundeBezahlt', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({id : parseInt(document.getElementById("kunde_id").innerHTML)})
            });
            const content = await rawResponse.json();
            //showPage("page_table"); //Bestellseite schließen wenn Getränk ausgewählt wurde
            //showPage(1);
            location.href = "?tisch_id=" + this.tisch_id + "#/tableView";
        })();
    }
}