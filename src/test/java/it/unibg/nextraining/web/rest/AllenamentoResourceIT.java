package it.unibg.nextraining.web.rest;

import it.unibg.nextraining.NextrainingApp;
import it.unibg.nextraining.domain.Allenamento;
import it.unibg.nextraining.domain.Calciatore;
import it.unibg.nextraining.repository.AllenamentoRepository;
import it.unibg.nextraining.service.AllenamentoService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@Link AllenamentoResource} REST controller.
 */
@SpringBootTest(classes = NextrainingApp.class)
public class AllenamentoResourceIT {

    private static final LocalDate DEFAULT_DATA_SVOLGIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_SVOLGIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Natura DEFAULT_NATURA = Natura.AEROBICO;
    private static final Natura UPDATED_NATURA = Natura.ANAEROBICO;

    private static final String DEFAULT_LAVORO = "AAAAAAAAAA";
    private static final String UPDATED_LAVORO = "BBBBBBBBBB";

    @Autowired
    private AllenamentoRepository allenamentoRepository;

    @Mock
    private AllenamentoRepository allenamentoRepositoryMock;

    @Mock
    private AllenamentoService allenamentoServiceMock;

    @Autowired
    private AllenamentoService allenamentoService;

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

    private MockMvc restAllenamentoMockMvc;

