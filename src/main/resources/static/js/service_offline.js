/*
    JavaScript Funktionen für die service.html Seite des Beverage Monitoring
*/

//Beim öffnen / neu laden der Seite
window.onload = () => {
    generateDeliveries(); //Alle ausstehenden Lieferungen generieren
    generateTableOverview(); //Tischübersicht generieren
    setInterval(() => { //Lieferungen und Tischübersicht alle 10 Sekunden aktualisieren
        generateDeliveries();
        generateTableOverview();
    }, 10000) //10 Sekunden warten
}

let showPage = (pageId) => {
    //Alle Seiten verstecken
    document.getElementsByClassName("page")[0].style.display = "none";
    document.getElementsByClassName("page")[1].style.display = "none";
    document.getElementsByClassName("page")[2].style.display = "none";

    //Gewünschte Seite anzeigen
    document.getElementsByClassName("page")[pageId].style.display = "block";
}

//Leeres Dummy Element anhängen um justify korrekt darzustellen
let addDummyElement = (container) => {
    let dummy = document.createElement("div");
    dummy.className = "dummy";
    dummy.innerHTML = " ";
    container.appendChild(dummy);
}

//Element für neue Lieferung erzeugen und anhängen
let addDeliveryElement = (order, container) => {
    let template = document.getElementById("overview_item_template"); //Template auswählen
    let newDeliveryElement = template.content.cloneNode(true); //Template Inhalt kopieren
    let spans = newDeliveryElement.querySelectorAll('span'); //Spans im Template auswählen
    spans[0].textContent = "Tisch " + order.tisch_id; //Tisch beschriften
    container.appendChild(newDeliveryElement); //Neue Lieferung hinzufügen
    startOffsetTimer(spans[1], order); //Timer mit offset Zeit starten
}

//Alle Lieferungen auf der Overview Page generieren
let generateDeliveries = () => {
    fetch("./resources/lieferung.json").then( //Test ohne MySQL Backend
//    fetch("./api/getLieferungen").then( //Fetch Spring Endpoint    
        response => {return response.json();} //Response in JSON parsen
    ).then(
        data => {
            let tische = []; //Array mit allen angezeigten Tischnummern
            let container = document.getElementById("deliveries");
            container.innerHTML = '<div class="title">Liefern</div>';
            data.some(order => {
                if(tische.includes(order.tisch_id)) return false; //Abbruch falls tisch_id bereits generiert wurde
                tische.push(order.tisch_id) //Tisch der Liste hinzufügen
                addDeliveryElement(order, container); //Tisch auf der Seite generieren 
            });
            addDummyElement(container); //Dummy Element für Justify hinzufügen
        }
    )
}

//Tisch bei Auslastungsübersicht hinzufügen
let addTableElement = (order, container) => {
    let template = document.getElementById("overview_item_tables_template"); //Tisch Template auswählen          
    let newTableElement = template.content.cloneNode(true); //Template Inhalt kopieren
    let spans = newTableElement.querySelectorAll('span'); //Alle Spans im Template auswählen
    spans[0].textContent = "Tisch " + order.tisch_id;
    spans[1].querySelectorAll("span")[0].textContent = "1"; //Default 1 / X wartend (da Tisch angelegt wird wenn das erste leere Getränk erkannt wird)
    spans[1].querySelectorAll("span")[1].textContent = order.plaetze_am_tisch;
    container.appendChild(newTableElement); //Tisch hinzufügen
    container.lastElementChild.addEventListener("click", () => { //Click Event zu zuletzt hinzugefügtem Element
        openTableOverview(order.tisch_id);
    });
    return container.lastElementChild; //Neu hinzugefügtes Element zurückgeben
}

