package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.TestdiCooper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TestdiCooper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestdiCooperRepository extends JpaRepository<TestdiCooper, Long> {

}
