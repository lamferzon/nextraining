package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.AllenamentoService;
import it.unibg.nextraining.domain.Allenamento;
import it.unibg.nextraining.repository.AllenamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Allenamento}.
 */
@Service
@Transactional
public class AllenamentoServiceImpl implements AllenamentoService {

    private final Logger log = LoggerFactory.getLogger(AllenamentoServiceImpl.class);

    private final AllenamentoRepository allenamentoRepository;

    public AllenamentoServiceImpl(AllenamentoRepository allenamentoRepository) {
        this.allenamentoRepository = allenamentoRepository;
    }

    /**
     * Save a allenamento.
     *
     * @param allenamento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Allenamento save(Allenamento allenamento) {
        log.debug("Request to save Allenamento : {}", allenamento);
        return allenamentoRepository.save(allenamento);
    }

    /**
     * Get all the allenamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Allenamento> findAll(Pageable pageable) {
        log.debug("Request to get all Allenamentos");
        return allenamentoRepository.findAll(pageable);
    }

    /**
     * Get all the allenamentos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Allenamento> findAllWithEagerRelationships(Pageable pageable) {
        return allenamentoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one allenamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Allenamento> findOne(Long id) {
        log.debug("Request to get Allenamento : {}", id);
        return allenamentoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the allenamento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allenamento : {}", id);
        allenamentoRepository.deleteById(id);
    }
}
