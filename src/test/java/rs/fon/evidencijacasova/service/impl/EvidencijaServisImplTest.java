package rs.fon.evidencijacasova.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import rs.fon.evidencijacasova.dto.KreirajEvidencijuZahtev;
import rs.fon.evidencijacasova.dto.StavkaZahtev;
import rs.fon.evidencijacasova.entity.EvidencijaCasova;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.entity.StavkaEvidencijeCasova;
import rs.fon.evidencijacasova.repository.EvidencijaCasovaRepository;
import rs.fon.evidencijacasova.repository.JezikRepository;
import rs.fon.evidencijacasova.repository.NastavnikRepository;
import rs.fon.evidencijacasova.repository.OsobaRepository;
import rs.fon.evidencijacasova.repository.PlanCasaRepository;

@ExtendWith(MockitoExtension.class)
class EvidencijaServisImplTest {

    @Mock
    private EvidencijaCasovaRepository evidencijaCasovaRepository;

    @Mock
    private NastavnikRepository nastavnikRepository;

    @Mock
    private OsobaRepository osobaRepository;

    @Mock
    private JezikRepository jezikRepository;

    @Mock
    private PlanCasaRepository planCasaRepository;

    @InjectMocks
    private EvidencijaServisImpl evidencijaServis;

    @AfterEach
    void tearDown() {
        evidencijaServis = null;
    }

