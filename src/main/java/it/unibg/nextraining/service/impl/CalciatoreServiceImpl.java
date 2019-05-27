package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.CalciatoreService;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.CalciatoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Calciatore}.
 */
@Service
@Transactional
public class CalciatoreServiceImpl implements CalciatoreService {

    private final Logger log = LoggerFactory.getLogger(CalciatoreServiceImpl.class);

    private final CalciatoreRepository calciatoreRepository;

    public CalciatoreServiceImpl(CalciatoreRepository calciatoreRepository) {
        this.calciatoreRepository = calciatoreRepository;
    }

    /**
     * Save a calciatore.
     *
     * @param calciatore the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Calciatore save(Calciatore calciatore) {
        log.debug("Request to save Calciatore : {}", calciatore);
        return calciatoreRepository.save(calciatore);
    }

    /**
     * Get all the calciatores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Calciatore> findAll(Pageable pageable) {
        log.debug("Request to get all Calciatores");
        return calciatoreRepository.findAll(pageable);
    }


    /**
     * Get one calciatore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calciatore> findOne(Long id) {
        log.debug("Request to get Calciatore : {}", id);
        return calciatoreRepository.findById(id);
    }

    /**
     * Delete the calciatore by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calciatore : {}", id);
        calciatoreRepository.deleteById(id);
    }
}
