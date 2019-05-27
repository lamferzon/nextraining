package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.InfortunioService;
import it.unibg.nextraining.domain.Infortunio;
import it.unibg.nextraining.repository.InfortunioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Infortunio}.
 */
@Service
@Transactional
public class InfortunioServiceImpl implements InfortunioService {

    private final Logger log = LoggerFactory.getLogger(InfortunioServiceImpl.class);

    private final InfortunioRepository infortunioRepository;

    public InfortunioServiceImpl(InfortunioRepository infortunioRepository) {
        this.infortunioRepository = infortunioRepository;
    }

    /**
     * Save a infortunio.
     *
     * @param infortunio the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Infortunio save(Infortunio infortunio) {
        log.debug("Request to save Infortunio : {}", infortunio);
        return infortunioRepository.save(infortunio);
    }

    /**
     * Get all the infortunios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Infortunio> findAll(Pageable pageable) {
        log.debug("Request to get all Infortunios");
        return infortunioRepository.findAll(pageable);
    }


    /**
     * Get one infortunio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Infortunio> findOne(Long id) {
        log.debug("Request to get Infortunio : {}", id);
        return infortunioRepository.findById(id);
    }

    /**
     * Delete the infortunio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Infortunio : {}", id);
        infortunioRepository.deleteById(id);
    }
}