    private Allenamento allenamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllenamentoResource allenamentoResource = new AllenamentoResource(allenamentoService);
        this.restAllenamentoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoResource)
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
    public static Allenamento createEntity(EntityManager em) {
        Allenamento allenamento = new Allenamento()
            .dataSvolgimento(DEFAULT_DATA_SVOLGIMENTO)
            .natura(DEFAULT_NATURA)
            .lavoro(DEFAULT_LAVORO);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        allenamento.getCalciatores().add(calciatore);
        return allenamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allenamento createUpdatedEntity(EntityManager em) {
        Allenamento allenamento = new Allenamento()
            .dataSvolgimento(UPDATED_DATA_SVOLGIMENTO)
            .natura(UPDATED_NATURA)
            .lavoro(UPDATED_LAVORO);
        // Add required entity
        Calciatore calciatore;
        if (TestUtil.findAll(em, Calciatore.class).isEmpty()) {
            calciatore = CalciatoreResourceIT.createUpdatedEntity(em);
            em.persist(calciatore);
            em.flush();
        } else {
            calciatore = TestUtil.findAll(em, Calciatore.class).get(0);
        }
        allenamento.getCalciatores().add(calciatore);
        return allenamento;
    }

    @BeforeEach
    public void initTest() {
        allenamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllenamento() throws Exception {
        int databaseSizeBeforeCreate = allenamentoRepository.findAll().size();

        // Create the Allenamento
        restAllenamentoMockMvc.perform(post("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamento)))
            .andExpect(status().isCreated());

        // Validate the Allenamento in the database
        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Allenamento testAllenamento = allenamentoList.get(allenamentoList.size() - 1);
        assertThat(testAllenamento.getDataSvolgimento()).isEqualTo(DEFAULT_DATA_SVOLGIMENTO);
        assertThat(testAllenamento.getNatura()).isEqualTo(DEFAULT_NATURA);
        assertThat(testAllenamento.getLavoro()).isEqualTo(DEFAULT_LAVORO);
    }

    @Test
    @Transactional
    public void createAllenamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allenamentoRepository.findAll().size();

        // Create the Allenamento with an existing ID
        allenamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllenamentoMockMvc.perform(post("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamento)))
            .andExpect(status().isBadRequest());

        // Validate the Allenamento in the database
        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataSvolgimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = allenamentoRepository.findAll().size();
        // set the field null
        allenamento.setDataSvolgimento(null);

        // Create the Allenamento, which fails.

        restAllenamentoMockMvc.perform(post("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamento)))
            .andExpect(status().isBadRequest());

        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = allenamentoRepository.findAll().size();
        // set the field null
        allenamento.setNatura(null);

        // Create the Allenamento, which fails.

        restAllenamentoMockMvc.perform(post("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamento)))
            .andExpect(status().isBadRequest());

        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAllenamentos() throws Exception {
        // Initialize the database
        allenamentoRepository.saveAndFlush(allenamento);

        // Get all the allenamentoList
        restAllenamentoMockMvc.perform(get("/api/allenamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataSvolgimento").value(hasItem(DEFAULT_DATA_SVOLGIMENTO.toString())))
            .andExpect(jsonPath("$.[*].natura").value(hasItem(DEFAULT_NATURA.toString())))
            .andExpect(jsonPath("$.[*].lavoro").value(hasItem(DEFAULT_LAVORO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAllenamentosWithEagerRelationshipsIsEnabled() throws Exception {
        AllenamentoResource allenamentoResource = new AllenamentoResource(allenamentoServiceMock);
        when(allenamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAllenamentoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAllenamentoMockMvc.perform(get("/api/allenamentos?eagerload=true"))
        .andExpect(status().isOk());

        verify(allenamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAllenamentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        AllenamentoResource allenamentoResource = new AllenamentoResource(allenamentoServiceMock);
            when(allenamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAllenamentoMockMvc = MockMvcBuilders.standaloneSetup(allenamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAllenamentoMockMvc.perform(get("/api/allenamentos?eagerload=true"))
        .andExpect(status().isOk());

            verify(allenamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAllenamento() throws Exception {
        // Initialize the database
        allenamentoRepository.saveAndFlush(allenamento);

        // Get the allenamento
        restAllenamentoMockMvc.perform(get("/api/allenamentos/{id}", allenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allenamento.getId().intValue()))
            .andExpect(jsonPath("$.dataSvolgimento").value(DEFAULT_DATA_SVOLGIMENTO.toString()))
            .andExpect(jsonPath("$.natura").value(DEFAULT_NATURA.toString()))
            .andExpect(jsonPath("$.lavoro").value(DEFAULT_LAVORO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllenamento() throws Exception {
        // Get the allenamento
        restAllenamentoMockMvc.perform(get("/api/allenamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllenamento() throws Exception {
        // Initialize the database
        allenamentoService.save(allenamento);

        int databaseSizeBeforeUpdate = allenamentoRepository.findAll().size();

        // Update the allenamento
        Allenamento updatedAllenamento = allenamentoRepository.findById(allenamento.getId()).get();
        // Disconnect from session so that the updates on updatedAllenamento are not directly saved in db
        em.detach(updatedAllenamento);
        updatedAllenamento
            .dataSvolgimento(UPDATED_DATA_SVOLGIMENTO)
            .natura(UPDATED_NATURA)
            .lavoro(UPDATED_LAVORO);

        restAllenamentoMockMvc.perform(put("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllenamento)))
            .andExpect(status().isOk());

        // Validate the Allenamento in the database
        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeUpdate);
        Allenamento testAllenamento = allenamentoList.get(allenamentoList.size() - 1);
        assertThat(testAllenamento.getDataSvolgimento()).isEqualTo(UPDATED_DATA_SVOLGIMENTO);
        assertThat(testAllenamento.getNatura()).isEqualTo(UPDATED_NATURA);
        assertThat(testAllenamento.getLavoro()).isEqualTo(UPDATED_LAVORO);
    }

    @Test
    @Transactional
    public void updateNonExistingAllenamento() throws Exception {
        int databaseSizeBeforeUpdate = allenamentoRepository.findAll().size();

        // Create the Allenamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllenamentoMockMvc.perform(put("/api/allenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allenamento)))
            .andExpect(status().isBadRequest());

        // Validate the Allenamento in the database
        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllenamento() throws Exception {
        // Initialize the database
        allenamentoService.save(allenamento);

        int databaseSizeBeforeDelete = allenamentoRepository.findAll().size();

        // Delete the allenamento
        restAllenamentoMockMvc.perform(delete("/api/allenamentos/{id}", allenamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Allenamento> allenamentoList = allenamentoRepository.findAll();
        assertThat(allenamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allenamento.class);
        Allenamento allenamento1 = new Allenamento();
        allenamento1.setId(1L);
        Allenamento allenamento2 = new Allenamento();
        allenamento2.setId(allenamento1.getId());
        assertThat(allenamento1).isEqualTo(allenamento2);
        allenamento2.setId(2L);
        assertThat(allenamento1).isNotEqualTo(allenamento2);
        allenamento1.setId(null);
        assertThat(allenamento1).isNotEqualTo(allenamento2);
    }
}
