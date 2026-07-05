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

class NastavnickiSertifikatTest {

    private NastavnickiSertifikat nastavnickiSertifikat;
    private Nastavnik nastavnik;
    private Sertifikat sertifikat;

    @BeforeEach
    void setUp() {
        nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
        sertifikat = new Sertifikat("CAE");
        nastavnickiSertifikat = new NastavnickiSertifikat(LocalDate.of(2020, 6, 15), nastavnik, sertifikat);
    }

    @AfterEach
    void tearDown() {
        nastavnickiSertifikat = null;
        nastavnik = null;
        sertifikat = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals(LocalDate.of(2020, 6, 15), nastavnickiSertifikat.getDatumIzdavanja());
        assertEquals(nastavnik, nastavnickiSertifikat.getNastavnik());
        assertEquals(sertifikat, nastavnickiSertifikat.getSertifikat());
    }

    @Test
    void testSetIdNastavnickiSertifikat() {
        nastavnickiSertifikat.setIdNastavnickiSertifikat(9L);
        assertEquals(9L, nastavnickiSertifikat.getIdNastavnickiSertifikat());
    }

    @Test
    void testSetDatumIzdavanja() {
        nastavnickiSertifikat.setDatumIzdavanja(LocalDate.of(2019, 9, 10));
        assertEquals(LocalDate.of(2019, 9, 10), nastavnickiSertifikat.getDatumIzdavanja());
    }

    @Test
    void testSetDatumIzdavanjaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnickiSertifikat.setDatumIzdavanja(null));
        assertEquals("Datum izdavanja ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetDatumIzdavanjaBuducnost() {
        LocalDate sutra = LocalDate.now().plusDays(1);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> nastavnickiSertifikat.setDatumIzdavanja(sutra));
        assertEquals("Datum izdavanja ne sme biti u budućnosti", ex.getMessage());
    }

    @Test
    void testSetNastavnik() {
        Nastavnik drugi = new Nastavnik("jelena", "lozinka2", "Jelena", "Jelic");
        nastavnickiSertifikat.setNastavnik(drugi);
        assertEquals(drugi, nastavnickiSertifikat.getNastavnik());
    }

    @Test
    void testSetNastavnikNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnickiSertifikat.setNastavnik(null));
        assertEquals("Nastavnik ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetSertifikat() {
        Sertifikat drugi = new Sertifikat("FCE");
        nastavnickiSertifikat.setSertifikat(drugi);
        assertEquals(drugi, nastavnickiSertifikat.getSertifikat());
    }

    @Test
    void testSetSertifikatNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnickiSertifikat.setSertifikat(null));
        assertEquals("Sertifikat ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1}/{2} -> {3}")
    @CsvSource({
            "2020-06-15, marko, CAE, true",
            "2019-09-10, marko, CAE, false",
            "2020-06-15, jelena, CAE, false",
            "2020-06-15, marko, FCE, false"
    })
    void testEquals(String datum, String username, String institucija, boolean ocekivano) {
        Nastavnik drugiNastavnik = new Nastavnik(username, "lozinka1", "Marko", "Markovic");
        Sertifikat drugiSertifikat = new Sertifikat(institucija);
        NastavnickiSertifikat drugi = new NastavnickiSertifikat(LocalDate.parse(datum), drugiNastavnik, drugiSertifikat);
        assertEquals(ocekivano, nastavnickiSertifikat.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(nastavnickiSertifikat.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(nastavnickiSertifikat.equals(nastavnickiSertifikat));
    }

    @Test
    void testToString() {
        String rezultat = nastavnickiSertifikat.toString();
        assertTrue(rezultat.contains("marko"));
        assertTrue(rezultat.contains("CAE"));
    }
}
