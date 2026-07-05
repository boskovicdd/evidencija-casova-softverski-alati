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

class PlanCasaTest {

    private PlanCasa planCasa;

    @BeforeEach
    void setUp() {
        planCasa = new PlanCasa("Konverzacija", "Vezbanje govora");
    }

    @AfterEach
    void tearDown() {
        planCasa = null;
    }

    @Test
    void testKonstruktor() {
        assertEquals("Konverzacija", planCasa.getNaziv());
        assertEquals("Vezbanje govora", planCasa.getOpis());
    }

    @Test
    void testSetIdPlanCasa() {
        planCasa.setIdPlanCasa(4L);
        assertEquals(4L, planCasa.getIdPlanCasa());
    }

    @Test
    void testSetNaziv() {
        planCasa.setNaziv("Gramatika");
        assertEquals("Gramatika", planCasa.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> planCasa.setNaziv(null));
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    void testSetNazivPrazan() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> planCasa.setNaziv(""));
        assertEquals("Naziv ne sme biti prazan", ex.getMessage());
    }

    @Test
    void testSetOpis() {
        planCasa.setOpis("Nov opis");
        assertEquals("Nov opis", planCasa.getOpis());
    }

    @Test
    void testSetOpisNull() {
        planCasa.setOpis(null);
        assertNull(planCasa.getOpis());
    }

    @ParameterizedTest(name = "{0}/{1} -> {2}")
    @CsvSource({
            "Konverzacija, Vezbanje govora, true",
            "Gramatika, Vezbanje govora, false",
            "Konverzacija, Drugi opis, false",
            "Gramatika, Drugi opis, false"
    })
    void testEquals(String naziv, String opis, boolean ocekivano) {
        PlanCasa drugi = new PlanCasa(naziv, opis);
        assertEquals(ocekivano, planCasa.equals(drugi));
    }

    @Test
    void testEqualsNull() {
        assertFalse(planCasa.equals(null));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(planCasa.equals(planCasa));
    }

    @Test
    void testToString() {
        String rezultat = planCasa.toString();
        assertTrue(rezultat.contains("Konverzacija"));
        assertTrue(rezultat.contains("Vezbanje govora"));
    }
}
