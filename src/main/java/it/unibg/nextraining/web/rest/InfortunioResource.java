package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Infortunio;
import it.unibg.nextraining.service.InfortunioService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Infortunio}.
 */
@RestController
@RequestMapping("/api")
public class InfortunioResource {

    private final Logger log = LoggerFactory.getLogger(InfortunioResource.class);

    private static final String ENTITY_NAME = "infortunio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfortunioService infortunioService;

    public InfortunioResource(InfortunioService infortunioService) {
        this.infortunioService = infortunioService;
    }

    /**
     * {@code POST  /infortunios} : Create a new infortunio.
     *
     * @param infortunio the infortunio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infortunio, or with status {@code 400 (Bad Request)} if the infortunio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/infortunios")
    public ResponseEntity<Infortunio> createInfortunio(@Valid @RequestBody Infortunio infortunio) throws URISyntaxException {
        log.debug("REST request to save Infortunio : {}", infortunio);
        if (infortunio.getId() != null) {
            throw new BadRequestAlertException("A new infortunio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Infortunio result = infortunioService.save(infortunio);
        return ResponseEntity.created(new URI("/api/infortunios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /infortunios} : Updates an existing infortunio.
     *
     * @param infortunio the infortunio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infortunio,
     * or with status {@code 400 (Bad Request)} if the infortunio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infortunio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/infortunios")
    public ResponseEntity<Infortunio> updateInfortunio(@Valid @RequestBody Infortunio infortunio) throws URISyntaxException {
        log.debug("REST request to update Infortunio : {}", infortunio);
        if (infortunio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Infortunio result = infortunioService.save(infortunio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infortunio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /infortunios} : get all the infortunios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infortunios in body.
     */
    @GetMapping("/infortunios")
    public ResponseEntity<List<Infortunio>> getAllInfortunios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Infortunios");
        Page<Infortunio> page = infortunioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /infortunios/:id} : get the "id" infortunio.
     *
     * @param id the id of the infortunio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infortunio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/infortunios/{id}")
    public ResponseEntity<Infortunio> getInfortunio(@PathVariable Long id) {
        log.debug("REST request to get Infortunio : {}", id);
        Optional<Infortunio> infortunio = infortunioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infortunio);
    }

    /**
     * {@code DELETE  /infortunios/:id} : delete the "id" infortunio.
     *
     * @param id the id of the infortunio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/infortunios/{id}")
    public ResponseEntity<Void> deleteInfortunio(@PathVariable Long id) {
        log.debug("REST request to delete Infortunio : {}", id);
        infortunioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
