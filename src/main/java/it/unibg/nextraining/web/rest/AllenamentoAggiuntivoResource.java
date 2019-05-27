package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.AllenamentoAggiuntivo;
import it.unibg.nextraining.service.AllenamentoAggiuntivoService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.AllenamentoAggiuntivo}.
 */
@RestController
@RequestMapping("/api")
public class AllenamentoAggiuntivoResource {

    private final Logger log = LoggerFactory.getLogger(AllenamentoAggiuntivoResource.class);

    private static final String ENTITY_NAME = "allenamentoAggiuntivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllenamentoAggiuntivoService allenamentoAggiuntivoService;

    public AllenamentoAggiuntivoResource(AllenamentoAggiuntivoService allenamentoAggiuntivoService) {
        this.allenamentoAggiuntivoService = allenamentoAggiuntivoService;
    }

    /**
     * {@code POST  /allenamento-aggiuntivos} : Create a new allenamentoAggiuntivo.
     *
     * @param allenamentoAggiuntivo the allenamentoAggiuntivo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allenamentoAggiuntivo, or with status {@code 400 (Bad Request)} if the allenamentoAggiuntivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allenamento-aggiuntivos")
    public ResponseEntity<AllenamentoAggiuntivo> createAllenamentoAggiuntivo(@Valid @RequestBody AllenamentoAggiuntivo allenamentoAggiuntivo) throws URISyntaxException {
        log.debug("REST request to save AllenamentoAggiuntivo : {}", allenamentoAggiuntivo);
        if (allenamentoAggiuntivo.getId() != null) {
            throw new BadRequestAlertException("A new allenamentoAggiuntivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllenamentoAggiuntivo result = allenamentoAggiuntivoService.save(allenamentoAggiuntivo);
        return ResponseEntity.created(new URI("/api/allenamento-aggiuntivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allenamento-aggiuntivos} : Updates an existing allenamentoAggiuntivo.
     *
     * @param allenamentoAggiuntivo the allenamentoAggiuntivo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allenamentoAggiuntivo,
     * or with status {@code 400 (Bad Request)} if the allenamentoAggiuntivo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allenamentoAggiuntivo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allenamento-aggiuntivos")
    public ResponseEntity<AllenamentoAggiuntivo> updateAllenamentoAggiuntivo(@Valid @RequestBody AllenamentoAggiuntivo allenamentoAggiuntivo) throws URISyntaxException {
        log.debug("REST request to update AllenamentoAggiuntivo : {}", allenamentoAggiuntivo);
        if (allenamentoAggiuntivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllenamentoAggiuntivo result = allenamentoAggiuntivoService.save(allenamentoAggiuntivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allenamentoAggiuntivo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allenamento-aggiuntivos} : get all the allenamentoAggiuntivos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allenamentoAggiuntivos in body.
     */
    @GetMapping("/allenamento-aggiuntivos")
    public ResponseEntity<List<AllenamentoAggiuntivo>> getAllAllenamentoAggiuntivos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of AllenamentoAggiuntivos");
        Page<AllenamentoAggiuntivo> page;
        if (eagerload) {
            page = allenamentoAggiuntivoService.findAllWithEagerRelationships(pageable);
        } else {
            page = allenamentoAggiuntivoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /allenamento-aggiuntivos/:id} : get the "id" allenamentoAggiuntivo.
     *
     * @param id the id of the allenamentoAggiuntivo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allenamentoAggiuntivo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allenamento-aggiuntivos/{id}")
    public ResponseEntity<AllenamentoAggiuntivo> getAllenamentoAggiuntivo(@PathVariable Long id) {
        log.debug("REST request to get AllenamentoAggiuntivo : {}", id);
        Optional<AllenamentoAggiuntivo> allenamentoAggiuntivo = allenamentoAggiuntivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allenamentoAggiuntivo);
    }

    /**
     * {@code DELETE  /allenamento-aggiuntivos/:id} : delete the "id" allenamentoAggiuntivo.
     *
     * @param id the id of the allenamentoAggiuntivo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allenamento-aggiuntivos/{id}")
    public ResponseEntity<Void> deleteAllenamentoAggiuntivo(@PathVariable Long id) {
        log.debug("REST request to delete AllenamentoAggiuntivo : {}", id);
        allenamentoAggiuntivoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
