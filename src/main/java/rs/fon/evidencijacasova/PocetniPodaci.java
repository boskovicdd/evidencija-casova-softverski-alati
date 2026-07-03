package rs.fon.evidencijacasova;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import rs.fon.evidencijacasova.entity.EvidencijaCasova;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NastavnickiSertifikat;
import rs.fon.evidencijacasova.entity.Nastavnik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.Osoba;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.entity.Sertifikat;
import rs.fon.evidencijacasova.entity.StavkaEvidencijeCasova;
import rs.fon.evidencijacasova.repository.EvidencijaCasovaRepository;
import rs.fon.evidencijacasova.repository.JezikRepository;
import rs.fon.evidencijacasova.repository.NastavnikRepository;
import rs.fon.evidencijacasova.repository.NivoZnanjaRepository;
import rs.fon.evidencijacasova.repository.OsobaRepository;
import rs.fon.evidencijacasova.repository.PlanCasaRepository;
import rs.fon.evidencijacasova.repository.SertifikatRepository;

@Component
public class PocetniPodaci implements CommandLineRunner {

    private final JezikRepository jezikRepository;
    private final NivoZnanjaRepository nivoZnanjaRepository;
    private final NastavnikRepository nastavnikRepository;
    private final SertifikatRepository sertifikatRepository;
    private final PlanCasaRepository planCasaRepository;
    private final OsobaRepository osobaRepository;
    private final EvidencijaCasovaRepository evidencijaCasovaRepository;

    public PocetniPodaci(JezikRepository jezikRepository, NivoZnanjaRepository nivoZnanjaRepository,
            NastavnikRepository nastavnikRepository, SertifikatRepository sertifikatRepository,
            PlanCasaRepository planCasaRepository, OsobaRepository osobaRepository,
            EvidencijaCasovaRepository evidencijaCasovaRepository) {
        this.jezikRepository = jezikRepository;
        this.nivoZnanjaRepository = nivoZnanjaRepository;
        this.nastavnikRepository = nastavnikRepository;
        this.sertifikatRepository = sertifikatRepository;
        this.planCasaRepository = planCasaRepository;
        this.osobaRepository = osobaRepository;
        this.evidencijaCasovaRepository = evidencijaCasovaRepository;
    }

    @Override
    public void run(String... args) {
        if (jezikRepository.count() > 0) {
            return;
        }

        Jezik engleski = jezikRepository.save(new Jezik("Engleski", "EN"));
        Jezik nemacki = jezikRepository.save(new Jezik("Nemački", "DE"));
        jezikRepository.save(new Jezik("Francuski", "FR"));

        NivoZnanja a1 = nivoZnanjaRepository.save(new NivoZnanja("A1", 1));
        nivoZnanjaRepository.save(new NivoZnanja("A2", 2));
        NivoZnanja b1 = nivoZnanjaRepository.save(new NivoZnanja("B1", 3));
        NivoZnanja b2 = nivoZnanjaRepository.save(new NivoZnanja("B2", 4));
        nivoZnanjaRepository.save(new NivoZnanja("C1", 5));
        nivoZnanjaRepository.save(new NivoZnanja("C2", 6));

        Nastavnik marko = new Nastavnik("marko", "marko123", "Marko", "Marković");
        Nastavnik jelena = new Nastavnik("jelena", "jelena123", "Jelena", "Jelić");

        Sertifikat cambridge = sertifikatRepository.save(new Sertifikat("Cambridge Assessment English"));
        Sertifikat goethe = sertifikatRepository.save(new Sertifikat("Goethe-Institut"));
        sertifikatRepository.save(new Sertifikat("Alliance Française"));

        marko.getNastavnickiSertifikati().add(new NastavnickiSertifikat(LocalDate.of(2020, 6, 15), marko, cambridge));
        jelena.getNastavnickiSertifikati().add(new NastavnickiSertifikat(LocalDate.of(2019, 9, 10), jelena, goethe));

        marko = nastavnikRepository.save(marko);
        jelena = nastavnikRepository.save(jelena);

        PlanCasa osnoveGramatike = planCasaRepository.save(
                new PlanCasa("Osnove gramatike", "Uvod u osnovna gramatička pravila"));
        PlanCasa konverzacija = planCasaRepository.save(
                new PlanCasa("Konverzacija", "Vežbanje govora kroz svakodnevne teme"));
        planCasaRepository.save(new PlanCasa("Poslovna komunikacija", "Jezik za poslovno okruženje"));

        Osoba ana = osobaRepository.save(new Osoba("Ana", "Anić", "064-1111111", a1));
        Osoba petar = osobaRepository.save(new Osoba("Petar", "Petrović", "064-2222222", b2));
        osobaRepository.save(new Osoba("Milica", "Milić", "064-3333333", b1));

        EvidencijaCasova evidencijaEngleski = new EvidencijaCasova(
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10), marko, ana, engleski);
        evidencijaEngleski.dodajStavku(new StavkaEvidencijeCasova(1, LocalDate.of(2026, 1, 12), 4, osnoveGramatike));
        evidencijaEngleski.dodajStavku(new StavkaEvidencijeCasova(2, LocalDate.of(2026, 1, 19), 5, konverzacija));
        evidencijaEngleski.dodajStavku(new StavkaEvidencijeCasova(3, LocalDate.of(2026, 1, 26), 4, konverzacija));
        evidencijaEngleski.preracunajProsecnuOcenu();
        evidencijaCasovaRepository.save(evidencijaEngleski);

        EvidencijaCasova evidencijaNemacki = new EvidencijaCasova(
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 3, 1), jelena, petar, nemacki);
        evidencijaNemacki.dodajStavku(new StavkaEvidencijeCasova(1, LocalDate.of(2026, 2, 3), 3, osnoveGramatike));
        evidencijaNemacki.dodajStavku(new StavkaEvidencijeCasova(2, LocalDate.of(2026, 2, 10), 4, konverzacija));
        evidencijaNemacki.preracunajProsecnuOcenu();
        evidencijaCasovaRepository.save(evidencijaNemacki);
    }
}
