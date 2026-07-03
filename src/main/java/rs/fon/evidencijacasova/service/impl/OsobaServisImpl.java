package rs.fon.evidencijacasova.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import rs.fon.evidencijacasova.dto.IzmeniOsobuZahtev;
import rs.fon.evidencijacasova.dto.KreirajOsobuZahtev;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.repository.NivoZnanjaRepository;
import rs.fon.evidencijacasova.repository.OsobaRepository;
import rs.fon.evidencijacasova.service.OsobaServis;

@Service
public class OsobaServisImpl implements OsobaServis {

    private final OsobaRepository osobaRepository;
    private final NivoZnanjaRepository nivoZnanjaRepository;

    public OsobaServisImpl(OsobaRepository osobaRepository, NivoZnanjaRepository nivoZnanjaRepository) {
        this.osobaRepository = osobaRepository;
        this.nivoZnanjaRepository = nivoZnanjaRepository;
    }

    @Override
    public Osoba kreirajOsobu(KreirajOsobuZahtev zahtev) {
        NivoZnanja nivoZnanja = nivoZnanjaRepository.findById(zahtev.idNivoZnanja())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivo znanja ne postoji"));
        Osoba osoba = new Osoba(zahtev.ime(), zahtev.prezime(), zahtev.brTel(), nivoZnanja);
        return osobaRepository.save(osoba);
    }

    @Override
    public List<Osoba> pretraziOsobe() {
        return osobaRepository.findAll();
    }

    @Override
    @Transactional
    public Osoba izmeniOsobu(Long idOsoba, IzmeniOsobuZahtev zahtev) {
        Osoba osoba = osobaRepository.findById(idOsoba)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Osoba ne postoji"));
        NivoZnanja nivoZnanja = nivoZnanjaRepository.findById(zahtev.idNivoZnanja())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivo znanja ne postoji"));
        osoba.setIme(zahtev.ime());
        osoba.setPrezime(zahtev.prezime());
        osoba.setBrTel(zahtev.brTel());
        osoba.setNivoZnanja(nivoZnanja);
        return osobaRepository.save(osoba);
    }
}
