package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Prova70m;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prova70m entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Prova70mRepository extends JpaRepository<Prova70m, Long> {

}
