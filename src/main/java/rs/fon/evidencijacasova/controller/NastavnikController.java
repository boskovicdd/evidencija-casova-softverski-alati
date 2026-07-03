package rs.fon.evidencijacasova.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.evidencijacasova.dto.DodajSertifikatZahtev;
import rs.fon.evidencijacasova.dto.KreirajNastavnikaZahtev;
import rs.fon.evidencijacasova.dto.PrijavaZahtev;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.service.NastavnikServis;

@RestController
@RequestMapping("/api/nastavnici")
public class NastavnikController {

    private final NastavnikServis nastavnikServis;

    public NastavnikController(NastavnikServis nastavnikServis) {
        this.nastavnikServis = nastavnikServis;
    }

    @PostMapping("/prijava")
    public Nastavnik prijava(@RequestBody PrijavaZahtev zahtev) {
        return nastavnikServis.prijava(zahtev);
    }

    @PostMapping
    public Nastavnik kreirajNastavnika(@RequestBody KreirajNastavnikaZahtev zahtev) {
        return nastavnikServis.kreirajNastavnika(zahtev);
    }

    @GetMapping
    public List<Nastavnik> pretraziNastavnike() {
        return nastavnikServis.pretraziNastavnike();
    }

    @PostMapping("/{id}/sertifikati")
    public Nastavnik dodajSertifikatNastavniku(@PathVariable Long id, @RequestBody DodajSertifikatZahtev zahtev) {
        return nastavnikServis.dodajSertifikatNastavniku(id, zahtev);
    }
}
