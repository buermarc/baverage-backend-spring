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