package rs.fon.evidencijacasova.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.evidencijacasova.dto.KreirajPlanCasaZahtev;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.service.SifarnikServis;

@RestController
public class SifarnikController {

    private final SifarnikServis sifarnikServis;

    public SifarnikController(SifarnikServis sifarnikServis) {
        this.sifarnikServis = sifarnikServis;
    }

    @PostMapping("/api/planovi-casa")
    public PlanCasa kreirajPlanCasa(@RequestBody KreirajPlanCasaZahtev zahtev) {
        return sifarnikServis.kreirajPlanCasa(zahtev);
    }

    @GetMapping("/api/jezici")
    public List<Jezik> pretraziJezike() {
        return sifarnikServis.pretraziJezike();
    }

    @GetMapping("/api/nivoi-znanja")
    public List<NivoZnanja> pretraziNivoeZnanja() {
        return sifarnikServis.pretraziNivoeZnanja();
    }

    @GetMapping("/api/planovi-casa")
    public List<PlanCasa> pretraziPlanoveCasa() {
        return sifarnikServis.pretraziPlanoveCasa();
    }
}
