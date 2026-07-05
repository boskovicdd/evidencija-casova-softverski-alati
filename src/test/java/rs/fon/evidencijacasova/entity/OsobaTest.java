package rs.fon.evidencijacasova.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OsobaTest {

    private Osoba osoba;

    @BeforeEach
    void setUp() {
        osoba = new Osoba("Ana", "Anic", "064-1111111", new NivoZnanja("A1", 1));
    }

    @AfterEach
    void tearDown() {
        osoba = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("Ana", osoba.getIme());
        assertEquals("Anic", osoba.getPrezime());
        assertEquals("064-1111111", osoba.getBrTel());
        assertEquals("A1", osoba.getNivoZnanja().getNaziv());
    }

    @Test
    void testSetIdOsoba() {
        osoba.setIdOsoba(7L);
        assertEquals(7L, osoba.getIdOsoba());
    }

    @Test
    void testSetIme() {
        osoba.setIme("Milica");
        assertEquals("Milica", osoba.getIme());
    }

    @Test
    void testSetImeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> osoba.setIme(null));
        assertEquals("Ime ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetImePrazno() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> osoba.setIme(""));
        assertEquals("Ime ne sme biti prazno", ex.getMessage());
    }

    @Test
    void testSetPrezime() {
        osoba.setPrezime("Milic");
        assertEquals("Milic", osoba.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> osoba.setPrezime(null));
        assertEquals("Prezime ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetPrezimePrazno() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> osoba.setPrezime(""));
        assertEquals("Prezime ne sme biti prazno", ex.getMessage());
    }

    @Test
    void testSetBrTel() {
        osoba.setBrTel("064-9999999");
        assertEquals("064-9999999", osoba.getBrTel());
    }

    @Test
    void testSetBrTelNull() {
        osoba.setBrTel(null);
        assertNull(osoba.getBrTel());
    }

    @Test
    void testSetNivoZnanja() {
        NivoZnanja b2 = new NivoZnanja("B2", 4);
        osoba.setNivoZnanja(b2);
        assertEquals(b2, osoba.getNivoZnanja());
    }

    @Test
    void testSetNivoZnanjaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> osoba.setNivoZnanja(null));
        assertEquals("Nivo znanja ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1}/{2}/{3}/{4} -> {5}")
    @CsvSource({
            "Ana, Anic, 064-1111111, A1, 1, true",
            "Petar, Anic, 064-1111111, A1, 1, false",
            "Ana, Petrovic, 064-1111111, A1, 1, false",
            "Ana, Anic, 064-2222222, A1, 1, false",
            "Ana, Anic, 064-1111111, B2, 4, false"
    })
    void testEquals(String ime, String prezime, String brTel, String nivoNaziv, int nivoSifra, boolean ocekivano) {
        Osoba drugi = new Osoba(ime, prezime, brTel, new NivoZnanja(nivoNaziv, nivoSifra));
        assertEquals(ocekivano, osoba.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(osoba.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(osoba.equals(osoba));
    }

    @Test
    void testToString() {
        String rezultat = osoba.toString();
        assertTrue(rezultat.contains("Ana"));
        assertTrue(rezultat.contains("Anic"));
        assertTrue(rezultat.contains("064-1111111"));
    }
}
