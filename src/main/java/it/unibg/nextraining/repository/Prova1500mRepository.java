package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Prova1500m;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prova1500m entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Prova1500mRepository extends JpaRepository<Prova1500m, Long> {

}
