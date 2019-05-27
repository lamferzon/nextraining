package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Allenamento;
import it.unibg.nextraining.service.AllenamentoService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Allenamento}.
 */
@RestController
@RequestMapping("/api")
public class AllenamentoResource {

    private final Logger log = LoggerFactory.getLogger(AllenamentoResource.class);

    private static final String ENTITY_NAME = "allenamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllenamentoService allenamentoService;

    public AllenamentoResource(AllenamentoService allenamentoService) {
        this.allenamentoService = allenamentoService;
    }

    /**
     * {@code POST  /allenamentos} : Create a new allenamento.
     *
     * @param allenamento the allenamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allenamento, or with status {@code 400 (Bad Request)} if the allenamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allenamentos")
    public ResponseEntity<Allenamento> createAllenamento(@Valid @RequestBody Allenamento allenamento) throws URISyntaxException {
        log.debug("REST request to save Allenamento : {}", allenamento);
        if (allenamento.getId() != null) {
            throw new BadRequestAlertException("A new allenamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allenamento result = allenamentoService.save(allenamento);
        return ResponseEntity.created(new URI("/api/allenamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allenamentos} : Updates an existing allenamento.
     *
     * @param allenamento the allenamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allenamento,
     * or with status {@code 400 (Bad Request)} if the allenamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allenamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allenamentos")
    public ResponseEntity<Allenamento> updateAllenamento(@Valid @RequestBody Allenamento allenamento) throws URISyntaxException {
        log.debug("REST request to update Allenamento : {}", allenamento);
        if (allenamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allenamento result = allenamentoService.save(allenamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allenamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allenamentos} : get all the allenamentos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allenamentos in body.
     */
    @GetMapping("/allenamentos")
    public ResponseEntity<List<Allenamento>> getAllAllenamentos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Allenamentos");
        Page<Allenamento> page;
        if (eagerload) {
            page = allenamentoService.findAllWithEagerRelationships(pageable);
        } else {
            page = allenamentoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allenamentos/:id} : get the "id" allenamento.
     *
     * @param id the id of the allenamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allenamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allenamentos/{id}")
    public ResponseEntity<Allenamento> getAllenamento(@PathVariable Long id) {
        log.debug("REST request to get Allenamento : {}", id);
        Optional<Allenamento> allenamento = allenamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allenamento);
    }

    /**
     * {@code DELETE  /allenamentos/:id} : delete the "id" allenamento.
     *
     * @param id the id of the allenamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allenamentos/{id}")
    public ResponseEntity<Void> deleteAllenamento(@PathVariable Long id) {
        log.debug("REST request to delete Allenamento : {}", id);
        allenamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
