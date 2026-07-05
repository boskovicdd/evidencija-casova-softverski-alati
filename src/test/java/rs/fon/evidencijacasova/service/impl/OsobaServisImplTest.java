package rs.fon.evidencijacasova.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import rs.fon.evidencijacasova.dto.IzmeniOsobuZahtev;
import rs.fon.evidencijacasova.dto.KreirajOsobuZahtev;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.repository.NivoZnanjaRepository;
import rs.fon.evidencijacasova.repository.OsobaRepository;

@ExtendWith(MockitoExtension.class)
class OsobaServisImplTest {

    @Mock
    private OsobaRepository osobaRepository;

    @Mock
    private NivoZnanjaRepository nivoZnanjaRepository;

    @InjectMocks
    private OsobaServisImpl osobaServis;

    @AfterEach
    void tearDown() {
        osobaServis = null;
    }

    @Test
    void testKreirajOsobu() {
        NivoZnanja nivoZnanja = new NivoZnanja("A1", 1);
        KreirajOsobuZahtev zahtev = new KreirajOsobuZahtev("Ana", "Anic", "064-1111111", 1L);
        Osoba sacuvana = new Osoba("Ana", "Anic", "064-1111111", nivoZnanja);
        sacuvana.setIdOsoba(1L);

        when(nivoZnanjaRepository.findById(1L)).thenReturn(Optional.of(nivoZnanja));
        when(osobaRepository.save(any(Osoba.class))).thenReturn(sacuvana);

        Osoba rezultat = osobaServis.kreirajOsobu(zahtev);

        assertEquals(sacuvana, rezultat);
    }

    @Test
    void testKreirajOsobuNivoZnanjaNePostoji() {
        KreirajOsobuZahtev zahtev = new KreirajOsobuZahtev("Ana", "Anic", "064-1111111", 99L);
        when(nivoZnanjaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> osobaServis.kreirajOsobu(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Nivo znanja ne postoji", ex.getReason());
    }

    @Test
    void testPretraziOsobe() {
        List<Osoba> osobe = List.of(new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1)));
        when(osobaRepository.findAll()).thenReturn(osobe);

        List<Osoba> rezultat = osobaServis.pretraziOsobe();

        assertEquals(osobe, rezultat);
    }

    @Test
    void testIzmeniOsobu() {
        NivoZnanja b2 = new NivoZnanja("B2", 4);
        Osoba postojeca = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        postojeca.setIdOsoba(1L);
        IzmeniOsobuZahtev zahtev = new IzmeniOsobuZahtev("Ana", "Anic", "064-9999999", 4L);

        when(osobaRepository.findById(1L)).thenReturn(Optional.of(postojeca));
        when(nivoZnanjaRepository.findById(4L)).thenReturn(Optional.of(b2));
        when(osobaRepository.save(any(Osoba.class))).thenReturn(postojeca);

        Osoba rezultat = osobaServis.izmeniOsobu(1L, zahtev);

        assertEquals("064-9999999", rezultat.getBrTel());
        assertEquals(b2, rezultat.getNivoZnanja());
    }

    @Test
    void testIzmeniOsobuOsobaNePostoji() {
        IzmeniOsobuZahtev zahtev = new IzmeniOsobuZahtev("Ana", "Anic", "064-9999999", 4L);
        when(osobaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> osobaServis.izmeniOsobu(99L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Osoba ne postoji", ex.getReason());
    }

    @Test
    void testIzmeniOsobuNivoZnanjaNePostoji() {
        Osoba postojeca = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        postojeca.setIdOsoba(1L);
        IzmeniOsobuZahtev zahtev = new IzmeniOsobuZahtev("Ana", "Anic", "064-9999999", 99L);

        when(osobaRepository.findById(1L)).thenReturn(Optional.of(postojeca));
        when(nivoZnanjaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> osobaServis.izmeniOsobu(1L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Nivo znanja ne postoji", ex.getReason());
    }

    @Test
    void testIzmeniOsobuPozivaSave() {
        NivoZnanja a1 = new NivoZnanja("A1", 1);
        Osoba postojeca = new Osoba("Ana", "Anic", "064-1111111", a1);
        postojeca.setIdOsoba(1L);
        IzmeniOsobuZahtev zahtev = new IzmeniOsobuZahtev("Ana", "Anic", "064-9999999", 1L);

        when(osobaRepository.findById(1L)).thenReturn(Optional.of(postojeca));
        when(nivoZnanjaRepository.findById(1L)).thenReturn(Optional.of(a1));
        when(osobaRepository.save(any(Osoba.class))).thenReturn(postojeca);

        osobaServis.izmeniOsobu(1L, zahtev);

        verify(osobaRepository).save(postojeca);
    }
}
