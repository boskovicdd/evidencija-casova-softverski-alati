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

@Entity
@Table(name = "nastavnicki_sertifikat")
public class NastavnickiSertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNastavnickiSertifikat;

    private LocalDate datumIzdavanja;

    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    @JsonBackReference
    private Nastavnik nastavnik;

    @ManyToOne
    @JoinColumn(name = "id_sertifikat")
    private Sertifikat sertifikat;

    public NastavnickiSertifikat() {
    }

    public NastavnickiSertifikat(LocalDate datumIzdavanja, Nastavnik nastavnik, Sertifikat sertifikat) {
        setDatumIzdavanja(datumIzdavanja);
        setNastavnik(nastavnik);
        setSertifikat(sertifikat);
    }

    public Long getIdNastavnickiSertifikat() {
        return idNastavnickiSertifikat;
    }

    public void setIdNastavnickiSertifikat(Long idNastavnickiSertifikat) {
        this.idNastavnickiSertifikat = idNastavnickiSertifikat;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        if (datumIzdavanja == null) {
            throw new NullPointerException("Datum izdavanja ne sme biti null");
        }
        if (datumIzdavanja.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Datum izdavanja ne sme biti u budućnosti");
        }
        this.datumIzdavanja = datumIzdavanja;
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

    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(Sertifikat sertifikat) {
        if (sertifikat == null) {
            throw new NullPointerException("Sertifikat ne sme biti null");
        }
        this.sertifikat = sertifikat;
    }

    @Override
    public String toString() {
        return "NastavnickiSertifikat{" +
                "idNastavnickiSertifikat=" + idNastavnickiSertifikat +
                ", datumIzdavanja=" + datumIzdavanja +
                ", nastavnik=" + (nastavnik != null ? nastavnik.getUsername() : null) +
                ", sertifikat=" + (sertifikat != null ? sertifikat.getInstitucija() : null) +
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
        NastavnickiSertifikat that = (NastavnickiSertifikat) o;
        return Objects.equals(idNastavnickiSertifikat, that.idNastavnickiSertifikat)
                && Objects.equals(datumIzdavanja, that.datumIzdavanja)
                && Objects.equals(nastavnik, that.nastavnik)
                && Objects.equals(sertifikat, that.sertifikat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNastavnickiSertifikat, datumIzdavanja, nastavnik, sertifikat);
    }
}
