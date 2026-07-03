package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.KreirajPlanCasaZahtev;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.PlanCasa;

public interface SifarnikServis {

    PlanCasa kreirajPlanCasa(KreirajPlanCasaZahtev zahtev);

    List<Jezik> pretraziJezike();

    List<NivoZnanja> pretraziNivoeZnanja();

    List<PlanCasa> pretraziPlanoveCasa();
}
