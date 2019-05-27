package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Calciatore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calciatore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalciatoreRepository extends JpaRepository<Calciatore, Long> {

}
