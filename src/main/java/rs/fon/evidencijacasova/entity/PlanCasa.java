package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Plan casa opisuje sta se radi na jednom casu (npr. "Konverzacija", "Gramatika").
 * Svaka stavka evidencije casova je vezana za tacno jedan plan casa.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "plan_casa")
public class PlanCasa {

    /** Identifikator plana casa u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanCasa;

    /** Naziv plana casa. */
    private String naziv;

    /** Opis plana casa, opciono polje. */
    private String opis;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public PlanCasa() {
    }

    /**
     * Pravi novi plan casa.
     *
     * @param naziv naziv plana casa, ne sme biti null ni prazan
     * @param opis  opis plana casa, moze biti null
     * @throws java.lang.NullPointerException     ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string
     */
    public PlanCasa(String naziv, String opis) {
        setNaziv(naziv);
        setOpis(opis);
    }

    /**
     * @return id plana casa, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdPlanCasa() {
        return idPlanCasa;
    }

    /**
     * @param idPlanCasa novi identifikator plana casa
     */
    public void setIdPlanCasa(Long idPlanCasa) {
        this.idPlanCasa = idPlanCasa;
    }

    /**
     * @return naziv plana casa
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv plana casa.
     *
     * @param naziv naziv, ne sme biti null ni prazan
     * @throws java.lang.NullPointerException     ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string
     */
    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv ne sme biti null");
        }
        if (naziv.isBlank()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan");
        }
        this.naziv = naziv;
    }

    /**
     * @return opis plana casa, moze biti null
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Postavlja opis plana casa. Opis je opciono polje, pa nema validacije.
     *
     * @param opis novi opis, moze biti null
     */
    public void setOpis(String opis) {
        this.opis = opis;
    }

    /**
     * Vraca tekstualnu reprezentaciju plana casa.
     */
    @Override
    public String toString() {
        return "PlanCasa{" +
                "idPlanCasa=" + idPlanCasa +
                ", naziv='" + naziv + '\'' +
                ", opis='" + opis + '\'' +
                '}';
    }

    /**
     * Dva plana casa su jednaka ako imaju iste vrednosti svih polja (id, naziv, opis).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca PlanCasa sa istim vrednostima polja
     *         false ako je o null, druge klase, ili se neko od polja razlikuje
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlanCasa planCasa = (PlanCasa) o;
        return Objects.equals(idPlanCasa, planCasa.idPlanCasa)
                && Objects.equals(naziv, planCasa.naziv)
                && Objects.equals(opis, planCasa.opis);
    }

    /**
     * Racuna hash kod na osnovu id-ja, naziva i opisa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPlanCasa, naziv, opis);
    }
}
