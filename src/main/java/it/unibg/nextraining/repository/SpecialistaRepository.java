package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Specialista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Specialista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialistaRepository extends JpaRepository<Specialista, Long> {

}
