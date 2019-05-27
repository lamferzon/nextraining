package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Infortunio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Infortunio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfortunioRepository extends JpaRepository<Infortunio, Long> {

}
