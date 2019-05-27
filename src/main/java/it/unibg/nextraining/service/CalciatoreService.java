package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Calciatore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Calciatore}.
 */
public interface CalciatoreService {

    /**
     * Save a calciatore.
     *
     * @param calciatore the entity to save.
     * @return the persisted entity.
     */
    Calciatore save(Calciatore calciatore);

    /**
     * Get all the calciatores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Calciatore> findAll(Pageable pageable);


    /**
     * Get the "id" calciatore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Calciatore> findOne(Long id);

    /**
     * Delete the "id" calciatore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
