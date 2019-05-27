package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.TestdiCooper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TestdiCooper}.
 */
public interface TestdiCooperService {

    /**
     * Save a testdiCooper.
     *
     * @param testdiCooper the entity to save.
     * @return the persisted entity.
     */
    TestdiCooper save(TestdiCooper testdiCooper);

    /**
     * Get all the testdiCoopers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestdiCooper> findAll(Pageable pageable);


    /**
     * Get the "id" testdiCooper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestdiCooper> findOne(Long id);

    /**
     * Delete the "id" testdiCooper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
