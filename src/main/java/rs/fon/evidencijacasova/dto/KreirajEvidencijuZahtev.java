package rs.fon.evidencijacasova.dto;

import java.time.LocalDate;
import java.util.List;

public record KreirajEvidencijuZahtev(
        Long idNastavnik,
        Long idOsoba,
        Long idJezik,
        LocalDate datOd,
        LocalDate datDo,
        List<StavkaZahtev> stavke) {
}
