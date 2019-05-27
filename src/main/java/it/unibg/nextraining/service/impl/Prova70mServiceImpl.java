package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.Prova70mService;
import it.unibg.nextraining.domain.Prova70m;
import it.unibg.nextraining.repository.Prova70mRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prova70m}.
 */
@Service
@Transactional
public class Prova70mServiceImpl implements Prova70mService {

    private final Logger log = LoggerFactory.getLogger(Prova70mServiceImpl.class);

    private final Prova70mRepository prova70mRepository;

    public Prova70mServiceImpl(Prova70mRepository prova70mRepository) {
        this.prova70mRepository = prova70mRepository;
    }

    /**
     * Save a prova70m.
     *
     * @param prova70m the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Prova70m save(Prova70m prova70m) {
        log.debug("Request to save Prova70m : {}", prova70m);
        return prova70mRepository.save(prova70m);
    }

    /**
     * Get all the prova70ms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prova70m> findAll(Pageable pageable) {
        log.debug("Request to get all Prova70ms");
        return prova70mRepository.findAll(pageable);
    }


    /**
     * Get one prova70m by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prova70m> findOne(Long id) {
        log.debug("Request to get Prova70m : {}", id);
        return prova70mRepository.findById(id);
    }

    /**
     * Delete the prova70m by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prova70m : {}", id);
        prova70mRepository.deleteById(id);
    }
}
