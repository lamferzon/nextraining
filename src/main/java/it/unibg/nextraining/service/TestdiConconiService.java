package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.TestdiConconi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TestdiConconi}.
 */
public interface TestdiConconiService {

    /**
     * Save a testdiConconi.
     *
     * @param testdiConconi the entity to save.
     * @return the persisted entity.
     */
    TestdiConconi save(TestdiConconi testdiConconi);

    /**
     * Get all the testdiConconis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestdiConconi> findAll(Pageable pageable);


    /**
     * Get the "id" testdiConconi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestdiConconi> findOne(Long id);

    /**
     * Delete the "id" testdiConconi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