    private Nastavnik nastavnik() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        nastavnik.setIdNastavnik(1L);
        return nastavnik;
    }

    private Osoba osoba() {
        Osoba osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        osoba.setIdOsoba(1L);
        return osoba;
    }

    private Jezik jezik() {
        Jezik jezik = new Jezik("Engleski", "EN");
        jezik.setIdJezik(1L);
        return jezik;
    }

    private PlanCasa planCasa() {
        PlanCasa planCasa = new PlanCasa("Konverzacija", "opis1");
        planCasa.setIdPlanCasa(1L);
        return planCasa;
    }

    @Test
    void testKreirajEvidenciju() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 1L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                List.of(new StavkaZahtev(1, LocalDate.of(2026, 1, 12), 4, 1L),
                        new StavkaZahtev(2, LocalDate.of(2026, 1, 19), 5, 1L)));

        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik()));
        when(osobaRepository.findById(1L)).thenReturn(Optional.of(osoba()));
        when(jezikRepository.findById(1L)).thenReturn(Optional.of(jezik()));
        when(planCasaRepository.findById(1L)).thenReturn(Optional.of(planCasa()));
        when(evidencijaCasovaRepository.save(any(EvidencijaCasova.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvidencijaCasova rezultat = evidencijaServis.kreirajEvidenciju(zahtev);

        assertEquals(2, rezultat.getBrojCasova());
        assertEquals(4.5, rezultat.getProsecnaOcena());
    }

    @Test
    void testKreirajEvidencijuBezStavki() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 1L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), List.of());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Evidencija mora imati bar jednu stavku", ex.getReason());
    }

    @Test
    void testKreirajEvidencijuNullStavke() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 1L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Evidencija mora imati bar jednu stavku", ex.getReason());
    }

    @Test
    void testKreirajEvidencijuNastavnikNePostoji() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(99L, 1L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                List.of(new StavkaZahtev(1, LocalDate.of(2026, 1, 12), 4, 1L)));
        when(nastavnikRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Nastavnik ne postoji", ex.getReason());
    }

    @Test
    void testKreirajEvidencijuOsobaNePostoji() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 99L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                List.of(new StavkaZahtev(1, LocalDate.of(2026, 1, 12), 4, 1L)));
        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik()));
        when(osobaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Osoba ne postoji", ex.getReason());
    }

    @Test
    void testKreirajEvidencijuJezikNePostoji() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 1L, 99L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                List.of(new StavkaZahtev(1, LocalDate.of(2026, 1, 12), 4, 1L)));
        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik()));
        when(osobaRepository.findById(1L)).thenReturn(Optional.of(osoba()));
        when(jezikRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Jezik ne postoji", ex.getReason());
    }

    @Test
    void testKreirajEvidencijuPlanCasaNePostoji() {
        KreirajEvidencijuZahtev zahtev = new KreirajEvidencijuZahtev(1L, 1L, 1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                List.of(new StavkaZahtev(1, LocalDate.of(2026, 1, 12), 4, 99L)));
        when(nastavnikRepository.findById(1L)).thenReturn(Optional.of(nastavnik()));
        when(osobaRepository.findById(1L)).thenReturn(Optional.of(osoba()));
        when(jezikRepository.findById(1L)).thenReturn(Optional.of(jezik()));
        when(planCasaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.kreirajEvidenciju(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Plan časa ne postoji", ex.getReason());
    }

    @Test
    void testPretraziEvidencijeJezikINastavnik() {
        evidencijaServis.pretraziEvidencije(1L, 2L);
        verify(evidencijaCasovaRepository).findByJezikIdJezikAndNastavnikIdNastavnik(1L, 2L);
    }

    @Test
    void testPretraziEvidencijeSamoJezik() {
        evidencijaServis.pretraziEvidencije(1L, null);
        verify(evidencijaCasovaRepository).findByJezikIdJezik(1L);
    }

    @Test
    void testPretraziEvidencijeSamoNastavnik() {
        evidencijaServis.pretraziEvidencije(null, 2L);
        verify(evidencijaCasovaRepository).findByNastavnikIdNastavnik(2L);
    }

    @Test
    void testPretraziEvidencijeBezFiltera() {
        evidencijaServis.pretraziEvidencije(null, null);
        verify(evidencijaCasovaRepository).findAll();
    }

    @Test
    void testVratiEvidenciju() {
        EvidencijaCasova evidencija = new EvidencijaCasova(
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), nastavnik(), osoba(), jezik());
        when(evidencijaCasovaRepository.findById(1L)).thenReturn(Optional.of(evidencija));

        EvidencijaCasova rezultat = evidencijaServis.vratiEvidenciju(1L);

        assertEquals(evidencija, rezultat);
    }

    @Test
    void testVratiEvidencijuNePostoji() {
        when(evidencijaCasovaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> evidencijaServis.vratiEvidenciju(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Evidencija ne postoji", ex.getReason());
    }

    @Test
    void testDodajStavkuUEvidenciju() {
        EvidencijaCasova evidencija = new EvidencijaCasova(
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), nastavnik(), osoba(), jezik());
        evidencija.dodajStavku(new StavkaEvidencijeCasova(1, LocalDate.of(2026, 1, 12), 4, planCasa()));
        evidencija.preracunajProsecnuOcenu();
        StavkaZahtev zahtev = new StavkaZahtev(2, LocalDate.of(2026, 1, 19), 5, 1L);

        when(evidencijaCasovaRepository.findById(1L)).thenReturn(Optional.of(evidencija));
        when(planCasaRepository.findById(1L)).thenReturn(Optional.of(planCasa()));
        when(evidencijaCasovaRepository.save(any(EvidencijaCasova.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvidencijaCasova rezultat = evidencijaServis.dodajStavkuUEvidenciju(1L, zahtev);

        assertEquals(2, rezultat.getBrojCasova());
        assertEquals(4.5, rezultat.getProsecnaOcena());
    }

    @Test
    void testDodajStavkuUEvidencijuEvidencijaNePostoji() {
        StavkaZahtev zahtev = new StavkaZahtev(2, LocalDate.of(2026, 1, 19), 5, 1L);
        when(evidencijaCasovaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.dodajStavkuUEvidenciju(99L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Evidencija ne postoji", ex.getReason());
    }

    @Test
    void testDodajStavkuUEvidencijuPlanCasaNePostoji() {
        EvidencijaCasova evidencija = new EvidencijaCasova(
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), nastavnik(), osoba(), jezik());
        StavkaZahtev zahtev = new StavkaZahtev(1, LocalDate.of(2026, 1, 19), 5, 99L);

        when(evidencijaCasovaRepository.findById(1L)).thenReturn(Optional.of(evidencija));
        when(planCasaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.dodajStavkuUEvidenciju(1L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Plan časa ne postoji", ex.getReason());
    }

    @Test
    void testObrisiEvidenciju() {
        when(evidencijaCasovaRepository.existsById(1L)).thenReturn(true);

        evidencijaServis.obrisiEvidenciju(1L);

        verify(evidencijaCasovaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObrisiEvidencijuNePostoji() {
        when(evidencijaCasovaRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> evidencijaServis.obrisiEvidenciju(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Evidencija ne postoji", ex.getReason());
        verify(evidencijaCasovaRepository, never()).deleteById(any());
    }
}
