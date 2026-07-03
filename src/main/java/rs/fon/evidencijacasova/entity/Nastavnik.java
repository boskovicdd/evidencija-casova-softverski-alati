package rs.fon.evidencijacasova.entity;

import java.util.ArrayList;
import java.util.List;

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
}
