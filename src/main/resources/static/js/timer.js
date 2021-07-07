//Timer für Span starten
let startTimer = (timeSpan, offsetMinutes = 0, offsetSeconds = 0) => {
    let labels = timeSpan.querySelectorAll('span');
    let minutesLabel = labels[0]; //Span für Minuten
    let secondsLabel = labels[1]; //Span für Sekunden
    let totalSeconds = offsetMinutes*60 + offsetSeconds; //Startzeit in Sekunden
    
    //Startzeit initial setzen, da Interval erst nach 1. Sekunde startet
    secondsLabel.innerHTML = pad(totalSeconds % 60);
    minutesLabel.innerHTML = pad(parseInt(totalSeconds / 60));
    if(parseInt(totalSeconds / 60) >= 2) {
        timeSpan.parentElement.style.backgroundColor = "orange";
    }
    if(parseInt(totalSeconds / 60) >= 5) {
        timeSpan.parentElement.style.backgroundColor = "red";
    }
    
    //Timer starten
    setInterval(() => {
        ++totalSeconds; //Zeit erhöhen
        secondsLabel.innerHTML = pad(totalSeconds % 60); //Minuten aktualisieren
        minutesLabel.innerHTML = pad(parseInt(totalSeconds / 60)); //Sekunden aktualisieren
        //Farbe des Elements ändern, wenn Grenzwerte erreicht werden
        if(parseInt(totalSeconds / 60) >= 2) { //2 Minuten --> Orange
            timeSpan.parentElement.style.backgroundColor = "orange";
        }
        if(parseInt(totalSeconds / 60) >= 5) { //5 Minuten --> Rot
            timeSpan.parentElement.style.backgroundColor = "red";
        }
    }, 1000); //Sekunde warten
};

//Zeitdarstellung formatieren
let pad = (val) => {
    let valString = val + ""; //Zahl zu String konvertieren
    if (valString.length < 2) { //Bei einstelliger Sekunde / Minute vorstehende 0 hinzufügen
      return "0" + valString;
    } else {
      return valString;
    }
};

//Offset Zeit berechnen und neuen Timer starten
startOffsetTimer = (span, order) => {
    let now = new Date(); //Systemzeit
    let hour = now.getHours();
    let minutes = now.getMinutes();
    let seconds = now.getSeconds();
    let t = order.zeitpunkt_vorbereitet.split(/[T : .]/); //Zeitstempel in Bestellung per RegEx an "T, : und ." spalten
    //t[0] -> Datum
    //t[1] -> Stunde
    //t[2] -> Minute
    //t[3] -> Sekunde
    let minutesSinceStart;
    let secondsSinceStart;
    t[1] = parseInt(t[1]) + 2; //Zeitzonenanpassung
    if(hour > t[1]) { //Wenn die aktuelle Stunde nach der Bestellstunde liegt
        minutesSinceStart = 60 - t[2] + minutes; //Rest zur vollen Stunde + aktuelle Minuten
    } else if(hour < t[1]) {
        minutesSinceStart = 60 - t[2] + minutes;
    } else if(hour == t[1]) {
        minutesSinceStart = minutes - t[2]; //Aktuelle Minute - Bestellminute
    }

    if(minutes > t[2]) { //Wenn die aktuelle Minute nach der Bestellminute liegt
        if(seconds < t[3]) {
            minutesSinceStart -= 1;
            secondsSinceStart = 60 - t[3] + seconds; //Restliche Sekunden zur vollen Minute + aktuelle Sekunden
        } else if(seconds > t[3]) {
            secondsSinceStart = seconds - t[3]; //Aktuelle Sekunde - Startsekunde
        } else if(seonds == t[3]) {
            secondsSinceStart = 0;
        }        
    } else if(minutes < t[2]) {
        secondsSinceStart = 60 - t[3] + seconds;
    } else if (minutes == t[2]) {
        secondsSinceStart = seconds - t[3]; //Aktuelle Sekunde - Startsekunde
    }
    console.log(minutesSinceStart + " : " + secondsSinceStart);
    startTimer(span, minutesSinceStart, secondsSinceStart);
}