package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.TestdiCooper;
import it.unibg.nextraining.service.TestdiCooperService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.TestdiCooper}.
 */
@RestController
@RequestMapping("/api")
public class TestdiCooperResource {

    private final Logger log = LoggerFactory.getLogger(TestdiCooperResource.class);

    private static final String ENTITY_NAME = "testdiCooper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestdiCooperService testdiCooperService;

    public TestdiCooperResource(TestdiCooperService testdiCooperService) {
        this.testdiCooperService = testdiCooperService;
    }

    /**
     * {@code POST  /testdi-coopers} : Create a new testdiCooper.
     *
     * @param testdiCooper the testdiCooper to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testdiCooper, or with status {@code 400 (Bad Request)} if the testdiCooper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/testdi-coopers")
    public ResponseEntity<TestdiCooper> createTestdiCooper(@Valid @RequestBody TestdiCooper testdiCooper) throws URISyntaxException {
        log.debug("REST request to save TestdiCooper : {}", testdiCooper);
        if (testdiCooper.getId() != null) {
            throw new BadRequestAlertException("A new testdiCooper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestdiCooper result = testdiCooperService.save(testdiCooper);
        return ResponseEntity.created(new URI("/api/testdi-coopers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /testdi-coopers} : Updates an existing testdiCooper.
     *
     * @param testdiCooper the testdiCooper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testdiCooper,
     * or with status {@code 400 (Bad Request)} if the testdiCooper is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testdiCooper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/testdi-coopers")
    public ResponseEntity<TestdiCooper> updateTestdiCooper(@Valid @RequestBody TestdiCooper testdiCooper) throws URISyntaxException {
        log.debug("REST request to update TestdiCooper : {}", testdiCooper);
        if (testdiCooper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestdiCooper result = testdiCooperService.save(testdiCooper);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testdiCooper.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /testdi-coopers} : get all the testdiCoopers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testdiCoopers in body.
     */
    @GetMapping("/testdi-coopers")
    public ResponseEntity<List<TestdiCooper>> getAllTestdiCoopers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TestdiCoopers");
        Page<TestdiCooper> page = testdiCooperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /testdi-coopers/:id} : get the "id" testdiCooper.
     *
     * @param id the id of the testdiCooper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testdiCooper, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/testdi-coopers/{id}")
    public ResponseEntity<TestdiCooper> getTestdiCooper(@PathVariable Long id) {
        log.debug("REST request to get TestdiCooper : {}", id);
        Optional<TestdiCooper> testdiCooper = testdiCooperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testdiCooper);
    }

    /**
     * {@code DELETE  /testdi-coopers/:id} : delete the "id" testdiCooper.
     *
     * @param id the id of the testdiCooper to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/testdi-coopers/{id}")
    public ResponseEntity<Void> deleteTestdiCooper(@PathVariable Long id) {
        log.debug("REST request to delete TestdiCooper : {}", id);
        testdiCooperService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