//Zahl der wartenden Kunden erhöhen wenn für den Tisch bereits ein Feld vorhanden ist
let updateNumberOfWaitingCustomers = (tische, tischElemente, order) => {
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
let generateTableOverview = () => {
    fetch("./resources/emptySpots.json").then(  //Test ohne MySQL Backend
//    fetch("./api/getLeerePlaetze").then( //Alle (fast) Leeren Plätze aus Spring Backend abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("ordering");
            container.innerHTML = '<div class="title">Bestellung aufnehmen</div>';
            let tische = []; //Array mit Tisch IDs
            let tischElemente = []; //Array mit Tisch Elementen
            data.some(order => { //Für jede Bestellung
                if(tische.includes(order.tisch_id)) { //Falls bereits ein Tisch mit der Tisch ID in der Bestellung angelegt ist
                    updateNumberOfWaitingCustomers(tische, tischElemente, order); //Zahl der wartenden Kunden erhöhen
                    return false;
                }
                tische.push(order.tisch_id); //Neue Tisch ID in Liste aufnehmen
                tischElemente.push(addTableElement(order, container)); //Neues Tisch Element erzeugen und in Liste aufnehmen
            });
            addDummyElement(container); //Dummy Element für Justify generieren
        }
    )
}

//Button Sonderbestellung -> Eingabefeld öffnen
let specialOrder = () => {
    document.getElementById("table_input").style.display = "block";
    let input = document.getElementById("table_id");
    input.focus(); //Cursor auf Input Feld setzen
    input.select();
}

//Sonderbestellung Tisch öffnen
let openTableSubmit = (element) => {
    let input = element.parentElement.querySelectorAll("input")[0];
    let input_tableId = input.value;
    document.getElementById("table_input").style.display = "none";
    openTableOverview(input_tableId);
}

//Übersicht eines bestimmten Tisches anzeigen (Page 2)
let openTableOverview = (tisch_id) => {
    showPage(1); //Table Page anzeigen
    document.getElementById("table_number").innerHTML = "Tisch " + tisch_id; //Überschrift an Tisch ID anpassen
    fetch("./resources/tisch.json").then( //Test ohne MySQL Backend
//    fetch("./api/getTisch?id="+tisch_id).then( //Tisch von Spring Endpoint abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("table_spots");
            container.innerHTML = ""; //Container leeren
            data.plaetze.forEach(spot => { //Für jeden Platz ein Element hinzufügen
                addSpotElement(spot, tisch_id, container); //Platz generieren und hinzufpgen
            });
            addDummyElement(container); //Dummy Element für Justify hinzufügen
        }
    )
}

//Neuen Platz auf Tischübersicht generieren
let addSpotElement = (spot, tisch_id, container) => {
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
                openOrderPage(spot.id, tisch_id, spot.name, customer.id);
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

//Übersicht über Platz / Getränke anzeigen (Page 3)
let openOrderPage = (platzId, tisch_id, name, kunde_id) => {
    showPage(2);//Platz / Getränkeübersicht öffnen
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
    fetch("./api/getGetraenke").then( //Getränkeliste im Spring Endpoint abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("spot_drinks"); //Container wählen
            container.innerHTML = ""; //Container leeren
            data.forEach(drink => { //Für jedes Getränk im Backend
                addDrinkElement(drink, container, platzId); //Getränk Tile erzeugen und hinzufügen
            });
            addDummyElement(container); //Dummy Element für Justify hinzufügen
        }
    )
}

//Neues Getränk anlegen und hinzufügen
let addDrinkElement = (drink, container, platz_id) => {
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
            showPage(1);//Bestellseite schließen wenn Getränk ausgewählt wurde
        })();
    });
}

//Input für neuen Gast öffnen
let newGuest = () => {
    document.getElementById("name_input").style.display = "block";
    let input = document.getElementById("customer_name");
    input.focus(); //Cursor auf Input Feld setzen
    input.select();
}

//Neuen Gast anlegen
let newGuestSubmit = (element) => {
    let input = element.parentElement.querySelectorAll("input")[0];
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
let guestDone = () => {
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
        showPage(1);
    })();
}