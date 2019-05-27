package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.TestdiConconi;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.TestdiConconiRepository;
import it.unibg.nextraining.service.TestdiConconiService;
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
import java.util.List;

import static it.unibg.nextraining.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.unibg.nextraining.domain.enumeration.Feedback;
/**
 * Integration tests for the {@Link TestdiConconiResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class TestdiConconiResourceIT {

    private static final Integer DEFAULT_FC_MAX = 220;
    private static final Integer UPDATED_FC_MAX = 219;

    private static final Integer DEFAULT_SOGLIA_ANAEROBICA = 220;
    private static final Integer UPDATED_SOGLIA_ANAEROBICA = 219;

    private static final Float DEFAULT_VEL_SOGLIA = 100F;
    private static final Float UPDATED_VEL_SOGLIA = 99F;

    private static final Float DEFAULT_DURATA = 60F;
    private static final Float UPDATED_DURATA = 59F;

    private static final Feedback DEFAULT_COMMENTO = Feedback.SCARSO;
    private static final Feedback UPDATED_COMMENTO = Feedback.DISCRETO;

    private static final String DEFAULT_COND_CLIMATICHE = "AAAAAAAAAA";
    private static final String UPDATED_COND_CLIMATICHE = "BBBBBBBBBB";

    @Autowired
    private TestdiConconiRepository testdiConconiRepository;

    @Autowired
    private TestdiConconiService testdiConconiService;

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

    private MockMvc restTestdiConconiMockMvc;

    private TestdiConconi testdiConconi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestdiConconiResource testdiConconiResource = new TestdiConconiResource(testdiConconiService);
        this.restTestdiConconiMockMvc = MockMvcBuilders.standaloneSetup(testdiConconiResource)
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
    public static TestdiConconi createEntity(EntityManager em) {
        TestdiConconi testdiConconi = new TestdiConconi()
            .fcMax(DEFAULT_FC_MAX)
            .sogliaAnaerobica(DEFAULT_SOGLIA_ANAEROBICA)
            .velSoglia(DEFAULT_VEL_SOGLIA)
            .durata(DEFAULT_DURATA)
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
        testdiConconi.setCalciatore(calciatore);
        return testdiConconi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestdiConconi createUpdatedEntity(EntityManager em) {
        TestdiConconi testdiConconi = new TestdiConconi()
            .fcMax(UPDATED_FC_MAX)
            .sogliaAnaerobica(UPDATED_SOGLIA_ANAEROBICA)
            .velSoglia(UPDATED_VEL_SOGLIA)
            .durata(UPDATED_DURATA)
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
        testdiConconi.setCalciatore(calciatore);
        return testdiConconi;
    }

    @BeforeEach
    public void initTest() {
        testdiConconi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestdiConconi() throws Exception {
        int databaseSizeBeforeCreate = testdiConconiRepository.findAll().size();

        // Create the TestdiConconi
        restTestdiConconiMockMvc.perform(post("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isCreated());

        // Validate the TestdiConconi in the database
        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeCreate + 1);
        TestdiConconi testTestdiConconi = testdiConconiList.get(testdiConconiList.size() - 1);
        assertThat(testTestdiConconi.getFcMax()).isEqualTo(DEFAULT_FC_MAX);
        assertThat(testTestdiConconi.getSogliaAnaerobica()).isEqualTo(DEFAULT_SOGLIA_ANAEROBICA);
        assertThat(testTestdiConconi.getVelSoglia()).isEqualTo(DEFAULT_VEL_SOGLIA);
        assertThat(testTestdiConconi.getDurata()).isEqualTo(DEFAULT_DURATA);
        assertThat(testTestdiConconi.getCommento()).isEqualTo(DEFAULT_COMMENTO);
        assertThat(testTestdiConconi.getCondClimatiche()).isEqualTo(DEFAULT_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void createTestdiConconiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testdiConconiRepository.findAll().size();

        // Create the TestdiConconi with an existing ID
        testdiConconi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestdiConconiMockMvc.perform(post("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isBadRequest());

        // Validate the TestdiConconi in the database
        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFcMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = testdiConconiRepository.findAll().size();
        // set the field null
        testdiConconi.setFcMax(null);

        // Create the TestdiConconi, which fails.

        restTestdiConconiMockMvc.perform(post("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isBadRequest());

        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSogliaAnaerobicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = testdiConconiRepository.findAll().size();
        // set the field null
        testdiConconi.setSogliaAnaerobica(null);

        // Create the TestdiConconi, which fails.

        restTestdiConconiMockMvc.perform(post("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isBadRequest());

        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurataIsRequired() throws Exception {
        int databaseSizeBeforeTest = testdiConconiRepository.findAll().size();
        // set the field null
        testdiConconi.setDurata(null);

        // Create the TestdiConconi, which fails.

        restTestdiConconiMockMvc.perform(post("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isBadRequest());

        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestdiConconis() throws Exception {
        // Initialize the database
        testdiConconiRepository.saveAndFlush(testdiConconi);

        // Get all the testdiConconiList
        restTestdiConconiMockMvc.perform(get("/api/testdi-conconis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testdiConconi.getId().intValue())))
            .andExpect(jsonPath("$.[*].fcMax").value(hasItem(DEFAULT_FC_MAX)))
            .andExpect(jsonPath("$.[*].sogliaAnaerobica").value(hasItem(DEFAULT_SOGLIA_ANAEROBICA)))
            .andExpect(jsonPath("$.[*].velSoglia").value(hasItem(DEFAULT_VEL_SOGLIA.doubleValue())))
            .andExpect(jsonPath("$.[*].durata").value(hasItem(DEFAULT_DURATA.doubleValue())))
            .andExpect(jsonPath("$.[*].commento").value(hasItem(DEFAULT_COMMENTO.toString())))
            .andExpect(jsonPath("$.[*].condClimatiche").value(hasItem(DEFAULT_COND_CLIMATICHE.toString())));
    }
    
    @Test
    @Transactional
    public void getTestdiConconi() throws Exception {
        // Initialize the database
        testdiConconiRepository.saveAndFlush(testdiConconi);

        // Get the testdiConconi
        restTestdiConconiMockMvc.perform(get("/api/testdi-conconis/{id}", testdiConconi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testdiConconi.getId().intValue()))
            .andExpect(jsonPath("$.fcMax").value(DEFAULT_FC_MAX))
            .andExpect(jsonPath("$.sogliaAnaerobica").value(DEFAULT_SOGLIA_ANAEROBICA))
            .andExpect(jsonPath("$.velSoglia").value(DEFAULT_VEL_SOGLIA.doubleValue()))
            .andExpect(jsonPath("$.durata").value(DEFAULT_DURATA.doubleValue()))
            .andExpect(jsonPath("$.commento").value(DEFAULT_COMMENTO.toString()))
            .andExpect(jsonPath("$.condClimatiche").value(DEFAULT_COND_CLIMATICHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestdiConconi() throws Exception {
        // Get the testdiConconi
        restTestdiConconiMockMvc.perform(get("/api/testdi-conconis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestdiConconi() throws Exception {
        // Initialize the database
        testdiConconiService.save(testdiConconi);

        int databaseSizeBeforeUpdate = testdiConconiRepository.findAll().size();

        // Update the testdiConconi
        TestdiConconi updatedTestdiConconi = testdiConconiRepository.findById(testdiConconi.getId()).get();
        // Disconnect from session so that the updates on updatedTestdiConconi are not directly saved in db
        em.detach(updatedTestdiConconi);
        updatedTestdiConconi
            .fcMax(UPDATED_FC_MAX)
            .sogliaAnaerobica(UPDATED_SOGLIA_ANAEROBICA)
            .velSoglia(UPDATED_VEL_SOGLIA)
            .durata(UPDATED_DURATA)
            .commento(UPDATED_COMMENTO)
            .condClimatiche(UPDATED_COND_CLIMATICHE);

        restTestdiConconiMockMvc.perform(put("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestdiConconi)))
            .andExpect(status().isOk());

        // Validate the TestdiConconi in the database
        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeUpdate);
        TestdiConconi testTestdiConconi = testdiConconiList.get(testdiConconiList.size() - 1);
        assertThat(testTestdiConconi.getFcMax()).isEqualTo(UPDATED_FC_MAX);
        assertThat(testTestdiConconi.getSogliaAnaerobica()).isEqualTo(UPDATED_SOGLIA_ANAEROBICA);
        assertThat(testTestdiConconi.getVelSoglia()).isEqualTo(UPDATED_VEL_SOGLIA);
        assertThat(testTestdiConconi.getDurata()).isEqualTo(UPDATED_DURATA);
        assertThat(testTestdiConconi.getCommento()).isEqualTo(UPDATED_COMMENTO);
        assertThat(testTestdiConconi.getCondClimatiche()).isEqualTo(UPDATED_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void updateNonExistingTestdiConconi() throws Exception {
        int databaseSizeBeforeUpdate = testdiConconiRepository.findAll().size();

        // Create the TestdiConconi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestdiConconiMockMvc.perform(put("/api/testdi-conconis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testdiConconi)))
            .andExpect(status().isBadRequest());

        // Validate the TestdiConconi in the database
        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestdiConconi() throws Exception {
        // Initialize the database
        testdiConconiService.save(testdiConconi);

        int databaseSizeBeforeDelete = testdiConconiRepository.findAll().size();

        // Delete the testdiConconi
        restTestdiConconiMockMvc.perform(delete("/api/testdi-conconis/{id}", testdiConconi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TestdiConconi> testdiConconiList = testdiConconiRepository.findAll();
        assertThat(testdiConconiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestdiConconi.class);
        TestdiConconi testdiConconi1 = new TestdiConconi();
        testdiConconi1.setId(1L);
        TestdiConconi testdiConconi2 = new TestdiConconi();
        testdiConconi2.setId(testdiConconi1.getId());
        assertThat(testdiConconi1).isEqualTo(testdiConconi2);
        testdiConconi2.setId(2L);
        assertThat(testdiConconi1).isNotEqualTo(testdiConconi2);
        testdiConconi1.setId(null);
        assertThat(testdiConconi1).isNotEqualTo(testdiConconi2);
    }
}
