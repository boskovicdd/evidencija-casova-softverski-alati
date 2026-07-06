package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Sertifikat koji izdaje neka institucija za znanje jezika (npr. CAE, FCE).
 * Nastavnik moze imati vise ovakvih sertifikata preko NastavnickiSertifikat.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "sertifikat")
public class Sertifikat {

    /** Identifikator sertifikata u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikat;

    /** Institucija koja izdaje sertifikat. */
    private String institucija;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public Sertifikat() {
    }

    /**
     * Pravi novi sertifikat.
     *
     * @param institucija institucija koja izdaje sertifikat, ne sme biti null ni prazna
     * @throws java.lang.NullPointerException     ako je institucija null
     * @throws java.lang.IllegalArgumentException ako je institucija prazan string
     */
    public Sertifikat(String institucija) {
        setInstitucija(institucija);
    }

    /**
     * @return id sertifikata, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdSertifikat() {
        return idSertifikat;
    }

    /**
     * @param idSertifikat novi identifikator sertifikata
     */
    public void setIdSertifikat(Long idSertifikat) {
        this.idSertifikat = idSertifikat;
    }

    /**
     * @return institucija koja izdaje sertifikat
     */
    public String getInstitucija() {
        return institucija;
    }

    /**
     * Postavlja instituciju.
     *
     * @param institucija institucija, ne sme biti null ni prazna
     * @throws java.lang.NullPointerException     ako je institucija null
     * @throws java.lang.IllegalArgumentException ako je institucija prazan string
     */
    public void setInstitucija(String institucija) {
        if (institucija == null) {
            throw new NullPointerException("Institucija ne sme biti null");
        }
        if (institucija.isBlank()) {
            throw new IllegalArgumentException("Institucija ne sme biti prazna");
        }
        this.institucija = institucija;
    }

    /**
     * Vraca tekstualnu reprezentaciju sertifikata.
     */
    @Override
    public String toString() {
        return "Sertifikat{" +
                "idSertifikat=" + idSertifikat +
                ", institucija='" + institucija + '\'' +
                '}';
    }

    /**
     * Dva sertifikata su jednaka ako imaju iste vrednosti svih polja (id, institucija).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca Sertifikat-a sa istim vrednostima polja
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
        Sertifikat sertifikat = (Sertifikat) o;
        return Objects.equals(idSertifikat, sertifikat.idSertifikat)
                && Objects.equals(institucija, sertifikat.institucija);
    }

    /**
     * Racuna hash kod na osnovu id-ja i institucije.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idSertifikat, institucija);
    }
}
