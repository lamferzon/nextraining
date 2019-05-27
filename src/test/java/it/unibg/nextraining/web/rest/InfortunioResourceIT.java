package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Infortunio;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.InfortunioRepository;
import it.unibg.nextraining.service.InfortunioService;
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

import it.unibg.nextraining.domain.enumeration.Gravita;
/**
 * Integration tests for the {@Link InfortunioResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class InfortunioResourceIT {

    private static final LocalDate DEFAULT_DATA_INIZIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INIZIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FINE = LocalDate.now(ZoneId.systemDefault());

    private static final Gravita DEFAULT_GRAVITA = Gravita.UNO;
    private static final Gravita UPDATED_GRAVITA = Gravita.DUE;

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private InfortunioRepository infortunioRepository;

    @Autowired
    private InfortunioService infortunioService;

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

    private MockMvc restInfortunioMockMvc;

    private Infortunio infortunio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfortunioResource infortunioResource = new InfortunioResource(infortunioService);
        this.restInfortunioMockMvc = MockMvcBuilders.standaloneSetup(infortunioResource)
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
    public static Infortunio createEntity(EntityManager em) {
        Infortunio infortunio = new Infortunio()
            .dataInizio(DEFAULT_DATA_INIZIO)
            .dataFine(DEFAULT_DATA_FINE)
            .gravita(DEFAULT_GRAVITA)
            .descrizione(DEFAULT_DESCRIZIONE);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        infortunio.setCalciatore(calciatore);
        return infortunio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infortunio createUpdatedEntity(EntityManager em) {
        Infortunio infortunio = new Infortunio()
            .dataInizio(UPDATED_DATA_INIZIO)
            .dataFine(UPDATED_DATA_FINE)
            .gravita(UPDATED_GRAVITA)
            .descrizione(UPDATED_DESCRIZIONE);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createUpdatedEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        infortunio.setCalciatore(calciatore);
        return infortunio;
    }

    @BeforeEach
    public void initTest() {
        infortunio = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfortunio() throws Exception {
        int databaseSizeBeforeCreate = infortunioRepository.findAll().size();

        // Create the Infortunio
        restInfortunioMockMvc.perform(post("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infortunio)))
            .andExpect(status().isCreated());

        // Validate the Infortunio in the database
        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeCreate + 1);
        Infortunio testInfortunio = infortunioList.get(infortunioList.size() - 1);
        assertThat(testInfortunio.getDataInizio()).isEqualTo(DEFAULT_DATA_INIZIO);
        assertThat(testInfortunio.getDataFine()).isEqualTo(DEFAULT_DATA_FINE);
        assertThat(testInfortunio.getGravita()).isEqualTo(DEFAULT_GRAVITA);
        assertThat(testInfortunio.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createInfortunioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infortunioRepository.findAll().size();

        // Create the Infortunio with an existing ID
        infortunio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfortunioMockMvc.perform(post("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infortunio)))
            .andExpect(status().isBadRequest());

        // Validate the Infortunio in the database
        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = infortunioRepository.findAll().size();
        // set the field null
        infortunio.setDataInizio(null);

        // Create the Infortunio, which fails.

        restInfortunioMockMvc.perform(post("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infortunio)))
            .andExpect(status().isBadRequest());

        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGravitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = infortunioRepository.findAll().size();
        // set the field null
        infortunio.setGravita(null);

        // Create the Infortunio, which fails.

        restInfortunioMockMvc.perform(post("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infortunio)))
            .andExpect(status().isBadRequest());

        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInfortunios() throws Exception {
        // Initialize the database
        infortunioRepository.saveAndFlush(infortunio);

        // Get all the infortunioList
        restInfortunioMockMvc.perform(get("/api/infortunios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infortunio.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInizio").value(hasItem(DEFAULT_DATA_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].dataFine").value(hasItem(DEFAULT_DATA_FINE.toString())))
            .andExpect(jsonPath("$.[*].gravita").value(hasItem(DEFAULT_GRAVITA.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }
    
    @Test
    @Transactional
    public void getInfortunio() throws Exception {
        // Initialize the database
        infortunioRepository.saveAndFlush(infortunio);

        // Get the infortunio
        restInfortunioMockMvc.perform(get("/api/infortunios/{id}", infortunio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infortunio.getId().intValue()))
            .andExpect(jsonPath("$.dataInizio").value(DEFAULT_DATA_INIZIO.toString()))
            .andExpect(jsonPath("$.dataFine").value(DEFAULT_DATA_FINE.toString()))
            .andExpect(jsonPath("$.gravita").value(DEFAULT_GRAVITA.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInfortunio() throws Exception {
        // Get the infortunio
        restInfortunioMockMvc.perform(get("/api/infortunios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfortunio() throws Exception {
        // Initialize the database
        infortunioService.save(infortunio);

        int databaseSizeBeforeUpdate = infortunioRepository.findAll().size();

        // Update the infortunio
        Infortunio updatedInfortunio = infortunioRepository.findById(infortunio.getId()).get();
        // Disconnect from session so that the updates on updatedInfortunio are not directly saved in db
        em.detach(updatedInfortunio);
        updatedInfortunio
            .dataInizio(UPDATED_DATA_INIZIO)
            .dataFine(UPDATED_DATA_FINE)
            .gravita(UPDATED_GRAVITA)
            .descrizione(UPDATED_DESCRIZIONE);

        restInfortunioMockMvc.perform(put("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfortunio)))
            .andExpect(status().isOk());

        // Validate the Infortunio in the database
        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeUpdate);
        Infortunio testInfortunio = infortunioList.get(infortunioList.size() - 1);
        assertThat(testInfortunio.getDataInizio()).isEqualTo(UPDATED_DATA_INIZIO);
        assertThat(testInfortunio.getDataFine()).isEqualTo(UPDATED_DATA_FINE);
        assertThat(testInfortunio.getGravita()).isEqualTo(UPDATED_GRAVITA);
        assertThat(testInfortunio.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingInfortunio() throws Exception {
        int databaseSizeBeforeUpdate = infortunioRepository.findAll().size();

        // Create the Infortunio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfortunioMockMvc.perform(put("/api/infortunios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infortunio)))
            .andExpect(status().isBadRequest());

        // Validate the Infortunio in the database
        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfortunio() throws Exception {
        // Initialize the database
        infortunioService.save(infortunio);

        int databaseSizeBeforeDelete = infortunioRepository.findAll().size();

        // Delete the infortunio
        restInfortunioMockMvc.perform(delete("/api/infortunios/{id}", infortunio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Infortunio> infortunioList = infortunioRepository.findAll();
        assertThat(infortunioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infortunio.class);
        Infortunio infortunio1 = new Infortunio();
        infortunio1.setId(1L);
        Infortunio infortunio2 = new Infortunio();
        infortunio2.setId(infortunio1.getId());
        assertThat(infortunio1).isEqualTo(infortunio2);
        infortunio2.setId(2L);
        assertThat(infortunio1).isNotEqualTo(infortunio2);
        infortunio1.setId(null);
        assertThat(infortunio1).isNotEqualTo(infortunio2);
    }
}
