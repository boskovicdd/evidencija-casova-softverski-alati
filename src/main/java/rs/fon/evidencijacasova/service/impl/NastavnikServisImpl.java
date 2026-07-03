package rs.fon.evidencijacasova.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import rs.fon.evidencijacasova.dto.DodajSertifikatZahtev;
import rs.fon.evidencijacasova.dto.KreirajNastavnikaZahtev;
import rs.fon.evidencijacasova.dto.PrijavaZahtev;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.entity.NastavnickiSertifikat;
import rs.fon.evidencijacasova.entity.Sertifikat;
import rs.fon.evidencijacasova.repository.NastavnikRepository;
import rs.fon.evidencijacasova.repository.SertifikatRepository;
import rs.fon.evidencijacasova.service.NastavnikServis;

@Service
public class NastavnikServisImpl implements NastavnikServis {

    private final NastavnikRepository nastavnikRepository;
    private final SertifikatRepository sertifikatRepository;

    public NastavnikServisImpl(NastavnikRepository nastavnikRepository, SertifikatRepository sertifikatRepository) {
        this.nastavnikRepository = nastavnikRepository;
        this.sertifikatRepository = sertifikatRepository;
    }

    @Override
    public Nastavnik prijava(PrijavaZahtev zahtev) {
        Nastavnik nastavnik = nastavnikRepository.findByUsername(zahtev.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogrešno korisničko ime ili lozinka"));
        if (!nastavnik.getPassword().equals(zahtev.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogrešno korisničko ime ili lozinka");
        }
        return nastavnik;
    }

    @Override
    public Nastavnik kreirajNastavnika(KreirajNastavnikaZahtev zahtev) {
        if (nastavnikRepository.findByUsername(zahtev.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisničko ime je već zauzeto");
        }
        Nastavnik nastavnik = new Nastavnik(zahtev.username(), zahtev.password(), zahtev.ime(), zahtev.prezime());
        return nastavnikRepository.save(nastavnik);
    }

    @Override
    public List<Nastavnik> pretraziNastavnike() {
        return nastavnikRepository.findAll();
    }

    @Override
    @Transactional
    public Nastavnik dodajSertifikatNastavniku(Long idNastavnik, DodajSertifikatZahtev zahtev) {
        Nastavnik nastavnik = nastavnikRepository.findById(idNastavnik)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nastavnik ne postoji"));
        Sertifikat sertifikat = sertifikatRepository.findById(zahtev.idSertifikat())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sertifikat ne postoji"));
        NastavnickiSertifikat nastavnickiSertifikat = new NastavnickiSertifikat(zahtev.datumIzdavanja(), nastavnik, sertifikat);
        nastavnik.getNastavnickiSertifikati().add(nastavnickiSertifikat);
        return nastavnikRepository.save(nastavnik);
    }
}
