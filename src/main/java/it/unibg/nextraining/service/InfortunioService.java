package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Infortunio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Infortunio}.
 */
public interface InfortunioService {

    /**
     * Save a infortunio.
     *
     * @param infortunio the entity to save.
     * @return the persisted entity.
     */
    Infortunio save(Infortunio infortunio);

    /**
     * Get all the infortunios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Infortunio> findAll(Pageable pageable);


    /**
     * Get the "id" infortunio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Infortunio> findOne(Long id);

    /**
     * Delete the "id" infortunio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
