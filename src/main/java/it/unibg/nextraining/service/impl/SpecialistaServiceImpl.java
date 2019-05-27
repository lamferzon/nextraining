package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.SpecialistaService;
import it.unibg.nextraining.domain.Specialista;
import it.unibg.nextraining.repository.SpecialistaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Specialista}.
 */
@Service
@Transactional
public class SpecialistaServiceImpl implements SpecialistaService {

    private final Logger log = LoggerFactory.getLogger(SpecialistaServiceImpl.class);

    private final SpecialistaRepository specialistaRepository;

    public SpecialistaServiceImpl(SpecialistaRepository specialistaRepository) {
        this.specialistaRepository = specialistaRepository;
    }

    /**
     * Save a specialista.
     *
     * @param specialista the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Specialista save(Specialista specialista) {
        log.debug("Request to save Specialista : {}", specialista);
        return specialistaRepository.save(specialista);
    }

    /**
     * Get all the specialistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Specialista> findAll(Pageable pageable) {
        log.debug("Request to get all Specialistas");
        return specialistaRepository.findAll(pageable);
    }


    /**
     * Get one specialista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Specialista> findOne(Long id) {
        log.debug("Request to get Specialista : {}", id);
        return specialistaRepository.findById(id);
    }

    /**
     * Delete the specialista by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Specialista : {}", id);
        specialistaRepository.deleteById(id);
    }
}
