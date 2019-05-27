package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.ParametriFisiciService;
import it.unibg.nextraining.domain.ParametriFisici;
import it.unibg.nextraining.repository.ParametriFisiciRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParametriFisici}.
 */
@Service
@Transactional
public class ParametriFisiciServiceImpl implements ParametriFisiciService {

    private final Logger log = LoggerFactory.getLogger(ParametriFisiciServiceImpl.class);

    private final ParametriFisiciRepository parametriFisiciRepository;

    public ParametriFisiciServiceImpl(ParametriFisiciRepository parametriFisiciRepository) {
        this.parametriFisiciRepository = parametriFisiciRepository;
    }

    /**
     * Save a parametriFisici.
     *
     * @param parametriFisici the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParametriFisici save(ParametriFisici parametriFisici) {
        log.debug("Request to save ParametriFisici : {}", parametriFisici);
        return parametriFisiciRepository.save(parametriFisici);
    }

    /**
     * Get all the parametriFisicis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParametriFisici> findAll(Pageable pageable) {
        log.debug("Request to get all ParametriFisicis");
        return parametriFisiciRepository.findAll(pageable);
    }


    /**
     * Get one parametriFisici by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametriFisici> findOne(Long id) {
        log.debug("Request to get ParametriFisici : {}", id);
        return parametriFisiciRepository.findById(id);
    }

    /**
     * Delete the parametriFisici by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParametriFisici : {}", id);
        parametriFisiciRepository.deleteById(id);
    }
}
