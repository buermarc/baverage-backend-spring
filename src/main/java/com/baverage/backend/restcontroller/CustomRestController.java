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
import com.baverage.backend.repo.TestTableRepo;
import com.baverage.backend.service.KundeService;
import com.baverage.backend.repo.TischRepo;
import com.mysql.cj.log.Log;
import com.baverage.backend.dto.OffeneBestellung;
import com.baverage.backend.DatabaseConnection.Bestellungen;
import com.baverage.backend.DatabaseConnection.Kunden;
import com.baverage.backend.DatabaseConnection.Getraenke;
import com.baverage.backend.DatabaseConnection.TestTable;
import com.baverage.backend.DatabaseConnection.Tische;
import com.baverage.backend.DatabaseConnection.Stati;
import com.baverage.backend.dto.CreateUserRequest;
import com.baverage.backend.dto.IdClass;

@Controller
@RequestMapping(path = "/api")
public class CustomRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestController.class);

    @Autowired
    private BestellungRepo bestellungRepo;

    @Autowired
    private TischRepo tischRepo;

    @Autowired
    private KundeService kundeService;

    @Autowired
    private GetraenkRepo getraenkRepo;

    @Autowired
    private TestTableRepo testTableRepo;

    @GetMapping(value = "/getOffeneBestellungen")
    public @ResponseBody Iterable<OffeneBestellung> aktiveBestellungen(Model model) {
        return this.bestellungRepo.getOffeneBestellungen();
    }

    @PostMapping(path = "/setBestellungsStatusVorbereitet", consumes = "application/json")
    public @ResponseBody String setBestellungsStatusVorbereitet(@RequestBody IdClass idClass) {
        // This returns a JSON or XML with the users
        this.bestellungRepo.setBestellungsStatusVorbereitet(idClass.getBestellungs_id(), new Date(), Stati.Status.VORBEREITET.getId());

        return "setBestellungsStatusVorbereitet erfolgreich";
    }

    @GetMapping(value = "/isGeliefert")
    public @ResponseBody boolean isGeliefert(@RequestParam int id) {
        // This returns a JSON or XML with the users
    	try {
    		return (this.bestellungRepo.getStatusForBestellung(id)== Stati.Status.GELIEFERT.getId());
    	} catch (Exception e) {
    		LOGGER.error("Bestellung mit der ID {} sehr wahrscheinlich noch nicht erstellt", id );
    		return false;
    	}
    	
    }

    @GetMapping(value="/getTisch")
    public @ResponseBody Tische getTisch(@RequestParam int id) {
    		return this.tischRepo.findById(id).orElse(null);
    }

    @GetMapping(value="/getGetraenke")
    public @ResponseBody Iterable<Getraenke> getGetraenke() {
    		return this.getraenkRepo.findAll();
    }

    @GetMapping(value="/getLieferungen")
    public @ResponseBody Iterable<Bestellungen> getLieferungen() {
    	return this.bestellungRepo.getLieferungen(Stati.Status.VORBEREITET.getId());
    }

    /**
     * Takes a JSON object with the name of the new kunde { "name": "Charlie" }.
     * Returns the newly created kunde.
     *
     * @param {@link CreateUserRequest}
     * @return {@link Kunden}
     *
     * */
    @PostMapping(path = "/createKunde", consumes = "application/json")
    public @ResponseBody Kunden createKunde(@RequestBody CreateUserRequest createUserRequest) {
        // This returns a JSON or XML with the users
        return this.kundeService.createKunde(createUserRequest.getName());
        //return "createKunde erfolgreich, return value: " + ret;
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

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<TestTable> getAll() {
        // This returns a JSON or XML with the users
        return this.testTableRepo.findAll();
    }

    @GetMapping(path = "/one")
    public @ResponseBody TestTable getOne() {
        // This returns a JSON or XML with the users
        try {
            return this.testTableRepo.findById(1).get();
        } catch (NoSuchElementException e) {
            System.err.println(e);
        }
        return new TestTable(1, "test");
    }

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        TestTable n = new TestTable();
        n.setName(name);
        testTableRepo.save(n);
        return "Saved";
    }

    @GetMapping(path = "/allTables")
    public @ResponseBody Collection<TestTable> getAllTables() {
        // This returns a JSON or XML with the users
        return this.testTableRepo.getAllTables();
    }
}
