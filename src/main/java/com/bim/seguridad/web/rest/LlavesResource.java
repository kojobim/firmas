package com.bim.seguridad.web.rest;

import com.bim.seguridad.domain.Llaves;
import com.bim.seguridad.repository.LlavesRepository;
import com.bim.seguridad.service.LlavesService;
import com.bim.seguridad.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bim.seguridad.domain.Llaves}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LlavesResource {

    private final Logger log = LoggerFactory.getLogger(LlavesResource.class);

    private static final String ENTITY_NAME = "firmspapLlaves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LlavesRepository llavesRepository;
    
    @Autowired
    private LlavesService llavesService;

    public LlavesResource(LlavesRepository llavesRepository) {
        this.llavesRepository = llavesRepository;
    }

    /**
     * {@code POST  /llaves} : Create a new llaves.
     *
     * @param llaves the llaves to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new llaves, or with status {@code 400 (Bad Request)} if the llaves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/llaves")
    public ResponseEntity<Llaves> createLlaves(@RequestBody Llaves llaves) throws URISyntaxException {
        log.debug("REST request to save Llaves : {}", llaves);
        if (llaves.getId() != null) {
            throw new BadRequestAlertException("A new llaves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        List<Llaves> lista=llavesService.createLlaves();
        lista=llavesRepository.saveAll(lista);
        Llaves result = lista.get(0);
        return ResponseEntity.created(new URI("/api/llaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /llaves} : Updates an existing llaves.
     *
     * @param llaves the llaves to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated llaves,
     * or with status {@code 400 (Bad Request)} if the llaves is not valid,
     * or with status {@code 500 (Internal Server Error)} if the llaves couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/llaves")
    public ResponseEntity<Llaves> updateLlaves(@RequestBody Llaves llaves) throws URISyntaxException {
        log.debug("REST request to update Llaves : {}", llaves);
        if (llaves.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        llaves=llavesService.fillLlaves(llaves);
        Llaves result = llavesRepository.save(llaves);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, llaves.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /llaves} : get all the llaves.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of llaves in body.
     */
    @GetMapping("/llaves")
    public List<Llaves> getAllLlaves() {
        log.debug("REST request to get all Llaves");
        return llavesRepository.findAll();
    }

    /**
     * {@code GET  /llaves/:id} : get the "id" llaves.
     *
     * @param id the id of the llaves to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the llaves, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/llaves/{id}")
    public ResponseEntity<Llaves> getLlaves(@PathVariable Long id) {
        log.debug("REST request to get Llaves : {}", id);
        Optional<Llaves> llaves = llavesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(llaves);
    }

    /**
     * {@code DELETE  /llaves/:id} : delete the "id" llaves.
     *
     * @param id the id of the llaves to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/llaves/{id}")
    public ResponseEntity<Void> deleteLlaves(@PathVariable Long id) {
        log.debug("REST request to delete Llaves : {}", id);
        llavesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
