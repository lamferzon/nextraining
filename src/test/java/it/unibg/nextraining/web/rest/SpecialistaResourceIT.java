package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Specialista;
import it.unibg.nextraining.repository.SpecialistaRepository;
import it.unibg.nextraining.service.SpecialistaService;
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

/**
 * Integration tests for the {@Link SpecialistaResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class SpecialistaResourceIT {

    private static final String DEFAULT_COD_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_COD_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALIZZAZIONE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIZZAZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_NUM_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_INDIRIZZO_STUDIO = "AAAAAAAAAA";
    private static final String UPDATED_INDIRIZZO_STUDIO = "BBBBBBBBBB";

    private static final String DEFAULT_PAESE_STUDIO = "AAAAAAAAAA";
    private static final String UPDATED_PAESE_STUDIO = "BBBBBBBBBB";

    @Autowired
    private SpecialistaRepository specialistaRepository;

    @Autowired
    private SpecialistaService specialistaService;

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

    private MockMvc restSpecialistaMockMvc;

    private Specialista specialista;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialistaResource specialistaResource = new SpecialistaResource(specialistaService);
        this.restSpecialistaMockMvc = MockMvcBuilders.standaloneSetup(specialistaResource)
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
    public static Specialista createEntity(EntityManager em) {
        Specialista specialista = new Specialista()
            .codFiscale(DEFAULT_COD_FISCALE)
            .cognome(DEFAULT_COGNOME)
            .nome(DEFAULT_NOME)
            .specializzazione(DEFAULT_SPECIALIZZAZIONE)
            .numTelefono(DEFAULT_NUM_TELEFONO)
            .email(DEFAULT_EMAIL)
            .indirizzoStudio(DEFAULT_INDIRIZZO_STUDIO)
            .paeseStudio(DEFAULT_PAESE_STUDIO);
        return specialista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialista createUpdatedEntity(EntityManager em) {
        Specialista specialista = new Specialista()
            .codFiscale(UPDATED_COD_FISCALE)
            .cognome(UPDATED_COGNOME)
            .nome(UPDATED_NOME)
            .specializzazione(UPDATED_SPECIALIZZAZIONE)
            .numTelefono(UPDATED_NUM_TELEFONO)
            .email(UPDATED_EMAIL)
            .indirizzoStudio(UPDATED_INDIRIZZO_STUDIO)
            .paeseStudio(UPDATED_PAESE_STUDIO);
        return specialista;
    }

    @BeforeEach
    public void initTest() {
        specialista = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialista() throws Exception {
        int databaseSizeBeforeCreate = specialistaRepository.findAll().size();

        // Create the Specialista
        restSpecialistaMockMvc.perform(post("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isCreated());

        // Validate the Specialista in the database
        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeCreate + 1);
        Specialista testSpecialista = specialistaList.get(specialistaList.size() - 1);
        assertThat(testSpecialista.getCodFiscale()).isEqualTo(DEFAULT_COD_FISCALE);
        assertThat(testSpecialista.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testSpecialista.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSpecialista.getSpecializzazione()).isEqualTo(DEFAULT_SPECIALIZZAZIONE);
        assertThat(testSpecialista.getNumTelefono()).isEqualTo(DEFAULT_NUM_TELEFONO);
        assertThat(testSpecialista.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSpecialista.getIndirizzoStudio()).isEqualTo(DEFAULT_INDIRIZZO_STUDIO);
        assertThat(testSpecialista.getPaeseStudio()).isEqualTo(DEFAULT_PAESE_STUDIO);
    }

    @Test
    @Transactional
    public void createSpecialistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialistaRepository.findAll().size();

        // Create the Specialista with an existing ID
        specialista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialistaMockMvc.perform(post("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isBadRequest());

        // Validate the Specialista in the database
        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialistaRepository.findAll().size();
        // set the field null
        specialista.setCognome(null);

        // Create the Specialista, which fails.

        restSpecialistaMockMvc.perform(post("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isBadRequest());

        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialistaRepository.findAll().size();
        // set the field null
        specialista.setNome(null);

        // Create the Specialista, which fails.

        restSpecialistaMockMvc.perform(post("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isBadRequest());

        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialistaRepository.findAll().size();
        // set the field null
        specialista.setNumTelefono(null);

        // Create the Specialista, which fails.

        restSpecialistaMockMvc.perform(post("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isBadRequest());

        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialistas() throws Exception {
        // Initialize the database
        specialistaRepository.saveAndFlush(specialista);

        // Get all the specialistaList
        restSpecialistaMockMvc.perform(get("/api/specialistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialista.getId().intValue())))
            .andExpect(jsonPath("$.[*].codFiscale").value(hasItem(DEFAULT_COD_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].specializzazione").value(hasItem(DEFAULT_SPECIALIZZAZIONE.toString())))
            .andExpect(jsonPath("$.[*].numTelefono").value(hasItem(DEFAULT_NUM_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].indirizzoStudio").value(hasItem(DEFAULT_INDIRIZZO_STUDIO.toString())))
            .andExpect(jsonPath("$.[*].paeseStudio").value(hasItem(DEFAULT_PAESE_STUDIO.toString())));
    }
    
    @Test
    @Transactional
    public void getSpecialista() throws Exception {
        // Initialize the database
        specialistaRepository.saveAndFlush(specialista);

        // Get the specialista
        restSpecialistaMockMvc.perform(get("/api/specialistas/{id}", specialista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialista.getId().intValue()))
            .andExpect(jsonPath("$.codFiscale").value(DEFAULT_COD_FISCALE.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.specializzazione").value(DEFAULT_SPECIALIZZAZIONE.toString()))
            .andExpect(jsonPath("$.numTelefono").value(DEFAULT_NUM_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.indirizzoStudio").value(DEFAULT_INDIRIZZO_STUDIO.toString()))
            .andExpect(jsonPath("$.paeseStudio").value(DEFAULT_PAESE_STUDIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialista() throws Exception {
        // Get the specialista
        restSpecialistaMockMvc.perform(get("/api/specialistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialista() throws Exception {
        // Initialize the database
        specialistaService.save(specialista);

        int databaseSizeBeforeUpdate = specialistaRepository.findAll().size();

        // Update the specialista
        Specialista updatedSpecialista = specialistaRepository.findById(specialista.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialista are not directly saved in db
        em.detach(updatedSpecialista);
        updatedSpecialista
            .codFiscale(UPDATED_COD_FISCALE)
            .cognome(UPDATED_COGNOME)
            .nome(UPDATED_NOME)
            .specializzazione(UPDATED_SPECIALIZZAZIONE)
            .numTelefono(UPDATED_NUM_TELEFONO)
            .email(UPDATED_EMAIL)
            .indirizzoStudio(UPDATED_INDIRIZZO_STUDIO)
            .paeseStudio(UPDATED_PAESE_STUDIO);

        restSpecialistaMockMvc.perform(put("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialista)))
            .andExpect(status().isOk());

        // Validate the Specialista in the database
        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeUpdate);
        Specialista testSpecialista = specialistaList.get(specialistaList.size() - 1);
        assertThat(testSpecialista.getCodFiscale()).isEqualTo(UPDATED_COD_FISCALE);
        assertThat(testSpecialista.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testSpecialista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSpecialista.getSpecializzazione()).isEqualTo(UPDATED_SPECIALIZZAZIONE);
        assertThat(testSpecialista.getNumTelefono()).isEqualTo(UPDATED_NUM_TELEFONO);
        assertThat(testSpecialista.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSpecialista.getIndirizzoStudio()).isEqualTo(UPDATED_INDIRIZZO_STUDIO);
        assertThat(testSpecialista.getPaeseStudio()).isEqualTo(UPDATED_PAESE_STUDIO);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialista() throws Exception {
        int databaseSizeBeforeUpdate = specialistaRepository.findAll().size();

        // Create the Specialista

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialistaMockMvc.perform(put("/api/specialistas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialista)))
            .andExpect(status().isBadRequest());

        // Validate the Specialista in the database
        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialista() throws Exception {
        // Initialize the database
        specialistaService.save(specialista);

        int databaseSizeBeforeDelete = specialistaRepository.findAll().size();

        // Delete the specialista
        restSpecialistaMockMvc.perform(delete("/api/specialistas/{id}", specialista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Specialista> specialistaList = specialistaRepository.findAll();
        assertThat(specialistaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialista.class);
        Specialista specialista1 = new Specialista();
        specialista1.setId(1L);
        Specialista specialista2 = new Specialista();
        specialista2.setId(specialista1.getId());
        assertThat(specialista1).isEqualTo(specialista2);
        specialista2.setId(2L);
        assertThat(specialista1).isNotEqualTo(specialista2);
        specialista1.setId(null);
        assertThat(specialista1).isNotEqualTo(specialista2);
    }
}
