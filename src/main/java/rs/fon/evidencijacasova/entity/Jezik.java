package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Strani jezik koji se predaje (npr. Engleski, Nemacki). Svaka evidencija casova
 * se vodi za tacno jedan jezik.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "jezik")
public class Jezik {

    /** Identifikator jezika u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJezik;

    /** Pun naziv jezika, npr. "Engleski". */
    private String naziv;

    /** Kratka oznaka jezika, npr. "EN". */
    private String oznaka;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public Jezik() {
    }

    /**
     * Pravi novi jezik.
     *
     * @param naziv  naziv jezika, ne sme biti null ni prazan
     * @param oznaka oznaka jezika, ne sme biti null ni prazna
     * @throws java.lang.NullPointerException     ako je naziv ili oznaka null
     * @throws java.lang.IllegalArgumentException ako je naziv ili oznaka prazan string
     */
    public Jezik(String naziv, String oznaka) {
        setNaziv(naziv);
        setOznaka(oznaka);
    }

    /**
     * @return id jezika, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdJezik() {
        return idJezik;
    }

    /**
     * @param idJezik novi identifikator jezika
     */
    public void setIdJezik(Long idJezik) {
        this.idJezik = idJezik;
    }

    /**
     * @return naziv jezika
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv jezika.
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
     * @return oznaka jezika
     */
    public String getOznaka() {
        return oznaka;
    }

    /**
     * Postavlja oznaku jezika.
     *
     * @param oznaka oznaka, ne sme biti null ni prazna
     * @throws java.lang.NullPointerException     ako je oznaka null
     * @throws java.lang.IllegalArgumentException ako je oznaka prazan string
     */
    public void setOznaka(String oznaka) {
        if (oznaka == null) {
            throw new NullPointerException("Oznaka ne sme biti null");
        }
        if (oznaka.isBlank()) {
            throw new IllegalArgumentException("Oznaka ne sme biti prazna");
        }
        this.oznaka = oznaka;
    }

    /**
     * Vraca tekstualnu reprezentaciju jezika.
     */
    @Override
    public String toString() {
        return "Jezik{" +
                "idJezik=" + idJezik +
                ", naziv='" + naziv + '\'' +
                ", oznaka='" + oznaka + '\'' +
                '}';
    }

    /**
     * Dva jezika su jednaka ako imaju iste vrednosti svih polja (id, naziv, oznaka).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca Jezik-a sa istim vrednostima polja
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
        Jezik jezik = (Jezik) o;
        return Objects.equals(idJezik, jezik.idJezik)
                && Objects.equals(naziv, jezik.naziv)
                && Objects.equals(oznaka, jezik.oznaka);
    }

    /**
     * Racuna hash kod na osnovu id-ja, naziva i oznake.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idJezik, naziv, oznaka);
    }
}
