class Helper {
    //Leeres Dummy Element anhängen um justify korrekt darzustellen
    static addDummyElement = (container) => {
        let dummy = document.createElement("div");
        dummy.className = "dummy";
        dummy.innerHTML = " ";
        container.appendChild(dummy);
    }
}