package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Prova1500m;
import it.unibg.nextraining.service.Prova1500mService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Prova1500m}.
 */
@RestController
@RequestMapping("/api")
public class Prova1500mResource {

    private final Logger log = LoggerFactory.getLogger(Prova1500mResource.class);

    private static final String ENTITY_NAME = "prova1500m";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Prova1500mService prova1500mService;

    public Prova1500mResource(Prova1500mService prova1500mService) {
        this.prova1500mService = prova1500mService;
    }

    /**
     * {@code POST  /prova-1500-ms} : Create a new prova1500m.
     *
     * @param prova1500m the prova1500m to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prova1500m, or with status {@code 400 (Bad Request)} if the prova1500m has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prova-1500-ms")
    public ResponseEntity<Prova1500m> createProva1500m(@Valid @RequestBody Prova1500m prova1500m) throws URISyntaxException {
        log.debug("REST request to save Prova1500m : {}", prova1500m);
        if (prova1500m.getId() != null) {
            throw new BadRequestAlertException("A new prova1500m cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prova1500m result = prova1500mService.save(prova1500m);
        return ResponseEntity.created(new URI("/api/prova-1500-ms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prova-1500-ms} : Updates an existing prova1500m.
     *
     * @param prova1500m the prova1500m to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prova1500m,
     * or with status {@code 400 (Bad Request)} if the prova1500m is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prova1500m couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prova-1500-ms")
    public ResponseEntity<Prova1500m> updateProva1500m(@Valid @RequestBody Prova1500m prova1500m) throws URISyntaxException {
        log.debug("REST request to update Prova1500m : {}", prova1500m);
        if (prova1500m.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prova1500m result = prova1500mService.save(prova1500m);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prova1500m.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prova-1500-ms} : get all the prova1500ms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prova1500ms in body.
     */
    @GetMapping("/prova-1500-ms")
    public ResponseEntity<List<Prova1500m>> getAllProva1500ms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Prova1500ms");
        Page<Prova1500m> page = prova1500mService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prova-1500-ms/:id} : get the "id" prova1500m.
     *
     * @param id the id of the prova1500m to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prova1500m, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prova-1500-ms/{id}")
    public ResponseEntity<Prova1500m> getProva1500m(@PathVariable Long id) {
        log.debug("REST request to get Prova1500m : {}", id);
        Optional<Prova1500m> prova1500m = prova1500mService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prova1500m);
    }

    /**
     * {@code DELETE  /prova-1500-ms/:id} : delete the "id" prova1500m.
     *
     * @param id the id of the prova1500m to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prova-1500-ms/{id}")
    public ResponseEntity<Void> deleteProva1500m(@PathVariable Long id) {
        log.debug("REST request to delete Prova1500m : {}", id);
        prova1500mService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
