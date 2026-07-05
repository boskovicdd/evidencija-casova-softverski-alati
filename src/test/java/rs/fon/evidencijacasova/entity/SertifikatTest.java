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

class SertifikatTest {

    private Sertifikat sertifikat;

    @BeforeEach
    void setUp() {
        sertifikat = new Sertifikat("CAE");
    }

    @AfterEach
    void tearDown() {
        sertifikat = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("CAE", sertifikat.getInstitucija());
    }

    @Test
    void testSetIdSertifikat() {
        sertifikat.setIdSertifikat(3L);
        assertEquals(3L, sertifikat.getIdSertifikat());
    }

    @Test
    void testSetInstitucija() {
        sertifikat.setInstitucija("FCE");
        assertEquals("FCE", sertifikat.getInstitucija());
    }

    @Test
    void testSetInstitucijaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> sertifikat.setInstitucija(null));
        assertEquals("Institucija ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetInstitucijaPrazna() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> sertifikat.setInstitucija(""));
        assertEquals("Institucija ne sme biti prazna", ex.getMessage());
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "CAE, true",
            "FCE, false"
    })
    void testEquals(String institucija, boolean ocekivano) {
        Sertifikat drugi = new Sertifikat(institucija);
        assertEquals(ocekivano, sertifikat.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(sertifikat.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(sertifikat.equals(sertifikat));
    }

    @Test
    void testToString() {
        String rezultat = sertifikat.toString();
        assertTrue(rezultat.contains("CAE"));
    }
}
