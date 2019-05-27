package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Prova70m;
import it.unibg.nextraining.service.Prova70mService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Prova70m}.
 */
@RestController
@RequestMapping("/api")
public class Prova70mResource {

    private final Logger log = LoggerFactory.getLogger(Prova70mResource.class);

    private static final String ENTITY_NAME = "prova70m";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Prova70mService prova70mService;

    public Prova70mResource(Prova70mService prova70mService) {
        this.prova70mService = prova70mService;
    }

    /**
     * {@code POST  /prova-70-ms} : Create a new prova70m.
     *
     * @param prova70m the prova70m to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prova70m, or with status {@code 400 (Bad Request)} if the prova70m has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prova-70-ms")
    public ResponseEntity<Prova70m> createProva70m(@Valid @RequestBody Prova70m prova70m) throws URISyntaxException {
        log.debug("REST request to save Prova70m : {}", prova70m);
        if (prova70m.getId() != null) {
            throw new BadRequestAlertException("A new prova70m cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prova70m result = prova70mService.save(prova70m);
        return ResponseEntity.created(new URI("/api/prova-70-ms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prova-70-ms} : Updates an existing prova70m.
     *
     * @param prova70m the prova70m to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prova70m,
     * or with status {@code 400 (Bad Request)} if the prova70m is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prova70m couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prova-70-ms")
    public ResponseEntity<Prova70m> updateProva70m(@Valid @RequestBody Prova70m prova70m) throws URISyntaxException {
        log.debug("REST request to update Prova70m : {}", prova70m);
        if (prova70m.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prova70m result = prova70mService.save(prova70m);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prova70m.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prova-70-ms} : get all the prova70ms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prova70ms in body.
     */
    @GetMapping("/prova-70-ms")
    public ResponseEntity<List<Prova70m>> getAllProva70ms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Prova70ms");
        Page<Prova70m> page = prova70mService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prova-70-ms/:id} : get the "id" prova70m.
     *
     * @param id the id of the prova70m to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prova70m, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prova-70-ms/{id}")
    public ResponseEntity<Prova70m> getProva70m(@PathVariable Long id) {
        log.debug("REST request to get Prova70m : {}", id);
        Optional<Prova70m> prova70m = prova70mService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prova70m);
    }

    /**
     * {@code DELETE  /prova-70-ms/:id} : delete the "id" prova70m.
     *
     * @param id the id of the prova70m to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prova-70-ms/{id}")
    public ResponseEntity<Void> deleteProva70m(@PathVariable Long id) {
        log.debug("REST request to delete Prova70m : {}", id);
        prova70mService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
