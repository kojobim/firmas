package com.bim.seguridad.web.rest;

import com.bim.seguridad.domain.Sesion;
import com.bim.seguridad.repository.SesionRepository;
import com.bim.seguridad.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bim.seguridad.domain.Sesion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SesionResource {

    private final Logger log = LoggerFactory.getLogger(SesionResource.class);

    private static final String ENTITY_NAME = "firmspapSesion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SesionRepository sesionRepository;

    public SesionResource(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    /**
     * {@code POST  /sesions} : Create a new sesion.
     *
     * @param sesion the sesion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sesion, or with status {@code 400 (Bad Request)} if the sesion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sesions")
    public ResponseEntity<Sesion> createSesion(@RequestBody Sesion sesion) throws URISyntaxException {
        log.debug("REST request to save Sesion : {}", sesion);
        if (sesion.getId() != null) {
            throw new BadRequestAlertException("A new sesion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sesion result = sesionRepository.save(sesion);
        return ResponseEntity.created(new URI("/api/sesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sesions} : Updates an existing sesion.
     *
     * @param sesion the sesion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sesion,
     * or with status {@code 400 (Bad Request)} if the sesion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sesion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sesions")
    public ResponseEntity<Sesion> updateSesion(@RequestBody Sesion sesion) throws URISyntaxException {
        log.debug("REST request to update Sesion : {}", sesion);
        if (sesion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sesion result = sesionRepository.save(sesion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sesion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sesions} : get all the sesions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sesions in body.
     */
    @GetMapping("/sesions")
    public List<Sesion> getAllSesions() {
        log.debug("REST request to get all Sesions");
        return sesionRepository.findAll();
    }

    /**
     * {@code GET  /sesions/:id} : get the "id" sesion.
     *
     * @param id the id of the sesion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sesion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sesions/{id}")
    public ResponseEntity<Sesion> getSesion(@PathVariable Long id) {
        log.debug("REST request to get Sesion : {}", id);
        Optional<Sesion> sesion = sesionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sesion);
    }

    /**
     * {@code DELETE  /sesions/:id} : delete the "id" sesion.
     *
     * @param id the id of the sesion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sesions/{id}")
    public ResponseEntity<Void> deleteSesion(@PathVariable Long id) {
        log.debug("REST request to delete Sesion : {}", id);
        sesionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
