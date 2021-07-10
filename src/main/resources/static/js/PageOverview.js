class PageOverview {
    constructor(app) {
        this._app = app;
    }

    async show() {
        let html = await fetch("pages/overview.html");
        let htmlContent = "";
        if (html.ok) {
            htmlContent = await html.text();
        }
        this._app.setPageContent(htmlContent);
        this.generateDeliveries();
        this.generateTableOverview();
        this.setClickListener()
    }

    setClickListener = () => {
        document.getElementById("special_order").addEventListener("click", () => {
            this.specialOrder();
        });

        document.getElementById("button_submitTable").addEventListener("click", () => {
            this.openTableSubmit();
        });
    }

    //Element für neue Lieferung erzeugen und anhängen
    addDeliveryElement = (order, container) => {
        let template = document.getElementById("overview_item_template"); //Template auswählen
        let newDeliveryElement = template.content.cloneNode(true); //Template Inhalt kopieren
        let spans = newDeliveryElement.querySelectorAll('span'); //Spans im Template auswählen
        spans[0].textContent = "Tisch " + order.tisch_id; //Tisch beschriften
        container.appendChild(newDeliveryElement); //Neue Lieferung hinzufügen
        new Timer(spans[1], order); //Timer mit offset Zeit starten
    }

    //Alle Lieferungen auf der Overview Page generieren
    generateDeliveries = () => {
        //fetch("./resources/lieferung.json").then( //Test ohne MySQL Backend
        fetch("./api/getLieferungen").then( //Fetch Spring Endpoint    
            response => {return response.json();} //Response in JSON parsen
        ).then(
            data => {
                let tische = []; //Array mit allen angezeigten Tischnummern
                let container = document.getElementById("deliveries");
                container.innerHTML = '<div class="title">Liefern</div>';
                data.some(order => {
                    if(tische.includes(order.tisch_id)) return false; //Abbruch falls tisch_id bereits generiert wurde
                    tische.push(order.tisch_id) //Tisch der Liste hinzufügen
                    this.addDeliveryElement(order, container); //Tisch auf der Seite generieren 
                });
                Helper.addDummyElement(container); //Dummy Element für Justify hinzufügen
            }
        )
    }

    //Tisch bei Auslastungsübersicht hinzufügen
    addTableElement = (order, container) => {
        let template = document.getElementById("overview_item_tables_template"); //Tisch Template auswählen          
        let newTableElement = template.content.cloneNode(true); //Template Inhalt kopieren
        let spans = newTableElement.querySelectorAll('span'); //Alle Spans im Template auswählen
        spans[0].textContent = "Tisch " + order.tisch_id;
        spans[1].querySelectorAll("span")[0].textContent = "1"; //Default 1 / X wartend (da Tisch angelegt wird wenn das erste leere Getränk erkannt wird)
        spans[1].querySelectorAll("span")[1].textContent = order.plaetze_am_tisch;
        container.appendChild(newTableElement); //Tisch hinzufügen
        container.lastElementChild.addEventListener("click", () => { //Click Event zu zuletzt hinzugefügtem Element
            location.href = "?tisch_id=" + order.tisch_id + "#/tableView";
        });
        return container.lastElementChild; //Neu hinzugefügtes Element zurückgeben
    }

    //Zahl der wartenden Kunden erhöhen wenn für den Tisch bereits ein Feld vorhanden ist
    updateNumberOfWaitingCustomers = (tische, tischElemente, order) => {
        let x = tische.indexOf(order.tisch_id); //Index der Bestellung im tische Array
        let tischElement = tischElemente[x]; //Dazugehöriges Element am selben Index wie ID
        let numberSpan = tischElement.querySelectorAll("span")[1].querySelectorAll("span")[0];
        let currentNumber = parseInt(numberSpan.textContent); //Aktuelle Zahl auslesen
        numberSpan.textContent = currentNumber + 1; //Zahl erhöhen
        if((currentNumber + 1) / order.plaetze_am_tisch >= 0.5) { //Wenn mehr als 50% warten --> Rot
            tischElement.style.backgroundColor = "red";
        } else if((currentNumber + 1) / order.plaetze_am_tisch >= 0.3) { //Wenn mehr als 30% warten --> Gelb
            tischElement.style.backgroundColor = "yellow";
        }
    }
    //Tischübersicht generieren (Page 1)
    generateTableOverview = () => {
        //fetch("./resources/emptySpots.json").then(  //Test ohne MySQL Backend
        fetch("./api/getLeerePlaetze").then( //Alle (fast) Leeren Plätze aus Spring Backend abrufen
            response => {return response.json();} //Response zu JSON parsen
        ).then(
            data => {
                let container = document.getElementById("ordering");
                container.innerHTML = '<div class="title">Bestellung aufnehmen</div>';
                let tische = []; //Array mit Tisch IDs
                let tischElemente = []; //Array mit Tisch Elementen
                data.some(order => { //Für jede Bestellung
                    if(tische.includes(order.tisch_id)) { //Falls bereits ein Tisch mit der Tisch ID in der Bestellung angelegt ist
                        this.updateNumberOfWaitingCustomers(tische, tischElemente, order); //Zahl der wartenden Kunden erhöhen
                        return false;
                    }
                    tische.push(order.tisch_id); //Neue Tisch ID in Liste aufnehmen
                    tischElemente.push(this.addTableElement(order, container)); //Neues Tisch Element erzeugen und in Liste aufnehmen
                });
                Helper.addDummyElement(container); //Dummy Element für Justify generieren
            }
        )
    }
    
    //Button Sonderbestellung -> Eingabefeld öffnen
    specialOrder = () => {
        document.getElementById("table_input").style.display = "block";
        let input = document.getElementById("table_id");
        input.focus(); //Cursor auf Input Feld setzen
        input.select();
    }
    
    //Sonderbestellung Tisch öffnen
    openTableSubmit = () => {
        let input = document.getElementById("table_input").querySelectorAll("input")[0];
        let input_tableId = input.value;
        document.getElementById("table_input").style.display = "none";
        location.href = "?tisch_id=" + input_tableId + "#/tableView";
    }
}