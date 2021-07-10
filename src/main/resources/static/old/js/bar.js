/*
    JavaScript Funktionen für die bar.html Seite des Beverage Monitoring
*/

//Beim öffnen / neu laden der Seite
window.onload = () => {
    generateDeliveries(); //Ausstehende Lieferungen generieren
    for (let index = 0; index < 4; index++) {
        generateNextOrder(); //Alle offenen Bestellungen generieren
    }
    setInterval(() => { //Lieferungen und Tischübersicht alle 10 Sekunden aktualisieren
        for (let index = 0; index < 4; index++) {
            generateNextOrder(); //Alle offenen Bestellungen generieren
            generateDeliveries(); //Ausstehende Lieferungen generieren
        }
    }, 10000) //10 Sekunden warten

    updateRfidConnection(false); //RFID Verbindungsanzeige initial Rot
    //Websocket zur Statusanzeige, ob ein Getränkt / RFID Chip auf dem Reader erkannt wurde
    let ws_address = location.origin.replace(new RegExp('https?'), 'ws') + "/web-socket"; //http / https in URL durch ws ersetzen
    let socket = new WebSocket(ws_address); //Websocket instanzieren

    //Websocket event Listener --> Wenn Tag auf Reader gestellt wird
    socket.addEventListener('message', function (event) {
        console.log("Found a bottle with the rfid: " + event.data);
        updateRfidConnection(true); //RFID Verbindungsanzeige grün schalten
    });

    setInterval(() => { 
        updateRfidConnection(false); //RFID Verbindungsanzeige Rot setzen
    }, 5000) //5 Sekunden warten
}

//Zur ToDo Area zugehörige DoneArea ermitteln und zurückgeben
let getDoneArea = (toDoArea)  => {
    return document.getElementById("done" + toDoArea.id.slice(-1));
}

//Rekursiv die erste leere Spalte ermitteln, um neue Bestellung zu generieren
let getFirstEmptyColumn = (count = 1) => { //count default beginnt bei 1
    //Wenn die Zelle leer ist, wird sie zurückgegeben und die Funktion beendet
    //Ist die Zeile gefüllt, wird die Funktion erneut mit erhöhtem counter aufgerufen
    let id = "toDo" + count;
    let el = document.getElementById(id);
    if(el == null || el.innerHTML == "") {
        return el; //Leere Spalte zurückgeben
    } else return getFirstEmptyColumn(++count); //Funktion erneut durchführen
};

//Getränk fertig vorbereitet
let drinkDone = (drinkId) => {
    drink = document.getElementById(drinkId);
    toDoArea = drink.parentElement;
    doneArea = getDoneArea(toDoArea); //Done Area ermitteln
    doneArea.appendChild(drink); //Drink von ToDo entfernen und Done anhängen

    (async () => {
        const rawResponse = await fetch('./api/setBestellungsStatusVorbereitet', {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({id : parseInt(drinkId.split(/[_]/)[1])})
        });
        updateRfidConnection(false);
        const content = await rawResponse.json();
        console.log("Bestellung vorbereitet: " + drinkId);
        //Wenn durch den Drink alle Bestellungen für den Tisch abgeschlossen wurden, wird eine neue Lieferung angelegt und alle Bestellungen rücken von rechts nach links auf
        if(toDoArea.childElementCount == 1) {
            generateDeliveries(); //Neue Lieferung anlegen
            moveOrders(toDoArea); //Von Rechts nach Links aufrücken
        }
    })();
};

let generateDeliveries = () => {
    fetch("./api/getLieferungen", {mode: 'cors'}).then( //fetch Spring Endpoint
        response => {
            let jsonResponse = response.json();
            return jsonResponse;
        } //parse response zu json
    ).then(
        data => {
            tische = [];
            document.getElementById("delivery_items").innerHTML = '<div class="order_tablenumber">Liefern</div><!-- Template für eine Lieferung --><template id="outgoing_delivery"><div class="outgoing_delivery"><span class="outgoing_tablenumber">Tisch 1</span><span class="deliverytime"><span>00</span>:<span>00</span></span></div></template>';
            data.some(order => { //durch alle Bestellungen iterieren
                if(tische.includes(order.tisch_id)) {
                    return false;
                } else {
                    newDelivery("Tisch " + order.tisch_id, order);
                    tische.push(order.tisch_id);
                }
            });
        }
    );
}

