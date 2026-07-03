package rs.fon.evidencijacasova.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sertifikat")
public class Sertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikat;

    private String institucija;

    public Sertifikat() {
    }

    public Sertifikat(String institucija) {
        setInstitucija(institucija);
    }

    public Long getIdSertifikat() {
        return idSertifikat;
    }

    public void setIdSertifikat(Long idSertifikat) {
        this.idSertifikat = idSertifikat;
    }

    public String getInstitucija() {
        return institucija;
    }

    public void setInstitucija(String institucija) {
        if (institucija == null) {
            throw new NullPointerException("Institucija ne sme biti null");
        }
        if (institucija.isBlank()) {
            throw new IllegalArgumentException("Institucija ne sme biti prazna");
        }
        this.institucija = institucija;
    }
}
