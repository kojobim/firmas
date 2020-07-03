package com.bim.seguridad.web.rest;

import com.bim.seguridad.FirmspapApp;
import com.bim.seguridad.domain.Sesion;
import com.bim.seguridad.repository.SesionRepository;
import com.bim.seguridad.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bim.seguridad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SesionResource} REST controller.
 */
@SpringBootTest(classes = FirmspapApp.class)
public class SesionResourceIT {

    private static final Integer DEFAULT_CLIENTE_ID = 1;
    private static final Integer UPDATED_CLIENTE_ID = 2;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSesionMockMvc;

    private Sesion sesion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SesionResource sesionResource = new SesionResource(sesionRepository);
        this.restSesionMockMvc = MockMvcBuilders.standaloneSetup(sesionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sesion createEntity(EntityManager em) {
        Sesion sesion = new Sesion()
            .clienteId(DEFAULT_CLIENTE_ID)
            .activo(DEFAULT_ACTIVO);
        return sesion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sesion createUpdatedEntity(EntityManager em) {
        Sesion sesion = new Sesion()
            .clienteId(UPDATED_CLIENTE_ID)
            .activo(UPDATED_ACTIVO);
        return sesion;
    }

    @BeforeEach
    public void initTest() {
        sesion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSesion() throws Exception {
        int databaseSizeBeforeCreate = sesionRepository.findAll().size();

        // Create the Sesion
        restSesionMockMvc.perform(post("/api/sesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesion)))
            .andExpect(status().isCreated());

        // Validate the Sesion in the database
        List<Sesion> sesionList = sesionRepository.findAll();
        assertThat(sesionList).hasSize(databaseSizeBeforeCreate + 1);
        Sesion testSesion = sesionList.get(sesionList.size() - 1);
        assertThat(testSesion.getClienteId()).isEqualTo(DEFAULT_CLIENTE_ID);
        assertThat(testSesion.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createSesionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sesionRepository.findAll().size();

        // Create the Sesion with an existing ID
        sesion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSesionMockMvc.perform(post("/api/sesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesion)))
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        List<Sesion> sesionList = sesionRepository.findAll();
        assertThat(sesionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSesions() throws Exception {
        // Initialize the database
        sesionRepository.saveAndFlush(sesion);

        // Get all the sesionList
        restSesionMockMvc.perform(get("/api/sesions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sesion.getId().intValue())))
            .andExpect(jsonPath("$.[*].clienteId").value(hasItem(DEFAULT_CLIENTE_ID)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSesion() throws Exception {
        // Initialize the database
        sesionRepository.saveAndFlush(sesion);

        // Get the sesion
        restSesionMockMvc.perform(get("/api/sesions/{id}", sesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sesion.getId().intValue()))
            .andExpect(jsonPath("$.clienteId").value(DEFAULT_CLIENTE_ID))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSesion() throws Exception {
        // Get the sesion
        restSesionMockMvc.perform(get("/api/sesions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSesion() throws Exception {
        // Initialize the database
        sesionRepository.saveAndFlush(sesion);

        int databaseSizeBeforeUpdate = sesionRepository.findAll().size();

        // Update the sesion
        Sesion updatedSesion = sesionRepository.findById(sesion.getId()).get();
        // Disconnect from session so that the updates on updatedSesion are not directly saved in db
        em.detach(updatedSesion);
        updatedSesion
            .clienteId(UPDATED_CLIENTE_ID)
            .activo(UPDATED_ACTIVO);

        restSesionMockMvc.perform(put("/api/sesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSesion)))
            .andExpect(status().isOk());

        // Validate the Sesion in the database
        List<Sesion> sesionList = sesionRepository.findAll();
        assertThat(sesionList).hasSize(databaseSizeBeforeUpdate);
        Sesion testSesion = sesionList.get(sesionList.size() - 1);
        assertThat(testSesion.getClienteId()).isEqualTo(UPDATED_CLIENTE_ID);
        assertThat(testSesion.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingSesion() throws Exception {
        int databaseSizeBeforeUpdate = sesionRepository.findAll().size();

        // Create the Sesion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionMockMvc.perform(put("/api/sesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesion)))
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        List<Sesion> sesionList = sesionRepository.findAll();
        assertThat(sesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSesion() throws Exception {
        // Initialize the database
        sesionRepository.saveAndFlush(sesion);

        int databaseSizeBeforeDelete = sesionRepository.findAll().size();

        // Delete the sesion
        restSesionMockMvc.perform(delete("/api/sesions/{id}", sesion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sesion> sesionList = sesionRepository.findAll();
        assertThat(sesionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
