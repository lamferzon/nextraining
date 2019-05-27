package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.CalciatoreRepository;
import it.unibg.nextraining.service.CalciatoreService;
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

import it.unibg.nextraining.domain.enumeration.Reparto;
import it.unibg.nextraining.domain.enumeration.Ruolo;
import it.unibg.nextraining.domain.enumeration.Selettore;
/**
 * Integration tests for the {@Link CalciatoreResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class CalciatoreResourceIT {

    private static final String DEFAULT_COD_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_COD_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCITA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUM_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_NUM_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Reparto DEFAULT_REPARTO = Reparto.DIFESA;
    private static final Reparto UPDATED_REPARTO = Reparto.CENTROCAMPO;

    private static final Ruolo DEFAULT_RUOLO = Ruolo.POR;
    private static final Ruolo UPDATED_RUOLO = Ruolo.LIB;

    private static final Selettore DEFAULT_SELETTORE = Selettore.DIFENSORE;
    private static final Selettore UPDATED_SELETTORE = Selettore.ATTACCANTE;

    @Autowired
    private CalciatoreRepository calciatoreRepository;

    @Autowired
    private CalciatoreService calciatoreService;

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

    private MockMvc restCalciatoreMockMvc;

    private Calciatore calciatore;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalciatoreResource calciatoreResource = new CalciatoreResource(calciatoreService);
        this.restCalciatoreMockMvc = MockMvcBuilders.standaloneSetup(calciatoreResource)
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
    public static Calciatore createEntity(EntityManager em) {
        Calciatore calciatore = new Calciatore()
            .codFiscale(DEFAULT_COD_FISCALE)
            .cognome(DEFAULT_COGNOME)
            .nome(DEFAULT_NOME)
            .dataNascita(DEFAULT_DATA_NASCITA)
            .numTelefono(DEFAULT_NUM_TELEFONO)
            .email(DEFAULT_EMAIL)
            .reparto(DEFAULT_REPARTO)
            .ruolo(DEFAULT_RUOLO)
            .selettore(DEFAULT_SELETTORE);
        return calciatore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calciatore createUpdatedEntity(EntityManager em) {
        Calciatore calciatore = new Calciatore()
            .codFiscale(UPDATED_COD_FISCALE)
            .cognome(UPDATED_COGNOME)
            .nome(UPDATED_NOME)
            .dataNascita(UPDATED_DATA_NASCITA)
            .numTelefono(UPDATED_NUM_TELEFONO)
            .email(UPDATED_EMAIL)
            .reparto(UPDATED_REPARTO)
            .ruolo(UPDATED_RUOLO)
            .selettore(UPDATED_SELETTORE);
        return calciatore;
    }

    @BeforeEach
    public void initTest() {
        calciatore = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalciatore() throws Exception {
        int databaseSizeBeforeCreate = calciatoreRepository.findAll().size();

        // Create the Calciatore
        restCalciatoreMockMvc.perform(post("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isCreated());

        // Validate the Calciatore in the database
        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeCreate + 1);
        Calciatore testCalciatore = calciatoreList.get(calciatoreList.size() - 1);
        assertThat(testCalciatore.getCodFiscale()).isEqualTo(DEFAULT_COD_FISCALE);
        assertThat(testCalciatore.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testCalciatore.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCalciatore.getDataNascita()).isEqualTo(DEFAULT_DATA_NASCITA);
        assertThat(testCalciatore.getNumTelefono()).isEqualTo(DEFAULT_NUM_TELEFONO);
        assertThat(testCalciatore.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCalciatore.getReparto()).isEqualTo(DEFAULT_REPARTO);
        assertThat(testCalciatore.getRuolo()).isEqualTo(DEFAULT_RUOLO);
        assertThat(testCalciatore.getSelettore()).isEqualTo(DEFAULT_SELETTORE);
    }

    @Test
    @Transactional
    public void createCalciatoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calciatoreRepository.findAll().size();

        // Create the Calciatore with an existing ID
        calciatore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalciatoreMockMvc.perform(post("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isBadRequest());

        // Validate the Calciatore in the database
        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calciatoreRepository.findAll().size();
        // set the field null
        calciatore.setCognome(null);

        // Create the Calciatore, which fails.

        restCalciatoreMockMvc.perform(post("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isBadRequest());

        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calciatoreRepository.findAll().size();
        // set the field null
        calciatore.setNome(null);

        // Create the Calciatore, which fails.

        restCalciatoreMockMvc.perform(post("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isBadRequest());

        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = calciatoreRepository.findAll().size();
        // set the field null
        calciatore.setNumTelefono(null);

        // Create the Calciatore, which fails.

        restCalciatoreMockMvc.perform(post("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isBadRequest());

        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalciatores() throws Exception {
        // Initialize the database
        calciatoreRepository.saveAndFlush(calciatore);

        // Get all the calciatoreList
        restCalciatoreMockMvc.perform(get("/api/calciatores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calciatore.getId().intValue())))
            .andExpect(jsonPath("$.[*].codFiscale").value(hasItem(DEFAULT_COD_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].dataNascita").value(hasItem(DEFAULT_DATA_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].numTelefono").value(hasItem(DEFAULT_NUM_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].reparto").value(hasItem(DEFAULT_REPARTO.toString())))
            .andExpect(jsonPath("$.[*].ruolo").value(hasItem(DEFAULT_RUOLO.toString())))
            .andExpect(jsonPath("$.[*].selettore").value(hasItem(DEFAULT_SELETTORE.toString())));
    }
    
    @Test
    @Transactional
    public void getCalciatore() throws Exception {
        // Initialize the database
        calciatoreRepository.saveAndFlush(calciatore);

        // Get the calciatore
        restCalciatoreMockMvc.perform(get("/api/calciatores/{id}", calciatore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calciatore.getId().intValue()))
            .andExpect(jsonPath("$.codFiscale").value(DEFAULT_COD_FISCALE.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.dataNascita").value(DEFAULT_DATA_NASCITA.toString()))
            .andExpect(jsonPath("$.numTelefono").value(DEFAULT_NUM_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.reparto").value(DEFAULT_REPARTO.toString()))
            .andExpect(jsonPath("$.ruolo").value(DEFAULT_RUOLO.toString()))
            .andExpect(jsonPath("$.selettore").value(DEFAULT_SELETTORE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalciatore() throws Exception {
        // Get the calciatore
        restCalciatoreMockMvc.perform(get("/api/calciatores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalciatore() throws Exception {
        // Initialize the database
        calciatoreService.save(calciatore);

        int databaseSizeBeforeUpdate = calciatoreRepository.findAll().size();

        // Update the calciatore
        Calciatore updatedCalciatore = calciatoreRepository.findById(calciatore.getId()).get();
        // Disconnect from session so that the updates on updatedCalciatore are not directly saved in db
        em.detach(updatedCalciatore);
        updatedCalciatore
            .codFiscale(UPDATED_COD_FISCALE)
            .cognome(UPDATED_COGNOME)
            .nome(UPDATED_NOME)
            .dataNascita(UPDATED_DATA_NASCITA)
            .numTelefono(UPDATED_NUM_TELEFONO)
            .email(UPDATED_EMAIL)
            .reparto(UPDATED_REPARTO)
            .ruolo(UPDATED_RUOLO)
            .selettore(UPDATED_SELETTORE);

        restCalciatoreMockMvc.perform(put("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalciatore)))
            .andExpect(status().isOk());

        // Validate the Calciatore in the database
        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeUpdate);
        Calciatore testCalciatore = calciatoreList.get(calciatoreList.size() - 1);
        assertThat(testCalciatore.getCodFiscale()).isEqualTo(UPDATED_COD_FISCALE);
        assertThat(testCalciatore.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testCalciatore.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCalciatore.getDataNascita()).isEqualTo(UPDATED_DATA_NASCITA);
        assertThat(testCalciatore.getNumTelefono()).isEqualTo(UPDATED_NUM_TELEFONO);
        assertThat(testCalciatore.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCalciatore.getReparto()).isEqualTo(UPDATED_REPARTO);
        assertThat(testCalciatore.getRuolo()).isEqualTo(UPDATED_RUOLO);
        assertThat(testCalciatore.getSelettore()).isEqualTo(UPDATED_SELETTORE);
    }

    @Test
    @Transactional
    public void updateNonExistingCalciatore() throws Exception {
        int databaseSizeBeforeUpdate = calciatoreRepository.findAll().size();

        // Create the Calciatore

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalciatoreMockMvc.perform(put("/api/calciatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calciatore)))
            .andExpect(status().isBadRequest());

        // Validate the Calciatore in the database
        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalciatore() throws Exception {
        // Initialize the database
        calciatoreService.save(calciatore);

        int databaseSizeBeforeDelete = calciatoreRepository.findAll().size();

        // Delete the calciatore
        restCalciatoreMockMvc.perform(delete("/api/calciatores/{id}", calciatore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Calciatore> calciatoreList = calciatoreRepository.findAll();
        assertThat(calciatoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calciatore.class);
        Calciatore calciatore1 = new Calciatore();
        calciatore1.setId(1L);
        Calciatore calciatore2 = new Calciatore();
        calciatore2.setId(calciatore1.getId());
        assertThat(calciatore1).isEqualTo(calciatore2);
        calciatore2.setId(2L);
        assertThat(calciatore1).isNotEqualTo(calciatore2);
        calciatore1.setId(null);
        assertThat(calciatore1).isNotEqualTo(calciatore2);
    }
}
