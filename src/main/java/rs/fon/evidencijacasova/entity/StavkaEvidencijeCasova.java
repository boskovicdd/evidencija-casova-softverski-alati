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
 * Jedna stavka evidencije casova - jedan odrzan cas, sa ocenom i planom casa koji
 * je odradjen. Uvek pripada tacno jednoj evidenciji casova.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "stavka_evidencije_casova")
public class StavkaEvidencijeCasova {

    /** Identifikator stavke u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavka;

    /** Redni broj stavke unutar evidencije, mora biti pozitivan. */
    private int rbStavke;

    /** Datum odrzavanja casa. */
    private LocalDate datum;

    /** Ocena casa, u opsegu od 1 do 5. */
    private int ocena;

    /** Evidencija casova kojoj stavka pripada. */
    @ManyToOne
    @JoinColumn(name = "id_evidencija")
    @JsonBackReference
    private EvidencijaCasova evidencijaCasova;

    /** Plan casa koji je odradjen na ovom casu. */
    @ManyToOne
    @JoinColumn(name = "id_plan_casa")
    private PlanCasa planCasa;

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public StavkaEvidencijeCasova() {
    }

    /**
     * Pravi novu stavku evidencije. Veza ka evidenciji se ne postavlja ovde, nego
     * kroz metodu dodajStavku u EvidencijaCasova.
     *
     * @param rbStavke redni broj stavke, mora biti pozitivan
     * @param datum    datum odrzavanja casa, ne sme biti null
     * @param ocena    ocena casa, mora biti u opsegu od 1 do 5
     * @param planCasa plan casa koji je odradjen, ne sme biti null
     * @throws java.lang.NullPointerException     ako je datum ili planCasa null
     * @throws java.lang.IllegalArgumentException ako rbStavke nije pozitivan ili ocena nije u opsegu od 1 do 5
     */
    public StavkaEvidencijeCasova(int rbStavke, LocalDate datum, int ocena, PlanCasa planCasa) {
        setRbStavke(rbStavke);
        setDatum(datum);
        setOcena(ocena);
        setPlanCasa(planCasa);
    }

    /**
     * @return id stavke, ili null ako jos nije sacuvana u bazi
     */
    public Long getIdStavka() {
        return idStavka;
    }

    /**
     * @param idStavka novi identifikator stavke
     */
    public void setIdStavka(Long idStavka) {
        this.idStavka = idStavka;
    }

    /**
     * @return redni broj stavke
     */
    public int getRbStavke() {
        return rbStavke;
    }

    /**
     * Postavlja redni broj stavke.
     *
     * @param rbStavke redni broj, mora biti pozitivan
     * @throws java.lang.IllegalArgumentException ako rbStavke nije pozitivan
     */
    public void setRbStavke(int rbStavke) {
        if (rbStavke <= 0) {
            throw new IllegalArgumentException("Redni broj stavke mora biti pozitivan");
        }
        this.rbStavke = rbStavke;
    }

    /**
     * @return datum odrzavanja casa
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Postavlja datum odrzavanja casa.
     *
     * @param datum datum, ne sme biti null
     * @throws java.lang.NullPointerException ako je datum null
     */
    public void setDatum(LocalDate datum) {
        if (datum == null) {
            throw new NullPointerException("Datum ne sme biti null");
        }
        this.datum = datum;
    }

    /**
     * @return ocena casa
     */
    public int getOcena() {
        return ocena;
    }

    /**
     * Postavlja ocenu casa.
     *
     * @param ocena ocena, mora biti u opsegu od 1 do 5
     * @throws java.lang.IllegalArgumentException ako ocena nije u opsegu od 1 do 5
     */
    public void setOcena(int ocena) {
        if (ocena < 1 || ocena > 5) {
            throw new IllegalArgumentException("Ocena mora biti u opsegu od 1 do 5");
        }
        this.ocena = ocena;
    }

    /**
     * @return evidencija casova kojoj stavka pripada
     */
    public EvidencijaCasova getEvidencijaCasova() {
        return evidencijaCasova;
    }

    /**
     * Postavlja evidenciju casova kojoj stavka pripada. Ovo poziva metoda
     * dodajStavku u EvidencijaCasova, ne poziva se rucno.
     *
     * @param evidencijaCasova evidencija, ne sme biti null
     * @throws java.lang.NullPointerException ako je evidencijaCasova null
     */
    public void setEvidencijaCasova(EvidencijaCasova evidencijaCasova) {
        if (evidencijaCasova == null) {
            throw new NullPointerException("Evidencija časova ne sme biti null");
        }
        this.evidencijaCasova = evidencijaCasova;
    }

    /**
     * @return plan casa koji je odradjen na ovom casu
     */
    public PlanCasa getPlanCasa() {
        return planCasa;
    }

    /**
     * Postavlja plan casa.
     *
     * @param planCasa plan casa, ne sme biti null
     * @throws java.lang.NullPointerException ako je planCasa null
     */
    public void setPlanCasa(PlanCasa planCasa) {
        if (planCasa == null) {
            throw new NullPointerException("Plan časa ne sme biti null");
        }
        this.planCasa = planCasa;
    }

    /**
     * Vraca tekstualnu reprezentaciju stavke.
     */
    @Override
    public String toString() {
        return "StavkaEvidencijeCasova{" +
                "idStavka=" + idStavka +
                ", rbStavke=" + rbStavke +
                ", datum=" + datum +
                ", ocena=" + ocena +
                ", planCasa=" + planCasa +
                ", evidencijaCasova=" + (evidencijaCasova != null ? evidencijaCasova.getIdEvidencija() : null) +
                '}';
    }

    /**
     * Dve stavke su jednake ako imaju iste vrednosti svih polja (id, rbStavke,
     * datum, ocena, planCasa, evidencijaCasova). Evidencija se namerno poredi
     * (a ne izostavlja) jer rbStavke ima smisla samo u okviru jedne evidencije.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca StavkaEvidencijeCasova sa istim vrednostima polja
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
        StavkaEvidencijeCasova that = (StavkaEvidencijeCasova) o;
        return rbStavke == that.rbStavke
                && ocena == that.ocena
                && Objects.equals(idStavka, that.idStavka)
                && Objects.equals(datum, that.datum)
                && Objects.equals(planCasa, that.planCasa)
                && Objects.equals(evidencijaCasova, that.evidencijaCasova);
    }

    /**
     * Racuna hash kod na osnovu id-ja, rednog broja, datuma, ocene, plana casa i evidencije.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStavka, rbStavke, datum, ocena, planCasa, evidencijaCasova);
    }
}
