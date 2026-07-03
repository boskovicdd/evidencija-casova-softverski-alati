package rs.fon.evidencijacasova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.fon.evidencijacasova.entity.EvidencijaCasova;

public interface EvidencijaCasovaRepository extends JpaRepository<EvidencijaCasova, Long> {

    List<EvidencijaCasova> findByJezikIdJezik(Long idJezik);

    List<EvidencijaCasova> findByNastavnikIdNastavnik(Long idNastavnik);

    List<EvidencijaCasova> findByJezikIdJezikAndNastavnikIdNastavnik(Long idJezik, Long idNastavnik);
}
