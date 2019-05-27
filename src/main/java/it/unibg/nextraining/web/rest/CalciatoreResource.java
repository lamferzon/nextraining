package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.service.CalciatoreService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Calciatore}.
 */
@RestController
@RequestMapping("/api")
public class CalciatoreResource {

    private final Logger log = LoggerFactory.getLogger(CalciatoreResource.class);

    private static final String ENTITY_NAME = "calciatore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalciatoreService calciatoreService;

    public CalciatoreResource(CalciatoreService calciatoreService) {
        this.calciatoreService = calciatoreService;
    }

    /**
     * {@code POST  /calciatores} : Create a new calciatore.
     *
     * @param calciatore the calciatore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calciatore, or with status {@code 400 (Bad Request)} if the calciatore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calciatores")
    public ResponseEntity<Calciatore> createCalciatore(@Valid @RequestBody Calciatore calciatore) throws URISyntaxException {
        log.debug("REST request to save Calciatore : {}", calciatore);
        if (calciatore.getId() != null) {
            throw new BadRequestAlertException("A new calciatore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calciatore result = calciatoreService.save(calciatore);
        return ResponseEntity.created(new URI("/api/calciatores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calciatores} : Updates an existing calciatore.
     *
     * @param calciatore the calciatore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calciatore,
     * or with status {@code 400 (Bad Request)} if the calciatore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calciatore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calciatores")
    public ResponseEntity<Calciatore> updateCalciatore(@Valid @RequestBody Calciatore calciatore) throws URISyntaxException {
        log.debug("REST request to update Calciatore : {}", calciatore);
        if (calciatore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calciatore result = calciatoreService.save(calciatore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calciatore.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calciatores} : get all the calciatores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calciatores in body.
     */
    @GetMapping("/calciatores")
    public ResponseEntity<List<Calciatore>> getAllCalciatores(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Calciatores");
        Page<Calciatore> page = calciatoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calciatores/:id} : get the "id" calciatore.
     *
     * @param id the id of the calciatore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calciatore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calciatores/{id}")
    public ResponseEntity<Calciatore> getCalciatore(@PathVariable Long id) {
        log.debug("REST request to get Calciatore : {}", id);
        Optional<Calciatore> calciatore = calciatoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calciatore);
    }

    /**
     * {@code DELETE  /calciatores/:id} : delete the "id" calciatore.
     *
     * @param id the id of the calciatore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calciatores/{id}")
    public ResponseEntity<Void> deleteCalciatore(@PathVariable Long id) {
        log.debug("REST request to delete Calciatore : {}", id);
        calciatoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
