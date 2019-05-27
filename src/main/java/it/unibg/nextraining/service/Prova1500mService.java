package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Prova1500m;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Prova1500m}.
 */
public interface Prova1500mService {

    /**
     * Save a prova1500m.
     *
     * @param prova1500m the entity to save.
     * @return the persisted entity.
     */
    Prova1500m save(Prova1500m prova1500m);

    /**
     * Get all the prova1500ms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prova1500m> findAll(Pageable pageable);


    /**
     * Get the "id" prova1500m.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prova1500m> findOne(Long id);

    /**
     * Delete the "id" prova1500m.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
