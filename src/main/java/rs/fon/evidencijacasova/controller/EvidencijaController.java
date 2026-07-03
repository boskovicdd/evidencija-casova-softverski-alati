package rs.fon.evidencijacasova.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.evidencijacasova.dto.KreirajEvidencijuZahtev;
import rs.fon.evidencijacasova.dto.StavkaZahtev;
import rs.fon.evidencijacasova.entity.EvidencijaCasova;
import rs.fon.evidencijacasova.service.EvidencijaServis;

@RestController
@RequestMapping("/api/evidencije")
public class EvidencijaController {

    private final EvidencijaServis evidencijaServis;

    public EvidencijaController(EvidencijaServis evidencijaServis) {
        this.evidencijaServis = evidencijaServis;
    }

    @PostMapping
    public EvidencijaCasova kreirajEvidenciju(@RequestBody KreirajEvidencijuZahtev zahtev) {
        return evidencijaServis.kreirajEvidenciju(zahtev);
    }

    @GetMapping
    public List<EvidencijaCasova> pretraziEvidencije(
            @RequestParam(required = false) Long idJezik,
            @RequestParam(required = false) Long idNastavnik) {
        return evidencijaServis.pretraziEvidencije(idJezik, idNastavnik);
    }

    @GetMapping("/{id}")
    public EvidencijaCasova vratiEvidenciju(@PathVariable Long id) {
        return evidencijaServis.vratiEvidenciju(id);
    }

    @PostMapping("/{id}/stavke")
    public EvidencijaCasova dodajStavkuUEvidenciju(@PathVariable Long id, @RequestBody StavkaZahtev zahtev) {
        return evidencijaServis.dodajStavkuUEvidenciju(id, zahtev);
    }

    @DeleteMapping("/{id}")
    public void obrisiEvidenciju(@PathVariable Long id) {
        evidencijaServis.obrisiEvidenciju(id);
    }
}
