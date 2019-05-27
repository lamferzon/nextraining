package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.TestdiCooper;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.TestdiCooperRepository;
import it.unibg.nextraining.service.TestdiCooperService;
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

import it.unibg.nextraining.domain.enumeration.Feedback;
/**
 * Integration tests for the {@Link TestdiCooperResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class TestdiCooperResourceIT {

    private static final LocalDate DEFAULT_DATA_TEST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TEST = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_DISTANZA = 10F;
    private static final Float UPDATED_DISTANZA = 9F;

    private static final Integer DEFAULT_V_02_MAX = 120;
    private static final Integer UPDATED_V_02_MAX = 119;

    private static final Feedback DEFAULT_COMMENTO = Feedback.SCARSO;
    private static final Feedback UPDATED_COMMENTO = Feedback.DISCRETO;

    private static final String DEFAULT_COND_CLIMATICHE = "AAAAAAAAAA";
    private static final String UPDATED_COND_CLIMATICHE = "BBBBBBBBBB";

    @Autowired
    private TestdiCooperRepository testdiCooperRepository;

    @Autowired
    private TestdiCooperService testdiCooperService;

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

    private MockMvc restTestdiCooperMockMvc;

    private TestdiCooper testdiCooper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestdiCooperResource testdiCooperResource = new TestdiCooperResource(testdiCooperService);
        this.restTestdiCooperMockMvc = MockMvcBuilders.standaloneSetup(testdiCooperResource)
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
    public static TestdiCooper createEntity(EntityManager em) {
        TestdiCooper testdiCooper = new TestdiCooper()
            .dataTest(DEFAULT_DATA_TEST)
            .distanza(DEFAULT_DISTANZA)
            .v02Max(DEFAULT_V_02_MAX)
            .commento(DEFAULT_COMMENTO)
            .condClimatiche(DEFAULT_COND_CLIMATICHE);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        testdiCooper.setCalciatore(calciatore);
        return testdiCooper;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestdiCooper createUpdatedEntity(EntityManager em) {
        TestdiCooper testdiCooper = new TestdiCooper()
            .dataTest(UPDATED_DATA_TEST)
            .distanza(UPDATED_DISTANZA)
            .v02Max(UPDATED_V_02_MAX)
            .commento(UPDATED_COMMENTO)
            .condClimatiche(UPDATED_COND_CLIMATICHE);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createUpdatedEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        testdiCooper.setCalciatore(calciatore);
        return testdiCooper;
    }

    @BeforeEach
    public void initTest() {
        testdiCooper = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestdiCooper() throws Exception {
        int databaseSizeBeforeCreate = testdiCooperRepository.findAll().size();

        // Create the TestdiCooper
        restTestdiCooperMockMvc.perform(post("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiCooper)))
            .andExpect(status().isCreated());

        // Validate the TestdiCooper in the database
        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeCreate + 1);
        TestdiCooper testTestdiCooper = testdiCooperList.get(testdiCooperList.size() - 1);
        assertThat(testTestdiCooper.getDataTest()).isEqualTo(DEFAULT_DATA_TEST);
        assertThat(testTestdiCooper.getDistanza()).isEqualTo(DEFAULT_DISTANZA);
        assertThat(testTestdiCooper.getv02Max()).isEqualTo(DEFAULT_V_02_MAX);
        assertThat(testTestdiCooper.getCommento()).isEqualTo(DEFAULT_COMMENTO);
        assertThat(testTestdiCooper.getCondClimatiche()).isEqualTo(DEFAULT_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void createTestdiCooperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testdiCooperRepository.findAll().size();

        // Create the TestdiCooper with an existing ID
        testdiCooper.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestdiCooperMockMvc.perform(post("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiCooper)))
            .andExpect(status().isBadRequest());

        // Validate the TestdiCooper in the database
        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = testdiCooperRepository.findAll().size();
        // set the field null
        testdiCooper.setDataTest(null);

        // Create the TestdiCooper, which fails.

        restTestdiCooperMockMvc.perform(post("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiCooper)))
            .andExpect(status().isBadRequest());

        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = testdiCooperRepository.findAll().size();
        // set the field null
        testdiCooper.setDistanza(null);

        // Create the TestdiCooper, which fails.

        restTestdiCooperMockMvc.perform(post("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiCooper)))
            .andExpect(status().isBadRequest());

        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestdiCoopers() throws Exception {
        // Initialize the database
        testdiCooperRepository.saveAndFlush(testdiCooper);

        // Get all the testdiCooperList
        restTestdiCooperMockMvc.perform(get("/api/testdi-coopers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testdiCooper.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataTest").value(hasItem(DEFAULT_DATA_TEST.toString())))
            .andExpect(jsonPath("$.[*].distanza").value(hasItem(DEFAULT_DISTANZA.doubleValue())))
            .andExpect(jsonPath("$.[*].v02Max").value(hasItem(DEFAULT_V_02_MAX)))
            .andExpect(jsonPath("$.[*].commento").value(hasItem(DEFAULT_COMMENTO.toString())))
            .andExpect(jsonPath("$.[*].condClimatiche").value(hasItem(DEFAULT_COND_CLIMATICHE.toString())));
    }
    
    @Test
    @Transactional
    public void getTestdiCooper() throws Exception {
        // Initialize the database
        testdiCooperRepository.saveAndFlush(testdiCooper);

        // Get the testdiCooper
        restTestdiCooperMockMvc.perform(get("/api/testdi-coopers/{id}", testdiCooper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testdiCooper.getId().intValue()))
            .andExpect(jsonPath("$.dataTest").value(DEFAULT_DATA_TEST.toString()))
            .andExpect(jsonPath("$.distanza").value(DEFAULT_DISTANZA.doubleValue()))
            .andExpect(jsonPath("$.v02Max").value(DEFAULT_V_02_MAX))
            .andExpect(jsonPath("$.commento").value(DEFAULT_COMMENTO.toString()))
            .andExpect(jsonPath("$.condClimatiche").value(DEFAULT_COND_CLIMATICHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestdiCooper() throws Exception {
        // Get the testdiCooper
        restTestdiCooperMockMvc.perform(get("/api/testdi-coopers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestdiCooper() throws Exception {
        // Initialize the database
        testdiCooperService.save(testdiCooper);

        int databaseSizeBeforeUpdate = testdiCooperRepository.findAll().size();

        // Update the testdiCooper
        TestdiCooper updatedTestdiCooper = testdiCooperRepository.findById(testdiCooper.getId()).get();
        // Disconnect from session so that the updates on updatedTestdiCooper are not directly saved in db
        em.detach(updatedTestdiCooper);
        updatedTestdiCooper
            .dataTest(UPDATED_DATA_TEST)
            .distanza(UPDATED_DISTANZA)
            .v02Max(UPDATED_V_02_MAX)
            .commento(UPDATED_COMMENTO)
            .condClimatiche(UPDATED_COND_CLIMATICHE);

        restTestdiCooperMockMvc.perform(put("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestdiCooper)))
            .andExpect(status().isOk());

        // Validate the TestdiCooper in the database
        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeUpdate);
        TestdiCooper testTestdiCooper = testdiCooperList.get(testdiCooperList.size() - 1);
        assertThat(testTestdiCooper.getDataTest()).isEqualTo(UPDATED_DATA_TEST);
        assertThat(testTestdiCooper.getDistanza()).isEqualTo(UPDATED_DISTANZA);
        assertThat(testTestdiCooper.getv02Max()).isEqualTo(UPDATED_V_02_MAX);
        assertThat(testTestdiCooper.getCommento()).isEqualTo(UPDATED_COMMENTO);
        assertThat(testTestdiCooper.getCondClimatiche()).isEqualTo(UPDATED_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void updateNonExistingTestdiCooper() throws Exception {
        int databaseSizeBeforeUpdate = testdiCooperRepository.findAll().size();

        // Create the TestdiCooper

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestdiCooperMockMvc.perform(put("/api/testdi-coopers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiCooper)))
            .andExpect(status().isBadRequest());

        // Validate the TestdiCooper in the database
        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestdiCooper() throws Exception {
        // Initialize the database
        testdiCooperService.save(testdiCooper);

        int databaseSizeBeforeDelete = testdiCooperRepository.findAll().size();

        // Delete the testdiCooper
        restTestdiCooperMockMvc.perform(delete("/api/testdi-coopers/{id}", testdiCooper.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TestdiCooper> testdiCooperList = testdiCooperRepository.findAll();
        assertThat(testdiCooperList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestdiCooper.class);
        TestdiCooper testdiCooper1 = new TestdiCooper();
        testdiCooper1.setId(1L);
        TestdiCooper testdiCooper2 = new TestdiCooper();
        testdiCooper2.setId(testdiCooper1.getId());
        assertThat(testdiCooper1).isEqualTo(testdiCooper2);
        testdiCooper2.setId(2L);
        assertThat(testdiCooper1).isNotEqualTo(testdiCooper2);
        testdiCooper1.setId(null);
        assertThat(testdiCooper1).isNotEqualTo(testdiCooper2);
    }
}
