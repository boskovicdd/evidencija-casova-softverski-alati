package rs.fon.evidencijacasova.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EvidencijaCasovaTest {

    private EvidencijaCasova evidencija;
    private Nastavnik nastavnik;
    private Osoba osoba;
    private Jezik jezik;

    @BeforeEach
    void setUp() {
        nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        jezik = new Jezik("Engleski", "EN");
        evidencija = new EvidencijaCasova(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), nastavnik, osoba, jezik);
    }

    @AfterEach
    void tearDown() {
        evidencija = null;
        nastavnik = null;
        osoba = null;
        jezik = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals(LocalDate.of(2026, 1, 10), evidencija.getDatOd());
        assertEquals(LocalDate.of(2026, 2, 10), evidencija.getDatDo());
        assertEquals(nastavnik, evidencija.getNastavnik());
        assertEquals(osoba, evidencija.getOsoba());
        assertEquals(jezik, evidencija.getJezik());
        assertEquals(0, evidencija.getBrojCasova());
        assertEquals(0, evidencija.getProsecnaOcena());
        assertTrue(evidencija.getStavke().isEmpty());
    }

    @Test
    void testSetIdEvidencija() {
        evidencija.setIdEvidencija(6L);
        assertEquals(6L, evidencija.getIdEvidencija());
    }

    @Test
    void testSetDatOd() {
        evidencija.setDatOd(LocalDate.of(2026, 1, 15));
        assertEquals(LocalDate.of(2026, 1, 15), evidencija.getDatOd());
    }

    @Test
    void testSetDatOdNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setDatOd(null));
        assertEquals("Datum od ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetDatOdPosleDatDo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> evidencija.setDatOd(LocalDate.of(2026, 3, 1)));
        assertEquals("Datum do ne sme biti pre datuma od", ex.getMessage());
    }

    @Test
    void testSetDatDo() {
        evidencija.setDatDo(LocalDate.of(2026, 2, 20));
        assertEquals(LocalDate.of(2026, 2, 20), evidencija.getDatDo());
    }

    @Test
    void testSetDatDoNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setDatDo(null));
        assertEquals("Datum do ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetDatDoPreDatOd() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> evidencija.setDatDo(LocalDate.of(2026, 1, 1)));
        assertEquals("Datum do ne sme biti pre datuma od", ex.getMessage());
    }

    @Test
    void testSetBrojCasova() {
        evidencija.setBrojCasova(5);
        assertEquals(5, evidencija.getBrojCasova());
    }

    @Test
    void testSetBrojCasovaNegativan() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> evidencija.setBrojCasova(-1));
        assertEquals("Broj časova ne sme biti negativan", ex.getMessage());
    }

    @Test
    void testSetProsecnaOcena() {
        evidencija.setProsecnaOcena(4.5);
        assertEquals(4.5, evidencija.getProsecnaOcena());
    }

    @Test
    void testSetNastavnik() {
        Nastavnik drugi = new Nastavnik("jelena", "lozinka2", "Jelena", "Jelic");
        evidencija.setNastavnik(drugi);
        assertEquals(drugi, evidencija.getNastavnik());
    }

    @Test
    void testSetNastavnikNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setNastavnik(null));
        assertEquals("Nastavnik ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetOsoba() {
        Osoba druga = new Osoba("Milica", "Milic", "064-2222222", new NivoZnanja("B1", 3));
        evidencija.setOsoba(druga);
        assertEquals(druga, evidencija.getOsoba());
    }

    @Test
    void testSetOsobaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setOsoba(null));
        assertEquals("Osoba ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetJezik() {
        Jezik drugi = new Jezik("Nemacki", "DE");
        evidencija.setJezik(drugi);
        assertEquals(drugi, evidencija.getJezik());
    }

    @Test
    void testSetJezikNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setJezik(null));
        assertEquals("Jezik ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetStavkeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.setStavke(null));
        assertEquals("Lista stavki ne sme biti null", ex.getMessage());
    }

    @Test
    void testDodajStavku() {
        PlanCasa planCasa = new PlanCasa("Konverzacija", "opis");
        StavkaEvidencijeCasova stavka = new StavkaEvidencijeCasova(1, LocalDate.of(2026, 1, 12), 5, planCasa);

        evidencija.dodajStavku(stavka);

        assertEquals(1, evidencija.getStavke().size());
        assertEquals(evidencija, stavka.getEvidencijaCasova());
    }

    @Test
    void testDodajStavkuNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> evidencija.dodajStavku(null));
        assertEquals("Stavka ne sme biti null", ex.getMessage());
    }

    @Test
    void testPreracunajProsecnuOcenuPrazna() {
        evidencija.preracunajProsecnuOcenu();
        assertEquals(0, evidencija.getProsecnaOcena());
        assertEquals(0, evidencija.getBrojCasova());
    }

    @Test
    void testPreracunajProsecnuOcenuSaStavkama() {
        PlanCasa planCasa = new PlanCasa("Konverzacija", "opis");
        evidencija.dodajStavku(new StavkaEvidencijeCasova(1, LocalDate.of(2026, 1, 12), 4, planCasa));
        evidencija.dodajStavku(new StavkaEvidencijeCasova(2, LocalDate.of(2026, 1, 19), 5, planCasa));

        evidencija.preracunajProsecnuOcenu();

        assertEquals(4.5, evidencija.getProsecnaOcena());
        assertEquals(2, evidencija.getBrojCasova());
    }

    @ParameterizedTest(name = "{0}/{1}/{2}/{3}/{4} -> {5}")
    @CsvSource({
            "2026-01-10, 2026-02-10, marko, Ana, Engleski, true",
            "2026-01-15, 2026-02-10, marko, Ana, Engleski, false",
            "2026-01-10, 2026-02-15, marko, Ana, Engleski, false",
            "2026-01-10, 2026-02-10, jelena, Ana, Engleski, false",
            "2026-01-10, 2026-02-10, marko, Milica, Engleski, false",
            "2026-01-10, 2026-02-10, marko, Ana, Nemacki, false"
    })
    void testEquals(String datOd, String datDo, String username, String ime, String jezikNaziv, boolean ocekivano) {
        Nastavnik drugiNastavnik = new Nastavnik(username, "lozinka1", "Marko", "Markovic");
        Osoba drugaOsoba = new Osoba(ime, "Anic", "064-1111111", new NivoZnanja("A1", 1));
        Jezik drugiJezik = new Jezik(jezikNaziv, "EN");
        EvidencijaCasova drugi = new EvidencijaCasova(
                LocalDate.parse(datOd), LocalDate.parse(datDo), drugiNastavnik, drugaOsoba, drugiJezik);
        assertEquals(ocekivano, evidencija.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(evidencija.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(evidencija.equals(evidencija));
    }

    @Test
    void testToString() {
        String rezultat = evidencija.toString();
        assertTrue(rezultat.contains("marko"));
        assertTrue(rezultat.contains("Ana"));
        assertTrue(rezultat.contains("Engleski"));
    }
}
