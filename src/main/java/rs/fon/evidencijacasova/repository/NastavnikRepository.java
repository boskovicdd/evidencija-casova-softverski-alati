package rs.fon.evidencijacasova.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.fon.evidencijacasova.entity.Nastavnik;

public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

    Optional<Nastavnik> findByUsername(String username);
}
