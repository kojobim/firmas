package com.bim.seguridad.web.rest;

import com.bim.seguridad.domain.Orden;
import com.bim.seguridad.repository.OrdenRepository;
import com.bim.seguridad.service.FirmaService;
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
import java.util.function.Consumer;

/**
 * REST controller for managing {@link com.bim.seguridad.domain.Orden}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrdenResource {

    private final Logger log = LoggerFactory.getLogger(OrdenResource.class);

    private static final String ENTITY_NAME = "firmspapOrden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdenRepository ordenRepository;
    
    @Autowired
    private FirmaService firmar;
    

    public OrdenResource(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    /**
     * {@code POST  /ordens} : Create a new orden.
     *	
     * @param orden the orden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orden, or with status {@code 400 (Bad Request)} if the orden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/firmar")
    public ResponseEntity<Orden> createOrden(@RequestBody Orden orden) throws URISyntaxException {
        log.debug("REST request to save Orden : {}", orden);
        if (orden.getId() != null) {
            throw new BadRequestAlertException("A new orden cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        
        orden=firmar.firmarService(orden);
        
        Orden result = ordenRepository.save(orden);
        
        //result.setLlaves(null);
        
        ResponseEntity<Orden> firmada=ResponseEntity.created(new URI("/api/ordens/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);        
        
        return firmada;
    }

    /**
     * {@code PUT  /ordens} : Updates an existing orden.
     *
     * @param orden the orden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orden,
     * or with status {@code 400 (Bad Request)} if the orden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orden")
    public ResponseEntity<Orden> updateOrden(@RequestBody Orden orden) throws URISyntaxException {
        log.debug("REST request to update Orden : {}", orden);
        if (orden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Orden up=ordenRepository.getOne(orden.getId());
        up.setNumero(orden.getNumero());
        Orden result = ordenRepository.save(up);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orden.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ordens} : get all the ordens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordens in body.
     */
    @GetMapping("/orden")
    public List<Orden> getAllOrdens(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Ordens");
        List<Orden> listaordenes=ordenRepository.findAllWithEagerRelationships();
        listaordenes.forEach(new Consumer<Orden>() {

			@Override
			public void accept(Orden arg0) {
				// TODO Auto-generated method stub
				arg0.setLlaves(null);
			}
		});
        return listaordenes; 
    }

    /**
     * {@code GET  /ordens/:id} : get the "id" orden.
     *
     * @param id the id of the orden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orden/{id}")
    public ResponseEntity<Orden> getOrden(@PathVariable Long id) {
        log.debug("REST request to get Orden : {}", id);
        Optional<Orden> orden = ordenRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(orden);
    }

    /**
     * {@code DELETE  /ordens/:id} : delete the "id" orden.
     *
     * @param id the id of the orden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        log.debug("REST request to delete Orden : {}", id);
        ordenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
