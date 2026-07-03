package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.KreirajEvidencijuZahtev;
import rs.fon.evidencijacasova.dto.StavkaZahtev;
import rs.fon.evidencijacasova.entity.EvidencijaCasova;

public interface EvidencijaServis {

    EvidencijaCasova kreirajEvidenciju(KreirajEvidencijuZahtev zahtev);

    List<EvidencijaCasova> pretraziEvidencije(Long idJezik, Long idNastavnik);

    EvidencijaCasova vratiEvidenciju(Long id);

    EvidencijaCasova dodajStavkuUEvidenciju(Long id, StavkaZahtev zahtev);

    void obrisiEvidenciju(Long id);
}
