package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.AllenamentoAggiuntivoService;
import it.unibg.nextraining.domain.AllenamentoAggiuntivo;
import it.unibg.nextraining.repository.AllenamentoAggiuntivoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AllenamentoAggiuntivo}.
 */
@Service
@Transactional
public class AllenamentoAggiuntivoServiceImpl implements AllenamentoAggiuntivoService {

    private final Logger log = LoggerFactory.getLogger(AllenamentoAggiuntivoServiceImpl.class);

    private final AllenamentoAggiuntivoRepository allenamentoAggiuntivoRepository;

    public AllenamentoAggiuntivoServiceImpl(AllenamentoAggiuntivoRepository allenamentoAggiuntivoRepository) {
        this.allenamentoAggiuntivoRepository = allenamentoAggiuntivoRepository;
    }

    /**
     * Save a allenamentoAggiuntivo.
     *
     * @param allenamentoAggiuntivo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AllenamentoAggiuntivo save(AllenamentoAggiuntivo allenamentoAggiuntivo) {
        log.debug("Request to save AllenamentoAggiuntivo : {}", allenamentoAggiuntivo);
        return allenamentoAggiuntivoRepository.save(allenamentoAggiuntivo);
    }

    /**
     * Get all the allenamentoAggiuntivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllenamentoAggiuntivo> findAll(Pageable pageable) {
        log.debug("Request to get all AllenamentoAggiuntivos");
        return allenamentoAggiuntivoRepository.findAll(pageable);
    }

    /**
     * Get all the allenamentoAggiuntivos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AllenamentoAggiuntivo> findAllWithEagerRelationships(Pageable pageable) {
        return allenamentoAggiuntivoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one allenamentoAggiuntivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AllenamentoAggiuntivo> findOne(Long id) {
        log.debug("Request to get AllenamentoAggiuntivo : {}", id);
        return allenamentoAggiuntivoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the allenamentoAggiuntivo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllenamentoAggiuntivo : {}", id);
        allenamentoAggiuntivoRepository.deleteById(id);
    }
}
