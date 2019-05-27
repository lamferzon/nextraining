package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.TestdiConconi;
import it.unibg.nextraining.service.TestdiConconiService;
import it.unibg.nextraining.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.unibg.nextraining.domain.TestdiConconi}.
 */
@RestController
@RequestMapping("/api")
public class TestdiConconiResource {

    private final Logger log = LoggerFactory.getLogger(TestdiConconiResource.class);

    private static final String ENTITY_NAME = "testdiConconi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestdiConconiService testdiConconiService;

    public TestdiConconiResource(TestdiConconiService testdiConconiService) {
        this.testdiConconiService = testdiConconiService;
    }

    /**
     * {@code POST  /testdi-conconis} : Create a new testdiConconi.
     *
     * @param testdiConconi the testdiConconi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testdiConconi, or with status {@code 400 (Bad Request)} if the testdiConconi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/testdi-conconis")
    public ResponseEntity<TestdiConconi> createTestdiConconi(@Valid @RequestBody TestdiConconi testdiConconi) throws URISyntaxException {
        log.debug("REST request to save TestdiConconi : {}", testdiConconi);
        if (testdiConconi.getId() != null) {
            throw new BadRequestAlertException("A new testdiConconi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestdiConconi result = testdiConconiService.save(testdiConconi);
        return ResponseEntity.created(new URI("/api/testdi-conconis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /testdi-conconis} : Updates an existing testdiConconi.
     *
     * @param testdiConconi the testdiConconi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testdiConconi,
     * or with status {@code 400 (Bad Request)} if the testdiConconi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testdiConconi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/testdi-conconis")
    public ResponseEntity<TestdiConconi> updateTestdiConconi(@Valid @RequestBody TestdiConconi testdiConconi) throws URISyntaxException {
        log.debug("REST request to update TestdiConconi : {}", testdiConconi);
        if (testdiConconi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestdiConconi result = testdiConconiService.save(testdiConconi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testdiConconi.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /testdi-conconis} : get all the testdiConconis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testdiConconis in body.
     */
    @GetMapping("/testdi-conconis")
    public ResponseEntity<List<TestdiConconi>> getAllTestdiConconis(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TestdiConconis");
        Page<TestdiConconi> page = testdiConconiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /testdi-conconis/:id} : get the "id" testdiConconi.
     *
     * @param id the id of the testdiConconi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testdiConconi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/testdi-conconis/{id}")
    public ResponseEntity<TestdiConconi> getTestdiConconi(@PathVariable Long id) {
        log.debug("REST request to get TestdiConconi : {}", id);
        Optional<TestdiConconi> testdiConconi = testdiConconiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testdiConconi);
    }

    /**
     * {@code DELETE  /testdi-conconis/:id} : delete the "id" testdiConconi.
     *
     * @param id the id of the testdiConconi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/testdi-conconis/{id}")
    public ResponseEntity<Void> deleteTestdiConconi(@PathVariable Long id) {
        log.debug("REST request to delete TestdiConconi : {}", id);
        testdiConconiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
