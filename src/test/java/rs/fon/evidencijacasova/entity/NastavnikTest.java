package rs.fon.evidencijacasova.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NastavnikTest {

    private Nastavnik nastavnik;

    @BeforeEach
    void setUp() {
        nastavnik = new Nastavnik("marko", "lozinka1", "Marko", "Markovic");
    }

    @AfterEach
    void tearDown() {
        nastavnik = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("marko", nastavnik.getUsername());
        assertEquals("lozinka1", nastavnik.getPassword());
        assertEquals("Marko", nastavnik.getIme());
        assertEquals("Markovic", nastavnik.getPrezime());
        assertNotNull(nastavnik.getNastavnickiSertifikati());
        assertTrue(nastavnik.getNastavnickiSertifikati().isEmpty());
    }

    @Test
    void testSetIdNastavnik() {
        nastavnik.setIdNastavnik(5L);
        assertEquals(5L, nastavnik.getIdNastavnik());
    }

    @Test
    void testSetUsername() {
        nastavnik.setUsername("jelena");
        assertEquals("jelena", nastavnik.getUsername());
    }

    @Test
    void testSetUsernameNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnik.setUsername(null));
        assertEquals("Korisničko ime ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "prazan username: [{0}]")
    @CsvSource({"''", "' '", "'   '"})
    void testSetUsernamePrazan(String username) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nastavnik.setUsername(username));
        assertEquals("Korisničko ime ne sme biti prazno", ex.getMessage());
    }

    @Test
    void testSetPassword() {
        nastavnik.setPassword("novaLozinka");
        assertEquals("novaLozinka", nastavnik.getPassword());
    }

    @Test
    void testSetPasswordNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnik.setPassword(null));
        assertEquals("Lozinka ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "prazna lozinka: [{0}]")
    @CsvSource({"''", "' '", "'   '"})
    void testSetPasswordPrazna(String password) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nastavnik.setPassword(password));
        assertEquals("Lozinka ne sme biti prazna", ex.getMessage());
    }

    @Test
    void testSetIme() {
        nastavnik.setIme("Jelena");
        assertEquals("Jelena", nastavnik.getIme());
    }

    @Test
    void testSetImeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnik.setIme(null));
        assertEquals("Ime ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "prazno ime: [{0}]")
    @CsvSource({"''", "' '", "'   '"})
    void testSetImePrazno(String ime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nastavnik.setIme(ime));
        assertEquals("Ime ne sme biti prazno", ex.getMessage());
    }

    @Test
    void testSetPrezime() {
        nastavnik.setPrezime("Jelic");
        assertEquals("Jelic", nastavnik.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnik.setPrezime(null));
        assertEquals("Prezime ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "prazno prezime: [{0}]")
    @CsvSource({"''", "' '", "'   '"})
    void testSetPrezimePrazno(String prezime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> nastavnik.setPrezime(prezime));
        assertEquals("Prezime ne sme biti prazno", ex.getMessage());
    }

    @Test
    void testSetNastavnickiSertifikati() {
        List<NastavnickiSertifikat> lista = new ArrayList<>();
        nastavnik.setNastavnickiSertifikati(lista);
        assertEquals(lista, nastavnik.getNastavnickiSertifikati());
    }

    @Test
    void testSetNastavnickiSertifikatiNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> nastavnik.setNastavnickiSertifikati(null));
        assertEquals("Lista sertifikata ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "{0}/{1}/{2}/{3} -> {4}")
    @CsvSource({
            "marko, lozinka1, Marko, Markovic, true",
            "petar, lozinka1, Marko, Markovic, false",
            "marko, lozinka2, Marko, Markovic, false",
            "marko, lozinka1, Petar, Markovic, false",
            "marko, lozinka1, Marko, Petrovic, false",
            "petar, lozinka2, Petar, Petrovic, false"
    })
    void testEquals(String username, String password, String ime, String prezime, boolean ocekivano) {
        Nastavnik drugi = new Nastavnik(username, password, ime, prezime);
        assertEquals(ocekivano, nastavnik.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(nastavnik.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(nastavnik.equals(nastavnik));
    }

    @Test
    void testToString() {
        String rezultat = nastavnik.toString();
        assertTrue(rezultat.contains("marko"));
        assertTrue(rezultat.contains("lozinka1"));
        assertTrue(rezultat.contains("Marko"));
        assertTrue(rezultat.contains("Markovic"));
    }
}
