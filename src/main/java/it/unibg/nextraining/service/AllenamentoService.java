package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.Allenamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Allenamento}.
 */
public interface AllenamentoService {

    /**
     * Save a allenamento.
     *
     * @param allenamento the entity to save.
     * @return the persisted entity.
     */
    Allenamento save(Allenamento allenamento);

    /**
     * Get all the allenamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Allenamento> findAll(Pageable pageable);

    /**
     * Get all the allenamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Allenamento> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" allenamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Allenamento> findOne(Long id);

    /**
     * Delete the "id" allenamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
