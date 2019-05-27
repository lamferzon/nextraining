package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.Prova1500mService;
import it.unibg.nextraining.domain.Prova1500m;
import it.unibg.nextraining.repository.Prova1500mRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prova1500m}.
 */
@Service
@Transactional
public class Prova1500mServiceImpl implements Prova1500mService {

    private final Logger log = LoggerFactory.getLogger(Prova1500mServiceImpl.class);

    private final Prova1500mRepository prova1500mRepository;

    public Prova1500mServiceImpl(Prova1500mRepository prova1500mRepository) {
        this.prova1500mRepository = prova1500mRepository;
    }

    /**
     * Save a prova1500m.
     *
     * @param prova1500m the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Prova1500m save(Prova1500m prova1500m) {
        log.debug("Request to save Prova1500m : {}", prova1500m);
        return prova1500mRepository.save(prova1500m);
    }

    /**
     * Get all the prova1500ms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prova1500m> findAll(Pageable pageable) {
        log.debug("Request to get all Prova1500ms");
        return prova1500mRepository.findAll(pageable);
    }


    /**
     * Get one prova1500m by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prova1500m> findOne(Long id) {
        log.debug("Request to get Prova1500m : {}", id);
        return prova1500mRepository.findById(id);
    }

    /**
     * Delete the prova1500m by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prova1500m : {}", id);
        prova1500mRepository.deleteById(id);
    }
}
