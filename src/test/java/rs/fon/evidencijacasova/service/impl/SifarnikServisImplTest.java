package rs.fon.evidencijacasova.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import rs.fon.evidencijacasova.dto.KreirajPlanCasaZahtev;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.PlanCasa;
import rs.fon.evidencijacasova.repository.JezikRepository;
import rs.fon.evidencijacasova.repository.NivoZnanjaRepository;
import rs.fon.evidencijacasova.repository.PlanCasaRepository;

@ExtendWith(MockitoExtension.class)
class SifarnikServisImplTest {

    @Mock
    private JezikRepository jezikRepository;

    @Mock
    private NivoZnanjaRepository nivoZnanjaRepository;

    @Mock
    private PlanCasaRepository planCasaRepository;

    @InjectMocks
    private SifarnikServisImpl sifarnikServis;

    @AfterEach
    void tearDown() {
        sifarnikServis = null;
    }

    @Test
    void testKreirajPlanCasa() {
        KreirajPlanCasaZahtev zahtev = new KreirajPlanCasaZahtev("Konverzacija", "opis1");
        PlanCasa sacuvan = new PlanCasa("Konverzacija", "opis1");
        sacuvan.setIdPlanCasa(1L);
        when(planCasaRepository.save(any(PlanCasa.class))).thenReturn(sacuvan);

        PlanCasa rezultat = sifarnikServis.kreirajPlanCasa(zahtev);

        assertEquals(sacuvan, rezultat);
        verify(planCasaRepository).save(any(PlanCasa.class));
    }

    @Test
    void testPretraziJezike() {
        List<Jezik> jezici = List.of(new Jezik("Engleski", "EN"), new Jezik("Nemacki", "DE"));
        when(jezikRepository.findAll()).thenReturn(jezici);

        List<Jezik> rezultat = sifarnikServis.pretraziJezike();

        assertEquals(jezici, rezultat);
    }

    @Test
    void testPretraziNivoeZnanja() {
        List<NivoZnanja> nivoi = List.of(new NivoZnanja("A1", 1), new NivoZnanja("B2", 4));
        when(nivoZnanjaRepository.findAll()).thenReturn(nivoi);

        List<NivoZnanja> rezultat = sifarnikServis.pretraziNivoeZnanja();

        assertEquals(nivoi, rezultat);
    }

    @Test
    void testPretraziPlanoveCasa() {
        List<PlanCasa> planovi = List.of(new PlanCasa("Konverzacija", "opis1"), new PlanCasa("Gramatika", "opis2"));
        when(planCasaRepository.findAll()).thenReturn(planovi);

        List<PlanCasa> rezultat = sifarnikServis.pretraziPlanoveCasa();

        assertEquals(planovi, rezultat);
    }
}
