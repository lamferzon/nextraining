package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.TestdiCooperService;
import it.unibg.nextraining.domain.TestdiCooper;
import it.unibg.nextraining.repository.TestdiCooperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TestdiCooper}.
 */
@Service
@Transactional
public class TestdiCooperServiceImpl implements TestdiCooperService {

    private final Logger log = LoggerFactory.getLogger(TestdiCooperServiceImpl.class);

    private final TestdiCooperRepository testdiCooperRepository;

    public TestdiCooperServiceImpl(TestdiCooperRepository testdiCooperRepository) {
        this.testdiCooperRepository = testdiCooperRepository;
    }

    /**
     * Save a testdiCooper.
     *
     * @param testdiCooper the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TestdiCooper save(TestdiCooper testdiCooper) {
        log.debug("Request to save TestdiCooper : {}", testdiCooper);
        return testdiCooperRepository.save(testdiCooper);
    }

    /**
     * Get all the testdiCoopers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TestdiCooper> findAll(Pageable pageable) {
        log.debug("Request to get all TestdiCoopers");
        return testdiCooperRepository.findAll(pageable);
    }


    /**
     * Get one testdiCooper by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TestdiCooper> findOne(Long id) {
        log.debug("Request to get TestdiCooper : {}", id);
        return testdiCooperRepository.findById(id);
    }

    /**
     * Delete the testdiCooper by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestdiCooper : {}", id);
        testdiCooperRepository.deleteById(id);
    }
}
