package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Prova70m;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.Prova70mRepository;
import it.unibg.nextraining.service.Prova70mService;
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
 * Integration tests for the {@Link Prova70mResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class Prova70mResourceIT {

    private static final LocalDate DEFAULT_DATA_PROVA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROVA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TEMPO = 1F;
    private static final Float UPDATED_TEMPO = 2F;

    private static final Boolean DEFAULT_PARTENZA_BLOCCHI = false;
    private static final Boolean UPDATED_PARTENZA_BLOCCHI = true;

    private static final Float DEFAULT_VEL_MAX = 100F;
    private static final Float UPDATED_VEL_MAX = 99F;

    private static final Feedback DEFAULT_COMMENTO = Feedback.SCARSO;
    private static final Feedback UPDATED_COMMENTO = Feedback.DISCRETO;

    private static final String DEFAULT_COND_CLIMATICHE = "AAAAAAAAAA";
    private static final String UPDATED_COND_CLIMATICHE = "BBBBBBBBBB";

    @Autowired
    private Prova70mRepository prova70mRepository;

    @Autowired
    private Prova70mService prova70mService;

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

    private MockMvc restProva70mMockMvc;

    private Prova70m prova70m;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Prova70mResource prova70mResource = new Prova70mResource(prova70mService);
        this.restProva70mMockMvc = MockMvcBuilders.standaloneSetup(prova70mResource)
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
    public static Prova70m createEntity(EntityManager em) {
        Prova70m prova70m = new Prova70m()
            .dataProva(DEFAULT_DATA_PROVA)
            .tempo(DEFAULT_TEMPO)
            .partenzaBlocchi(DEFAULT_PARTENZA_BLOCCHI)
            .velMax(DEFAULT_VEL_MAX)
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
        prova70m.setCalciatore(calciatore);
        return prova70m;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prova70m createUpdatedEntity(EntityManager em) {
        Prova70m prova70m = new Prova70m()
            .dataProva(UPDATED_DATA_PROVA)
            .tempo(UPDATED_TEMPO)
            .partenzaBlocchi(UPDATED_PARTENZA_BLOCCHI)
            .velMax(UPDATED_VEL_MAX)
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
        prova70m.setCalciatore(calciatore);
        return prova70m;
    }

    @BeforeEach
    public void initTest() {
        prova70m = createEntity(em);
    }

    @Test
    @Transactional
    public void createProva70m() throws Exception {
        int databaseSizeBeforeCreate = prova70mRepository.findAll().size();

        // Create the Prova70m
        restProva70mMockMvc.perform(post("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova70m)))
            .andExpect(status().isCreated());

        // Validate the Prova70m in the database
        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeCreate + 1);
        Prova70m testProva70m = prova70mList.get(prova70mList.size() - 1);
        assertThat(testProva70m.getDataProva()).isEqualTo(DEFAULT_DATA_PROVA);
        assertThat(testProva70m.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testProva70m.isPartenzaBlocchi()).isEqualTo(DEFAULT_PARTENZA_BLOCCHI);
        assertThat(testProva70m.getVelMax()).isEqualTo(DEFAULT_VEL_MAX);
        assertThat(testProva70m.getCommento()).isEqualTo(DEFAULT_COMMENTO);
        assertThat(testProva70m.getCondClimatiche()).isEqualTo(DEFAULT_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void createProva70mWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prova70mRepository.findAll().size();

        // Create the Prova70m with an existing ID
        prova70m.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProva70mMockMvc.perform(post("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova70m)))
            .andExpect(status().isBadRequest());

        // Validate the Prova70m in the database
        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataProvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = prova70mRepository.findAll().size();
        // set the field null
        prova70m.setDataProva(null);

        // Create the Prova70m, which fails.

        restProva70mMockMvc.perform(post("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova70m)))
            .andExpect(status().isBadRequest());

        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempoIsRequired() throws Exception {
        int databaseSizeBeforeTest = prova70mRepository.findAll().size();
        // set the field null
        prova70m.setTempo(null);

        // Create the Prova70m, which fails.

        restProva70mMockMvc.perform(post("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova70m)))
            .andExpect(status().isBadRequest());

        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProva70ms() throws Exception {
        // Initialize the database
        prova70mRepository.saveAndFlush(prova70m);

        // Get all the prova70mList
        restProva70mMockMvc.perform(get("/api/prova-70-ms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prova70m.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProva").value(hasItem(DEFAULT_DATA_PROVA.toString())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO.doubleValue())))
            .andExpect(jsonPath("$.[*].partenzaBlocchi").value(hasItem(DEFAULT_PARTENZA_BLOCCHI.booleanValue())))
            .andExpect(jsonPath("$.[*].velMax").value(hasItem(DEFAULT_VEL_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].commento").value(hasItem(DEFAULT_COMMENTO.toString())))
            .andExpect(jsonPath("$.[*].condClimatiche").value(hasItem(DEFAULT_COND_CLIMATICHE.toString())));
    }
    
    @Test
    @Transactional
    public void getProva70m() throws Exception {
        // Initialize the database
        prova70mRepository.saveAndFlush(prova70m);

        // Get the prova70m
        restProva70mMockMvc.perform(get("/api/prova-70-ms/{id}", prova70m.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prova70m.getId().intValue()))
            .andExpect(jsonPath("$.dataProva").value(DEFAULT_DATA_PROVA.toString()))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO.doubleValue()))
            .andExpect(jsonPath("$.partenzaBlocchi").value(DEFAULT_PARTENZA_BLOCCHI.booleanValue()))
            .andExpect(jsonPath("$.velMax").value(DEFAULT_VEL_MAX.doubleValue()))
            .andExpect(jsonPath("$.commento").value(DEFAULT_COMMENTO.toString()))
            .andExpect(jsonPath("$.condClimatiche").value(DEFAULT_COND_CLIMATICHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProva70m() throws Exception {
        // Get the prova70m
        restProva70mMockMvc.perform(get("/api/prova-70-ms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProva70m() throws Exception {
        // Initialize the database
        prova70mService.save(prova70m);

        int databaseSizeBeforeUpdate = prova70mRepository.findAll().size();

        // Update the prova70m
        Prova70m updatedProva70m = prova70mRepository.findById(prova70m.getId()).get();
        // Disconnect from session so that the updates on updatedProva70m are not directly saved in db
        em.detach(updatedProva70m);
        updatedProva70m
            .dataProva(UPDATED_DATA_PROVA)
            .tempo(UPDATED_TEMPO)
            .partenzaBlocchi(UPDATED_PARTENZA_BLOCCHI)
            .velMax(UPDATED_VEL_MAX)
            .commento(UPDATED_COMMENTO)
            .condClimatiche(UPDATED_COND_CLIMATICHE);

        restProva70mMockMvc.perform(put("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProva70m)))
            .andExpect(status().isOk());

        // Validate the Prova70m in the database
        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeUpdate);
        Prova70m testProva70m = prova70mList.get(prova70mList.size() - 1);
        assertThat(testProva70m.getDataProva()).isEqualTo(UPDATED_DATA_PROVA);
        assertThat(testProva70m.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testProva70m.isPartenzaBlocchi()).isEqualTo(UPDATED_PARTENZA_BLOCCHI);
        assertThat(testProva70m.getVelMax()).isEqualTo(UPDATED_VEL_MAX);
        assertThat(testProva70m.getCommento()).isEqualTo(UPDATED_COMMENTO);
        assertThat(testProva70m.getCondClimatiche()).isEqualTo(UPDATED_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void updateNonExistingProva70m() throws Exception {
        int databaseSizeBeforeUpdate = prova70mRepository.findAll().size();

        // Create the Prova70m

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProva70mMockMvc.perform(put("/api/prova-70-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova70m)))
            .andExpect(status().isBadRequest());

        // Validate the Prova70m in the database
        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProva70m() throws Exception {
        // Initialize the database
        prova70mService.save(prova70m);

        int databaseSizeBeforeDelete = prova70mRepository.findAll().size();

        // Delete the prova70m
        restProva70mMockMvc.perform(delete("/api/prova-70-ms/{id}", prova70m.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Prova70m> prova70mList = prova70mRepository.findAll();
        assertThat(prova70mList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prova70m.class);
        Prova70m prova70m1 = new Prova70m();
        prova70m1.setId(1L);
        Prova70m prova70m2 = new Prova70m();
        prova70m2.setId(prova70m1.getId());
        assertThat(prova70m1).isEqualTo(prova70m2);
        prova70m2.setId(2L);
        assertThat(prova70m1).isNotEqualTo(prova70m2);
        prova70m1.setId(null);
        assertThat(prova70m1).isNotEqualTo(prova70m2);
    }
}
