package com.bim.seguridad.web.rest;

import com.bim.seguridad.FirmspapApp;
import com.bim.seguridad.domain.Llaves;
import com.bim.seguridad.repository.LlavesRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bim.seguridad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LlavesResource} REST controller.
 */
@SpringBootTest(classes = FirmspapApp.class)
public class LlavesResourceIT {

    private static final byte[] DEFAULT_PRIVADA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRIVADA = TestUtil.createByteArray(1, "1");
    private static final byte[] DEFAULT_PRIVADA_CONTENT_TYPE = TestUtil.createByteArray(1, "1");
//    private static final String UPDATED_PRIVADA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PUBLICA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PUBLICA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PUBLICA_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_PUBLICA_CONTENT_TYPE = "image/png";

    @Autowired
    private LlavesRepository llavesRepository;

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

    private MockMvc restLlavesMockMvc;

    private Llaves llaves;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LlavesResource llavesResource = new LlavesResource(llavesRepository);
        this.restLlavesMockMvc = MockMvcBuilders.standaloneSetup(llavesResource)
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
    public static Llaves createEntity(EntityManager em) {
        Llaves llaves = new Llaves()
            .privada(DEFAULT_PRIVADA)
            .publica(DEFAULT_PUBLICA);
        return llaves;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Llaves createUpdatedEntity(EntityManager em) {
        Llaves llaves = new Llaves()
            .privada(UPDATED_PRIVADA)
            .publica(UPDATED_PUBLICA);
        return llaves;
    }

    @BeforeEach
    public void initTest() {
        llaves = createEntity(em);
    }

    @Test
    @Transactional
    public void createLlaves() throws Exception {
        int databaseSizeBeforeCreate = llavesRepository.findAll().size();

        // Create the Llaves
        restLlavesMockMvc.perform(post("/api/llaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llaves)))
            .andExpect(status().isCreated());

        // Validate the Llaves in the database
        List<Llaves> llavesList = llavesRepository.findAll();
        assertThat(llavesList).hasSize(databaseSizeBeforeCreate + 1);
        Llaves testLlaves = llavesList.get(llavesList.size() - 1);
        assertThat(testLlaves.getPrivada()).isEqualTo(DEFAULT_PRIVADA);
        assertThat(testLlaves.getPublica()).isEqualTo(DEFAULT_PUBLICA);
    }

    @Test
    @Transactional
    public void createLlavesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = llavesRepository.findAll().size();

        // Create the Llaves with an existing ID
        llaves.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLlavesMockMvc.perform(post("/api/llaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llaves)))
            .andExpect(status().isBadRequest());

        // Validate the Llaves in the database
        List<Llaves> llavesList = llavesRepository.findAll();
        assertThat(llavesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLlaves() throws Exception {
        // Initialize the database
        llavesRepository.saveAndFlush(llaves);

        // Get all the llavesList
        restLlavesMockMvc.perform(get("/api/llaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(llaves.getId().intValue())))
            .andExpect(jsonPath("$.[*].privadaContentType").value(hasItem(DEFAULT_PRIVADA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].privada").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRIVADA))))
            .andExpect(jsonPath("$.[*].publicaContentType").value(hasItem(DEFAULT_PUBLICA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].publica").value(hasItem(Base64Utils.encodeToString(DEFAULT_PUBLICA))));
    }
    
    @Test
    @Transactional
    public void getLlaves() throws Exception {
        // Initialize the database
        llavesRepository.saveAndFlush(llaves);

        // Get the llaves
        restLlavesMockMvc.perform(get("/api/llaves/{id}", llaves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(llaves.getId().intValue()))
            .andExpect(jsonPath("$.privadaContentType").value(DEFAULT_PRIVADA_CONTENT_TYPE))
            .andExpect(jsonPath("$.privada").value(Base64Utils.encodeToString(DEFAULT_PRIVADA)))
            .andExpect(jsonPath("$.publicaContentType").value(DEFAULT_PUBLICA_CONTENT_TYPE))
            .andExpect(jsonPath("$.publica").value(Base64Utils.encodeToString(DEFAULT_PUBLICA)));
    }

    @Test
    @Transactional
    public void getNonExistingLlaves() throws Exception {
        // Get the llaves
        restLlavesMockMvc.perform(get("/api/llaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLlaves() throws Exception {
        // Initialize the database
        llavesRepository.saveAndFlush(llaves);

        int databaseSizeBeforeUpdate = llavesRepository.findAll().size();

        // Update the llaves
        Llaves updatedLlaves = llavesRepository.findById(llaves.getId()).get();
        // Disconnect from session so that the updates on updatedLlaves are not directly saved in db
        em.detach(updatedLlaves);
        updatedLlaves
            .privada(UPDATED_PRIVADA)
            .publica(UPDATED_PUBLICA);

        restLlavesMockMvc.perform(put("/api/llaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLlaves)))
            .andExpect(status().isOk());

        // Validate the Llaves in the database
        List<Llaves> llavesList = llavesRepository.findAll();
        assertThat(llavesList).hasSize(databaseSizeBeforeUpdate);
        Llaves testLlaves = llavesList.get(llavesList.size() - 1);
        assertThat(testLlaves.getPrivada()).isEqualTo(UPDATED_PRIVADA);
        assertThat(testLlaves.getPublica()).isEqualTo(UPDATED_PUBLICA);
    }

    @Test
    @Transactional
    public void updateNonExistingLlaves() throws Exception {
        int databaseSizeBeforeUpdate = llavesRepository.findAll().size();

        // Create the Llaves

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLlavesMockMvc.perform(put("/api/llaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(llaves)))
            .andExpect(status().isBadRequest());

        // Validate the Llaves in the database
        List<Llaves> llavesList = llavesRepository.findAll();
        assertThat(llavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLlaves() throws Exception {
        // Initialize the database
        llavesRepository.saveAndFlush(llaves);

        int databaseSizeBeforeDelete = llavesRepository.findAll().size();

        // Delete the llaves
        restLlavesMockMvc.perform(delete("/api/llaves/{id}", llaves.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Llaves> llavesList = llavesRepository.findAll();
        assertThat(llavesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
