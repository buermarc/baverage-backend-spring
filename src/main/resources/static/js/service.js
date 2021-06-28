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
    document.getElementById("page_overview").style.display = "none";
    document.getElementById("page_order").style.display = "none";
    document.getElementById("page_table").style.display = "none";

    //Gewünschte Seite anzeigen
    document.getElementById(pageId).style.display = "block";
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
    spans[0].textContent = "Tisch " + order.tischId; //Tisch beschriften
    container.appendChild(newDeliveryElement); //Neue Lieferung hinzufügen
    startOffsetTimer(spans[1], order); //Timer mit offset Zeit starten
}

//Offset Zeit berechnen und neuen Timer starten
startOffsetTimer = (span, order) => {
    let now = new Date(); //Systemzeit
    let hour = now.getHours();
    let minutes = now.getMinutes();
    let seconds = now.getSeconds();
    let t = order.zeitpunkt_vorbereitet.split(/[T : .]/); //Zeitstempel in Bestellung per RegEx an "T, : und ." spalten
    let minutesSinceStart;
    let secondsSinceStart;
    
    if(hour > t[1]) { //Wenn die aktuelle Stunde nach der Bestellstunde liegt
        minutesSinceStart = 60 - t[2] + minutes; //Rest zur vollen Stunde + aktuelle Minuten
        minutesSinceStart = minutesSinceStart % 60;
    } else if(hour == t[1]) {
        minutesSinceStart = minutes - t[2]; //Aktuelle Minute - Bestellminute
    }

    if(minutes > t[2]) { //Wenn die aktuelle Minute nach der Bestellminute liegt
        secondsSinceStart = 60 - t[3] + seconds; //Restliche Sekunden zur vollen Minute + aktuelle Sekunden
        minutesSinceStart += parseInt(secondsSinceStart / 60); //Falls Sekunden über 60 sind, Minuten hinzufügen
        secondsSinceStart = secondsSinceStart % 60; //Rest von Sekunden > 60
    } else {
        secondsSinceStart = seconds - t[3]; //Aktuelle Sekunde - Startsekunde
    }
    startTimer(span, minutesSinceStart, secondsSinceStart);
}

