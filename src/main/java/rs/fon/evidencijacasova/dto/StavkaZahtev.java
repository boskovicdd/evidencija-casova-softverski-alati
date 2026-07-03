package rs.fon.evidencijacasova.dto;

import java.time.LocalDate;

public record StavkaZahtev(int rbStavke, LocalDate datum, int ocena, Long idPlanCasa) {
}
