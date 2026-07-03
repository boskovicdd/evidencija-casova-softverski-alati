package rs.fon.evidencijacasova.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.evidencijacasova.dto.IzmeniOsobuZahtev;
import rs.fon.evidencijacasova.dto.KreirajOsobuZahtev;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.service.OsobaServis;

@RestController
@RequestMapping("/api/osobe")
public class OsobaController {

    private final OsobaServis osobaServis;

    public OsobaController(OsobaServis osobaServis) {
        this.osobaServis = osobaServis;
    }

    @PostMapping
    public Osoba kreirajOsobu(@RequestBody KreirajOsobuZahtev zahtev) {
        return osobaServis.kreirajOsobu(zahtev);
    }

    @GetMapping
    public List<Osoba> pretraziOsobe() {
        return osobaServis.pretraziOsobe();
    }

    @PutMapping("/{id}")
    public Osoba izmeniOsobu(@PathVariable Long id, @RequestBody IzmeniOsobuZahtev zahtev) {
        return osobaServis.izmeniOsobu(id, zahtev);
    }
}