//Alle Lieferungen auf der Overview Page generieren
let generateDeliveries = () => {
    fetch("./resources/lieferung.json").then( //Fetch Spring Endpoint 
        response => {return response.json();} //Response in JSON parsen
    ).then(
        data => {
            let tische = []; //Array mit allen angezeigten Tischnummern
            let container = document.getElementById("deliveries");
            container.innerHTML = '<div class="title">Liefern</div>';
            data.bestellungen_lieferbar.some(order => {
                if(tische.includes(order.tischId)) return false; //Abbruch falls TischId bereits generiert wurde
                tische.push(order.tischId) //Tisch der Liste hinzufügen
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
    spans[0].textContent = "Tisch " + order.tischId;
    spans[1].querySelectorAll("span")[0].textContent = "1"; //Default 1 / X wartend (da Tisch angelegt wird wenn das erste leere Getränk erkannt wird)
    spans[1].querySelectorAll("span")[1].textContent = order.plaetzeAmTisch;
    container.appendChild(newTableElement); //Tisch hinzufügen
    container.lastElementChild.addEventListener("click", () => { //Click Event zu zuletzt hinzugefügtem Element
        openTableOverview(order.tischId);
    });
    return container.lastElementChild; //Neu hinzugefügtes Element zurückgeben
}

//Zahl der wartenden Kunden erhöhen wenn für den Tisch bereits ein Feld vorhanden ist
let updateNumberOfWaitingCustomers = (tische, tischElemente, order) => {
    let x = tische.indexOf(order.tischId); //Index der Bestellung im tische Array
    let tischElement = tischElemente[x]; //Dazugehöriges Element am selben Index wie ID
    let numberSpan = tischElement.querySelectorAll("span")[1].querySelectorAll("span")[0];
    let currentNumber = parseInt(numberSpan.textContent); //Aktuelle Zahl auslesen
    numberSpan.textContent = currentNumber + 1; //Zahl erhöhen
    if((currentNumber + 1) / order.plaetzeAmTisch >= 0.5) { //Wenn mehr als 50% warten --> Rot
        tischElement.style.backgroundColor = "red";
    } else if((currentNumber + 1) / order.plaetzeAmTisch >= 0.3) { //Wenn mehr als 30% warten --> Gelb
        tischElement.style.backgroundColor = "yellow";
    }
}

//Tischübersicht generieren (Page 1)
let generateTableOverview = () => {
    fetch("./resources/emptySpots.json").then( //Alle (fast) Leeren Plätze aus Spring Backend abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("ordering");
            container.innerHTML = '<div class="title">Bestellung aufnehmen</div>';
            let tische = []; //Array mit Tisch IDs
            let tischElemente = []; //Array mit Tisch Elementen
            data.leerePlaetze.some(order => { //Für jede Bestellung
                if(tische.includes(order.tischId)) { //Falls bereits ein Tisch mit der Tisch ID in der Bestellung angelegt ist
                    updateNumberOfWaitingCustomers(tische, tischElemente, order); //Zahl der wartenden Kunden erhöhen
                    return false;
                }
                tische.push(order.tischId); //Neue Tisch ID in Liste aufnehmen
                tischElemente.push(addTableElement(order, container)); //Neues Tisch Element erzeugen und in Liste aufnehmen
            });
            addDummyElement(container); //Dummy Element für Justify generieren
        }
    )
}

//Übersicht eines bestimmten Tisches anzeigen (Page 2)
let openTableOverview = (tischId) => {
    showPage("page_table"); //Table Page anzeigen
    document.getElementById("table_number").innerHTML = "Tisch " + tischId; //Überschrift an Tisch ID anpassen
    fetch("./resources/tisch.json").then( //Tisch von Spring Endpoint abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("table_spots");
            container.innerHTML = ""; //Container leeren
            data.plaetze.forEach(spot => { //Für jeden Platz ein Element hinzufügen
                addSpotElement(spot, tischId, container); //Platz generieren und hinzufpgen
            });
            addDummyElement(container); //Dummy Element für Justify hinzufügen
        }
    )
}

//Neuen Platz auf Tischübersicht generieren
let addSpotElement = (spot, tischId, container) => {
    let template = document.getElementById("spot_template"); //Platz Template auswählen
    let newSpotElement = template.content.cloneNode(true); //Template Inhalt kopieren
    newSpotElement.querySelectorAll("div")[1].textContent = spot.name==""?"Frei":spot.name; //Spot mit Name oder "Frei" beschriften
    container.appendChild(newSpotElement); //Neuen Platz hinzufügen
    container.lastElementChild.addEventListener("click", () => { //Click Event zum Bestellen hinzufügen
        openOrderPage(spot.id, tischId, spot.name);
    });

    //Farbe basierend auf Füllstand des jeweiligen Platzes
    if(spot.fuellstand > 0.3) { //Mehr als 30% --> Grün
        container.lastElementChild.style.backgroundColor = "green";
    } else if(spot.fuellstand <= 0.3 && spot.name != "") { //30% oder weniger --> Gelb
        container.lastElementChild.style.backgroundColor = "yellow";
    }
    if(spot.fuellstand <= 0.1 && spot.name != "") { //10% oder weniger --> Rot
        container.lastElementChild.style.backgroundColor = "red";
    }
}

//Übersicht über Platz / Getränke anzeigen (Page 3)
let openOrderPage = (platzId, tischId, name) => {
    showPage("page_order"); //Platz / Getränkeübersicht öffnen
    if(name == "") { //Titel mit Name / "Frei" beschriften
        document.getElementById("name").innerHTML = "Frei";
        document.getElementById("button_new").style.display = "block";
        document.getElementById("button_done").style.display = "none";
    } else {
        document.getElementById("name").innerHTML = name;
        document.getElementById("button_done").style.display = "block";
        document.getElementById("button_new").style.display = "none";
    } 
    document.getElementById("tablenr").innerHTML = "T"+tischId; //Tisch ID anzeigen
    fetch("./resources/getraenke.json").then( //Getränkeliste im Spring Endpoint abrufen
        response => {return response.json();} //Response zu JSON parsen
    ).then(
        data => {
            let container = document.getElementById("spot_drinks"); //Container wählen
            container.innerHTML = ""; //Container leeren
            data.getraenke.forEach(drink => { //Für jedes Getränk im Backend
                addDrinkElement(drink, container); //Getränk Tile erzeugen und hinzufügen
            });
            addDummyElement(container); //Dummy Element für Justify hinzufügen
        }
    )
}

//Neues Getränk anlegen und hinzufügen
let addDrinkElement = (drink, container) => {
    let template = document.getElementById("drink_template"); //Drink Template auswählen
    let newDrinkElement = template.content.cloneNode(true); //Template Inhalt kopieren
    newDrinkElement.querySelectorAll("div")[1].textContent = drink.name + " " + drink.groesse; //Getränk Element beschriften
    container.appendChild(newDrinkElement); //Neues Getränk hinzufpgen
    container.lastElementChild.addEventListener("click", () => { //Click Event zu Getränk hinzufügen
        //TODO: Bestellung in Backend anlegen
        showPage("page_table"); //Bestellseite schließen wenn Getränk ausgewählt wurde
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
    //TODO: Backend neuen User anlegen
    document.getElementById("name_input").style.display = "none";
    document.getElementById("button_done").style.display = "block";
    document.getElementById("button_new").style.display = "none";
}

//Gast beenden
let guestDone = () => {
    document.getElementById("name").innerHTML = "Frei";
    document.getElementById("button_done").style.display = "none";
    document.getElementById("button_new").style.display = "block";
    //TODO: Backend User beenden
}