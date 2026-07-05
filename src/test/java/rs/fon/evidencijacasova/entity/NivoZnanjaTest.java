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

class NivoZnanjaTest {

    private NivoZnanja nivoZnanja;

    @BeforeEach
    void setUp() {
        nivoZnanja = new NivoZnanja("A1", 1);
    }

    @AfterEach
    void tearDown() {
        nivoZnanja = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("A1", nivoZnanja.getNaziv());
        assertEquals(1, nivoZnanja.getSifraNivo());
    }

    @Test
    void testSetIdNivoZnanja() {
        nivoZnanja.setIdNivoZnanja(2L);
        assertEquals(2L, nivoZnanja.getIdNivoZnanja());
    }

    @Test
    void testSetNaziv() {
        nivoZnanja.setNaziv("B2");
        assertEquals("B2", nivoZnanja.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nivoZnanja.setNaziv(null));
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetNazivPrazan() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nivoZnanja.setNaziv(""));
        assertEquals("Naziv ne sme biti prazan", ex.getMessage());
    }

    @Test
    void testSetSifraNivo() {
        nivoZnanja.setSifraNivo(6);
        assertEquals(6, nivoZnanja.getSifraNivo());
    }

    @ParameterizedTest(name = "nevalidna sifra nivoa: [{0}]")
    @CsvSource({"0", "-1"})
    void testSetSifraNivoNevalidna(int sifraNivo) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nivoZnanja.setSifraNivo(sifraNivo));
        assertEquals("Šifra nivoa mora biti pozitivan broj", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1} -> {2}")
    @CsvSource({
            "A1, 1, true",
            "B2, 1, false",
            "A1, 4, false",
            "B2, 4, false"
    })
    void testEquals(String naziv, int sifraNivo, boolean ocekivano) {
        NivoZnanja drugi = new NivoZnanja(naziv, sifraNivo);
        assertEquals(ocekivano, nivoZnanja.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(nivoZnanja.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(nivoZnanja.equals(nivoZnanja));
    }

    @Test
    void testToString() {
        String rezultat = nivoZnanja.toString();
        assertTrue(rezultat.contains("A1"));
        assertTrue(rezultat.contains("1"));
    }
}
