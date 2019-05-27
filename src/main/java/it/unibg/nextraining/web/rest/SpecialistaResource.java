package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.domain.Specialista;
import it.unibg.nextraining.service.SpecialistaService;
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
 * REST controller for managing {@link it.unibg.nextraining.domain.Specialista}.
 */
@RestController
@RequestMapping("/api")
public class SpecialistaResource {

    private final Logger log = LoggerFactory.getLogger(SpecialistaResource.class);

    private static final String ENTITY_NAME = "specialista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialistaService specialistaService;

    public SpecialistaResource(SpecialistaService specialistaService) {
        this.specialistaService = specialistaService;
    }

    /**
     * {@code POST  /specialistas} : Create a new specialista.
     *
     * @param specialista the specialista to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialista, or with status {@code 400 (Bad Request)} if the specialista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialistas")
    public ResponseEntity<Specialista> createSpecialista(@Valid @RequestBody Specialista specialista) throws URISyntaxException {
        log.debug("REST request to save Specialista : {}", specialista);
        if (specialista.getId() != null) {
            throw new BadRequestAlertException("A new specialista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specialista result = specialistaService.save(specialista);
        return ResponseEntity.created(new URI("/api/specialistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialistas} : Updates an existing specialista.
     *
     * @param specialista the specialista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialista,
     * or with status {@code 400 (Bad Request)} if the specialista is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialistas")
    public ResponseEntity<Specialista> updateSpecialista(@Valid @RequestBody Specialista specialista) throws URISyntaxException {
        log.debug("REST request to update Specialista : {}", specialista);
        if (specialista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Specialista result = specialistaService.save(specialista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialista.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specialistas} : get all the specialistas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialistas in body.
     */
    @GetMapping("/specialistas")
    public ResponseEntity<List<Specialista>> getAllSpecialistas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Specialistas");
        Page<Specialista> page = specialistaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specialistas/:id} : get the "id" specialista.
     *
     * @param id the id of the specialista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialistas/{id}")
    public ResponseEntity<Specialista> getSpecialista(@PathVariable Long id) {
        log.debug("REST request to get Specialista : {}", id);
        Optional<Specialista> specialista = specialistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialista);
    }

    /**
     * {@code DELETE  /specialistas/:id} : delete the "id" specialista.
     *
     * @param id the id of the specialista to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialistas/{id}")
    public ResponseEntity<Void> deleteSpecialista(@PathVariable Long id) {
        log.debug("REST request to delete Specialista : {}", id);
        specialistaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
