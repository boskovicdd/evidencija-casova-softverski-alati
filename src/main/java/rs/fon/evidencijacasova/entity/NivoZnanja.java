package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Nivo znanja jezika po CEFR skali (npr. A1, B2, C1). Svaka osoba ima tacno jedan
 * nivo znanja.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "nivo_znanja")
public class NivoZnanja {

    /** Identifikator nivoa znanja u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivoZnanja;

    /** Naziv nivoa, npr. "A1", "B2", "C1". */
    private String naziv;

    /** Numericka sifra nivoa, koristi se za sortiranje/poredjenje nivoa. */
    private int sifraNivo;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public NivoZnanja() {
    }

    /**
     * Pravi novi nivo znanja.
     *
     * @param naziv     naziv nivoa, ne sme biti null ni prazan
     * @param sifraNivo sifra nivoa, mora biti pozitivan broj
     * @throws java.lang.NullPointerException     ako je naziv null
     * @throws java.lang.IllegalArgumentException ako je naziv prazan string ili sifraNivo nije pozitivan
     */
    public NivoZnanja(String naziv, int sifraNivo) {
        setNaziv(naziv);
        setSifraNivo(sifraNivo);
    }

    /**
     * @return id nivoa znanja, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdNivoZnanja() {
        return idNivoZnanja;
    }

    /**
     * @param idNivoZnanja novi identifikator nivoa znanja
     */
    public void setIdNivoZnanja(Long idNivoZnanja) {
        this.idNivoZnanja = idNivoZnanja;
    }

    /**
     * @return naziv nivoa znanja
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv nivoa.
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
     * @return sifra nivoa
     */
    public int getSifraNivo() {
        return sifraNivo;
    }

    /**
     * Postavlja sifru nivoa.
     *
     * @param sifraNivo sifra, mora biti pozitivan broj
     * @throws java.lang.IllegalArgumentException ako sifraNivo nije pozitivan broj
     */
    public void setSifraNivo(int sifraNivo) {
        if (sifraNivo <= 0) {
            throw new IllegalArgumentException("Šifra nivoa mora biti pozitivan broj");
        }
        this.sifraNivo = sifraNivo;
    }

    /**
     * Vraca tekstualnu reprezentaciju nivoa znanja.
     */
    @Override
    public String toString() {
        return "NivoZnanja{" +
                "idNivoZnanja=" + idNivoZnanja +
                ", naziv='" + naziv + '\'' +
                ", sifraNivo=" + sifraNivo +
                '}';
    }

    /**
     * Dva nivoa znanja su jednaka ako imaju iste vrednosti svih polja (id, naziv, sifraNivo).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca NivoZnanja sa istim vrednostima polja
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
        NivoZnanja nivoZnanja = (NivoZnanja) o;
        return sifraNivo == nivoZnanja.sifraNivo
                && Objects.equals(idNivoZnanja, nivoZnanja.idNivoZnanja)
                && Objects.equals(naziv, nivoZnanja.naziv);
    }

    /**
     * Racuna hash kod na osnovu id-ja, naziva i sifre nivoa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idNivoZnanja, naziv, sifraNivo);
    }
}
