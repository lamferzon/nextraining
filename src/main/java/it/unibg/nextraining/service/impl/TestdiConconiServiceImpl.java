package it.unibg.nextraining.service.impl;

import it.unibg.nextraining.service.TestdiConconiService;
import it.unibg.nextraining.domain.TestdiConconi;
import it.unibg.nextraining.repository.TestdiConconiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TestdiConconi}.
 */
@Service
@Transactional
public class TestdiConconiServiceImpl implements TestdiConconiService {

    private final Logger log = LoggerFactory.getLogger(TestdiConconiServiceImpl.class);

    private final TestdiConconiRepository testdiConconiRepository;

    public TestdiConconiServiceImpl(TestdiConconiRepository testdiConconiRepository) {
        this.testdiConconiRepository = testdiConconiRepository;
    }

    /**
     * Save a testdiConconi.
     *
     * @param testdiConconi the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TestdiConconi save(TestdiConconi testdiConconi) {
        log.debug("Request to save TestdiConconi : {}", testdiConconi);
        return testdiConconiRepository.save(testdiConconi);
    }

    /**
     * Get all the testdiConconis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TestdiConconi> findAll(Pageable pageable) {
        log.debug("Request to get all TestdiConconis");
        return testdiConconiRepository.findAll(pageable);
    }


    /**
     * Get one testdiConconi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TestdiConconi> findOne(Long id) {
        log.debug("Request to get TestdiConconi : {}", id);
        return testdiConconiRepository.findById(id);
    }

    /**
     * Delete the testdiConconi by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestdiConconi : {}", id);
        testdiConconiRepository.deleteById(id);
    }
}
