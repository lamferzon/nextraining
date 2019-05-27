package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.ParametriFisici;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ParametriFisici}.
 */
public interface ParametriFisiciService {

    /**
     * Save a parametriFisici.
     *
     * @param parametriFisici the entity to save.
     * @return the persisted entity.
     */
    ParametriFisici save(ParametriFisici parametriFisici);

    /**
     * Get all the parametriFisicis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParametriFisici> findAll(Pageable pageable);


    /**
     * Get the "id" parametriFisici.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametriFisici> findOne(Long id);

    /**
     * Delete the "id" parametriFisici.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
