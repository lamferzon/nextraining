package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.ParametriFisici;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParametriFisici entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametriFisiciRepository extends JpaRepository<ParametriFisici, Long> {

}
