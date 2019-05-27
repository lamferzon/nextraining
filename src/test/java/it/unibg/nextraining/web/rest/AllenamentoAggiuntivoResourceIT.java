package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.AllenamentoAggiuntivo;
import it.unibg.nextraining.repository.AllenamentoAggiuntivoRepository;
import it.unibg.nextraining.service.AllenamentoAggiuntivoService;
import it.unibg.nextraining.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static it.unibg.nextraining.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.unibg.nextraining.domain.enumeration.Natura;
/**
 * Integration tests for the {@Link AllenamentoAggiuntivoResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class AllenamentoAggiuntivoResourceIT {

    private static final String DEFAULT_SPORT = "AAAAAAAAAA";
    private static final String UPDATED_SPORT = "BBBBBBBBBB";

    private static final Natura DEFAULT_NATURA = Natura.AEROBICO;
    private static final Natura UPDATED_NATURA = Natura.ANAEROBICO;

    private static final String DEFAULT_LAVORO = "AAAAAAAAAA";
    private static final String UPDATED_LAVORO = "BBBBBBBBBB";

    private static final Float DEFAULT_DURATA = 1F;
    private static final Float UPDATED_DURATA = 2F;

    @Autowired
    private AllenamentoAggiuntivoRepository allenamentoAggiuntivoRepository;

    @Mock
    private AllenamentoAggiuntivoRepository allenamentoAggiuntivoRepositoryMock;

    @Mock
    private AllenamentoAggiuntivoService allenamentoAggiuntivoServiceMock;

    @Autowired
    private AllenamentoAggiuntivoService allenamentoAggiuntivoService;

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

    private MockMvc restAllenamentoAggiuntivoMockMvc;

    private AllenamentoAggiuntivo allenamentoAggiuntivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllenamentoAggiuntivoResource allenamentoAggiuntivoResource = new AllenamentoAggiuntivoResource(allenamentoAggiuntivoService);
        this.restAllenamentoAggiuntivoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoAggiuntivoResource)
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
    public static AllenamentoAggiuntivo createEntity(EntityManager em) {
        AllenamentoAggiuntivo allenamentoAggiuntivo = new AllenamentoAggiuntivo()
            .sport(DEFAULT_SPORT)
            .natura(DEFAULT_NATURA)
            .lavoro(DEFAULT_LAVORO)
            .durata(DEFAULT_DURATA);
        return allenamentoAggiuntivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AllenamentoAggiuntivo createUpdatedEntity(EntityManager em) {
        AllenamentoAggiuntivo allenamentoAggiuntivo = new AllenamentoAggiuntivo()
            .sport(UPDATED_SPORT)
            .natura(UPDATED_NATURA)
            .lavoro(UPDATED_LAVORO)
            .durata(UPDATED_DURATA);
        return allenamentoAggiuntivo;
    }

    @BeforeEach
    public void initTest() {
        allenamentoAggiuntivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllenamentoAggiuntivo() throws Exception {
        int databaseSizeBeforeCreate = allenamentoAggiuntivoRepository.findAll().size();

        // Create the AllenamentoAggiuntivo
        restAllenamentoAggiuntivoMockMvc.perform(post("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamentoAggiuntivo)))
            .andExpect(status().isCreated());

        // Validate the AllenamentoAggiuntivo in the database
        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeCreate + 1);
        AllenamentoAggiuntivo testAllenamentoAggiuntivo = allenamentoAggiuntivoList.get(allenamentoAggiuntivoList.size() - 1);
        assertThat(testAllenamentoAggiuntivo.getSport()).isEqualTo(DEFAULT_SPORT);
        assertThat(testAllenamentoAggiuntivo.getNatura()).isEqualTo(DEFAULT_NATURA);
        assertThat(testAllenamentoAggiuntivo.getLavoro()).isEqualTo(DEFAULT_LAVORO);
        assertThat(testAllenamentoAggiuntivo.getDurata()).isEqualTo(DEFAULT_DURATA);
    }

    @Test
    @Transactional
    public void createAllenamentoAggiuntivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allenamentoAggiuntivoRepository.findAll().size();

        // Create the AllenamentoAggiuntivo with an existing ID
        allenamentoAggiuntivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllenamentoAggiuntivoMockMvc.perform(post("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamentoAggiuntivo)))
            .andExpect(status().isBadRequest());

        // Validate the AllenamentoAggiuntivo in the database
        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSportIsRequired() throws Exception {
        int databaseSizeBeforeTest = allenamentoAggiuntivoRepository.findAll().size();
        // set the field null
        allenamentoAggiuntivo.setSport(null);

        // Create the AllenamentoAggiuntivo, which fails.

        restAllenamentoAggiuntivoMockMvc.perform(post("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamentoAggiuntivo)))
            .andExpect(status().isBadRequest());

        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = allenamentoAggiuntivoRepository.findAll().size();
        // set the field null
        allenamentoAggiuntivo.setNatura(null);

        // Create the AllenamentoAggiuntivo, which fails.

        restAllenamentoAggiuntivoMockMvc.perform(post("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamentoAggiuntivo)))
            .andExpect(status().isBadRequest());

        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAllenamentoAggiuntivos() throws Exception {
        // Initialize the database
        allenamentoAggiuntivoRepository.saveAndFlush(allenamentoAggiuntivo);

        // Get all the allenamentoAggiuntivoList
        restAllenamentoAggiuntivoMockMvc.perform(get("/api/allenamento-aggiuntivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allenamentoAggiuntivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].sport").value(hasItem(DEFAULT_SPORT.toString())))
            .andExpect(jsonPath("$.[*].natura").value(hasItem(DEFAULT_NATURA.toString())))
            .andExpect(jsonPath("$.[*].lavoro").value(hasItem(DEFAULT_LAVORO.toString())))
            .andExpect(jsonPath("$.[*].durata").value(hasItem(DEFAULT_DURATA.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAllenamentoAggiuntivosWithEagerRelationshipsIsEnabled() throws Exception {
        AllenamentoAggiuntivoResource allenamentoAggiuntivoResource = new AllenamentoAggiuntivoResource(allenamentoAggiuntivoServiceMock);
        when(allenamentoAggiuntivoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAllenamentoAggiuntivoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoAggiuntivoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAllenamentoAggiuntivoMockMvc.perform(get("/api/allenamento-aggiuntivos?eagerload=true"))
        .andExpect(status().isOk());

        verify(allenamentoAggiuntivoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAllenamentoAggiuntivosWithEagerRelationshipsIsNotEnabled() throws Exception {
        AllenamentoAggiuntivoResource allenamentoAggiuntivoResource = new AllenamentoAggiuntivoResource(allenamentoAggiuntivoServiceMock);
            when(allenamentoAggiuntivoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAllenamentoAggiuntivoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoAggiuntivoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAllenamentoAggiuntivoMockMvc.perform(get("/api/allenamento-aggiuntivos?eagerload=true"))
        .andExpect(status().isOk());

            verify(allenamentoAggiuntivoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAllenamentoAggiuntivo() throws Exception {
        // Initialize the database
        allenamentoAggiuntivoRepository.saveAndFlush(allenamentoAggiuntivo);

        // Get the allenamentoAggiuntivo
        restAllenamentoAggiuntivoMockMvc.perform(get("/api/allenamento-aggiuntivos/{id}", allenamentoAggiuntivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allenamentoAggiuntivo.getId().intValue()))
            .andExpect(jsonPath("$.sport").value(DEFAULT_SPORT.toString()))
            .andExpect(jsonPath("$.natura").value(DEFAULT_NATURA.toString()))
            .andExpect(jsonPath("$.lavoro").value(DEFAULT_LAVORO.toString()))
            .andExpect(jsonPath("$.durata").value(DEFAULT_DURATA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAllenamentoAggiuntivo() throws Exception {
        // Get the allenamentoAggiuntivo
        restAllenamentoAggiuntivoMockMvc.perform(get("/api/allenamento-aggiuntivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllenamentoAggiuntivo() throws Exception {
        // Initialize the database
        allenamentoAggiuntivoService.save(allenamentoAggiuntivo);

        int databaseSizeBeforeUpdate = allenamentoAggiuntivoRepository.findAll().size();

        // Update the allenamentoAggiuntivo
        AllenamentoAggiuntivo updatedAllenamentoAggiuntivo = allenamentoAggiuntivoRepository.findById(allenamentoAggiuntivo.getId()).get();
        // Disconnect from session so that the updates on updatedAllenamentoAggiuntivo are not directly saved in db
        em.detach(updatedAllenamentoAggiuntivo);
        updatedAllenamentoAggiuntivo
            .sport(UPDATED_SPORT)
            .natura(UPDATED_NATURA)
            .lavoro(UPDATED_LAVORO)
            .durata(UPDATED_DURATA);

        restAllenamentoAggiuntivoMockMvc.perform(put("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllenamentoAggiuntivo)))
            .andExpect(status().isOk());

        // Validate the AllenamentoAggiuntivo in the database
        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeUpdate);
        AllenamentoAggiuntivo testAllenamentoAggiuntivo = allenamentoAggiuntivoList.get(allenamentoAggiuntivoList.size() - 1);
        assertThat(testAllenamentoAggiuntivo.getSport()).isEqualTo(UPDATED_SPORT);
        assertThat(testAllenamentoAggiuntivo.getNatura()).isEqualTo(UPDATED_NATURA);
        assertThat(testAllenamentoAggiuntivo.getLavoro()).isEqualTo(UPDATED_LAVORO);
        assertThat(testAllenamentoAggiuntivo.getDurata()).isEqualTo(UPDATED_DURATA);
    }

    @Test
    @Transactional
    public void updateNonExistingAllenamentoAggiuntivo() throws Exception {
        int databaseSizeBeforeUpdate = allenamentoAggiuntivoRepository.findAll().size();

        // Create the AllenamentoAggiuntivo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllenamentoAggiuntivoMockMvc.perform(put("/api/allenamento-aggiuntivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamentoAggiuntivo)))
            .andExpect(status().isBadRequest());

        // Validate the AllenamentoAggiuntivo in the database
        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllenamentoAggiuntivo() throws Exception {
        // Initialize the database
        allenamentoAggiuntivoService.save(allenamentoAggiuntivo);

        int databaseSizeBeforeDelete = allenamentoAggiuntivoRepository.findAll().size();

        // Delete the allenamentoAggiuntivo
        restAllenamentoAggiuntivoMockMvc.perform(delete("/api/allenamento-aggiuntivos/{id}", allenamentoAggiuntivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AllenamentoAggiuntivo> allenamentoAggiuntivoList = allenamentoAggiuntivoRepository.findAll();
        assertThat(allenamentoAggiuntivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllenamentoAggiuntivo.class);
        AllenamentoAggiuntivo allenamentoAggiuntivo1 = new AllenamentoAggiuntivo();
        allenamentoAggiuntivo1.setId(1L);
        AllenamentoAggiuntivo allenamentoAggiuntivo2 = new AllenamentoAggiuntivo();
        allenamentoAggiuntivo2.setId(allenamentoAggiuntivo1.getId());
        assertThat(allenamentoAggiuntivo1).isEqualTo(allenamentoAggiuntivo2);
        allenamentoAggiuntivo2.setId(2L);
        assertThat(allenamentoAggiuntivo1).isNotEqualTo(allenamentoAggiuntivo2);
        allenamentoAggiuntivo1.setId(null);
        assertThat(allenamentoAggiuntivo1).isNotEqualTo(allenamentoAggiuntivo2);
    }
}
