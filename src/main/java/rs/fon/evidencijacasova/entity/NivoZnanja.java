package rs.fon.evidencijacasova.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nivo_znanja")
public class NivoZnanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivoZnanja;

    private String naziv;

    private int sifraNivo;

    public NivoZnanja() {
    }

    public NivoZnanja(String naziv, int sifraNivo) {
        setNaziv(naziv);
        setSifraNivo(sifraNivo);
    }

    public Long getIdNivoZnanja() {
        return idNivoZnanja;
    }

    public void setIdNivoZnanja(Long idNivoZnanja) {
        this.idNivoZnanja = idNivoZnanja;
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

    public int getSifraNivo() {
        return sifraNivo;
    }

    public void setSifraNivo(int sifraNivo) {
        if (sifraNivo <= 0) {
            throw new IllegalArgumentException("Šifra nivoa mora biti pozitivan broj");
        }
        this.sifraNivo = sifraNivo;
    }

    @Override
    public String toString() {
        return "NivoZnanja{" +
                "idNivoZnanja=" + idNivoZnanja +
                ", naziv='" + naziv + '\'' +
                ", sifraNivo=" + sifraNivo +
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
        NivoZnanja nivoZnanja = (NivoZnanja) o;
        return sifraNivo == nivoZnanja.sifraNivo
                && Objects.equals(idNivoZnanja, nivoZnanja.idNivoZnanja)
                && Objects.equals(naziv, nivoZnanja.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNivoZnanja, naziv, sifraNivo);
    }
}
