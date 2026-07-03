package rs.fon.evidencijacasova.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stavka_evidencije_casova")
public class StavkaEvidencijeCasova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavka;

    private int rbStavke;

    private LocalDate datum;

    private int ocena;

    @ManyToOne
    @JoinColumn(name = "id_evidencija")
    @JsonBackReference
    private EvidencijaCasova evidencijaCasova;

    @ManyToOne
    @JoinColumn(name = "id_plan_casa")
    private PlanCasa planCasa;

    public StavkaEvidencijeCasova() {
    }

    public StavkaEvidencijeCasova(int rbStavke, LocalDate datum, int ocena, PlanCasa planCasa) {
        setRbStavke(rbStavke);
        setDatum(datum);
        setOcena(ocena);
        setPlanCasa(planCasa);
    }

    public Long getIdStavka() {
        return idStavka;
    }

    public void setIdStavka(Long idStavka) {
        this.idStavka = idStavka;
    }

    public int getRbStavke() {
        return rbStavke;
    }

    public void setRbStavke(int rbStavke) {
        if (rbStavke <= 0) {
            throw new IllegalArgumentException("Redni broj stavke mora biti pozitivan");
        }
        this.rbStavke = rbStavke;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        if (datum == null) {
            throw new NullPointerException("Datum ne sme biti null");
        }
        this.datum = datum;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        if (ocena < 1 || ocena > 5) {
            throw new IllegalArgumentException("Ocena mora biti u opsegu od 1 do 5");
        }
        this.ocena = ocena;
    }

    public EvidencijaCasova getEvidencijaCasova() {
        return evidencijaCasova;
    }

    public void setEvidencijaCasova(EvidencijaCasova evidencijaCasova) {
        if (evidencijaCasova == null) {
            throw new NullPointerException("Evidencija časova ne sme biti null");
        }
        this.evidencijaCasova = evidencijaCasova;
    }

    public PlanCasa getPlanCasa() {
        return planCasa;
    }

    public void setPlanCasa(PlanCasa planCasa) {
        if (planCasa == null) {
            throw new NullPointerException("Plan časa ne sme biti null");
        }
        this.planCasa = planCasa;
    }
}
