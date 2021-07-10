package com.baverage.backend.restcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.Iterable;

import com.baverage.backend.repo.BestellungRepo;
import com.baverage.backend.repo.GetraenkRepo;
import com.baverage.backend.repo.KundeRepo;
import com.baverage.backend.service.KundeService;
import com.baverage.backend.service.BestellungService;
import com.baverage.backend.repo.TischRepo;
import com.baverage.backend.repo.GlasRepo;
import com.mysql.cj.log.Log;
import com.baverage.backend.dto.OffeneBestellung;
import com.baverage.backend.dto.BasicResponse;
import com.baverage.backend.dto.UpdateQueryResponse;
import com.baverage.backend.databaseConnection.Bestellungen;
import com.baverage.backend.databaseConnection.Kunden;
import com.baverage.backend.databaseConnection.Getraenke;
import com.baverage.backend.databaseConnection.Glaeser;
import com.baverage.backend.databaseConnection.Tische;
import com.baverage.backend.databaseConnection.Stati;
import com.baverage.backend.dto.CreateUserRequest;
import com.baverage.backend.dto.EmptySeat;
import com.baverage.backend.dto.IdClass;
import com.baverage.backend.dto.NewBestellung;
import com.baverage.backend.dto.Lieferung;
import com.baverage.backend.handler.MyTextWebSocketHandler;

