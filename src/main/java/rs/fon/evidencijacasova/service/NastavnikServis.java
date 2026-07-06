package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.DodajSertifikatZahtev;
import rs.fon.evidencijacasova.dto.KreirajNastavnikaZahtev;
import rs.fon.evidencijacasova.dto.PrijavaZahtev;
import rs.fon.evidencijacasova.entity.Nastavnik;

/**
 * Interfejs koji definise poslovnu logiku za rad sa nastavnicima - prijavu,
 * kreiranje naloga i dodavanje sertifikata.
 *
 * @author boskovicdd
 */
public interface NastavnikServis {

    /**
     * Prijavljuje nastavnika na osnovu korisnickog imena i lozinke.
     *
     * @param zahtev korisnicko ime i lozinka
     * @return nastavnik ciji su podaci za prijavu ispravni
     * @throws org.springframework.web.server.ResponseStatusException ako korisnicko ime ne postoji ili je lozinka pogresna (HTTP 401 UNAUTHORIZED)
     */
    Nastavnik prijava(PrijavaZahtev zahtev);

    /**
     * Kreira novi nalog nastavnika.
     *
     * @param zahtev podaci za novog nastavnika
     * @return sacuvan nastavnik sa dodeljenim identifikatorom
     * @throws org.springframework.web.server.ResponseStatusException ako je korisnicko ime vec zauzeto (HTTP 400 BAD_REQUEST)
     */
    Nastavnik kreirajNastavnika(KreirajNastavnikaZahtev zahtev);

    /**
     * @return lista svih nastavnika
     */
    List<Nastavnik> pretraziNastavnike();

    /**
     * Dodaje sertifikat postojecem nastavniku.
     *
     * @param idNastavnik id nastavnika kome se dodaje sertifikat
     * @param zahtev      id sertifikata i datum izdavanja
     * @return nastavnik sa dodatim sertifikatom
     * @throws org.springframework.web.server.ResponseStatusException ako nastavnik ili sertifikat sa zadatim id-jem ne postoji (HTTP 404 NOT_FOUND)
     */
    Nastavnik dodajSertifikatNastavniku(Long idNastavnik, DodajSertifikatZahtev zahtev);
}
