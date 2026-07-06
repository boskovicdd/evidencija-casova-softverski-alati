package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.IzmeniOsobuZahtev;
import rs.fon.evidencijacasova.dto.KreirajOsobuZahtev;
import rs.fon.evidencijacasova.entity.Osoba;

/**
 * Interfejs koji definise poslovnu logiku za rad sa osobama (polaznicima kursa).
 *
 * @author boskovicdd
 */
public interface OsobaServis {

    /**
     * Kreira novu osobu.
     *
     * @param zahtev podaci za novu osobu
     * @return sacuvana osoba sa dodeljenim identifikatorom
     * @throws org.springframework.web.server.ResponseStatusException ako nivo znanja sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    Osoba kreirajOsobu(KreirajOsobuZahtev zahtev);

    /**
     * @return lista svih osoba
     */
    List<Osoba> pretraziOsobe();

    /**
     * Menja podatke postojece osobe.
     *
     * @param idOsoba id osobe koja se menja
     * @param zahtev  izmenjeni podaci
     * @return izmenjena osoba
     * @throws org.springframework.web.server.ResponseStatusException ako osoba ili nivo znanja sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    Osoba izmeniOsobu(Long idOsoba, IzmeniOsobuZahtev zahtev);
}
