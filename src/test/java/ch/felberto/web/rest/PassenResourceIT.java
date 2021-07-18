package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Passen;
import ch.felberto.repository.PassenRepository;
import ch.felberto.service.criteria.PassenCriteria;
import ch.felberto.service.dto.PassenDTO;
import ch.felberto.service.mapper.PassenMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PassenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassenResourceIT {

    private static final Integer DEFAULT_P_1 = 0;
    private static final Integer UPDATED_P_1 = 1;
    private static final Integer SMALLER_P_1 = 0 - 1;

    private static final Integer DEFAULT_P_2 = 0;
    private static final Integer UPDATED_P_2 = 1;
    private static final Integer SMALLER_P_2 = 0 - 1;

    private static final Integer DEFAULT_P_3 = 0;
    private static final Integer UPDATED_P_3 = 1;
    private static final Integer SMALLER_P_3 = 0 - 1;

    private static final Integer DEFAULT_P_4 = 0;
    private static final Integer UPDATED_P_4 = 1;
    private static final Integer SMALLER_P_4 = 0 - 1;

    private static final Integer DEFAULT_P_5 = 0;
    private static final Integer UPDATED_P_5 = 1;
    private static final Integer SMALLER_P_5 = 0 - 1;

    private static final Integer DEFAULT_P_6 = 0;
    private static final Integer UPDATED_P_6 = 1;
    private static final Integer SMALLER_P_6 = 0 - 1;

    private static final Integer DEFAULT_P_7 = 0;
    private static final Integer UPDATED_P_7 = 1;
    private static final Integer SMALLER_P_7 = 0 - 1;

    private static final Integer DEFAULT_P_8 = 0;
    private static final Integer UPDATED_P_8 = 1;
    private static final Integer SMALLER_P_8 = 0 - 1;

    private static final Integer DEFAULT_P_9 = 0;
    private static final Integer UPDATED_P_9 = 1;
    private static final Integer SMALLER_P_9 = 0 - 1;

    private static final Integer DEFAULT_P_10 = 0;
    private static final Integer UPDATED_P_10 = 1;
    private static final Integer SMALLER_P_10 = 0 - 1;

    private static final Integer DEFAULT_RESULTAT = 1;
    private static final Integer UPDATED_RESULTAT = 2;
    private static final Integer SMALLER_RESULTAT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/passens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassenRepository passenRepository;

    @Autowired
    private PassenMapper passenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassenMockMvc;

    private Passen passen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passen createEntity(EntityManager em) {
        Passen passen = new Passen()
            .p1(DEFAULT_P_1)
            .p2(DEFAULT_P_2)
            .p3(DEFAULT_P_3)
            .p4(DEFAULT_P_4)
            .p5(DEFAULT_P_5)
            .p6(DEFAULT_P_6)
            .p7(DEFAULT_P_7)
            .p8(DEFAULT_P_8)
            .p9(DEFAULT_P_9)
            .p10(DEFAULT_P_10)
            .resultat(DEFAULT_RESULTAT);
        return passen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passen createUpdatedEntity(EntityManager em) {
        Passen passen = new Passen()
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);
        return passen;
    }

    @BeforeEach
    public void initTest() {
        passen = createEntity(em);
    }

    @Test
    @Transactional
    void createPassen() throws Exception {
        int databaseSizeBeforeCreate = passenRepository.findAll().size();
        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);
        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isCreated());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeCreate + 1);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(DEFAULT_P_1);
        assertThat(testPassen.getp2()).isEqualTo(DEFAULT_P_2);
        assertThat(testPassen.getp3()).isEqualTo(DEFAULT_P_3);
        assertThat(testPassen.getp4()).isEqualTo(DEFAULT_P_4);
        assertThat(testPassen.getp5()).isEqualTo(DEFAULT_P_5);
        assertThat(testPassen.getp6()).isEqualTo(DEFAULT_P_6);
        assertThat(testPassen.getp7()).isEqualTo(DEFAULT_P_7);
        assertThat(testPassen.getp8()).isEqualTo(DEFAULT_P_8);
        assertThat(testPassen.getp9()).isEqualTo(DEFAULT_P_9);
        assertThat(testPassen.getp10()).isEqualTo(DEFAULT_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(DEFAULT_RESULTAT);
    }

    @Test
    @Transactional
    void createPassenWithExistingId() throws Exception {
        // Create the Passen with an existing ID
        passen.setId(1L);
        PassenDTO passenDTO = passenMapper.toDto(passen);

        int databaseSizeBeforeCreate = passenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkp1IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp1(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp2IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp2(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp3IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp3(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp4IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp4(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp5IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp5(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp6IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp6(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp7IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp7(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp8IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp8(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp9IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp9(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp10IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp10(null);

        // Create the Passen, which fails.
        PassenDTO passenDTO = passenMapper.toDto(passen);

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPassens() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passen.getId().intValue())))
            .andExpect(jsonPath("$.[*].p1").value(hasItem(DEFAULT_P_1)))
            .andExpect(jsonPath("$.[*].p2").value(hasItem(DEFAULT_P_2)))
            .andExpect(jsonPath("$.[*].p3").value(hasItem(DEFAULT_P_3)))
            .andExpect(jsonPath("$.[*].p4").value(hasItem(DEFAULT_P_4)))
            .andExpect(jsonPath("$.[*].p5").value(hasItem(DEFAULT_P_5)))
            .andExpect(jsonPath("$.[*].p6").value(hasItem(DEFAULT_P_6)))
            .andExpect(jsonPath("$.[*].p7").value(hasItem(DEFAULT_P_7)))
            .andExpect(jsonPath("$.[*].p8").value(hasItem(DEFAULT_P_8)))
            .andExpect(jsonPath("$.[*].p9").value(hasItem(DEFAULT_P_9)))
            .andExpect(jsonPath("$.[*].p10").value(hasItem(DEFAULT_P_10)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)));
    }

    @Test
    @Transactional
    void getPassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get the passen
        restPassenMockMvc
            .perform(get(ENTITY_API_URL_ID, passen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passen.getId().intValue()))
            .andExpect(jsonPath("$.p1").value(DEFAULT_P_1))
            .andExpect(jsonPath("$.p2").value(DEFAULT_P_2))
            .andExpect(jsonPath("$.p3").value(DEFAULT_P_3))
            .andExpect(jsonPath("$.p4").value(DEFAULT_P_4))
            .andExpect(jsonPath("$.p5").value(DEFAULT_P_5))
            .andExpect(jsonPath("$.p6").value(DEFAULT_P_6))
            .andExpect(jsonPath("$.p7").value(DEFAULT_P_7))
            .andExpect(jsonPath("$.p8").value(DEFAULT_P_8))
            .andExpect(jsonPath("$.p9").value(DEFAULT_P_9))
            .andExpect(jsonPath("$.p10").value(DEFAULT_P_10))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT));
    }

    @Test
    @Transactional
    void getPassensByIdFiltering() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        Long id = passen.getId();

        defaultPassenShouldBeFound("id.equals=" + id);
        defaultPassenShouldNotBeFound("id.notEquals=" + id);

        defaultPassenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPassenShouldNotBeFound("id.greaterThan=" + id);

        defaultPassenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPassenShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPassensByp1IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 equals to DEFAULT_P_1
        defaultPassenShouldBeFound("p1.equals=" + DEFAULT_P_1);

        // Get all the passenList where p1 equals to UPDATED_P_1
        defaultPassenShouldNotBeFound("p1.equals=" + UPDATED_P_1);
    }

    @Test
    @Transactional
    void getAllPassensByp1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 not equals to DEFAULT_P_1
        defaultPassenShouldNotBeFound("p1.notEquals=" + DEFAULT_P_1);

        // Get all the passenList where p1 not equals to UPDATED_P_1
        defaultPassenShouldBeFound("p1.notEquals=" + UPDATED_P_1);
    }

    @Test
    @Transactional
    void getAllPassensByp1IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 in DEFAULT_P_1 or UPDATED_P_1
        defaultPassenShouldBeFound("p1.in=" + DEFAULT_P_1 + "," + UPDATED_P_1);

        // Get all the passenList where p1 equals to UPDATED_P_1
        defaultPassenShouldNotBeFound("p1.in=" + UPDATED_P_1);
    }

    @Test
    @Transactional
    void getAllPassensByp1IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 is not null
        defaultPassenShouldBeFound("p1.specified=true");

        // Get all the passenList where p1 is null
        defaultPassenShouldNotBeFound("p1.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 is greater than or equal to DEFAULT_P_1
        defaultPassenShouldBeFound("p1.greaterThanOrEqual=" + DEFAULT_P_1);

        // Get all the passenList where p1 is greater than or equal to (DEFAULT_P_1 + 1)
        defaultPassenShouldNotBeFound("p1.greaterThanOrEqual=" + (DEFAULT_P_1 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 is less than or equal to DEFAULT_P_1
        defaultPassenShouldBeFound("p1.lessThanOrEqual=" + DEFAULT_P_1);

        // Get all the passenList where p1 is less than or equal to SMALLER_P_1
        defaultPassenShouldNotBeFound("p1.lessThanOrEqual=" + SMALLER_P_1);
    }

    @Test
    @Transactional
    void getAllPassensByp1IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 is less than DEFAULT_P_1
        defaultPassenShouldNotBeFound("p1.lessThan=" + DEFAULT_P_1);

        // Get all the passenList where p1 is less than (DEFAULT_P_1 + 1)
        defaultPassenShouldBeFound("p1.lessThan=" + (DEFAULT_P_1 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p1 is greater than DEFAULT_P_1
        defaultPassenShouldNotBeFound("p1.greaterThan=" + DEFAULT_P_1);

        // Get all the passenList where p1 is greater than SMALLER_P_1
        defaultPassenShouldBeFound("p1.greaterThan=" + SMALLER_P_1);
    }

    @Test
    @Transactional
    void getAllPassensByp2IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 equals to DEFAULT_P_2
        defaultPassenShouldBeFound("p2.equals=" + DEFAULT_P_2);

        // Get all the passenList where p2 equals to UPDATED_P_2
        defaultPassenShouldNotBeFound("p2.equals=" + UPDATED_P_2);
    }

    @Test
    @Transactional
    void getAllPassensByp2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 not equals to DEFAULT_P_2
        defaultPassenShouldNotBeFound("p2.notEquals=" + DEFAULT_P_2);

        // Get all the passenList where p2 not equals to UPDATED_P_2
        defaultPassenShouldBeFound("p2.notEquals=" + UPDATED_P_2);
    }

    @Test
    @Transactional
    void getAllPassensByp2IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 in DEFAULT_P_2 or UPDATED_P_2
        defaultPassenShouldBeFound("p2.in=" + DEFAULT_P_2 + "," + UPDATED_P_2);

        // Get all the passenList where p2 equals to UPDATED_P_2
        defaultPassenShouldNotBeFound("p2.in=" + UPDATED_P_2);
    }

    @Test
    @Transactional
    void getAllPassensByp2IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 is not null
        defaultPassenShouldBeFound("p2.specified=true");

        // Get all the passenList where p2 is null
        defaultPassenShouldNotBeFound("p2.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 is greater than or equal to DEFAULT_P_2
        defaultPassenShouldBeFound("p2.greaterThanOrEqual=" + DEFAULT_P_2);

        // Get all the passenList where p2 is greater than or equal to (DEFAULT_P_2 + 1)
        defaultPassenShouldNotBeFound("p2.greaterThanOrEqual=" + (DEFAULT_P_2 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 is less than or equal to DEFAULT_P_2
        defaultPassenShouldBeFound("p2.lessThanOrEqual=" + DEFAULT_P_2);

        // Get all the passenList where p2 is less than or equal to SMALLER_P_2
        defaultPassenShouldNotBeFound("p2.lessThanOrEqual=" + SMALLER_P_2);
    }

    @Test
    @Transactional
    void getAllPassensByp2IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 is less than DEFAULT_P_2
        defaultPassenShouldNotBeFound("p2.lessThan=" + DEFAULT_P_2);

        // Get all the passenList where p2 is less than (DEFAULT_P_2 + 1)
        defaultPassenShouldBeFound("p2.lessThan=" + (DEFAULT_P_2 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p2 is greater than DEFAULT_P_2
        defaultPassenShouldNotBeFound("p2.greaterThan=" + DEFAULT_P_2);

        // Get all the passenList where p2 is greater than SMALLER_P_2
        defaultPassenShouldBeFound("p2.greaterThan=" + SMALLER_P_2);
    }

    @Test
    @Transactional
    void getAllPassensByp3IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 equals to DEFAULT_P_3
        defaultPassenShouldBeFound("p3.equals=" + DEFAULT_P_3);

        // Get all the passenList where p3 equals to UPDATED_P_3
        defaultPassenShouldNotBeFound("p3.equals=" + UPDATED_P_3);
    }

    @Test
    @Transactional
    void getAllPassensByp3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 not equals to DEFAULT_P_3
        defaultPassenShouldNotBeFound("p3.notEquals=" + DEFAULT_P_3);

        // Get all the passenList where p3 not equals to UPDATED_P_3
        defaultPassenShouldBeFound("p3.notEquals=" + UPDATED_P_3);
    }

    @Test
    @Transactional
    void getAllPassensByp3IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 in DEFAULT_P_3 or UPDATED_P_3
        defaultPassenShouldBeFound("p3.in=" + DEFAULT_P_3 + "," + UPDATED_P_3);

        // Get all the passenList where p3 equals to UPDATED_P_3
        defaultPassenShouldNotBeFound("p3.in=" + UPDATED_P_3);
    }

    @Test
    @Transactional
    void getAllPassensByp3IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 is not null
        defaultPassenShouldBeFound("p3.specified=true");

        // Get all the passenList where p3 is null
        defaultPassenShouldNotBeFound("p3.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 is greater than or equal to DEFAULT_P_3
        defaultPassenShouldBeFound("p3.greaterThanOrEqual=" + DEFAULT_P_3);

        // Get all the passenList where p3 is greater than or equal to (DEFAULT_P_3 + 1)
        defaultPassenShouldNotBeFound("p3.greaterThanOrEqual=" + (DEFAULT_P_3 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 is less than or equal to DEFAULT_P_3
        defaultPassenShouldBeFound("p3.lessThanOrEqual=" + DEFAULT_P_3);

        // Get all the passenList where p3 is less than or equal to SMALLER_P_3
        defaultPassenShouldNotBeFound("p3.lessThanOrEqual=" + SMALLER_P_3);
    }

    @Test
    @Transactional
    void getAllPassensByp3IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 is less than DEFAULT_P_3
        defaultPassenShouldNotBeFound("p3.lessThan=" + DEFAULT_P_3);

        // Get all the passenList where p3 is less than (DEFAULT_P_3 + 1)
        defaultPassenShouldBeFound("p3.lessThan=" + (DEFAULT_P_3 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p3 is greater than DEFAULT_P_3
        defaultPassenShouldNotBeFound("p3.greaterThan=" + DEFAULT_P_3);

        // Get all the passenList where p3 is greater than SMALLER_P_3
        defaultPassenShouldBeFound("p3.greaterThan=" + SMALLER_P_3);
    }

    @Test
    @Transactional
    void getAllPassensByp4IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 equals to DEFAULT_P_4
        defaultPassenShouldBeFound("p4.equals=" + DEFAULT_P_4);

        // Get all the passenList where p4 equals to UPDATED_P_4
        defaultPassenShouldNotBeFound("p4.equals=" + UPDATED_P_4);
    }

    @Test
    @Transactional
    void getAllPassensByp4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 not equals to DEFAULT_P_4
        defaultPassenShouldNotBeFound("p4.notEquals=" + DEFAULT_P_4);

        // Get all the passenList where p4 not equals to UPDATED_P_4
        defaultPassenShouldBeFound("p4.notEquals=" + UPDATED_P_4);
    }

    @Test
    @Transactional
    void getAllPassensByp4IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 in DEFAULT_P_4 or UPDATED_P_4
        defaultPassenShouldBeFound("p4.in=" + DEFAULT_P_4 + "," + UPDATED_P_4);

        // Get all the passenList where p4 equals to UPDATED_P_4
        defaultPassenShouldNotBeFound("p4.in=" + UPDATED_P_4);
    }

    @Test
    @Transactional
    void getAllPassensByp4IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 is not null
        defaultPassenShouldBeFound("p4.specified=true");

        // Get all the passenList where p4 is null
        defaultPassenShouldNotBeFound("p4.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 is greater than or equal to DEFAULT_P_4
        defaultPassenShouldBeFound("p4.greaterThanOrEqual=" + DEFAULT_P_4);

        // Get all the passenList where p4 is greater than or equal to (DEFAULT_P_4 + 1)
        defaultPassenShouldNotBeFound("p4.greaterThanOrEqual=" + (DEFAULT_P_4 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 is less than or equal to DEFAULT_P_4
        defaultPassenShouldBeFound("p4.lessThanOrEqual=" + DEFAULT_P_4);

        // Get all the passenList where p4 is less than or equal to SMALLER_P_4
        defaultPassenShouldNotBeFound("p4.lessThanOrEqual=" + SMALLER_P_4);
    }

    @Test
    @Transactional
    void getAllPassensByp4IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 is less than DEFAULT_P_4
        defaultPassenShouldNotBeFound("p4.lessThan=" + DEFAULT_P_4);

        // Get all the passenList where p4 is less than (DEFAULT_P_4 + 1)
        defaultPassenShouldBeFound("p4.lessThan=" + (DEFAULT_P_4 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p4 is greater than DEFAULT_P_4
        defaultPassenShouldNotBeFound("p4.greaterThan=" + DEFAULT_P_4);

        // Get all the passenList where p4 is greater than SMALLER_P_4
        defaultPassenShouldBeFound("p4.greaterThan=" + SMALLER_P_4);
    }

    @Test
    @Transactional
    void getAllPassensByp5IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 equals to DEFAULT_P_5
        defaultPassenShouldBeFound("p5.equals=" + DEFAULT_P_5);

        // Get all the passenList where p5 equals to UPDATED_P_5
        defaultPassenShouldNotBeFound("p5.equals=" + UPDATED_P_5);
    }

    @Test
    @Transactional
    void getAllPassensByp5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 not equals to DEFAULT_P_5
        defaultPassenShouldNotBeFound("p5.notEquals=" + DEFAULT_P_5);

        // Get all the passenList where p5 not equals to UPDATED_P_5
        defaultPassenShouldBeFound("p5.notEquals=" + UPDATED_P_5);
    }

    @Test
    @Transactional
    void getAllPassensByp5IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 in DEFAULT_P_5 or UPDATED_P_5
        defaultPassenShouldBeFound("p5.in=" + DEFAULT_P_5 + "," + UPDATED_P_5);

        // Get all the passenList where p5 equals to UPDATED_P_5
        defaultPassenShouldNotBeFound("p5.in=" + UPDATED_P_5);
    }

    @Test
    @Transactional
    void getAllPassensByp5IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 is not null
        defaultPassenShouldBeFound("p5.specified=true");

        // Get all the passenList where p5 is null
        defaultPassenShouldNotBeFound("p5.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 is greater than or equal to DEFAULT_P_5
        defaultPassenShouldBeFound("p5.greaterThanOrEqual=" + DEFAULT_P_5);

        // Get all the passenList where p5 is greater than or equal to (DEFAULT_P_5 + 1)
        defaultPassenShouldNotBeFound("p5.greaterThanOrEqual=" + (DEFAULT_P_5 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 is less than or equal to DEFAULT_P_5
        defaultPassenShouldBeFound("p5.lessThanOrEqual=" + DEFAULT_P_5);

        // Get all the passenList where p5 is less than or equal to SMALLER_P_5
        defaultPassenShouldNotBeFound("p5.lessThanOrEqual=" + SMALLER_P_5);
    }

    @Test
    @Transactional
    void getAllPassensByp5IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 is less than DEFAULT_P_5
        defaultPassenShouldNotBeFound("p5.lessThan=" + DEFAULT_P_5);

        // Get all the passenList where p5 is less than (DEFAULT_P_5 + 1)
        defaultPassenShouldBeFound("p5.lessThan=" + (DEFAULT_P_5 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p5 is greater than DEFAULT_P_5
        defaultPassenShouldNotBeFound("p5.greaterThan=" + DEFAULT_P_5);

        // Get all the passenList where p5 is greater than SMALLER_P_5
        defaultPassenShouldBeFound("p5.greaterThan=" + SMALLER_P_5);
    }

    @Test
    @Transactional
    void getAllPassensByp6IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 equals to DEFAULT_P_6
        defaultPassenShouldBeFound("p6.equals=" + DEFAULT_P_6);

        // Get all the passenList where p6 equals to UPDATED_P_6
        defaultPassenShouldNotBeFound("p6.equals=" + UPDATED_P_6);
    }

    @Test
    @Transactional
    void getAllPassensByp6IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 not equals to DEFAULT_P_6
        defaultPassenShouldNotBeFound("p6.notEquals=" + DEFAULT_P_6);

        // Get all the passenList where p6 not equals to UPDATED_P_6
        defaultPassenShouldBeFound("p6.notEquals=" + UPDATED_P_6);
    }

    @Test
    @Transactional
    void getAllPassensByp6IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 in DEFAULT_P_6 or UPDATED_P_6
        defaultPassenShouldBeFound("p6.in=" + DEFAULT_P_6 + "," + UPDATED_P_6);

        // Get all the passenList where p6 equals to UPDATED_P_6
        defaultPassenShouldNotBeFound("p6.in=" + UPDATED_P_6);
    }

    @Test
    @Transactional
    void getAllPassensByp6IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 is not null
        defaultPassenShouldBeFound("p6.specified=true");

        // Get all the passenList where p6 is null
        defaultPassenShouldNotBeFound("p6.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 is greater than or equal to DEFAULT_P_6
        defaultPassenShouldBeFound("p6.greaterThanOrEqual=" + DEFAULT_P_6);

        // Get all the passenList where p6 is greater than or equal to (DEFAULT_P_6 + 1)
        defaultPassenShouldNotBeFound("p6.greaterThanOrEqual=" + (DEFAULT_P_6 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp6IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 is less than or equal to DEFAULT_P_6
        defaultPassenShouldBeFound("p6.lessThanOrEqual=" + DEFAULT_P_6);

        // Get all the passenList where p6 is less than or equal to SMALLER_P_6
        defaultPassenShouldNotBeFound("p6.lessThanOrEqual=" + SMALLER_P_6);
    }

    @Test
    @Transactional
    void getAllPassensByp6IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 is less than DEFAULT_P_6
        defaultPassenShouldNotBeFound("p6.lessThan=" + DEFAULT_P_6);

        // Get all the passenList where p6 is less than (DEFAULT_P_6 + 1)
        defaultPassenShouldBeFound("p6.lessThan=" + (DEFAULT_P_6 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp6IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p6 is greater than DEFAULT_P_6
        defaultPassenShouldNotBeFound("p6.greaterThan=" + DEFAULT_P_6);

        // Get all the passenList where p6 is greater than SMALLER_P_6
        defaultPassenShouldBeFound("p6.greaterThan=" + SMALLER_P_6);
    }

    @Test
    @Transactional
    void getAllPassensByp7IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 equals to DEFAULT_P_7
        defaultPassenShouldBeFound("p7.equals=" + DEFAULT_P_7);

        // Get all the passenList where p7 equals to UPDATED_P_7
        defaultPassenShouldNotBeFound("p7.equals=" + UPDATED_P_7);
    }

    @Test
    @Transactional
    void getAllPassensByp7IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 not equals to DEFAULT_P_7
        defaultPassenShouldNotBeFound("p7.notEquals=" + DEFAULT_P_7);

        // Get all the passenList where p7 not equals to UPDATED_P_7
        defaultPassenShouldBeFound("p7.notEquals=" + UPDATED_P_7);
    }

    @Test
    @Transactional
    void getAllPassensByp7IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 in DEFAULT_P_7 or UPDATED_P_7
        defaultPassenShouldBeFound("p7.in=" + DEFAULT_P_7 + "," + UPDATED_P_7);

        // Get all the passenList where p7 equals to UPDATED_P_7
        defaultPassenShouldNotBeFound("p7.in=" + UPDATED_P_7);
    }

    @Test
    @Transactional
    void getAllPassensByp7IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 is not null
        defaultPassenShouldBeFound("p7.specified=true");

        // Get all the passenList where p7 is null
        defaultPassenShouldNotBeFound("p7.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp7IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 is greater than or equal to DEFAULT_P_7
        defaultPassenShouldBeFound("p7.greaterThanOrEqual=" + DEFAULT_P_7);

        // Get all the passenList where p7 is greater than or equal to (DEFAULT_P_7 + 1)
        defaultPassenShouldNotBeFound("p7.greaterThanOrEqual=" + (DEFAULT_P_7 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp7IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 is less than or equal to DEFAULT_P_7
        defaultPassenShouldBeFound("p7.lessThanOrEqual=" + DEFAULT_P_7);

        // Get all the passenList where p7 is less than or equal to SMALLER_P_7
        defaultPassenShouldNotBeFound("p7.lessThanOrEqual=" + SMALLER_P_7);
    }

    @Test
    @Transactional
    void getAllPassensByp7IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 is less than DEFAULT_P_7
        defaultPassenShouldNotBeFound("p7.lessThan=" + DEFAULT_P_7);

        // Get all the passenList where p7 is less than (DEFAULT_P_7 + 1)
        defaultPassenShouldBeFound("p7.lessThan=" + (DEFAULT_P_7 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp7IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p7 is greater than DEFAULT_P_7
        defaultPassenShouldNotBeFound("p7.greaterThan=" + DEFAULT_P_7);

        // Get all the passenList where p7 is greater than SMALLER_P_7
        defaultPassenShouldBeFound("p7.greaterThan=" + SMALLER_P_7);
    }

    @Test
    @Transactional
    void getAllPassensByp8IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 equals to DEFAULT_P_8
        defaultPassenShouldBeFound("p8.equals=" + DEFAULT_P_8);

        // Get all the passenList where p8 equals to UPDATED_P_8
        defaultPassenShouldNotBeFound("p8.equals=" + UPDATED_P_8);
    }

    @Test
    @Transactional
    void getAllPassensByp8IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 not equals to DEFAULT_P_8
        defaultPassenShouldNotBeFound("p8.notEquals=" + DEFAULT_P_8);

        // Get all the passenList where p8 not equals to UPDATED_P_8
        defaultPassenShouldBeFound("p8.notEquals=" + UPDATED_P_8);
    }

    @Test
    @Transactional
    void getAllPassensByp8IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 in DEFAULT_P_8 or UPDATED_P_8
        defaultPassenShouldBeFound("p8.in=" + DEFAULT_P_8 + "," + UPDATED_P_8);

        // Get all the passenList where p8 equals to UPDATED_P_8
        defaultPassenShouldNotBeFound("p8.in=" + UPDATED_P_8);
    }

    @Test
    @Transactional
    void getAllPassensByp8IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 is not null
        defaultPassenShouldBeFound("p8.specified=true");

        // Get all the passenList where p8 is null
        defaultPassenShouldNotBeFound("p8.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp8IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 is greater than or equal to DEFAULT_P_8
        defaultPassenShouldBeFound("p8.greaterThanOrEqual=" + DEFAULT_P_8);

        // Get all the passenList where p8 is greater than or equal to (DEFAULT_P_8 + 1)
        defaultPassenShouldNotBeFound("p8.greaterThanOrEqual=" + (DEFAULT_P_8 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp8IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 is less than or equal to DEFAULT_P_8
        defaultPassenShouldBeFound("p8.lessThanOrEqual=" + DEFAULT_P_8);

        // Get all the passenList where p8 is less than or equal to SMALLER_P_8
        defaultPassenShouldNotBeFound("p8.lessThanOrEqual=" + SMALLER_P_8);
    }

    @Test
    @Transactional
    void getAllPassensByp8IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 is less than DEFAULT_P_8
        defaultPassenShouldNotBeFound("p8.lessThan=" + DEFAULT_P_8);

        // Get all the passenList where p8 is less than (DEFAULT_P_8 + 1)
        defaultPassenShouldBeFound("p8.lessThan=" + (DEFAULT_P_8 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp8IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p8 is greater than DEFAULT_P_8
        defaultPassenShouldNotBeFound("p8.greaterThan=" + DEFAULT_P_8);

        // Get all the passenList where p8 is greater than SMALLER_P_8
        defaultPassenShouldBeFound("p8.greaterThan=" + SMALLER_P_8);
    }

    @Test
    @Transactional
    void getAllPassensByp9IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 equals to DEFAULT_P_9
        defaultPassenShouldBeFound("p9.equals=" + DEFAULT_P_9);

        // Get all the passenList where p9 equals to UPDATED_P_9
        defaultPassenShouldNotBeFound("p9.equals=" + UPDATED_P_9);
    }

    @Test
    @Transactional
    void getAllPassensByp9IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 not equals to DEFAULT_P_9
        defaultPassenShouldNotBeFound("p9.notEquals=" + DEFAULT_P_9);

        // Get all the passenList where p9 not equals to UPDATED_P_9
        defaultPassenShouldBeFound("p9.notEquals=" + UPDATED_P_9);
    }

    @Test
    @Transactional
    void getAllPassensByp9IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 in DEFAULT_P_9 or UPDATED_P_9
        defaultPassenShouldBeFound("p9.in=" + DEFAULT_P_9 + "," + UPDATED_P_9);

        // Get all the passenList where p9 equals to UPDATED_P_9
        defaultPassenShouldNotBeFound("p9.in=" + UPDATED_P_9);
    }

    @Test
    @Transactional
    void getAllPassensByp9IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 is not null
        defaultPassenShouldBeFound("p9.specified=true");

        // Get all the passenList where p9 is null
        defaultPassenShouldNotBeFound("p9.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp9IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 is greater than or equal to DEFAULT_P_9
        defaultPassenShouldBeFound("p9.greaterThanOrEqual=" + DEFAULT_P_9);

        // Get all the passenList where p9 is greater than or equal to (DEFAULT_P_9 + 1)
        defaultPassenShouldNotBeFound("p9.greaterThanOrEqual=" + (DEFAULT_P_9 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp9IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 is less than or equal to DEFAULT_P_9
        defaultPassenShouldBeFound("p9.lessThanOrEqual=" + DEFAULT_P_9);

        // Get all the passenList where p9 is less than or equal to SMALLER_P_9
        defaultPassenShouldNotBeFound("p9.lessThanOrEqual=" + SMALLER_P_9);
    }

    @Test
    @Transactional
    void getAllPassensByp9IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 is less than DEFAULT_P_9
        defaultPassenShouldNotBeFound("p9.lessThan=" + DEFAULT_P_9);

        // Get all the passenList where p9 is less than (DEFAULT_P_9 + 1)
        defaultPassenShouldBeFound("p9.lessThan=" + (DEFAULT_P_9 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp9IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p9 is greater than DEFAULT_P_9
        defaultPassenShouldNotBeFound("p9.greaterThan=" + DEFAULT_P_9);

        // Get all the passenList where p9 is greater than SMALLER_P_9
        defaultPassenShouldBeFound("p9.greaterThan=" + SMALLER_P_9);
    }

    @Test
    @Transactional
    void getAllPassensByp10IsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 equals to DEFAULT_P_10
        defaultPassenShouldBeFound("p10.equals=" + DEFAULT_P_10);

        // Get all the passenList where p10 equals to UPDATED_P_10
        defaultPassenShouldNotBeFound("p10.equals=" + UPDATED_P_10);
    }

    @Test
    @Transactional
    void getAllPassensByp10IsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 not equals to DEFAULT_P_10
        defaultPassenShouldNotBeFound("p10.notEquals=" + DEFAULT_P_10);

        // Get all the passenList where p10 not equals to UPDATED_P_10
        defaultPassenShouldBeFound("p10.notEquals=" + UPDATED_P_10);
    }

    @Test
    @Transactional
    void getAllPassensByp10IsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 in DEFAULT_P_10 or UPDATED_P_10
        defaultPassenShouldBeFound("p10.in=" + DEFAULT_P_10 + "," + UPDATED_P_10);

        // Get all the passenList where p10 equals to UPDATED_P_10
        defaultPassenShouldNotBeFound("p10.in=" + UPDATED_P_10);
    }

    @Test
    @Transactional
    void getAllPassensByp10IsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 is not null
        defaultPassenShouldBeFound("p10.specified=true");

        // Get all the passenList where p10 is null
        defaultPassenShouldNotBeFound("p10.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByp10IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 is greater than or equal to DEFAULT_P_10
        defaultPassenShouldBeFound("p10.greaterThanOrEqual=" + DEFAULT_P_10);

        // Get all the passenList where p10 is greater than or equal to (DEFAULT_P_10 + 1)
        defaultPassenShouldNotBeFound("p10.greaterThanOrEqual=" + (DEFAULT_P_10 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp10IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 is less than or equal to DEFAULT_P_10
        defaultPassenShouldBeFound("p10.lessThanOrEqual=" + DEFAULT_P_10);

        // Get all the passenList where p10 is less than or equal to SMALLER_P_10
        defaultPassenShouldNotBeFound("p10.lessThanOrEqual=" + SMALLER_P_10);
    }

    @Test
    @Transactional
    void getAllPassensByp10IsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 is less than DEFAULT_P_10
        defaultPassenShouldNotBeFound("p10.lessThan=" + DEFAULT_P_10);

        // Get all the passenList where p10 is less than (DEFAULT_P_10 + 1)
        defaultPassenShouldBeFound("p10.lessThan=" + (DEFAULT_P_10 + 1));
    }

    @Test
    @Transactional
    void getAllPassensByp10IsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where p10 is greater than DEFAULT_P_10
        defaultPassenShouldNotBeFound("p10.greaterThan=" + DEFAULT_P_10);

        // Get all the passenList where p10 is greater than SMALLER_P_10
        defaultPassenShouldBeFound("p10.greaterThan=" + SMALLER_P_10);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat equals to DEFAULT_RESULTAT
        defaultPassenShouldBeFound("resultat.equals=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat equals to UPDATED_RESULTAT
        defaultPassenShouldNotBeFound("resultat.equals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat not equals to DEFAULT_RESULTAT
        defaultPassenShouldNotBeFound("resultat.notEquals=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat not equals to UPDATED_RESULTAT
        defaultPassenShouldBeFound("resultat.notEquals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsInShouldWork() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat in DEFAULT_RESULTAT or UPDATED_RESULTAT
        defaultPassenShouldBeFound("resultat.in=" + DEFAULT_RESULTAT + "," + UPDATED_RESULTAT);

        // Get all the passenList where resultat equals to UPDATED_RESULTAT
        defaultPassenShouldNotBeFound("resultat.in=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsNullOrNotNull() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat is not null
        defaultPassenShouldBeFound("resultat.specified=true");

        // Get all the passenList where resultat is null
        defaultPassenShouldNotBeFound("resultat.specified=false");
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat is greater than or equal to DEFAULT_RESULTAT
        defaultPassenShouldBeFound("resultat.greaterThanOrEqual=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat is greater than or equal to UPDATED_RESULTAT
        defaultPassenShouldNotBeFound("resultat.greaterThanOrEqual=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat is less than or equal to DEFAULT_RESULTAT
        defaultPassenShouldBeFound("resultat.lessThanOrEqual=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat is less than or equal to SMALLER_RESULTAT
        defaultPassenShouldNotBeFound("resultat.lessThanOrEqual=" + SMALLER_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsLessThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat is less than DEFAULT_RESULTAT
        defaultPassenShouldNotBeFound("resultat.lessThan=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat is less than UPDATED_RESULTAT
        defaultPassenShouldBeFound("resultat.lessThan=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllPassensByResultatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList where resultat is greater than DEFAULT_RESULTAT
        defaultPassenShouldNotBeFound("resultat.greaterThan=" + DEFAULT_RESULTAT);

        // Get all the passenList where resultat is greater than SMALLER_RESULTAT
        defaultPassenShouldBeFound("resultat.greaterThan=" + SMALLER_RESULTAT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPassenShouldBeFound(String filter) throws Exception {
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passen.getId().intValue())))
            .andExpect(jsonPath("$.[*].p1").value(hasItem(DEFAULT_P_1)))
            .andExpect(jsonPath("$.[*].p2").value(hasItem(DEFAULT_P_2)))
            .andExpect(jsonPath("$.[*].p3").value(hasItem(DEFAULT_P_3)))
            .andExpect(jsonPath("$.[*].p4").value(hasItem(DEFAULT_P_4)))
            .andExpect(jsonPath("$.[*].p5").value(hasItem(DEFAULT_P_5)))
            .andExpect(jsonPath("$.[*].p6").value(hasItem(DEFAULT_P_6)))
            .andExpect(jsonPath("$.[*].p7").value(hasItem(DEFAULT_P_7)))
            .andExpect(jsonPath("$.[*].p8").value(hasItem(DEFAULT_P_8)))
            .andExpect(jsonPath("$.[*].p9").value(hasItem(DEFAULT_P_9)))
            .andExpect(jsonPath("$.[*].p10").value(hasItem(DEFAULT_P_10)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)));

        // Check, that the count call also returns 1
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPassenShouldNotBeFound(String filter) throws Exception {
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPassen() throws Exception {
        // Get the passen
        restPassenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen
        Passen updatedPassen = passenRepository.findById(passen.getId()).get();
        // Disconnect from session so that the updates on updatedPassen are not directly saved in db
        em.detach(updatedPassen);
        updatedPassen
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);
        PassenDTO passenDTO = passenMapper.toDto(updatedPassen);

        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(UPDATED_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(UPDATED_P_7);
        assertThat(testPassen.getp8()).isEqualTo(UPDATED_P_8);
        assertThat(testPassen.getp9()).isEqualTo(UPDATED_P_9);
        assertThat(testPassen.getp10()).isEqualTo(UPDATED_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void putNonExistingPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassenWithPatch() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen using partial update
        Passen partialUpdatedPassen = new Passen();
        partialUpdatedPassen.setId(passen.getId());

        partialUpdatedPassen.p1(UPDATED_P_1).p2(UPDATED_P_2).p3(UPDATED_P_3).p4(UPDATED_P_4).p6(UPDATED_P_6).resultat(UPDATED_RESULTAT);

        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassen))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(DEFAULT_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(DEFAULT_P_7);
        assertThat(testPassen.getp8()).isEqualTo(DEFAULT_P_8);
        assertThat(testPassen.getp9()).isEqualTo(DEFAULT_P_9);
        assertThat(testPassen.getp10()).isEqualTo(DEFAULT_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void fullUpdatePassenWithPatch() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen using partial update
        Passen partialUpdatedPassen = new Passen();
        partialUpdatedPassen.setId(passen.getId());

        partialUpdatedPassen
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);

        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassen))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(UPDATED_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(UPDATED_P_7);
        assertThat(testPassen.getp8()).isEqualTo(UPDATED_P_8);
        assertThat(testPassen.getp9()).isEqualTo(UPDATED_P_9);
        assertThat(testPassen.getp10()).isEqualTo(UPDATED_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void patchNonExistingPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // Create the Passen
        PassenDTO passenDTO = passenMapper.toDto(passen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(passenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeDelete = passenRepository.findAll().size();

        // Delete the passen
        restPassenMockMvc
            .perform(delete(ENTITY_API_URL_ID, passen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
