package rs.fon.evidencijacasova.service;

import java.util.List;

import rs.fon.evidencijacasova.dto.KreirajPlanCasaZahtev;
import rs.fon.evidencijacasova.entity.Jezik;
import rs.fon.evidencijacasova.entity.NivoZnanja;
import rs.fon.evidencijacasova.entity.PlanCasa;

/**
 * Interfejs koji definise poslovnu logiku za rad sa sifarnicima aplikacije -
 * jezicima, nivoima znanja i planovima casa.
 *
 * @author boskovicdd
 */
public interface SifarnikServis {

    /**
     * Kreira novi plan casa.
     *
     * @param zahtev podaci za novi plan casa
     * @return sacuvan plan casa sa dodeljenim identifikatorom
     */
    PlanCasa kreirajPlanCasa(KreirajPlanCasaZahtev zahtev);

    /**
     * @return lista svih jezika
     */
    List<Jezik> pretraziJezike();

    /**
     * @return lista svih nivoa znanja
     */
    List<NivoZnanja> pretraziNivoeZnanja();

    /**
     * @return lista svih planova casa
     */
    List<PlanCasa> pretraziPlanoveCasa();
}
