package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Prova1500m;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.Prova1500mRepository;
import it.unibg.nextraining.service.Prova1500mService;
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
 * Integration tests for the {@Link Prova1500mResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class Prova1500mResourceIT {

    private static final LocalDate DEFAULT_DATA_PROVA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROVA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TEMPO = 1F;
    private static final Float UPDATED_TEMPO = 2F;

    private static final Float DEFAULT_TEMPO_KM = 10F;
    private static final Float UPDATED_TEMPO_KM = 9F;

    private static final Feedback DEFAULT_COMMENTO = Feedback.SCARSO;
    private static final Feedback UPDATED_COMMENTO = Feedback.DISCRETO;

    private static final String DEFAULT_COND_CLIMATICHE = "AAAAAAAAAA";
    private static final String UPDATED_COND_CLIMATICHE = "BBBBBBBBBB";

    @Autowired
    private Prova1500mRepository prova1500mRepository;

    @Autowired
    private Prova1500mService prova1500mService;

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

    private MockMvc restProva1500mMockMvc;

    private Prova1500m prova1500m;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Prova1500mResource prova1500mResource = new Prova1500mResource(prova1500mService);
        this.restProva1500mMockMvc = MockMvcBuilders.standaloneSetup(prova1500mResource)
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
    public static Prova1500m createEntity(EntityManager em) {
        Prova1500m prova1500m = new Prova1500m()
            .dataProva(DEFAULT_DATA_PROVA)
            .tempo(DEFAULT_TEMPO)
            .tempoKm(DEFAULT_TEMPO_KM)
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
        prova1500m.setCalciatore(calciatore);
        return prova1500m;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prova1500m createUpdatedEntity(EntityManager em) {
        Prova1500m prova1500m = new Prova1500m()
            .dataProva(UPDATED_DATA_PROVA)
            .tempo(UPDATED_TEMPO)
            .tempoKm(UPDATED_TEMPO_KM)
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
        prova1500m.setCalciatore(calciatore);
        return prova1500m;
    }

    @BeforeEach
    public void initTest() {
        prova1500m = createEntity(em);
    }

    @Test
    @Transactional
    public void createProva1500m() throws Exception {
        int databaseSizeBeforeCreate = prova1500mRepository.findAll().size();

        // Create the Prova1500m
        restProva1500mMockMvc.perform(post("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova1500m)))
            .andExpect(status().isCreated());

        // Validate the Prova1500m in the database
        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeCreate + 1);
        Prova1500m testProva1500m = prova1500mList.get(prova1500mList.size() - 1);
        assertThat(testProva1500m.getDataProva()).isEqualTo(DEFAULT_DATA_PROVA);
        assertThat(testProva1500m.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testProva1500m.getTempoKm()).isEqualTo(DEFAULT_TEMPO_KM);
        assertThat(testProva1500m.getCommento()).isEqualTo(DEFAULT_COMMENTO);
        assertThat(testProva1500m.getCondClimatiche()).isEqualTo(DEFAULT_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void createProva1500mWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prova1500mRepository.findAll().size();

        // Create the Prova1500m with an existing ID
        prova1500m.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProva1500mMockMvc.perform(post("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova1500m)))
            .andExpect(status().isBadRequest());

        // Validate the Prova1500m in the database
        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataProvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = prova1500mRepository.findAll().size();
        // set the field null
        prova1500m.setDataProva(null);

        // Create the Prova1500m, which fails.

        restProva1500mMockMvc.perform(post("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova1500m)))
            .andExpect(status().isBadRequest());

        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempoIsRequired() throws Exception {
        int databaseSizeBeforeTest = prova1500mRepository.findAll().size();
        // set the field null
        prova1500m.setTempo(null);

        // Create the Prova1500m, which fails.

        restProva1500mMockMvc.perform(post("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova1500m)))
            .andExpect(status().isBadRequest());

        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProva1500ms() throws Exception {
        // Initialize the database
        prova1500mRepository.saveAndFlush(prova1500m);

        // Get all the prova1500mList
        restProva1500mMockMvc.perform(get("/api/prova-1500-ms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prova1500m.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProva").value(hasItem(DEFAULT_DATA_PROVA.toString())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO.doubleValue())))
            .andExpect(jsonPath("$.[*].tempoKm").value(hasItem(DEFAULT_TEMPO_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].commento").value(hasItem(DEFAULT_COMMENTO.toString())))
            .andExpect(jsonPath("$.[*].condClimatiche").value(hasItem(DEFAULT_COND_CLIMATICHE.toString())));
    }
    
    @Test
    @Transactional
    public void getProva1500m() throws Exception {
        // Initialize the database
        prova1500mRepository.saveAndFlush(prova1500m);

        // Get the prova1500m
        restProva1500mMockMvc.perform(get("/api/prova-1500-ms/{id}", prova1500m.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prova1500m.getId().intValue()))
            .andExpect(jsonPath("$.dataProva").value(DEFAULT_DATA_PROVA.toString()))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO.doubleValue()))
            .andExpect(jsonPath("$.tempoKm").value(DEFAULT_TEMPO_KM.doubleValue()))
            .andExpect(jsonPath("$.commento").value(DEFAULT_COMMENTO.toString()))
            .andExpect(jsonPath("$.condClimatiche").value(DEFAULT_COND_CLIMATICHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProva1500m() throws Exception {
        // Get the prova1500m
        restProva1500mMockMvc.perform(get("/api/prova-1500-ms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProva1500m() throws Exception {
        // Initialize the database
        prova1500mService.save(prova1500m);

        int databaseSizeBeforeUpdate = prova1500mRepository.findAll().size();

        // Update the prova1500m
        Prova1500m updatedProva1500m = prova1500mRepository.findById(prova1500m.getId()).get();
        // Disconnect from session so that the updates on updatedProva1500m are not directly saved in db
        em.detach(updatedProva1500m);
        updatedProva1500m
            .dataProva(UPDATED_DATA_PROVA)
            .tempo(UPDATED_TEMPO)
            .tempoKm(UPDATED_TEMPO_KM)
            .commento(UPDATED_COMMENTO)
            .condClimatiche(UPDATED_COND_CLIMATICHE);

        restProva1500mMockMvc.perform(put("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProva1500m)))
            .andExpect(status().isOk());

        // Validate the Prova1500m in the database
        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeUpdate);
        Prova1500m testProva1500m = prova1500mList.get(prova1500mList.size() - 1);
        assertThat(testProva1500m.getDataProva()).isEqualTo(UPDATED_DATA_PROVA);
        assertThat(testProva1500m.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testProva1500m.getTempoKm()).isEqualTo(UPDATED_TEMPO_KM);
        assertThat(testProva1500m.getCommento()).isEqualTo(UPDATED_COMMENTO);
        assertThat(testProva1500m.getCondClimatiche()).isEqualTo(UPDATED_COND_CLIMATICHE);
    }

    @Test
    @Transactional
    public void updateNonExistingProva1500m() throws Exception {
        int databaseSizeBeforeUpdate = prova1500mRepository.findAll().size();

        // Create the Prova1500m

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProva1500mMockMvc.perform(put("/api/prova-1500-ms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova1500m)))
            .andExpect(status().isBadRequest());

        // Validate the Prova1500m in the database
        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProva1500m() throws Exception {
        // Initialize the database
        prova1500mService.save(prova1500m);

        int databaseSizeBeforeDelete = prova1500mRepository.findAll().size();

        // Delete the prova1500m
        restProva1500mMockMvc.perform(delete("/api/prova-1500-ms/{id}", prova1500m.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Prova1500m> prova1500mList = prova1500mRepository.findAll();
        assertThat(prova1500mList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prova1500m.class);
        Prova1500m prova1500m1 = new Prova1500m();
        prova1500m1.setId(1L);
        Prova1500m prova1500m2 = new Prova1500m();
        prova1500m2.setId(prova1500m1.getId());
        assertThat(prova1500m1).isEqualTo(prova1500m2);
        prova1500m2.setId(2L);
        assertThat(prova1500m1).isNotEqualTo(prova1500m2);
        prova1500m1.setId(null);
        assertThat(prova1500m1).isNotEqualTo(prova1500m2);
    }
}
