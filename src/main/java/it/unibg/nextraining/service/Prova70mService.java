package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Prova70m;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Prova70m}.
 */
public interface Prova70mService {

    /**
     * Save a prova70m.
     *
     * @param prova70m the entity to save.
     * @return the persisted entity.
     */
    Prova70m save(Prova70m prova70m);

    /**
     * Get all the prova70ms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prova70m> findAll(Pageable pageable);


    /**
     * Get the "id" prova70m.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prova70m> findOne(Long id);

    /**
     * Delete the "id" prova70m.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
