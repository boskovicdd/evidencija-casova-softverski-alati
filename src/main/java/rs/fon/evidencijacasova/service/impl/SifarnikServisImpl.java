package rs.fon.evidencijacasova.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.fon.evidencijacasova.dto.KreirajPlanCasaZahtev;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.repository.JezikRepository;
import rs.fon.evidencijacasova.repository.NivoZnanjaRepository;
import rs.fon.evidencijacasova.repository.PlanCasaRepository;
import rs.fon.evidencijacasova.service.SifarnikServis;

/**
 * Implementira SifarnikServis koristeci JezikRepository, NivoZnanjaRepository
 * i PlanCasaRepository za pristup podacima.
 *
 * @author boskovicdd
 */
@Service
public class SifarnikServisImpl implements SifarnikServis {

    /** Repozitorijum za pristup podacima o jezicima. */
    private final JezikRepository jezikRepository;

    /** Repozitorijum za pristup podacima o nivoima znanja. */
    private final NivoZnanjaRepository nivoZnanjaRepository;

    /** Repozitorijum za pristup podacima o planovima casa. */
    private final PlanCasaRepository planCasaRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumima.
     *
     * @param jezikRepository      repozitorijum za jezike
     * @param nivoZnanjaRepository repozitorijum za nivoe znanja
     * @param planCasaRepository   repozitorijum za planove casa
     */
    public SifarnikServisImpl(JezikRepository jezikRepository, NivoZnanjaRepository nivoZnanjaRepository,
            PlanCasaRepository planCasaRepository) {
        this.jezikRepository = jezikRepository;
        this.nivoZnanjaRepository = nivoZnanjaRepository;
        this.planCasaRepository = planCasaRepository;
    }

    @Override
    public PlanCasa kreirajPlanCasa(KreirajPlanCasaZahtev zahtev) {
        PlanCasa planCasa = new PlanCasa(zahtev.naziv(), zahtev.opis());
        return planCasaRepository.save(planCasa);
    }

    @Override
    public List<Jezik> pretraziJezike() {
        return jezikRepository.findAll();
    }

    @Override
    public List<NivoZnanja> pretraziNivoeZnanja() {
        return nivoZnanjaRepository.findAll();
    }

    @Override
    public List<PlanCasa> pretraziPlanoveCasa() {
        return planCasaRepository.findAll();
    }
}
