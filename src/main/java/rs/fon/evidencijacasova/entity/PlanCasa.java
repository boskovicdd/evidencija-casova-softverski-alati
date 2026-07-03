package rs.fon.evidencijacasova.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan_casa")
public class PlanCasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanCasa;

    private String naziv;

    private String opis;

    public PlanCasa() {
    }

    public PlanCasa(String naziv, String opis) {
        setNaziv(naziv);
        setOpis(opis);
    }

    public Long getIdPlanCasa() {
        return idPlanCasa;
    }

    public void setIdPlanCasa(Long idPlanCasa) {
        this.idPlanCasa = idPlanCasa;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
