package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "osoba")
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOsoba;

    private String ime;

    private String prezime;

    private String brTel;

    @ManyToOne
    @JoinColumn(name = "id_nivo_znanja")
    private NivoZnanja nivoZnanja;

    public Osoba() {
    }

    public Osoba(String ime, String prezime, String brTel, NivoZnanja nivoZnanja) {
        setIme(ime);
        setPrezime(prezime);
        setBrTel(brTel);
        setNivoZnanja(nivoZnanja);
    }

    public Long getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(Long idOsoba) {
        this.idOsoba = idOsoba;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        if (ime == null) {
            throw new NullPointerException("Ime ne sme biti null");
        }
        if (ime.isBlank()) {
            throw new IllegalArgumentException("Ime ne sme biti prazno");
        }
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        if (prezime == null) {
            throw new NullPointerException("Prezime ne sme biti null");
        }
        if (prezime.isBlank()) {
            throw new IllegalArgumentException("Prezime ne sme biti prazno");
        }
        this.prezime = prezime;
    }

    public String getBrTel() {
        return brTel;
    }

    public void setBrTel(String brTel) {
        this.brTel = brTel;
    }

    public NivoZnanja getNivoZnanja() {
        return nivoZnanja;
    }

    public void setNivoZnanja(NivoZnanja nivoZnanja) {
        if (nivoZnanja == null) {
            throw new NullPointerException("Nivo znanja ne sme biti null");
        }
        this.nivoZnanja = nivoZnanja;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(idOsoba, ime, prezime, brTel, nivoZnanja);
    }
}
