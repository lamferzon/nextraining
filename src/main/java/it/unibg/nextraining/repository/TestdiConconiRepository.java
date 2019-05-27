package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.TestdiConconi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TestdiConconi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestdiConconiRepository extends JpaRepository<TestdiConconi, Long> {

}
