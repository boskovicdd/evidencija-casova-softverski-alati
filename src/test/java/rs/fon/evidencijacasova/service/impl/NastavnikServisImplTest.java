package rs.fon.evidencijacasova.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import rs.fon.evidencijacasova.dto.DodajSertifikatZahtev;
import rs.fon.evidencijacasova.dto.KreirajNastavnikaZahtev;
import rs.fon.evidencijacasova.dto.PrijavaZahtev;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.entity.Sertifikat;
import rs.fon.evidencijacasova.repository.NastavnikRepository;
import rs.fon.evidencijacasova.repository.SertifikatRepository;

@ExtendWith(MockitoExtension.class)
class NastavnikServisImplTest {

    @Mock
    private NastavnikRepository nastavnikRepository;

    @Mock
    private SertifikatRepository sertifikatRepository;

    @InjectMocks
    private NastavnikServisImpl nastavnikServis;

    @AfterEach
    void tearDown() {
        nastavnikServis = null;
    }

    @Test
    void testPrijava() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        when(nastavnikRepository.findByUsername("marko")).thenReturn(Optional.of(nastavnik));

        Nastavnik rezultat = nastavnikServis.prijava(new PrijavaZahtev("marko", "lozinka1"));

        assertEquals(nastavnik, rezultat);
    }

    @Test
    void testPrijavaPogresnaLozinka() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        when(nastavnikRepository.findByUsername("marko")).thenReturn(Optional.of(nastavnik));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> nastavnikServis.prijava(new PrijavaZahtev("marko", "pogresna")));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("Pogrešno korisničko ime ili lozinka", ex.getReason());
    }

    @Test
    void testPrijavaNepostojeciUsername() {
        when(nastavnikRepository.findByUsername("nepostoji")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> nastavnikServis.prijava(new PrijavaZahtev("nepostoji", "lozinka1")));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("Pogrešno korisničko ime ili lozinka", ex.getReason());
    }

    @Test
    void testKreirajNastavnika() {
        KreirajNastavnikaZahtev zahtev = new KreirajNastavnikaZahtev("jelena", "lozinka2", "Jelena", "Jelic");
        Nastavnik sacuvan = new Nastavnik("jelena", "lozinka2", "Jelena", "Jelic");
        sacuvan.setIdNastavnik(2L);

        when(nastavnikRepository.findByUsername("jelena")).thenReturn(Optional.empty());
        when(nastavnikRepository.save(any(Nastavnik.class))).thenReturn(sacuvan);

        Nastavnik rezultat = nastavnikServis.kreirajNastavnika(zahtev);

        assertEquals(sacuvan, rezultat);
    }

    @Test
    void testKreirajNastavnikaUsernameZauzet() {
        KreirajNastavnikaZahtev zahtev = new KreirajNastavnikaZahtev("marko", "lozinka1", "Marko", "Markovic");
        when(nastavnikRepository.findByUsername("marko"))
                .thenReturn(Optional.of(new Nastavnik("marko", "lozinka1", "Marko", "Markovic")));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> nastavnikServis.kreirajNastavnika(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Korisničko ime je već zauzeto", ex.getReason());
    }

    @Test
    void testPretraziNastavnike() {
        List<Nastavnik> nastavnici = List.of(new Nastavnik("marko", "lozinka1", "Marko", "Markovic"));
        when(nastavnikRepository.findAll()).thenReturn(nastavnici);

        List<Nastavnik> rezultat = nastavnikServis.pretraziNastavnike();

        assertEquals(nastavnici, rezultat);
    }

    @Test
    void testDodajSertifikatNastavniku() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        nastavnik.setIdNastavnik(1L);
        Sertifikat sertifikat = new Sertifikat("CAE");
        sertifikat.setIdSertifikat(1L);
        DodajSertifikatZahtev zahtev = new DodajSertifikatZahtev(1L, LocalDate.of(2020, 6, 15));

        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik));
        when(sertifikatRepository.findById(1L)).thenReturn(Optional.of(sertifikat));
        when(nastavnikRepository.save(any(Nastavnik.class))).thenReturn(nastavnik);

        Nastavnik rezultat = nastavnikServis.dodajSertifikatNastavniku(1L, zahtev);

        assertEquals(1, rezultat.getNastavnickiSertifikati().size());
        assertEquals(sertifikat, rezultat.getNastavnickiSertifikati().get(0).getSertifikat());
    }

    @Test
    void testDodajSertifikatNastavnikNePostoji() {
        DodajSertifikatZahtev zahtev = new DodajSertifikatZahtev(1L, LocalDate.of(2020, 6, 15));
        when(nastavnikRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> nastavnikServis.dodajSertifikatNastavniku(99L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Nastavnik ne postoji", ex.getReason());
    }

    @Test
    void testDodajSertifikatSertifikatNePostoji() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        nastavnik.setIdNastavnik(1L);
        DodajSertifikatZahtev zahtev = new DodajSertifikatZahtev(99L, LocalDate.of(2020, 6, 15));

        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik));
        when(sertifikatRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> nastavnikServis.dodajSertifikatNastavniku(1L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Sertifikat ne postoji", ex.getReason());
    }
}
