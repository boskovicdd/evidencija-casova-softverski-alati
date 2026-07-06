package rs.fon.evidencijacasova.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Nastavnik koji predaje strane jezike. Ima nalog (username/password) preko kog se
 * prijavljuje i vodi evidenciju casova, i moze imati vise sertifikata za znanje jezika.
 *
 * @author boskovicdd
 */
@Entity
@Table(name = "nastavnik")
public class Nastavnik {

    /** Identifikator nastavnika u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNastavnik;

    /** Korisnicko ime za prijavu, jedinstveno u bazi. */
    @Column(unique = true)
    private String username;

    /** Lozinka za prijavu. */
    private String password;

    /** Ime nastavnika. */
    private String ime;

    /** Prezime nastavnika. */
    private String prezime;

    /** Sertifikati koje nastavnik ima za pojedine jezike. */
    @OneToMany(mappedBy = "nastavnik", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NastavnickiSertifikat> nastavnickiSertifikati = new ArrayList<>();

    /**
     * Prazan konstruktor, potreban Hibernate-u da napravi instancu pre nego sto
     * ucita podatke iz baze.
     */
    public Nastavnik() {
    }

    /**
     * Pravi novog nastavnika. Sva polja se postavljaju preko setter-a, tako da
     * validacija uvek prodje, bez obzira da li se objekat pravi ovako ili se
     * kasnije menja preko getter-a/setter-a.
     *
     * @param username korisnicko ime za prijavu, mora biti jedinstveno i ne prazno
     * @param password lozinka za prijavu, ne sme biti prazna
     * @param ime      ime nastavnika
     * @param prezime  prezime nastavnika
     * @throws java.lang.NullPointerException     ako je neki od parametara null
     * @throws java.lang.IllegalArgumentException ako je neki od parametara prazan
     */
    public Nastavnik(String username, String password, String ime, String prezime) {
        setUsername(username);
        setPassword(password);
        setIme(ime);
        setPrezime(prezime);
    }

    /**
     * @return id nastavnika, ili null ako jos nije sacuvan u bazi
     */
    public Long getIdNastavnik() {
        return idNastavnik;
    }

    /**
     * @param idNastavnik novi identifikator nastavnika
     */
    public void setIdNastavnik(Long idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    /**
     * @return korisnicko ime za prijavu
     */
    public String getUsername() {
        return username;
    }

    /**
     * Postavlja korisnicko ime.
     *
     * @param username korisnicko ime, ne sme biti null ni prazno
     * @throws java.lang.NullPointerException     ako je username null
     * @throws java.lang.IllegalArgumentException ako je username prazan
     */
    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException("Korisničko ime ne sme biti null");
        }
        if (username.isBlank()) {
            throw new IllegalArgumentException("Korisničko ime ne sme biti prazno");
        }
        this.username = username;
    }

    /**
     * @return lozinka za prijavu
     */
    public String getPassword() {
        return password;
    }

    /**
     * Postavlja lozinku.
     *
     * @param password lozinka, ne sme biti null ni prazna
     * @throws java.lang.NullPointerException     ako je password null
     * @throws java.lang.IllegalArgumentException ako je password prazan
     */
    public void setPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Lozinka ne sme biti null");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("Lozinka ne sme biti prazna");
        }
        this.password = password;
    }

    /**
     * @return ime nastavnika
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime.
     *
     * @param ime ime nastavnika, ne sme biti null ni prazno
     * @throws java.lang.NullPointerException     ako je ime null
     * @throws java.lang.IllegalArgumentException ako je ime prazan
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
     * @return prezime nastavnika
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime.
     *
     * @param prezime prezime nastavnika, ne sme biti null ni prazno
     * @throws java.lang.NullPointerException     ako je prezime null
     * @throws java.lang.IllegalArgumentException ako je prezime prazan
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
     * @return lista sertifikata koje nastavnik ima inicijalizuje se zajedno sa
     *         nastavnikom na praznu listu, pa nikad nije null
     */
    public List<NastavnickiSertifikat> getNastavnickiSertifikati() {
        return nastavnickiSertifikati;
    }

    /**
     * Postavlja celu listu sertifikata (zamenjuje postojecu).
     *
     * @param nastavnickiSertifikati nova lista, ne sme biti null
     * @throws java.lang.NullPointerException ako je lista null
     */
    public void setNastavnickiSertifikati(List<NastavnickiSertifikat> nastavnickiSertifikati) {
        if (nastavnickiSertifikati == null) {
            throw new NullPointerException("Lista sertifikata ne sme biti null");
        }
        this.nastavnickiSertifikati = nastavnickiSertifikati;
    }

    /**
     * Vraca tekstualnu reprezentaciju nastavnika.
     */
    @Override
    public String toString() {
        return "Nastavnik{" +
                "idNastavnik=" + idNastavnik +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                '}';
    }

    /**
     * Dva nastavnika su jednaka ako imaju iste vrednosti svih polja (id, username,
     * password, ime, prezime). Lista sertifikata se ne poredi.
     *
     * @param o objekat sa kojim se poredi
     * @return true ako je o isti objekat ili instanca Nastavnik-a sa istim vrednostima polja
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
        Nastavnik nastavnik = (Nastavnik) o;
        return Objects.equals(idNastavnik, nastavnik.idNastavnik)
                && Objects.equals(username, nastavnik.username)
                && Objects.equals(password, nastavnik.password)
                && Objects.equals(ime, nastavnik.ime)
                && Objects.equals(prezime, nastavnik.prezime);
    }

    /**
     * Racuna hash kod na osnovu id-ja, username-a, password-a, imena i prezimena.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idNastavnik, username, password, ime, prezime);
    }
}
