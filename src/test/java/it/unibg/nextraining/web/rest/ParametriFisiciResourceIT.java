package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.ParametriFisici;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.ParametriFisiciRepository;
import it.unibg.nextraining.service.ParametriFisiciService;
import it.unibg.nextraining.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static it.unibg.nextraining.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.unibg.nextraining.domain.enumeration.Stato;
/**
 * Integration tests for the {@Link ParametriFisiciResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class ParametriFisiciResourceIT {

    private static final LocalDate DEFAULT_DATA_RIVELAZIONE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_RIVELAZIONE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MASSA_CORPOREA = 150;
    private static final Integer UPDATED_MASSA_CORPOREA = 149;

    private static final Integer DEFAULT_ALTEZZA = 250;
    private static final Integer UPDATED_ALTEZZA = 249;

    private static final Float DEFAULT_BMI = 50F;
    private static final Float UPDATED_BMI = 49F;

    private static final Stato DEFAULT_CONDIZIONE = Stato.SOTTOPESO;
    private static final Stato UPDATED_CONDIZIONE = Stato.NORMOPESO;

    private static final Integer DEFAULT_FC_RIPOSO = 150;
    private static final Integer UPDATED_FC_RIPOSO = 149;

    @Autowired
    private ParametriFisiciRepository parametriFisiciRepository;

    @Autowired
    private ParametriFisiciService parametriFisiciService;

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

    private MockMvc restParametriFisiciMockMvc;

    private ParametriFisici parametriFisici;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametriFisiciResource parametriFisiciResource = new ParametriFisiciResource(parametriFisiciService);
        this.restParametriFisiciMockMvc = MockMvcBuilders.standaloneSetup(parametriFisiciResource)
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
    public static ParametriFisici createEntity(EntityManager em) {
        ParametriFisici parametriFisici = new ParametriFisici()
            .dataRivelazione(DEFAULT_DATA_RIVELAZIONE)
            .massaCorporea(DEFAULT_MASSA_CORPOREA)
            .altezza(DEFAULT_ALTEZZA)
            .bmi(DEFAULT_BMI)
            .condizione(DEFAULT_CONDIZIONE)
            .fcRiposo(DEFAULT_FC_RIPOSO);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        parametriFisici.setCalciatore(calciatore);
        return parametriFisici;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParametriFisici createUpdatedEntity(EntityManager em) {
        ParametriFisici parametriFisici = new ParametriFisici()
            .dataRivelazione(UPDATED_DATA_RIVELAZIONE)
            .massaCorporea(UPDATED_MASSA_CORPOREA)
            .altezza(UPDATED_ALTEZZA)
            .bmi(UPDATED_BMI)
            .condizione(UPDATED_CONDIZIONE)
            .fcRiposo(UPDATED_FC_RIPOSO);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createUpdatedEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        parametriFisici.setCalciatore(calciatore);
        return parametriFisici;
    }

    @BeforeEach
    public void initTest() {
        parametriFisici = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametriFisici() throws Exception {
        int databaseSizeBeforeCreate = parametriFisiciRepository.findAll().size();

        // Create the ParametriFisici
        restParametriFisiciMockMvc.perform(post("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isCreated());

        // Validate the ParametriFisici in the database
        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeCreate + 1);
        ParametriFisici testParametriFisici = parametriFisiciList.get(parametriFisiciList.size() - 1);
        assertThat(testParametriFisici.getDataRivelazione()).isEqualTo(DEFAULT_DATA_RIVELAZIONE);
        assertThat(testParametriFisici.getMassaCorporea()).isEqualTo(DEFAULT_MASSA_CORPOREA);
        assertThat(testParametriFisici.getAltezza()).isEqualTo(DEFAULT_ALTEZZA);
        assertThat(testParametriFisici.getBmi()).isEqualTo(DEFAULT_BMI);
        assertThat(testParametriFisici.getCondizione()).isEqualTo(DEFAULT_CONDIZIONE);
        assertThat(testParametriFisici.getFcRiposo()).isEqualTo(DEFAULT_FC_RIPOSO);
    }

    @Test
    @Transactional
    public void createParametriFisiciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametriFisiciRepository.findAll().size();

        // Create the ParametriFisici with an existing ID
        parametriFisici.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametriFisiciMockMvc.perform(post("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isBadRequest());

        // Validate the ParametriFisici in the database
        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataRivelazioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametriFisiciRepository.findAll().size();
        // set the field null
        parametriFisici.setDataRivelazione(null);

        // Create the ParametriFisici, which fails.

        restParametriFisiciMockMvc.perform(post("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isBadRequest());

        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMassaCorporeaIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametriFisiciRepository.findAll().size();
        // set the field null
        parametriFisici.setMassaCorporea(null);

        // Create the ParametriFisici, which fails.

        restParametriFisiciMockMvc.perform(post("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isBadRequest());

        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAltezzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametriFisiciRepository.findAll().size();
        // set the field null
        parametriFisici.setAltezza(null);

        // Create the ParametriFisici, which fails.

        restParametriFisiciMockMvc.perform(post("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isBadRequest());

        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametriFisicis() throws Exception {
        // Initialize the database
        parametriFisiciRepository.saveAndFlush(parametriFisici);

        // Get all the parametriFisiciList
        restParametriFisiciMockMvc.perform(get("/api/parametri-fisicis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametriFisici.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataRivelazione").value(hasItem(DEFAULT_DATA_RIVELAZIONE.toString())))
            .andExpect(jsonPath("$.[*].massaCorporea").value(hasItem(DEFAULT_MASSA_CORPOREA)))
            .andExpect(jsonPath("$.[*].altezza").value(hasItem(DEFAULT_ALTEZZA)))
            .andExpect(jsonPath("$.[*].bmi").value(hasItem(DEFAULT_BMI.doubleValue())))
            .andExpect(jsonPath("$.[*].condizione").value(hasItem(DEFAULT_CONDIZIONE.toString())))
            .andExpect(jsonPath("$.[*].fcRiposo").value(hasItem(DEFAULT_FC_RIPOSO)));
    }
    
    @Test
    @Transactional
    public void getParametriFisici() throws Exception {
        // Initialize the database
        parametriFisiciRepository.saveAndFlush(parametriFisici);

        // Get the parametriFisici
        restParametriFisiciMockMvc.perform(get("/api/parametri-fisicis/{id}", parametriFisici.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametriFisici.getId().intValue()))
            .andExpect(jsonPath("$.dataRivelazione").value(DEFAULT_DATA_RIVELAZIONE.toString()))
            .andExpect(jsonPath("$.massaCorporea").value(DEFAULT_MASSA_CORPOREA))
            .andExpect(jsonPath("$.altezza").value(DEFAULT_ALTEZZA))
            .andExpect(jsonPath("$.bmi").value(DEFAULT_BMI.doubleValue()))
            .andExpect(jsonPath("$.condizione").value(DEFAULT_CONDIZIONE.toString()))
            .andExpect(jsonPath("$.fcRiposo").value(DEFAULT_FC_RIPOSO));
    }

    @Test
    @Transactional
    public void getNonExistingParametriFisici() throws Exception {
        // Get the parametriFisici
        restParametriFisiciMockMvc.perform(get("/api/parametri-fisicis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametriFisici() throws Exception {
        // Initialize the database
        parametriFisiciService.save(parametriFisici);

        int databaseSizeBeforeUpdate = parametriFisiciRepository.findAll().size();

        // Update the parametriFisici
        ParametriFisici updatedParametriFisici = parametriFisiciRepository.findById(parametriFisici.getId()).get();
        // Disconnect from session so that the updates on updatedParametriFisici are not directly saved in db
        em.detach(updatedParametriFisici);
        updatedParametriFisici
            .dataRivelazione(UPDATED_DATA_RIVELAZIONE)
            .massaCorporea(UPDATED_MASSA_CORPOREA)
            .altezza(UPDATED_ALTEZZA)
            .bmi(UPDATED_BMI)
            .condizione(UPDATED_CONDIZIONE)
            .fcRiposo(UPDATED_FC_RIPOSO);

        restParametriFisiciMockMvc.perform(put("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametriFisici)))
            .andExpect(status().isOk());

        // Validate the ParametriFisici in the database
        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeUpdate);
        ParametriFisici testParametriFisici = parametriFisiciList.get(parametriFisiciList.size() - 1);
        assertThat(testParametriFisici.getDataRivelazione()).isEqualTo(UPDATED_DATA_RIVELAZIONE);
        assertThat(testParametriFisici.getMassaCorporea()).isEqualTo(UPDATED_MASSA_CORPOREA);
        assertThat(testParametriFisici.getAltezza()).isEqualTo(UPDATED_ALTEZZA);
        assertThat(testParametriFisici.getBmi()).isEqualTo(UPDATED_BMI);
        assertThat(testParametriFisici.getCondizione()).isEqualTo(UPDATED_CONDIZIONE);
        assertThat(testParametriFisici.getFcRiposo()).isEqualTo(UPDATED_FC_RIPOSO);
    }

    @Test
    @Transactional
    public void updateNonExistingParametriFisici() throws Exception {
        int databaseSizeBeforeUpdate = parametriFisiciRepository.findAll().size();

        // Create the ParametriFisici

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametriFisiciMockMvc.perform(put("/api/parametri-fisicis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametriFisici)))
            .andExpect(status().isBadRequest());

        // Validate the ParametriFisici in the database
        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametriFisici() throws Exception {
        // Initialize the database
        parametriFisiciService.save(parametriFisici);

        int databaseSizeBeforeDelete = parametriFisiciRepository.findAll().size();

        // Delete the parametriFisici
        restParametriFisiciMockMvc.perform(delete("/api/parametri-fisicis/{id}", parametriFisici.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ParametriFisici> parametriFisiciList = parametriFisiciRepository.findAll();
        assertThat(parametriFisiciList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametriFisici.class);
        ParametriFisici parametriFisici1 = new ParametriFisici();
        parametriFisici1.setId(1L);
        ParametriFisici parametriFisici2 = new ParametriFisici();
        parametriFisici2.setId(parametriFisici1.getId());
        assertThat(parametriFisici1).isEqualTo(parametriFisici2);
        parametriFisici2.setId(2L);
        assertThat(parametriFisici1).isNotEqualTo(parametriFisici2);
        parametriFisici1.setId(null);
        assertThat(parametriFisici1).isNotEqualTo(parametriFisici2);
    }
}
