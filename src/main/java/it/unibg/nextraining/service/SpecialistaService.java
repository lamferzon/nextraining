package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Specialista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Specialista}.
 */
public interface SpecialistaService {

    /**
     * Save a specialista.
     *
     * @param specialista the entity to save.
     * @return the persisted entity.
     */
    Specialista save(Specialista specialista);

    /**
     * Get all the specialistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Specialista> findAll(Pageable pageable);


    /**
     * Get the "id" specialista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Specialista> findOne(Long id);

    /**
     * Delete the "id" specialista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
