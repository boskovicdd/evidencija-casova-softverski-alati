package rs.fon.evidencijacasova.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JezikTest {

    private Jezik jezik;

    @BeforeEach
    void setUp() {
        jezik = new Jezik("Engleski", "EN");
    }

    @AfterEach
    void tearDown() {
        jezik = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("Engleski", jezik.getNaziv());
        assertEquals("EN", jezik.getOznaka());
    }

    @Test
    void testSetIdJezik() {
        jezik.setIdJezik(2L);
        assertEquals(2L, jezik.getIdJezik());
    }

    @Test
    void testSetNaziv() {
        jezik.setNaziv("Nemacki");
        assertEquals("Nemacki", jezik.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> jezik.setNaziv(null));
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetNazivPrazan() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> jezik.setNaziv(""));
        assertEquals("Naziv ne sme biti prazan", ex.getMessage());
    }

    @Test
    void testSetOznaka() {
        jezik.setOznaka("DE");
        assertEquals("DE", jezik.getOznaka());
    }

    @Test
    void testSetOznakaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> jezik.setOznaka(null));
        assertEquals("Oznaka ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetOznakaPrazna() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> jezik.setOznaka(""));
        assertEquals("Oznaka ne sme biti prazna", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1} -> {2}")
    @CsvSource({
            "Engleski, EN, true",
            "Nemacki, EN, false",
            "Engleski, DE, false",
            "Nemacki, DE, false"
    })
    void testEquals(String naziv, String oznaka, boolean ocekivano) {
        Jezik drugi = new Jezik(naziv, oznaka);
        assertEquals(ocekivano, jezik.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(jezik.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(jezik.equals(jezik));
    }

    @Test
    void testToString() {
        String rezultat = jezik.toString();
        assertTrue(rezultat.contains("Engleski"));
        assertTrue(rezultat.contains("EN"));
    }
}
