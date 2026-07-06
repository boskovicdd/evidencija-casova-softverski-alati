package rs.fon.evidencijacasova.entity;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Asocijativna klasa koja povezuje nastavnika sa sertifikatom koji poseduje, i
 * cuva datum kad je taj sertifikat izdat.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "nastavnicki_sertifikat")
public class NastavnickiSertifikat {

    /** Identifikator zapisa u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNastavnickiSertifikat;

    /** Datum kad je sertifikat izdat, ne sme biti u buducnosti. */
    private LocalDate datumIzdavanja;

    /** Nastavnik koji poseduje sertifikat. */
    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    @JsonBackReference
    private Nastavnik nastavnik;

    /** Sertifikat koji nastavnik poseduje. */
    @ManyToOne
    @JoinColumn(name = "id_sertifikat")
    private Sertifikat sertifikat;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public NastavnickiSertifikat() {
    }

    /**
     * Pravi novi zapis o sertifikatu nastavnika.
     *
     * @param datumIzdavanja datum izdavanja sertifikata, ne sme biti null ni u buducnosti
     * @param nastavnik      nastavnik koji poseduje sertifikat, ne sme biti null
     * @param sertifikat     sertifikat koji nastavnik poseduje, ne sme biti null
     * @throws java.lang.NullPointerException     ako je neki od parametara null
     * @throws java.lang.IllegalArgumentException ako je datumIzdavanja u buducnosti
     */
    public NastavnickiSertifikat(LocalDate datumIzdavanja, Nastavnik nastavnik, Sertifikat sertifikat) {
        setDatumIzdavanja(datumIzdavanja);
        setNastavnik(nastavnik);
        setSertifikat(sertifikat);
    }

    /**
     * @return id zapisa, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdNastavnickiSertifikat() {
        return idNastavnickiSertifikat;
    }

    /**
     * @param idNastavnickiSertifikat novi identifikator zapisa
     */
    public void setIdNastavnickiSertifikat(Long idNastavnickiSertifikat) {
        this.idNastavnickiSertifikat = idNastavnickiSertifikat;
    }

    /**
     * @return datum izdavanja sertifikata
     */
    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Postavlja datum izdavanja sertifikata.
     *
     * @param datumIzdavanja datum, ne sme biti null ni u buducnosti
     * @throws java.lang.NullPointerException     ako je datumIzdavanja null
     * @throws java.lang.IllegalArgumentException ako je datumIzdavanja u buducnosti
     */
    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        if (datumIzdavanja == null) {
            throw new NullPointerException("Datum izdavanja ne sme biti null");
        }
        if (datumIzdavanja.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Datum izdavanja ne sme biti u budućnosti");
        }
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * @return nastavnik koji poseduje sertifikat
     */
    public Nastavnik getNastavnik() {
        return nastavnik;
    }

    /**
     * Postavlja nastavnika.
     *
     * @param nastavnik nastavnik, ne sme biti null
     * @throws java.lang.NullPointerException ako je nastavnik null
     */
    public void setNastavnik(Nastavnik nastavnik) {
        if (nastavnik == null) {
            throw new NullPointerException("Nastavnik ne sme biti null");
        }
        this.nastavnik = nastavnik;
    }

    /**
     * @return sertifikat koji nastavnik poseduje
     */
    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    /**
     * Postavlja sertifikat.
     *
     * @param sertifikat sertifikat, ne sme biti null
     * @throws java.lang.NullPointerException ako je sertifikat null
     */
    public void setSertifikat(Sertifikat sertifikat) {
        if (sertifikat == null) {
            throw new NullPointerException("Sertifikat ne sme biti null");
        }
        this.sertifikat = sertifikat;
    }

    /**
     * Vraca tekstualnu reprezentaciju zapisa.
     */
    @Override
    public String toString() {
        return "NastavnickiSertifikat{" +
                "idNastavnickiSertifikat=" + idNastavnickiSertifikat +
                ", datumIzdavanja=" + datumIzdavanja +
                ", nastavnik=" + (nastavnik != null ? nastavnik.getUsername() : null) +
                ", sertifikat=" + (sertifikat != null ? sertifikat.getInstitucija() : null) +
                '}';
    }

    /**
     * Dva zapisa su jednaka ako imaju iste vrednosti svih polja (id, datumIzdavanja,
     * nastavnik, sertifikat).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca NastavnickiSertifikat sa istim vrednostima polja
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
        NastavnickiSertifikat that = (NastavnickiSertifikat) o;
        return Objects.equals(idNastavnickiSertifikat, that.idNastavnickiSertifikat)
                && Objects.equals(datumIzdavanja, that.datumIzdavanja)
                && Objects.equals(nastavnik, that.nastavnik)
                && Objects.equals(sertifikat, that.sertifikat);
    }

    /**
     * Racuna hash kod na osnovu id-ja, datuma izdavanja, nastavnika i sertifikata.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idNastavnickiSertifikat, datumIzdavanja, nastavnik, sertifikat);
    }
}
