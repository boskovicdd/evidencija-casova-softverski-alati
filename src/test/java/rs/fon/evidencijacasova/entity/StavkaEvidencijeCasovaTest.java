package rs.fon.evidencijacasova.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StavkaEvidencijeCasovaTest {

    private StavkaEvidencijeCasova stavka;
    private PlanCasa planCasa;

    @BeforeEach
    void setUp() {
        planCasa = new PlanCasa("Konverzacija", "opis1");
        stavka = new StavkaEvidencijeCasova(1, LocalDate.of(2026, 6, 5), 5, planCasa);
    }

    @AfterEach
    void tearDown() {
        stavka = null;
        planCasa = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals(1, stavka.getRbStavke());
        assertEquals(LocalDate.of(2026, 6, 5), stavka.getDatum());
        assertEquals(5, stavka.getOcena());
        assertEquals(planCasa, stavka.getPlanCasa());
        assertNull(stavka.getEvidencijaCasova());
    }

    @Test
    void testSetIdStavka() {
        stavka.setIdStavka(11L);
        assertEquals(11L, stavka.getIdStavka());
    }

    @Test
    void testSetRbStavke() {
        stavka.setRbStavke(3);
        assertEquals(3, stavka.getRbStavke());
    }

    @Test
    void testSetRbStavkeNevalidan() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> stavka.setRbStavke(0));
        assertEquals("Redni broj stavke mora biti pozitivan", ex.getMessage());
    }

    @Test
    void testSetDatum() {
        stavka.setDatum(LocalDate.of(2026, 6, 12));
        assertEquals(LocalDate.of(2026, 6, 12), stavka.getDatum());
    }

    @Test
    void testSetDatumNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> stavka.setDatum(null));
        assertEquals("Datum ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetOcena() {
        stavka.setOcena(3);
        assertEquals(3, stavka.getOcena());
    }

    @ParameterizedTest(name = "nevalidna ocena: [{0}]")
    @CsvSource({"0", "6"})
    void testSetOcenaNevalidna(int ocena) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> stavka.setOcena(ocena));
        assertEquals("Ocena mora biti u opsegu od 1 do 5", ex.getMessage());
    }

    @Test
    void testSetPlanCasa() {
        PlanCasa drugi = new PlanCasa("Gramatika", "opis2");
        stavka.setPlanCasa(drugi);
        assertEquals(drugi, stavka.getPlanCasa());
    }

    @Test
    void testSetPlanCasaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> stavka.setPlanCasa(null));
        assertEquals("Plan časa ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetEvidencijaCasova() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        Osoba osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        Jezik jezik = new Jezik("Engleski", "EN");
        EvidencijaCasova evidencija = new EvidencijaCasova(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1), nastavnik, osoba, jezik);
        stavka.setEvidencijaCasova(evidencija);
        assertEquals(evidencija, stavka.getEvidencijaCasova());
    }

    @Test
    void testSetEvidencijaCasovaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> stavka.setEvidencijaCasova(null));
        assertEquals("Evidencija časova ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1}/{2}/{3} -> {4}")
    @CsvSource({
            "1, 2026-06-05, 5, Konverzacija, true",
            "2, 2026-06-05, 5, Konverzacija, false",
            "1, 2026-06-12, 5, Konverzacija, false",
            "1, 2026-06-05, 4, Konverzacija, false",
            "1, 2026-06-05, 5, Gramatika, false"
    })
    void testEquals(int rbStavke, String datum, int ocena, String planNaziv, boolean ocekivano) {
        PlanCasa plan = new PlanCasa(planNaziv, "opis1");
        StavkaEvidencijeCasova drugi = new StavkaEvidencijeCasova(rbStavke, LocalDate.parse(datum), ocena, plan);
        assertEquals(ocekivano, stavka.equals(drugi));
    }

    @Test
    void testEqualsIstaEvidencija() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        Osoba osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        Jezik jezik = new Jezik("Engleski", "EN");
        EvidencijaCasova evidencija = new EvidencijaCasova(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1), nastavnik, osoba, jezik);

        stavka.setEvidencijaCasova(evidencija);
        StavkaEvidencijeCasova drugi = new StavkaEvidencijeCasova(1, LocalDate.of(2026, 6, 5), 5, planCasa);
        drugi.setEvidencijaCasova(evidencija);

        assertTrue(stavka.equals(drugi));
    }

    @Test
    void testEqualsRazlicitaEvidencija() {
        Nastavnik nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        Osoba osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
        Jezik jezik = new Jezik("Engleski", "EN");
        EvidencijaCasova evidencija1 = new EvidencijaCasova(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1), nastavnik, osoba, jezik);
        EvidencijaCasova evidencija2 = new EvidencijaCasova(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1), nastavnik, osoba, jezik);

        stavka.setEvidencijaCasova(evidencija1);
        StavkaEvidencijeCasova drugi = new StavkaEvidencijeCasova(1, LocalDate.of(2026, 6, 5), 5, planCasa);
        drugi.setEvidencijaCasova(evidencija2);

        assertFalse(stavka.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(stavka.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(stavka.equals(stavka));
    }

    @Test
    void testToString() {
        String rezultat = stavka.toString();
        assertTrue(rezultat.contains("1"));
        assertTrue(rezultat.contains("5"));
    }
}