//Neue Lieferung für bestimmte Tischnummer in linker Spalte hinzufügen
let newDelivery = (tablenumber, order=null) => {
    let deliverCol = document.getElementById("delivery_items"); //Lieferspalte ermitteln
    let template = document.getElementById("outgoing_delivery"); //Template ermitteln
    let newDeliveryElement = template.content.cloneNode(true); //Template content copieren
    let spans = newDeliveryElement.querySelectorAll('span'); //Spans des Templates auswählen
    spans[0].textContent = tablenumber; //ersten Span mit Tischnummer beschriften
    deliverCol.appendChild(newDeliveryElement); //Lieferung der Spalte hinzufügen
    if(order != null) startOffsetTimer(spans[1], order); //Timer in zweitem Span starten
    if(order == null) startTimer(spans[1]);
};

//Alle Bestellungen rechts des fertiggestellten Bereichs nach links verschieben
let moveOrders = (finishedArea) => {
    if(finishedArea.id == "toDo4") { //Wenn der fertiggestellte Bereich ganz rechts ist
        finishedArea.innerHTML = ""; //Bereich leeren
        document.getElementById("done4").innerHTML = ""; //Bereich leeren
        generateNextOrder(); //Neue Bestellung aus Backlog generieren
        return; //Ende der Rekursion
    }
    let nextToDoArea = finishedArea.parentElement.nextElementSibling.firstElementChild; //nächsten Bereich ermitteln
    let doneArea = getDoneArea(finishedArea); //DoneArea zu finishedArea ermitteln
    let nextDoneArea = doneArea.parentElement.nextElementSibling.firstElementChild; //nächste Done Area ermitteln
    finishedArea.innerHTML = nextToDoArea.innerHTML; //ToDo von Rechts nach Links kopieren
    doneArea.innerHTML = nextDoneArea.innerHTML; //Done von Rechts nach Links kopieren
    moveOrders(nextToDoArea); //Rekursion --> Methode mit Rechter Spalte (nextToDoArea) wiederholen
};

//Nächste Spalten mit Bestellungen generieren
let generateNextOrder = () => {
    fetch("./api/getOffeneBestellungen", {mode: 'cors'}).then( //fetch Spring Endpoint
        response => {
            let jsonResponse = response.json();
            return jsonResponse;
        } //parse response zu json
    ).then(
        data => {
            toDoArea = getFirstEmptyColumn();
            data.some(order => { //durch alle Bestellungen iterieren
                if(document.getElementById("drink_" + order.id) == null && order.status == 1){ //wenn die Bestellung noch nicht auf dem Frontend vorhanden ist
                    toDoArea.innerHTML = '<div class="order_tablenumber">Tisch '+ order.tisch_id +'</div>'; //Spalte für Tischnummer der Bestellung anlegen
                    generateOrdersForTable(data, order.tisch_id, toDoArea); //Alle offenen Bestellungen für den gewählten Tisch erzeugen
                    return true; //Iterieren beenden wenn alle Bestellungen für den ermittelten Tisch hinzugefügt wurden
                } else {
                    return false; //weiter iterieren wenn Bestellung schon angezeigt wird
                }
            });
        }
    );
}

let generateOrdersForTable = (data, tisch_id, toDoArea) => {
    data.forEach(order => { //durch alle Bestellungen iterieren
        if(order.tisch_id == tisch_id) { //jede Bestellung mit der zuvor ermittelten TischId
            let newOrder = createNewOrder(order); //Neues Bestellungselement erzeugen
            if(order.status == 1) {
                toDoArea.appendChild(newOrder); //Bestellung der Spalte hinzufügen
            }
            if(order.status == 2) {
                getDoneArea(toDoArea).appendChild(newOrder);
            }
        }
    })
}

//Neues Bestellungselement erzeugen
let createNewOrder = (order) => {
    let newOrder = document.createElement("div"); //neues Div für Bestellung erzeugen
    newOrder.id = "drink_" + order.id; //Id vergeben
    newOrder.className = "order_item"; //Klasse zuweisen
    newOrder.setAttribute("onClick", "drinkDone(this.id)"); //OnClick funktion zuweisen
    newOrder.innerHTML = order.getraenkname + " " + order.getraengroesse/1000; //Beschriftung zuweisen
    return newOrder;
}


//Statussymbol für RFID Connection updaten
let updateRfidConnection = (connected) => {
    if(connected) {
        document.getElementById("rfid_status").style.backgroundColor = "green";
    } else document.getElementById("rfid_status").style.backgroundColor = "red";
}
