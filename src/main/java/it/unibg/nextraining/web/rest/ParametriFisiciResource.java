package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.ParametriFisici;
import it.unibg.nextraining.service.ParametriFisiciService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.ParametriFisici}.
 */
@RestController
@RequestMapping("/api")
public class ParametriFisiciResource {

    private final Logger log = LoggerFactory.getLogger(ParametriFisiciResource.class);

    private static final String ENTITY_NAME = "parametriFisici";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametriFisiciService parametriFisiciService;

    public ParametriFisiciResource(ParametriFisiciService parametriFisiciService) {
        this.parametriFisiciService = parametriFisiciService;
    }

    /**
     * {@code POST  /parametri-fisicis} : Create a new parametriFisici.
     *
     * @param parametriFisici the parametriFisici to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametriFisici, or with status {@code 400 (Bad Request)} if the parametriFisici has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametri-fisicis")
    public ResponseEntity<ParametriFisici> createParametriFisici(@Valid @RequestBody ParametriFisici parametriFisici) throws URISyntaxException {
        log.debug("REST request to save ParametriFisici : {}", parametriFisici);
        if (parametriFisici.getId() != null) {
            throw new BadRequestAlertException("A new parametriFisici cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametriFisici result = parametriFisiciService.save(parametriFisici);
        return ResponseEntity.created(new URI("/api/parametri-fisicis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametri-fisicis} : Updates an existing parametriFisici.
     *
     * @param parametriFisici the parametriFisici to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametriFisici,
     * or with status {@code 400 (Bad Request)} if the parametriFisici is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametriFisici couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametri-fisicis")
    public ResponseEntity<ParametriFisici> updateParametriFisici(@Valid @RequestBody ParametriFisici parametriFisici) throws URISyntaxException {
        log.debug("REST request to update ParametriFisici : {}", parametriFisici);
        if (parametriFisici.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametriFisici result = parametriFisiciService.save(parametriFisici);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametriFisici.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametri-fisicis} : get all the parametriFisicis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametriFisicis in body.
     */
    @GetMapping("/parametri-fisicis")
    public ResponseEntity<List<ParametriFisici>> getAllParametriFisicis(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ParametriFisicis");
        Page<ParametriFisici> page = parametriFisiciService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parametri-fisicis/:id} : get the "id" parametriFisici.
     *
     * @param id the id of the parametriFisici to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametriFisici, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametri-fisicis/{id}")
    public ResponseEntity<ParametriFisici> getParametriFisici(@PathVariable Long id) {
        log.debug("REST request to get ParametriFisici : {}", id);
        Optional<ParametriFisici> parametriFisici = parametriFisiciService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametriFisici);
    }

    /**
     * {@code DELETE  /parametri-fisicis/:id} : delete the "id" parametriFisici.
     *
     * @param id the id of the parametriFisici to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametri-fisicis/{id}")
    public ResponseEntity<Void> deleteParametriFisici(@PathVariable Long id) {
        log.debug("REST request to delete ParametriFisici : {}", id);
        parametriFisiciService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
