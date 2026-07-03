package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.DodajSertifikatZahtev;
import rs.fon.evidencijacasova.dto.KreirajNastavnikaZahtev;
import rs.fon.evidencijacasova.dto.PrijavaZahtev;
import rs.fon.evidencijacasova.entity.Nastavnik;

public interface NastavnikServis {

    Nastavnik prijava(PrijavaZahtev zahtev);

    Nastavnik kreirajNastavnika(KreirajNastavnikaZahtev zahtev);

    List<Nastavnik> pretraziNastavnike();

    Nastavnik dodajSertifikatNastavniku(Long idNastavnik, DodajSertifikatZahtev zahtev);
}
