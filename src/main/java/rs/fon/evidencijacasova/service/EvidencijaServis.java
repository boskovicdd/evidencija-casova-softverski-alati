package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.KreirajEvidencijuZahtev;
import rs.fon.evidencijacasova.dto.StavkaZahtev;
import rs.fon.evidencijacasova.entity.EvidencijaCasova;

/**
 * Interfejs koji definise poslovnu logiku za rad sa evidencijama casova.
 *
 * @author boskovicdd
 */
public interface EvidencijaServis {

    /**
     * Kreira novu evidenciju casova, zajedno sa svim stavkama iz zahteva, i
     * odmah preracunava prosecnu ocenu.
     *
     * @param zahtev podaci za novu evidenciju, ukljucujuci listu stavki
     * @return sacuvana evidencija sa dodeljenim identifikatorom
     * @throws org.springframework.web.server.ResponseStatusException ako zahtev nema nijednu stavku (HTTP 400 BAD_REQUEST),
     *         ili ako nastavnik, osoba, jezik ili neki od planova casa iz stavki ne postoje (HTTP 404 NOT_FOUND)
     */
    EvidencijaCasova kreirajEvidenciju(KreirajEvidencijuZahtev zahtev);

    /**
     * Pretrazuje evidencije, opciono filtrirane po jeziku i/ili nastavniku.
     *
     * @param idJezik     id jezika za filtriranje, ili null ako se ne filtrira po jeziku
     * @param idNastavnik id nastavnika za filtriranje, ili null ako se ne filtrira po nastavniku
     * @return lista evidencija koje odgovaraju zadatim filterima
     */
    List<EvidencijaCasova> pretraziEvidencije(Long idJezik, Long idNastavnik);

    /**
     * Vraca evidenciju sa zadatim identifikatorom.
     *
     * @param id id evidencije
     * @return evidencija sa zadatim identifikatorom
     * @throws org.springframework.web.server.ResponseStatusException ako evidencija sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    EvidencijaCasova vratiEvidenciju(Long id);

    /**
     * Dodaje novu stavku postojecoj evidenciji i ponovo preracunava prosecnu ocenu.
     *
     * @param id     id evidencije kojoj se dodaje stavka
     * @param zahtev podaci za novu stavku
     * @return evidencija sa dodatom stavkom
     * @throws org.springframework.web.server.ResponseStatusException ako evidencija ili plan casa sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    EvidencijaCasova dodajStavkuUEvidenciju(Long id, StavkaZahtev zahtev);

    /**
     * Brise evidenciju sa zadatim identifikatorom.
     *
     * @param id id evidencije koja se brise
     * @throws org.springframework.web.server.ResponseStatusException ako evidencija sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    void obrisiEvidenciju(Long id);
}
