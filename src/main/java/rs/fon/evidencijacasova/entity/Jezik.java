package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jezik")
public class Jezik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJezik;

    private String naziv;

    private String oznaka;

    public Jezik() {
    }

    public Jezik(String naziv, String oznaka) {
        setNaziv(naziv);
        setOznaka(oznaka);
    }

    public Long getIdJezik() {
        return idJezik;
    }

    public void setIdJezik(Long idJezik) {
        this.idJezik = idJezik;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) {
            throw new NullPointerException("Naziv ne sme biti null");
        }
        if (naziv.isBlank()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan");
        }
        this.naziv = naziv;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        if (oznaka == null) {
            throw new NullPointerException("Oznaka ne sme biti null");
        }
        if (oznaka.isBlank()) {
            throw new IllegalArgumentException("Oznaka ne sme biti prazna");
        }
        this.oznaka = oznaka;
    }

    @Override
    public String toString() {
        return "Jezik{" +
                "idJezik=" + idJezik +
                ", naziv='" + naziv + '\'' +
                ", oznaka='" + oznaka + '\'' +
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
        Jezik jezik = (Jezik) o;
        return Objects.equals(idJezik, jezik.idJezik)
                && Objects.equals(naziv, jezik.naziv)
                && Objects.equals(oznaka, jezik.oznaka);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJezik, naziv, oznaka);
    }
}
