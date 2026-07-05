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

@Entity
@Table(name = "nastavnik")
public class Nastavnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNastavnik;

    @Column(unique = true)
    private String username;

    private String password;

    private String ime;

    private String prezime;

    @OneToMany(mappedBy = "nastavnik", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NastavnickiSertifikat> nastavnickiSertifikati = new ArrayList<>();

    public Nastavnik() {
    }

    public Nastavnik(String username, String password, String ime, String prezime) {
        setUsername(username);
        setPassword(password);
        setIme(ime);
        setPrezime(prezime);
    }

    public Long getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Long idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException("Korisničko ime ne sme biti null");
        }
        if (username.isBlank()) {
            throw new IllegalArgumentException("Korisničko ime ne sme biti prazno");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Lozinka ne sme biti null");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("Lozinka ne sme biti prazna");
        }
        this.password = password;
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

    public List<NastavnickiSertifikat> getNastavnickiSertifikati() {
        return nastavnickiSertifikati;
    }

    public void setNastavnickiSertifikati(List<NastavnickiSertifikat> nastavnickiSertifikati) {
        if (nastavnickiSertifikati == null) {
            throw new NullPointerException("Lista sertifikata ne sme biti null");
        }
        this.nastavnickiSertifikati = nastavnickiSertifikati;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(idNastavnik, username, password, ime, prezime);
    }
}