@Controller
@RequestMapping(path = "/api")
public class CustomRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestController.class);

    @Autowired
    private BestellungRepo bestellungRepo;

    @Autowired
    private TischRepo tischRepo;

    @Autowired
    private GlasRepo glasRepo;

    @Autowired
    private KundeRepo kundeRepo;

    @Autowired
    private KundeService kundeService;

    @Autowired
    private BestellungService bestellungService;

    @Autowired
    private GetraenkRepo getraenkRepo;

    @GetMapping(value = "/getOffeneBestellungen")
    public @ResponseBody Iterable<OffeneBestellung> aktiveBestellungen(Model model) {
        return this.bestellungRepo.getOffeneBestellungen();
    }

    // TODO better error handling if it does not work
    @PostMapping(path = "/setBestellungsStatusVorbereitet", consumes = "application/json")
    public @ResponseBody UpdateQueryResponse setBestellungsStatusVorbereitet(@RequestBody IdClass idClass) {
        // This returns a JSON or XML with the users
        int rows = this.bestellungRepo.setBestellungsStatusVorbereitet(idClass.getId(), new Date(),
                Stati.Status.VORBEREITET.getId());
        // Use the latest rfid we found to set the corresponding glas as the glas of the ready order
        // TODO move this into a seperate service function?

        String lastRfid = null;
        Glaeser glas = null;
        Bestellungen bestellung = null;
        try {
            // We access the latest seen rfid, and check if it belongs to any known glas
            lastRfid = MyTextWebSocketHandler.getLastRfid();
            glas = this.glasRepo.findByRfid(lastRfid);
            if (glas == null) {
                LOGGER.error("Could not find any glas with the rfid '{}'", lastRfid);
            }
            bestellung = this.bestellungRepo.findById(idClass.getId()).get();
            if (bestellung == null) {
                LOGGER.error("Could not find any bestellung with the id '{}'. I think this should not be possible", idClass.getId());
            }
            bestellung.setGlas(glas);
            bestellungRepo.save(bestellung);
        } catch (Exception e) {
            LOGGER.error("While trying to set the glas we failed. Exception {}", e.toString());
        }

        // Based on the glass and the getraenke id of the besetellung we can
        // get a sane default initialgewicht, because at the moment our bar
        // station has no mass measurement sensor
        // TODO equip the bar station with a mass measurement sensor
        try {
            int leergewicht = glas.getLeergewicht();
            Getraenke getraenk = this.getraenkRepo.findById(bestellung.getGetraenk().getId()).get();
            if (getraenk == null) {
                LOGGER.error("Could not find any getraenk with the id '{}'. I think this should not be possible", bestellung.getGetraenk().getId());
            }
            int groesse = getraenk.getGroesse();
            bestellung.setInitialgewicht((double) (groesse + leergewicht - 50));
            this.bestellungRepo.save(bestellung);
        } catch (Exception e) {
            LOGGER.error("While trying to set the initialgewicht we failed. Exception {}", e.toString());
        }


        UpdateQueryResponse res = new UpdateQueryResponse();
        if (rows == 1) {
            res.setSuccess(true);
            res.setErrorMessage(null);
            res.setRowsChanged(rows);
            return res;
        } else {
            res.setSuccess(false);
            res.setErrorMessage(
                    "setBestellungsStatusVorbereitet nicht erfolgreich. Es sollte eigentlich genau eine Zeile geändert werden (rows != 1)");
            res.setRowsChanged(rows);
            return res;
        }
    }

    @GetMapping(value = "/isGeliefert")
    public @ResponseBody boolean isGeliefert(@RequestParam int id) {
        // This returns a JSON or XML with the users
        try {
            return (this.bestellungRepo.getStatusForBestellung(id) == Stati.Status.GELIEFERT.getId());
        } catch (Exception e) {
            LOGGER.error("Bestellung mit der ID {} sehr wahrscheinlich noch nicht erstellt", id);
            return false;
        }

    }

    @GetMapping(value = "/getTisch")
    public @ResponseBody Tische getTisch(@RequestParam int id) {
        return this.tischRepo.findById(id).orElse(null);
    }

    @GetMapping(value = "/getKundeByPlatzId")
    public @ResponseBody Kunden getKundeByPlatzId(@RequestParam int id) {

	Kunden kunde = this.kundeRepo.findLatestByPlatzId(id);
	if (kunde == null) {
		LOGGER.warn("kunde is null with id {}", id);
	}
        return kunde;
    }

    @GetMapping(value = "/getGetraenke")
    public @ResponseBody Iterable<Getraenke> getGetraenke() {
        return this.getraenkRepo.findAll();
    }

    @GetMapping(value = "/getLieferungen")
    public @ResponseBody Iterable<Lieferung> getLieferungen() {
        return this.bestellungRepo.getLieferungen(Stati.Status.VORBEREITET.getId());
    }

    /**
     * Takes a JSON object with the name of the new kunde { "name": "Charlie", "platz_id": 1 }.
     * Returns the newly created kunde.
     *
     * @param {@link CreateUserRequest}
     * @return {@link Kunden}
     *
     */
    @PostMapping(path = "/createKunde", consumes = "application/json")
    public @ResponseBody Kunden createKunde(@RequestBody CreateUserRequest createUserRequest) {
        // This returns a JSON or XML with the users
        return this.kundeService.createKunde(createUserRequest.getName(), createUserRequest.getPlatz_id());
        // return "createKunde erfolgreich, return value: " + ret;
    }

    /**
     * Sets the bezahlt member variable of an kunde with the given kunde_id to true.
     * Takes a JSON Object with the id of the kunde { "id": 42 }.
     *
     */
    @PostMapping(path = "/setKundeBezahlt", consumes = "application/json")
    public @ResponseBody UpdateQueryResponse setKundeBezahlt(@RequestBody IdClass idClass) {
        // This returns a JSON or XML with the users
        int rows = this.kundeRepo.setKundeBezahlt(idClass.getId());
        UpdateQueryResponse res = new UpdateQueryResponse();
        if (rows == 1) {
            res.setSuccess(true);
            res.setErrorMessage(null);
            res.setRowsChanged(rows);
            return res;
        } else {
            res.setSuccess(false);
            res.setErrorMessage(
                    "setKundeBezahlt nicht erfolgreich. Es sollte eigentlich genau eine Zeile geändert werden (rows != 1)");
            res.setRowsChanged(rows);
            return res;
        }
    }

    /**
     * Sets the bezahlt member variable of an kunde with the given kunde_id Create a
     * new bestellung. to true. Takes a JSON Object with some of the content each
     * Bestellung needs. { "platz_id": 1, "getraenke_id": 1, "kunde_id": 1 }
     *
     */
    @PostMapping(path = "/createBestellung", consumes = "application/json")
    public @ResponseBody Bestellungen createBestellung(@RequestBody NewBestellung newBestellung) {
        // This returns a JSON or XML with the users
        return this.bestellungService.createBestellung(newBestellung.getPlatz_id(), newBestellung.getGetraenk_id(),
                Stati.Status.BESTELLT.getId());
    }

    @GetMapping(value = "/getLeerePlaetze")
    public @ResponseBody Iterable<EmptySeat> getLeerePlaetze(Model model) {
        return this.bestellungRepo.getLeerePlaetze();
    }


    // funktional unnoetig ~ Marc

    @GetMapping(value = "/getAlleBestellungen")
    public @ResponseBody Iterable<Bestellungen> alleBestellungen(Model model) {
        return this.bestellungRepo.findAll();
    }

    @GetMapping(value = "/getID")
    public @ResponseBody Iterable<Bestellungen> getID(Model model) {
        return this.bestellungRepo.getID();
    }
}
