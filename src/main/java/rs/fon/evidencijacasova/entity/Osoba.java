package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Osoba koja pohadja kurs stranog jezika (polaznik). Ima tacno jedan nivo znanja
 * i moze imati vise evidencija casova (po jednu za svaki jezik koji uci).
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "osoba")
public class Osoba {

    /** Identifikator osobe u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOsoba;

    /** Ime osobe. */
    private String ime;

    /** Prezime osobe. */
    private String prezime;

    /** Broj telefona, opciono polje. */
    private String brTel;

    /** Nivo znanja jezika koji osoba trenutno ima. */
    @ManyToOne
    @JoinColumn(name = "id_nivo_znanja")
    private NivoZnanja nivoZnanja;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public Osoba() {
    }

    /**
     * Pravi novu osobu.
     *
     * @param ime        ime osobe, ne sme biti null ni prazno
     * @param prezime    prezime osobe, ne sme biti null ni prazno
     * @param brTel      broj telefona, moze biti null
     * @param nivoZnanja nivo znanja osobe, ne sme biti null
     * @throws java.lang.NullPointerException     ako je ime, prezime ili nivoZnanja null
     * @throws java.lang.IllegalArgumentException ako je ime ili prezime prazan string
     */
    public Osoba(String ime, String prezime, String brTel, NivoZnanja nivoZnanja) {
        setIme(ime);
        setPrezime(prezime);
        setBrTel(brTel);
        setNivoZnanja(nivoZnanja);
    }

    /**
     * @return id osobe, ili null ako jos nije sacuvana u bazi
     */
    public Long getIdOsoba() {
        return idOsoba;
    }

    /**
     * @param idOsoba novi identifikator osobe
     */
    public void setIdOsoba(Long idOsoba) {
        this.idOsoba = idOsoba;
    }

    /**
     * @return ime osobe
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime osobe.
     *
     * @param ime ime, ne sme biti null ni prazno
     * @throws java.lang.NullPointerException     ako je ime null
     * @throws java.lang.IllegalArgumentException ako je ime prazan string
     */
    public void setIme(String ime) {
        if (ime == null) {
            throw new NullPointerException("Ime ne sme biti null");
        }
        if (ime.isBlank()) {
            throw new IllegalArgumentException("Ime ne sme biti prazno");
        }
        this.ime = ime;
    }

    /**
     * @return prezime osobe
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime osobe.
     *
     * @param prezime prezime, ne sme biti null ni prazno
     * @throws java.lang.NullPointerException     ako je prezime null
     * @throws java.lang.IllegalArgumentException ako je prezime prazan string
     */
    public void setPrezime(String prezime) {
        if (prezime == null) {
            throw new NullPointerException("Prezime ne sme biti null");
        }
        if (prezime.isBlank()) {
            throw new IllegalArgumentException("Prezime ne sme biti prazno");
        }
        this.prezime = prezime;
    }

    /**
     * @return broj telefona, moze biti null
     */
    public String getBrTel() {
        return brTel;
    }

    /**
     * Postavlja broj telefona. Ovo je opciono polje, pa nema validacije.
     *
     * @param brTel novi broj telefona, moze biti null
     */
    public void setBrTel(String brTel) {
        this.brTel = brTel;
    }

    /**
     * @return nivo znanja osobe
     */
    public NivoZnanja getNivoZnanja() {
        return nivoZnanja;
    }

    /**
     * Postavlja nivo znanja osobe.
     *
     * @param nivoZnanja novi nivo znanja, ne sme biti null
     * @throws java.lang.NullPointerException ako je nivoZnanja null
     */
    public void setNivoZnanja(NivoZnanja nivoZnanja) {
        if (nivoZnanja == null) {
            throw new NullPointerException("Nivo znanja ne sme biti null");
        }
        this.nivoZnanja = nivoZnanja;
    }

    /**
     * Vraca tekstualnu reprezentaciju osobe.
     */
    @Override
    public String toString() {
        return "Osoba{" +
                "idOsoba=" + idOsoba +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", brTel='" + brTel + '\'' +
                ", nivoZnanja=" + nivoZnanja +
                '}';
    }

    /**
     * Dve osobe su jednake ako imaju iste vrednosti svih polja (id, ime, prezime,
     * brTel, nivoZnanja).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca Osoba sa istim vrednostima polja
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
        Osoba osoba = (Osoba) o;
        return Objects.equals(idOsoba, osoba.idOsoba)
                && Objects.equals(ime, osoba.ime)
                && Objects.equals(prezime, osoba.prezime)
                && Objects.equals(brTel, osoba.brTel)
                && Objects.equals(nivoZnanja, osoba.nivoZnanja);
    }

    /**
     * Racuna hash kod na osnovu id-ja, imena, prezimena, broja telefona i nivoa znanja.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOsoba, ime, prezime, brTel, nivoZnanja);
    }
}
