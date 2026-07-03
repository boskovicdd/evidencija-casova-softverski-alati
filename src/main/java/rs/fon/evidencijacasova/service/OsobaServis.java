package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.IzmeniOsobuZahtev;
import rs.fon.evidencijacasova.dto.KreirajOsobuZahtev;
import rs.fon.evidencijacasova.entity.Osoba;

public interface OsobaServis {

    Osoba kreirajOsobu(KreirajOsobuZahtev zahtev);

    List<Osoba> pretraziOsobe();

    Osoba izmeniOsobu(Long idOsoba, IzmeniOsobuZahtev zahtev);
}
