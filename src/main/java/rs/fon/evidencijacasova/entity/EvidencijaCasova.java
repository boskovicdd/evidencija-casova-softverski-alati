package rs.fon.evidencijacasova.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "evidencija_casova")
public class EvidencijaCasova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencija;

    private LocalDate datOd;

    private LocalDate datDo;

    private int brojCasova;

    private double prosecnaOcena;

    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    private Nastavnik nastavnik;

    @ManyToOne
    @JoinColumn(name = "id_osoba")
    private Osoba osoba;

    @ManyToOne
    @JoinColumn(name = "id_jezik")
    private Jezik jezik;

    @OneToMany(mappedBy = "evidencijaCasova", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StavkaEvidencijeCasova> stavke = new ArrayList<>();

    public EvidencijaCasova() {
    }

    public EvidencijaCasova(LocalDate datOd, LocalDate datDo, Nastavnik nastavnik, Osoba osoba, Jezik jezik) {
        setDatOd(datOd);
        setDatDo(datDo);
        setNastavnik(nastavnik);
        setOsoba(osoba);
        setJezik(jezik);
        this.brojCasova = 0;
        this.prosecnaOcena = 0;
    }

    public void dodajStavku(StavkaEvidencijeCasova stavka) {
        if (stavka == null) {
            throw new NullPointerException("Stavka ne sme biti null");
        }
        stavka.setEvidencijaCasova(this);
        stavke.add(stavka);
    }

    public void preracunajProsecnuOcenu() {
        if (stavke.isEmpty()) {
            prosecnaOcena = 0;
            brojCasova = 0;
            return;
        }
        prosecnaOcena = stavke.stream().mapToInt(StavkaEvidencijeCasova::getOcena).average().orElse(0);
        brojCasova = stavke.size();
    }

    public Long getIdEvidencija() {
        return idEvidencija;
    }

    public void setIdEvidencija(Long idEvidencija) {
        this.idEvidencija = idEvidencija;
    }

    public LocalDate getDatOd() {
        return datOd;
    }

    public void setDatOd(LocalDate datOd) {
        if (datOd == null) {
            throw new NullPointerException("Datum od ne sme biti null");
        }
        if (datDo != null && datDo.isBefore(datOd)) {
            throw new IllegalArgumentException("Datum do ne sme biti pre datuma od");
        }
        this.datOd = datOd;
    }

    public LocalDate getDatDo() {
        return datDo;
    }

    public void setDatDo(LocalDate datDo) {
        if (datDo == null) {
            throw new NullPointerException("Datum do ne sme biti null");
        }
        if (datOd != null && datDo.isBefore(datOd)) {
            throw new IllegalArgumentException("Datum do ne sme biti pre datuma od");
        }
        this.datDo = datDo;
    }

    public int getBrojCasova() {
        return brojCasova;
    }

    public void setBrojCasova(int brojCasova) {
        if (brojCasova < 0) {
            throw new IllegalArgumentException("Broj časova ne sme biti negativan");
        }
        this.brojCasova = brojCasova;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Nastavnik getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(Nastavnik nastavnik) {
        if (nastavnik == null) {
            throw new NullPointerException("Nastavnik ne sme biti null");
        }
        this.nastavnik = nastavnik;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        if (osoba == null) {
            throw new NullPointerException("Osoba ne sme biti null");
        }
        this.osoba = osoba;
    }

    public Jezik getJezik() {
        return jezik;
    }

    public void setJezik(Jezik jezik) {
        if (jezik == null) {
            throw new NullPointerException("Jezik ne sme biti null");
        }
        this.jezik = jezik;
    }

    public List<StavkaEvidencijeCasova> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaEvidencijeCasova> stavke) {
        if (stavke == null) {
            throw new NullPointerException("Lista stavki ne sme biti null");
        }
        this.stavke = stavke;
    }
}
