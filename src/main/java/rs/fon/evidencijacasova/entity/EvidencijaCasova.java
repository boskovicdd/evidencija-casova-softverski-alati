package rs.fon.evidencijacasova.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

/**
 * Evidencija casova koju nastavnik vodi za jednu osobu, za tacno jedan jezik, u
 * periodu od datOd do datDo. Sastoji se od stavki (pojedinacnih odrzanih casova),
 * a brojCasova i prosecnaOcena se racunaju na osnovu tih stavki preko metode
 * preracunajProsecnuOcenu.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "evidencija_casova")
public class EvidencijaCasova {

    /** Identifikator evidencije u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencija;

    /** Datum od kog vazi evidencija. */
    private LocalDate datOd;

    /** Datum do kog vazi evidencija, ne sme biti pre datOd. */
    private LocalDate datDo;

    /** Broj odrzanih casova, racuna se preko preracunajProsecnuOcenu. */
    private int brojCasova;

    /** Prosecna ocena svih stavki, racuna se preko preracunajProsecnuOcenu. */
    private double prosecnaOcena;

    /** Nastavnik koji vodi evidenciju. */
    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    private Nastavnik nastavnik;

    /** Osoba (polaznik) za koju se vodi evidencija. */
    @ManyToOne
    @JoinColumn(name = "id_osoba")
    private Osoba osoba;

    /** Jezik za koji se vodi evidencija. */
    @ManyToOne
    @JoinColumn(name = "id_jezik")
    private Jezik jezik;

    /** Stavke evidencije, po jedna za svaki odrzan cas. */
    @OneToMany(mappedBy = "evidencijaCasova", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StavkaEvidencijeCasova> stavke = new ArrayList<>();

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public EvidencijaCasova() {
    }

    /**
     * Pravi novu evidenciju casova. brojCasova i prosecnaOcena pocinju od 0, jer
     * evidencija na pocetku nema nijednu stavku.
     *
     * @param datOd     datum od kog vazi evidencija, ne sme biti null
     * @param datDo     datum do kog vazi evidencija, ne sme biti null ni pre datOd
     * @param nastavnik nastavnik koji vodi evidenciju, ne sme biti null
     * @param osoba     osoba za koju se vodi evidencija, ne sme biti null
     * @param jezik     jezik za koji se vodi evidencija, ne sme biti null
     * @throws java.lang.NullPointerException     ako je neki od parametara null
     * @throws java.lang.IllegalArgumentException ako je datDo pre datOd
     */
    public EvidencijaCasova(LocalDate datOd, LocalDate datDo, Nastavnik nastavnik, Osoba osoba, Jezik jezik) {
        setDatOd(datOd);
        setDatDo(datDo);
        setNastavnik(nastavnik);
        setOsoba(osoba);
        setJezik(jezik);
        this.brojCasova = 0;
        this.prosecnaOcena = 0;
    }

    /**
     * Dodaje stavku u evidenciju i postavlja povratnu vezu stavke ka ovoj evidenciji.
     * Ne racuna prosecnu ocenu automatski, za to treba pozvati preracunajProsecnuOcenu.
     *
     * @param stavka stavka koja se dodaje, ne sme biti null
     * @throws java.lang.NullPointerException ako je stavka null
     */
    public void dodajStavku(StavkaEvidencijeCasova stavka) {
        if (stavka == null) {
            throw new NullPointerException("Stavka ne sme biti null");
        }
        stavka.setEvidencijaCasova(this);
        stavke.add(stavka);
    }

    /**
     * Ponovo racuna prosecnaOcena i brojCasova na osnovu trenutnih stavki. Ako
     * evidencija nema nijednu stavku, oba polja se postavljaju na 0.
     */
    public void preracunajProsecnuOcenu() {
        if (stavke.isEmpty()) {
            prosecnaOcena = 0;
            brojCasova = 0;
            return;
        }
        prosecnaOcena = stavke.stream().mapToInt(StavkaEvidencijeCasova::getOcena).average().orElse(0);
        brojCasova = stavke.size();
    }

    /**
     * @return id evidencije, ili null ako jos nije sacuvana u bazi
     */
    public Long getIdEvidencija() {
        return idEvidencija;
    }

    /**
     * @param idEvidencija novi identifikator evidencije
     */
    public void setIdEvidencija(Long idEvidencija) {
        this.idEvidencija = idEvidencija;
    }

    /**
     * @return datum od kog vazi evidencija
     */
    public LocalDate getDatOd() {
        return datOd;
    }

    /**
     * Postavlja datOd. Ako je datDo vec postavljen, proverava se da datDo ne
     * bude pre novog datOd.
     *
     * @param datOd datum, ne sme biti null
     * @throws java.lang.NullPointerException     ako je datOd null
     * @throws java.lang.IllegalArgumentException ako je vec postavljen datDo koji je pre datOd
     */
    public void setDatOd(LocalDate datOd) {
        if (datOd == null) {
            throw new NullPointerException("Datum od ne sme biti null");
        }
        if (datDo != null && datDo.isBefore(datOd)) {
            throw new IllegalArgumentException("Datum do ne sme biti pre datuma od");
        }
        this.datOd = datOd;
    }

    /**
     * @return datum do kog vazi evidencija
     */
    public LocalDate getDatDo() {
        return datDo;
    }

    /**
     * Postavlja datDo. Ako je datOd vec postavljen, proverava se da novi datDo
     * ne bude pre njega.
     *
     * @param datDo datum, ne sme biti null
     * @throws java.lang.NullPointerException     ako je datDo null
     * @throws java.lang.IllegalArgumentException ako je datDo pre vec postavljenog datOd
     */
    public void setDatDo(LocalDate datDo) {
        if (datDo == null) {
            throw new NullPointerException("Datum do ne sme biti null");
        }
        if (datOd != null && datDo.isBefore(datOd)) {
            throw new IllegalArgumentException("Datum do ne sme biti pre datuma od");
        }
        this.datDo = datDo;
    }

    /**
     * @return broj odrzanih casova
     */
    public int getBrojCasova() {
        return brojCasova;
    }

    /**
     * Postavlja broj casova. Obicno se ne poziva rucno, vec preko preracunajProsecnuOcenu.
     *
     * @param brojCasova broj casova, ne sme biti negativan
     * @throws java.lang.IllegalArgumentException ako je brojCasova negativan
     */
    public void setBrojCasova(int brojCasova) {
        if (brojCasova < 0) {
            throw new IllegalArgumentException("Broj časova ne sme biti negativan");
        }
        this.brojCasova = brojCasova;
    }

    /**
     * @return prosecna ocena svih stavki
     */
    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    /**
     * Postavlja prosecnu ocenu direktno, bez validacije. Obicno se ne poziva
     * rucno, vec preko preracunajProsecnuOcenu.
     *
     * @param prosecnaOcena nova prosecna ocena
     */
    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    /**
     * @return nastavnik koji vodi evidenciju
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
     * @return osoba za koju se vodi evidencija
     */
    public Osoba getOsoba() {
        return osoba;
    }

    /**
     * Postavlja osobu.
     *
     * @param osoba osoba, ne sme biti null
     * @throws java.lang.NullPointerException ako je osoba null
     */
    public void setOsoba(Osoba osoba) {
        if (osoba == null) {
            throw new NullPointerException("Osoba ne sme biti null");
        }
        this.osoba = osoba;
    }

    /**
     * @return jezik za koji se vodi evidencija
     */
    public Jezik getJezik() {
        return jezik;
    }

    /**
     * Postavlja jezik.
     *
     * @param jezik jezik, ne sme biti null
     * @throws java.lang.NullPointerException ako je jezik null
     */
    public void setJezik(Jezik jezik) {
        if (jezik == null) {
            throw new NullPointerException("Jezik ne sme biti null");
        }
        this.jezik = jezik;
    }

    /**
     * @return lista stavki evidencije; inicijalizuje se zajedno sa evidencijom
     *         na praznu listu, pa nikad nije null
     */
    public List<StavkaEvidencijeCasova> getStavke() {
        return stavke;
    }

    /**
     * Postavlja celu listu stavki (zamenjuje postojecu). Za dodavanje pojedinacne
     * stavke koristiti metodu dodajStavku.
     *
     * @param stavke nova lista, ne sme biti null
     * @throws java.lang.NullPointerException ako je lista null
     */
    public void setStavke(List<StavkaEvidencijeCasova> stavke) {
        if (stavke == null) {
            throw new NullPointerException("Lista stavki ne sme biti null");
        }
        this.stavke = stavke;
    }

    /**
     * Vraca tekstualnu reprezentaciju evidencije.
     */
    @Override
    public String toString() {
        return "EvidencijaCasova{" +
                "idEvidencija=" + idEvidencija +
                ", datOd=" + datOd +
                ", datDo=" + datDo +
                ", brojCasova=" + brojCasova +
                ", prosecnaOcena=" + prosecnaOcena +
                ", nastavnik=" + (nastavnik != null ? nastavnik.getUsername() : null) +
                ", osoba=" + (osoba != null ? osoba.getIme() + " " + osoba.getPrezime() : null) +
                ", jezik=" + (jezik != null ? jezik.getNaziv() : null) +
                ", brojStavki=" + stavke.size() +
                '}';
    }

    /**
     * Dve evidencije su jednake ako imaju iste vrednosti svih polja osim liste
     * stavki (id, datOd, datDo, brojCasova, prosecnaOcena, nastavnik, osoba, jezik).
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca EvidencijaCasova sa istim vrednostima polja
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
        EvidencijaCasova that = (EvidencijaCasova) o;
        return brojCasova == that.brojCasova
                && Double.compare(prosecnaOcena, that.prosecnaOcena) == 0
                && Objects.equals(idEvidencija, that.idEvidencija)
                && Objects.equals(datOd, that.datOd)
                && Objects.equals(datDo, that.datDo)
                && Objects.equals(nastavnik, that.nastavnik)
                && Objects.equals(osoba, that.osoba)
                && Objects.equals(jezik, that.jezik);
    }

    /**
     * Racuna hash kod na osnovu id-ja, datOd, datDo, broja casova, prosecne ocene,
     * nastavnika, osobe i jezika.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEvidencija, datOd, datDo, brojCasova, prosecnaOcena, nastavnik, osoba, jezik);
    }
}
