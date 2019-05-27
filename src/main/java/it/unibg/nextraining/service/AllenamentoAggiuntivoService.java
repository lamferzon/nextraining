package it.unibg.nextraining.service;

import it.unibg.nextraining.domain.AllenamentoAggiuntivo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AllenamentoAggiuntivo}.
 */
public interface AllenamentoAggiuntivoService {

    /**
     * Save a allenamentoAggiuntivo.
     *
     * @param allenamentoAggiuntivo the entity to save.
     * @return the persisted entity.
     */
    AllenamentoAggiuntivo save(AllenamentoAggiuntivo allenamentoAggiuntivo);

    /**
     * Get all the allenamentoAggiuntivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllenamentoAggiuntivo> findAll(Pageable pageable);

    /**
     * Get all the allenamentoAggiuntivos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<AllenamentoAggiuntivo> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" allenamentoAggiuntivo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AllenamentoAggiuntivo> findOne(Long id);

    /**
     * Delete the "id" allenamentoAggiuntivo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
