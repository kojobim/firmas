package com.bim.seguridad.web.rest;

import com.bim.seguridad.FirmspapApp;
import com.bim.seguridad.domain.Orden;
import com.bim.seguridad.repository.OrdenRepository;
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
 * Integration tests for the {@link OrdenResource} REST controller.
 */
@SpringBootTest(classes = FirmspapApp.class)
public class OrdenResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_FIRMA = "AAAAAAAAAA";
    private static final String UPDATED_FIRMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_LLAVA_ID = 1;
    private static final Integer UPDATED_LLAVA_ID = 2;

    private static final String DEFAULT_PRIMARY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_KEY = "BBBBBBBBBB";

    @Autowired
    private OrdenRepository ordenRepository;

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

    private MockMvc restOrdenMockMvc;

    private Orden orden;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenResource ordenResource = new OrdenResource(ordenRepository);
        this.restOrdenMockMvc = MockMvcBuilders.standaloneSetup(ordenResource)
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
    public static Orden createEntity(EntityManager em) {
        Orden orden = new Orden()
            .numero(DEFAULT_NUMERO)
            .firma(DEFAULT_FIRMA)
            .llavaId(DEFAULT_LLAVA_ID)
            .primaryKey(DEFAULT_PRIMARY_KEY);
        return orden;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orden createUpdatedEntity(EntityManager em) {
        Orden orden = new Orden()
            .numero(UPDATED_NUMERO)
            .firma(UPDATED_FIRMA)
            .llavaId(UPDATED_LLAVA_ID)
            .primaryKey(UPDATED_PRIMARY_KEY);
        return orden;
    }

    @BeforeEach
    public void initTest() {
        orden = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrden() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isCreated());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate + 1);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testOrden.getFirma()).isEqualTo(DEFAULT_FIRMA);
        assertThat(testOrden.getLlavaId()).isEqualTo(DEFAULT_LLAVA_ID);
        assertThat(testOrden.getPrimaryKey()).isEqualTo(DEFAULT_PRIMARY_KEY);
    }

    @Test
    @Transactional
    public void createOrdenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden with an existing ID
        orden.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrdens() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList
        restOrdenMockMvc.perform(get("/api/ordens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orden.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].firma").value(hasItem(DEFAULT_FIRMA)))
            .andExpect(jsonPath("$.[*].llavaId").value(hasItem(DEFAULT_LLAVA_ID)))
            .andExpect(jsonPath("$.[*].primaryKey").value(hasItem(DEFAULT_PRIMARY_KEY)));
    }
    
    @Test
    @Transactional
    public void getOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", orden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orden.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.firma").value(DEFAULT_FIRMA))
            .andExpect(jsonPath("$.llavaId").value(DEFAULT_LLAVA_ID))
            .andExpect(jsonPath("$.primaryKey").value(DEFAULT_PRIMARY_KEY));
    }

    @Test
    @Transactional
    public void getNonExistingOrden() throws Exception {
        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden
        Orden updatedOrden = ordenRepository.findById(orden.getId()).get();
        // Disconnect from session so that the updates on updatedOrden are not directly saved in db
        em.detach(updatedOrden);
        updatedOrden
            .numero(UPDATED_NUMERO)
            .firma(UPDATED_FIRMA)
            .llavaId(UPDATED_LLAVA_ID)
            .primaryKey(UPDATED_PRIMARY_KEY);

        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrden)))
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testOrden.getFirma()).isEqualTo(UPDATED_FIRMA);
        assertThat(testOrden.getLlavaId()).isEqualTo(UPDATED_LLAVA_ID);
        assertThat(testOrden.getPrimaryKey()).isEqualTo(UPDATED_PRIMARY_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Create the Orden

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orden)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeDelete = ordenRepository.findAll().size();

        // Delete the orden
        restOrdenMockMvc.perform(delete("/api/ordens/{id}", orden.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
