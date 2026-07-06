package rs.fon.evidencijacasova.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import rs.fon.evidencijacasova.dto.KreirajEvidencijuZahtev;
import rs.fon.evidencijacasova.dto.StavkaZahtev;
import rs.fon.evidencijacasova.entity.EvidencijaCasova;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.entity.StavkaEvidencijeCasova;
import rs.fon.evidencijacasova.repository.EvidencijaCasovaRepository;
import rs.fon.evidencijacasova.repository.JezikRepository;
import rs.fon.evidencijacasova.repository.NastavnikRepository;
import rs.fon.evidencijacasova.repository.OsobaRepository;
import rs.fon.evidencijacasova.repository.PlanCasaRepository;
import rs.fon.evidencijacasova.service.EvidencijaServis;

/**
 * Implementira EvidencijaServis koristeci EvidencijaCasovaRepository,
 * NastavnikRepository, OsobaRepository, JezikRepository i PlanCasaRepository
 * za pristup podacima.
 *
 * @author boskovicdd
 */
@Service
public class EvidencijaServisImpl implements EvidencijaServis {

    /** Repozitorijum za pristup podacima o evidencijama casova. */
    private final EvidencijaCasovaRepository evidencijaCasovaRepository;

    /** Repozitorijum za pristup podacima o nastavnicima. */
    private final NastavnikRepository nastavnikRepository;

    /** Repozitorijum za pristup podacima o osobama. */
    private final OsobaRepository osobaRepository;

    /** Repozitorijum za pristup podacima o jezicima. */
    private final JezikRepository jezikRepository;

    /** Repozitorijum za pristup podacima o planovima casa. */
    private final PlanCasaRepository planCasaRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumima.
     *
     * @param evidencijaCasovaRepository repozitorijum za evidencije casova
     * @param nastavnikRepository        repozitorijum za nastavnike
     * @param osobaRepository            repozitorijum za osobe
     * @param jezikRepository            repozitorijum za jezike
     * @param planCasaRepository         repozitorijum za planove casa
     */
    public EvidencijaServisImpl(EvidencijaCasovaRepository evidencijaCasovaRepository,
            NastavnikRepository nastavnikRepository,
            OsobaRepository osobaRepository,
            JezikRepository jezikRepository,
            PlanCasaRepository planCasaRepository) {
        this.evidencijaCasovaRepository = evidencijaCasovaRepository;
        this.nastavnikRepository = nastavnikRepository;
        this.osobaRepository = osobaRepository;
        this.jezikRepository = jezikRepository;
        this.planCasaRepository = planCasaRepository;
    }

    @Override
    @Transactional
    public EvidencijaCasova kreirajEvidenciju(KreirajEvidencijuZahtev zahtev) {
        if (zahtev.stavke() == null || zahtev.stavke().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evidencija mora imati bar jednu stavku");
        }
        Nastavnik nastavnik = nastavnikRepository.findById(zahtev.idNastavnik())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nastavnik ne postoji"));
        Osoba osoba = osobaRepository.findById(zahtev.idOsoba())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Osoba ne postoji"));
        Jezik jezik = jezikRepository.findById(zahtev.idJezik())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jezik ne postoji"));

        EvidencijaCasova evidencija = new EvidencijaCasova(zahtev.datOd(), zahtev.datDo(), nastavnik, osoba, jezik);

        for (StavkaZahtev stavkaZahtev : zahtev.stavke()) {
            PlanCasa planCasa = planCasaRepository.findById(stavkaZahtev.idPlanCasa())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan časa ne postoji"));
            evidencija.dodajStavku(new StavkaEvidencijeCasova(stavkaZahtev.rbStavke(), stavkaZahtev.datum(),
                    stavkaZahtev.ocena(), planCasa));
        }
        evidencija.preracunajProsecnuOcenu();
        return evidencijaCasovaRepository.save(evidencija);
    }

    @Override
    public List<EvidencijaCasova> pretraziEvidencije(Long idJezik, Long idNastavnik) {
        if (idJezik != null && idNastavnik != null) {
            return evidencijaCasovaRepository.findByJezikIdJezikAndNastavnikIdNastavnik(idJezik, idNastavnik);
        }
        if (idJezik != null) {
            return evidencijaCasovaRepository.findByJezikIdJezik(idJezik);
        }
        if (idNastavnik != null) {
            return evidencijaCasovaRepository.findByNastavnikIdNastavnik(idNastavnik);
        }
        return evidencijaCasovaRepository.findAll();
    }

    @Override
    public EvidencijaCasova vratiEvidenciju(Long id) {
        return evidencijaCasovaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evidencija ne postoji"));
    }

    @Override
    @Transactional
    public EvidencijaCasova dodajStavkuUEvidenciju(Long id, StavkaZahtev zahtev) {
        EvidencijaCasova evidencija = evidencijaCasovaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evidencija ne postoji"));
        PlanCasa planCasa = planCasaRepository.findById(zahtev.idPlanCasa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan časa ne postoji"));
        evidencija.dodajStavku(new StavkaEvidencijeCasova(zahtev.rbStavke(), zahtev.datum(), zahtev.ocena(), planCasa));
        evidencija.preracunajProsecnuOcenu();
        return evidencijaCasovaRepository.save(evidencija);
    }

    @Override
    @Transactional
    public void obrisiEvidenciju(Long id) {
        if (!evidencijaCasovaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evidencija ne postoji");
        }
        evidencijaCasovaRepository.deleteById(id);
    }
}
