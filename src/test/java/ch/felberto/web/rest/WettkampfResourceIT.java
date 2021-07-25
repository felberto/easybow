package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.criteria.WettkampfCriteria;
import ch.felberto.service.dto.WettkampfDTO;
import ch.felberto.service.mapper.WettkampfMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link WettkampfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WettkampfResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JAHR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JAHR = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_JAHR = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_ANZAHL_RUNDEN = 1;
    private static final Integer UPDATED_ANZAHL_RUNDEN = 2;
    private static final Integer SMALLER_ANZAHL_RUNDEN = 1 - 1;

    private static final Integer DEFAULT_ANZAHL_PASSEN = 1;
    private static final Integer UPDATED_ANZAHL_PASSEN = 2;
    private static final Integer SMALLER_ANZAHL_PASSEN = 1 - 1;

    private static final Boolean DEFAULT_FINAL_RUNDE = false;
    private static final Boolean UPDATED_FINAL_RUNDE = true;

    private static final Boolean DEFAULT_FINAL_VORBEREITUNG = false;
    private static final Boolean UPDATED_FINAL_VORBEREITUNG = true;

    private static final Integer DEFAULT_ANZAHL_FINALTEILNEHMER = 1;
    private static final Integer UPDATED_ANZAHL_FINALTEILNEHMER = 2;
    private static final Integer SMALLER_ANZAHL_FINALTEILNEHMER = 1 - 1;

    private static final Integer DEFAULT_ANZAHL_PASSEN_FINAL = 1;
    private static final Integer UPDATED_ANZAHL_PASSEN_FINAL = 2;
    private static final Integer SMALLER_ANZAHL_PASSEN_FINAL = 1 - 1;

    private static final Integer DEFAULT_ANZAHL_TEAM = 1;
    private static final Integer UPDATED_ANZAHL_TEAM = 2;
    private static final Integer SMALLER_ANZAHL_TEAM = 1 - 1;

    private static final Boolean DEFAULT_TEMPLATE = false;
    private static final Boolean UPDATED_TEMPLATE = true;

    private static final String ENTITY_API_URL = "/api/wettkampfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WettkampfRepository wettkampfRepository;

    @Autowired
    private WettkampfMapper wettkampfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWettkampfMockMvc;

    private Wettkampf wettkampf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wettkampf createEntity(EntityManager em) {
        Wettkampf wettkampf = new Wettkampf()
            .name(DEFAULT_NAME)
            .jahr(DEFAULT_JAHR)
            .anzahlRunden(DEFAULT_ANZAHL_RUNDEN)
            .anzahlPassen(DEFAULT_ANZAHL_PASSEN)
            .finalRunde(DEFAULT_FINAL_RUNDE)
            .finalVorbereitung(DEFAULT_FINAL_VORBEREITUNG)
            .anzahlFinalteilnehmer(DEFAULT_ANZAHL_FINALTEILNEHMER)
            .anzahlPassenFinal(DEFAULT_ANZAHL_PASSEN_FINAL)
            .anzahlTeam(DEFAULT_ANZAHL_TEAM)
            .template(DEFAULT_TEMPLATE);
        return wettkampf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wettkampf createUpdatedEntity(EntityManager em) {
        Wettkampf wettkampf = new Wettkampf()
            .name(UPDATED_NAME)
            .jahr(UPDATED_JAHR)
            .anzahlRunden(UPDATED_ANZAHL_RUNDEN)
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .finalRunde(UPDATED_FINAL_RUNDE)
            .finalVorbereitung(UPDATED_FINAL_VORBEREITUNG)
            .anzahlFinalteilnehmer(UPDATED_ANZAHL_FINALTEILNEHMER)
            .anzahlPassenFinal(UPDATED_ANZAHL_PASSEN_FINAL)
            .anzahlTeam(UPDATED_ANZAHL_TEAM)
            .template(UPDATED_TEMPLATE);
        return wettkampf;
    }

    @BeforeEach
    public void initTest() {
        wettkampf = createEntity(em);
    }

    @Test
    @Transactional
    void createWettkampf() throws Exception {
        int databaseSizeBeforeCreate = wettkampfRepository.findAll().size();
        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);
        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isCreated());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeCreate + 1);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(DEFAULT_JAHR);
        assertThat(testWettkampf.getAnzahlRunden()).isEqualTo(DEFAULT_ANZAHL_RUNDEN);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(DEFAULT_ANZAHL_PASSEN);
        assertThat(testWettkampf.getFinalRunde()).isEqualTo(DEFAULT_FINAL_RUNDE);
        assertThat(testWettkampf.getFinalVorbereitung()).isEqualTo(DEFAULT_FINAL_VORBEREITUNG);
        assertThat(testWettkampf.getAnzahlFinalteilnehmer()).isEqualTo(DEFAULT_ANZAHL_FINALTEILNEHMER);
        assertThat(testWettkampf.getAnzahlPassenFinal()).isEqualTo(DEFAULT_ANZAHL_PASSEN_FINAL);
        assertThat(testWettkampf.getAnzahlTeam()).isEqualTo(DEFAULT_ANZAHL_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    void createWettkampfWithExistingId() throws Exception {
        // Create the Wettkampf with an existing ID
        wettkampf.setId(1L);
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        int databaseSizeBeforeCreate = wettkampfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wettkampfRepository.findAll().size();
        // set the field null
        wettkampf.setName(null);

        // Create the Wettkampf, which fails.
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isBadRequest());

        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnzahlRundenIsRequired() throws Exception {
        int databaseSizeBeforeTest = wettkampfRepository.findAll().size();
        // set the field null
        wettkampf.setAnzahlRunden(null);

        // Create the Wettkampf, which fails.
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isBadRequest());

        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnzahlPassenIsRequired() throws Exception {
        int databaseSizeBeforeTest = wettkampfRepository.findAll().size();
        // set the field null
        wettkampf.setAnzahlPassen(null);

        // Create the Wettkampf, which fails.
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isBadRequest());

        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWettkampfs() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wettkampf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jahr").value(hasItem(DEFAULT_JAHR.toString())))
            .andExpect(jsonPath("$.[*].anzahlRunden").value(hasItem(DEFAULT_ANZAHL_RUNDEN)))
            .andExpect(jsonPath("$.[*].anzahlPassen").value(hasItem(DEFAULT_ANZAHL_PASSEN)))
            .andExpect(jsonPath("$.[*].finalRunde").value(hasItem(DEFAULT_FINAL_RUNDE.booleanValue())))
            .andExpect(jsonPath("$.[*].finalVorbereitung").value(hasItem(DEFAULT_FINAL_VORBEREITUNG.booleanValue())))
            .andExpect(jsonPath("$.[*].anzahlFinalteilnehmer").value(hasItem(DEFAULT_ANZAHL_FINALTEILNEHMER)))
            .andExpect(jsonPath("$.[*].anzahlPassenFinal").value(hasItem(DEFAULT_ANZAHL_PASSEN_FINAL)))
            .andExpect(jsonPath("$.[*].anzahlTeam").value(hasItem(DEFAULT_ANZAHL_TEAM)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.booleanValue())));
    }

    @Test
    @Transactional
    void getWettkampf() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get the wettkampf
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL_ID, wettkampf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wettkampf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.jahr").value(DEFAULT_JAHR.toString()))
            .andExpect(jsonPath("$.anzahlRunden").value(DEFAULT_ANZAHL_RUNDEN))
            .andExpect(jsonPath("$.anzahlPassen").value(DEFAULT_ANZAHL_PASSEN))
            .andExpect(jsonPath("$.finalRunde").value(DEFAULT_FINAL_RUNDE.booleanValue()))
            .andExpect(jsonPath("$.finalVorbereitung").value(DEFAULT_FINAL_VORBEREITUNG.booleanValue()))
            .andExpect(jsonPath("$.anzahlFinalteilnehmer").value(DEFAULT_ANZAHL_FINALTEILNEHMER))
            .andExpect(jsonPath("$.anzahlPassenFinal").value(DEFAULT_ANZAHL_PASSEN_FINAL))
            .andExpect(jsonPath("$.anzahlTeam").value(DEFAULT_ANZAHL_TEAM))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.booleanValue()));
    }

    @Test
    @Transactional
    void getWettkampfsByIdFiltering() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        Long id = wettkampf.getId();

        defaultWettkampfShouldBeFound("id.equals=" + id);
        defaultWettkampfShouldNotBeFound("id.notEquals=" + id);

        defaultWettkampfShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWettkampfShouldNotBeFound("id.greaterThan=" + id);

        defaultWettkampfShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWettkampfShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name equals to DEFAULT_NAME
        defaultWettkampfShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the wettkampfList where name equals to UPDATED_NAME
        defaultWettkampfShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name not equals to DEFAULT_NAME
        defaultWettkampfShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the wettkampfList where name not equals to UPDATED_NAME
        defaultWettkampfShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWettkampfShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the wettkampfList where name equals to UPDATED_NAME
        defaultWettkampfShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name is not null
        defaultWettkampfShouldBeFound("name.specified=true");

        // Get all the wettkampfList where name is null
        defaultWettkampfShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameContainsSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name contains DEFAULT_NAME
        defaultWettkampfShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the wettkampfList where name contains UPDATED_NAME
        defaultWettkampfShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWettkampfsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where name does not contain DEFAULT_NAME
        defaultWettkampfShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the wettkampfList where name does not contain UPDATED_NAME
        defaultWettkampfShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr equals to DEFAULT_JAHR
        defaultWettkampfShouldBeFound("jahr.equals=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr equals to UPDATED_JAHR
        defaultWettkampfShouldNotBeFound("jahr.equals=" + UPDATED_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr not equals to DEFAULT_JAHR
        defaultWettkampfShouldNotBeFound("jahr.notEquals=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr not equals to UPDATED_JAHR
        defaultWettkampfShouldBeFound("jahr.notEquals=" + UPDATED_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr in DEFAULT_JAHR or UPDATED_JAHR
        defaultWettkampfShouldBeFound("jahr.in=" + DEFAULT_JAHR + "," + UPDATED_JAHR);

        // Get all the wettkampfList where jahr equals to UPDATED_JAHR
        defaultWettkampfShouldNotBeFound("jahr.in=" + UPDATED_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr is not null
        defaultWettkampfShouldBeFound("jahr.specified=true");

        // Get all the wettkampfList where jahr is null
        defaultWettkampfShouldNotBeFound("jahr.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr is greater than or equal to DEFAULT_JAHR
        defaultWettkampfShouldBeFound("jahr.greaterThanOrEqual=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr is greater than or equal to UPDATED_JAHR
        defaultWettkampfShouldNotBeFound("jahr.greaterThanOrEqual=" + UPDATED_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr is less than or equal to DEFAULT_JAHR
        defaultWettkampfShouldBeFound("jahr.lessThanOrEqual=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr is less than or equal to SMALLER_JAHR
        defaultWettkampfShouldNotBeFound("jahr.lessThanOrEqual=" + SMALLER_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr is less than DEFAULT_JAHR
        defaultWettkampfShouldNotBeFound("jahr.lessThan=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr is less than UPDATED_JAHR
        defaultWettkampfShouldBeFound("jahr.lessThan=" + UPDATED_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByJahrIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where jahr is greater than DEFAULT_JAHR
        defaultWettkampfShouldNotBeFound("jahr.greaterThan=" + DEFAULT_JAHR);

        // Get all the wettkampfList where jahr is greater than SMALLER_JAHR
        defaultWettkampfShouldBeFound("jahr.greaterThan=" + SMALLER_JAHR);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden equals to DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.equals=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden equals to UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.equals=" + UPDATED_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden not equals to DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.notEquals=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden not equals to UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.notEquals=" + UPDATED_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden in DEFAULT_ANZAHL_RUNDEN or UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.in=" + DEFAULT_ANZAHL_RUNDEN + "," + UPDATED_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden equals to UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.in=" + UPDATED_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden is not null
        defaultWettkampfShouldBeFound("anzahlRunden.specified=true");

        // Get all the wettkampfList where anzahlRunden is null
        defaultWettkampfShouldNotBeFound("anzahlRunden.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden is greater than or equal to DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.greaterThanOrEqual=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden is greater than or equal to UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.greaterThanOrEqual=" + UPDATED_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden is less than or equal to DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.lessThanOrEqual=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden is less than or equal to SMALLER_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.lessThanOrEqual=" + SMALLER_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden is less than DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.lessThan=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden is less than UPDATED_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.lessThan=" + UPDATED_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlRundenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlRunden is greater than DEFAULT_ANZAHL_RUNDEN
        defaultWettkampfShouldNotBeFound("anzahlRunden.greaterThan=" + DEFAULT_ANZAHL_RUNDEN);

        // Get all the wettkampfList where anzahlRunden is greater than SMALLER_ANZAHL_RUNDEN
        defaultWettkampfShouldBeFound("anzahlRunden.greaterThan=" + SMALLER_ANZAHL_RUNDEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen equals to DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.equals=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen equals to UPDATED_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.equals=" + UPDATED_ANZAHL_PASSEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen not equals to DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.notEquals=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen not equals to UPDATED_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.notEquals=" + UPDATED_ANZAHL_PASSEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen in DEFAULT_ANZAHL_PASSEN or UPDATED_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.in=" + DEFAULT_ANZAHL_PASSEN + "," + UPDATED_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen equals to UPDATED_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.in=" + UPDATED_ANZAHL_PASSEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen is not null
        defaultWettkampfShouldBeFound("anzahlPassen.specified=true");

        // Get all the wettkampfList where anzahlPassen is null
        defaultWettkampfShouldNotBeFound("anzahlPassen.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen is greater than or equal to DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.greaterThanOrEqual=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen is greater than or equal to (DEFAULT_ANZAHL_PASSEN + 1)
        defaultWettkampfShouldNotBeFound("anzahlPassen.greaterThanOrEqual=" + (DEFAULT_ANZAHL_PASSEN + 1));
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen is less than or equal to DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.lessThanOrEqual=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen is less than or equal to SMALLER_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.lessThanOrEqual=" + SMALLER_ANZAHL_PASSEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen is less than DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.lessThan=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen is less than (DEFAULT_ANZAHL_PASSEN + 1)
        defaultWettkampfShouldBeFound("anzahlPassen.lessThan=" + (DEFAULT_ANZAHL_PASSEN + 1));
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassen is greater than DEFAULT_ANZAHL_PASSEN
        defaultWettkampfShouldNotBeFound("anzahlPassen.greaterThan=" + DEFAULT_ANZAHL_PASSEN);

        // Get all the wettkampfList where anzahlPassen is greater than SMALLER_ANZAHL_PASSEN
        defaultWettkampfShouldBeFound("anzahlPassen.greaterThan=" + SMALLER_ANZAHL_PASSEN);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalRundeIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalRunde equals to DEFAULT_FINAL_RUNDE
        defaultWettkampfShouldBeFound("finalRunde.equals=" + DEFAULT_FINAL_RUNDE);

        // Get all the wettkampfList where finalRunde equals to UPDATED_FINAL_RUNDE
        defaultWettkampfShouldNotBeFound("finalRunde.equals=" + UPDATED_FINAL_RUNDE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalRundeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalRunde not equals to DEFAULT_FINAL_RUNDE
        defaultWettkampfShouldNotBeFound("finalRunde.notEquals=" + DEFAULT_FINAL_RUNDE);

        // Get all the wettkampfList where finalRunde not equals to UPDATED_FINAL_RUNDE
        defaultWettkampfShouldBeFound("finalRunde.notEquals=" + UPDATED_FINAL_RUNDE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalRundeIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalRunde in DEFAULT_FINAL_RUNDE or UPDATED_FINAL_RUNDE
        defaultWettkampfShouldBeFound("finalRunde.in=" + DEFAULT_FINAL_RUNDE + "," + UPDATED_FINAL_RUNDE);

        // Get all the wettkampfList where finalRunde equals to UPDATED_FINAL_RUNDE
        defaultWettkampfShouldNotBeFound("finalRunde.in=" + UPDATED_FINAL_RUNDE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalRundeIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalRunde is not null
        defaultWettkampfShouldBeFound("finalRunde.specified=true");

        // Get all the wettkampfList where finalRunde is null
        defaultWettkampfShouldNotBeFound("finalRunde.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalVorbereitungIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalVorbereitung equals to DEFAULT_FINAL_VORBEREITUNG
        defaultWettkampfShouldBeFound("finalVorbereitung.equals=" + DEFAULT_FINAL_VORBEREITUNG);

        // Get all the wettkampfList where finalVorbereitung equals to UPDATED_FINAL_VORBEREITUNG
        defaultWettkampfShouldNotBeFound("finalVorbereitung.equals=" + UPDATED_FINAL_VORBEREITUNG);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalVorbereitungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalVorbereitung not equals to DEFAULT_FINAL_VORBEREITUNG
        defaultWettkampfShouldNotBeFound("finalVorbereitung.notEquals=" + DEFAULT_FINAL_VORBEREITUNG);

        // Get all the wettkampfList where finalVorbereitung not equals to UPDATED_FINAL_VORBEREITUNG
        defaultWettkampfShouldBeFound("finalVorbereitung.notEquals=" + UPDATED_FINAL_VORBEREITUNG);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalVorbereitungIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalVorbereitung in DEFAULT_FINAL_VORBEREITUNG or UPDATED_FINAL_VORBEREITUNG
        defaultWettkampfShouldBeFound("finalVorbereitung.in=" + DEFAULT_FINAL_VORBEREITUNG + "," + UPDATED_FINAL_VORBEREITUNG);

        // Get all the wettkampfList where finalVorbereitung equals to UPDATED_FINAL_VORBEREITUNG
        defaultWettkampfShouldNotBeFound("finalVorbereitung.in=" + UPDATED_FINAL_VORBEREITUNG);
    }

    @Test
    @Transactional
    void getAllWettkampfsByFinalVorbereitungIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where finalVorbereitung is not null
        defaultWettkampfShouldBeFound("finalVorbereitung.specified=true");

        // Get all the wettkampfList where finalVorbereitung is null
        defaultWettkampfShouldNotBeFound("finalVorbereitung.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer equals to DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.equals=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer equals to UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.equals=" + UPDATED_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer not equals to DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.notEquals=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer not equals to UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.notEquals=" + UPDATED_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer in DEFAULT_ANZAHL_FINALTEILNEHMER or UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.in=" + DEFAULT_ANZAHL_FINALTEILNEHMER + "," + UPDATED_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer equals to UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.in=" + UPDATED_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer is not null
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.specified=true");

        // Get all the wettkampfList where anzahlFinalteilnehmer is null
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer is greater than or equal to DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.greaterThanOrEqual=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer is greater than or equal to UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.greaterThanOrEqual=" + UPDATED_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer is less than or equal to DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.lessThanOrEqual=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer is less than or equal to SMALLER_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.lessThanOrEqual=" + SMALLER_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer is less than DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.lessThan=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer is less than UPDATED_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.lessThan=" + UPDATED_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlFinalteilnehmerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlFinalteilnehmer is greater than DEFAULT_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldNotBeFound("anzahlFinalteilnehmer.greaterThan=" + DEFAULT_ANZAHL_FINALTEILNEHMER);

        // Get all the wettkampfList where anzahlFinalteilnehmer is greater than SMALLER_ANZAHL_FINALTEILNEHMER
        defaultWettkampfShouldBeFound("anzahlFinalteilnehmer.greaterThan=" + SMALLER_ANZAHL_FINALTEILNEHMER);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal equals to DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.equals=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal equals to UPDATED_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.equals=" + UPDATED_ANZAHL_PASSEN_FINAL);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal not equals to DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.notEquals=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal not equals to UPDATED_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.notEquals=" + UPDATED_ANZAHL_PASSEN_FINAL);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal in DEFAULT_ANZAHL_PASSEN_FINAL or UPDATED_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.in=" + DEFAULT_ANZAHL_PASSEN_FINAL + "," + UPDATED_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal equals to UPDATED_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.in=" + UPDATED_ANZAHL_PASSEN_FINAL);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal is not null
        defaultWettkampfShouldBeFound("anzahlPassenFinal.specified=true");

        // Get all the wettkampfList where anzahlPassenFinal is null
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal is greater than or equal to DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.greaterThanOrEqual=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal is greater than or equal to (DEFAULT_ANZAHL_PASSEN_FINAL + 1)
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.greaterThanOrEqual=" + (DEFAULT_ANZAHL_PASSEN_FINAL + 1));
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal is less than or equal to DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.lessThanOrEqual=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal is less than or equal to SMALLER_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.lessThanOrEqual=" + SMALLER_ANZAHL_PASSEN_FINAL);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal is less than DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.lessThan=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal is less than (DEFAULT_ANZAHL_PASSEN_FINAL + 1)
        defaultWettkampfShouldBeFound("anzahlPassenFinal.lessThan=" + (DEFAULT_ANZAHL_PASSEN_FINAL + 1));
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlPassenFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlPassenFinal is greater than DEFAULT_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldNotBeFound("anzahlPassenFinal.greaterThan=" + DEFAULT_ANZAHL_PASSEN_FINAL);

        // Get all the wettkampfList where anzahlPassenFinal is greater than SMALLER_ANZAHL_PASSEN_FINAL
        defaultWettkampfShouldBeFound("anzahlPassenFinal.greaterThan=" + SMALLER_ANZAHL_PASSEN_FINAL);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam equals to DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.equals=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam equals to UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.equals=" + UPDATED_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam not equals to DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.notEquals=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam not equals to UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.notEquals=" + UPDATED_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam in DEFAULT_ANZAHL_TEAM or UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.in=" + DEFAULT_ANZAHL_TEAM + "," + UPDATED_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam equals to UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.in=" + UPDATED_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam is not null
        defaultWettkampfShouldBeFound("anzahlTeam.specified=true");

        // Get all the wettkampfList where anzahlTeam is null
        defaultWettkampfShouldNotBeFound("anzahlTeam.specified=false");
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam is greater than or equal to DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.greaterThanOrEqual=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam is greater than or equal to UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.greaterThanOrEqual=" + UPDATED_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam is less than or equal to DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.lessThanOrEqual=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam is less than or equal to SMALLER_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.lessThanOrEqual=" + SMALLER_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsLessThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam is less than DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.lessThan=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam is less than UPDATED_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.lessThan=" + UPDATED_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByAnzahlTeamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where anzahlTeam is greater than DEFAULT_ANZAHL_TEAM
        defaultWettkampfShouldNotBeFound("anzahlTeam.greaterThan=" + DEFAULT_ANZAHL_TEAM);

        // Get all the wettkampfList where anzahlTeam is greater than SMALLER_ANZAHL_TEAM
        defaultWettkampfShouldBeFound("anzahlTeam.greaterThan=" + SMALLER_ANZAHL_TEAM);
    }

    @Test
    @Transactional
    void getAllWettkampfsByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where template equals to DEFAULT_TEMPLATE
        defaultWettkampfShouldBeFound("template.equals=" + DEFAULT_TEMPLATE);

        // Get all the wettkampfList where template equals to UPDATED_TEMPLATE
        defaultWettkampfShouldNotBeFound("template.equals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByTemplateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where template not equals to DEFAULT_TEMPLATE
        defaultWettkampfShouldNotBeFound("template.notEquals=" + DEFAULT_TEMPLATE);

        // Get all the wettkampfList where template not equals to UPDATED_TEMPLATE
        defaultWettkampfShouldBeFound("template.notEquals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where template in DEFAULT_TEMPLATE or UPDATED_TEMPLATE
        defaultWettkampfShouldBeFound("template.in=" + DEFAULT_TEMPLATE + "," + UPDATED_TEMPLATE);

        // Get all the wettkampfList where template equals to UPDATED_TEMPLATE
        defaultWettkampfShouldNotBeFound("template.in=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void getAllWettkampfsByTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        // Get all the wettkampfList where template is not null
        defaultWettkampfShouldBeFound("template.specified=true");

        // Get all the wettkampfList where template is null
        defaultWettkampfShouldNotBeFound("template.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWettkampfShouldBeFound(String filter) throws Exception {
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wettkampf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jahr").value(hasItem(DEFAULT_JAHR.toString())))
            .andExpect(jsonPath("$.[*].anzahlRunden").value(hasItem(DEFAULT_ANZAHL_RUNDEN)))
            .andExpect(jsonPath("$.[*].anzahlPassen").value(hasItem(DEFAULT_ANZAHL_PASSEN)))
            .andExpect(jsonPath("$.[*].finalRunde").value(hasItem(DEFAULT_FINAL_RUNDE.booleanValue())))
            .andExpect(jsonPath("$.[*].finalVorbereitung").value(hasItem(DEFAULT_FINAL_VORBEREITUNG.booleanValue())))
            .andExpect(jsonPath("$.[*].anzahlFinalteilnehmer").value(hasItem(DEFAULT_ANZAHL_FINALTEILNEHMER)))
            .andExpect(jsonPath("$.[*].anzahlPassenFinal").value(hasItem(DEFAULT_ANZAHL_PASSEN_FINAL)))
            .andExpect(jsonPath("$.[*].anzahlTeam").value(hasItem(DEFAULT_ANZAHL_TEAM)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.booleanValue())));

        // Check, that the count call also returns 1
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWettkampfShouldNotBeFound(String filter) throws Exception {
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWettkampfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWettkampf() throws Exception {
        // Get the wettkampf
        restWettkampfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWettkampf() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();

        // Update the wettkampf
        Wettkampf updatedWettkampf = wettkampfRepository.findById(wettkampf.getId()).get();
        // Disconnect from session so that the updates on updatedWettkampf are not directly saved in db
        em.detach(updatedWettkampf);
        updatedWettkampf
            .name(UPDATED_NAME)
            .jahr(UPDATED_JAHR)
            .anzahlRunden(UPDATED_ANZAHL_RUNDEN)
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .finalRunde(UPDATED_FINAL_RUNDE)
            .finalVorbereitung(UPDATED_FINAL_VORBEREITUNG)
            .anzahlFinalteilnehmer(UPDATED_ANZAHL_FINALTEILNEHMER)
            .anzahlPassenFinal(UPDATED_ANZAHL_PASSEN_FINAL)
            .anzahlTeam(UPDATED_ANZAHL_TEAM)
            .template(UPDATED_TEMPLATE);
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(updatedWettkampf);

        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wettkampfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isOk());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(UPDATED_JAHR);
        assertThat(testWettkampf.getAnzahlRunden()).isEqualTo(UPDATED_ANZAHL_RUNDEN);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(UPDATED_ANZAHL_PASSEN);
        assertThat(testWettkampf.getFinalRunde()).isEqualTo(UPDATED_FINAL_RUNDE);
        assertThat(testWettkampf.getFinalVorbereitung()).isEqualTo(UPDATED_FINAL_VORBEREITUNG);
        assertThat(testWettkampf.getAnzahlFinalteilnehmer()).isEqualTo(UPDATED_ANZAHL_FINALTEILNEHMER);
        assertThat(testWettkampf.getAnzahlPassenFinal()).isEqualTo(UPDATED_ANZAHL_PASSEN_FINAL);
        assertThat(testWettkampf.getAnzahlTeam()).isEqualTo(UPDATED_ANZAHL_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void putNonExistingWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wettkampfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWettkampfWithPatch() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();

        // Update the wettkampf using partial update
        Wettkampf partialUpdatedWettkampf = new Wettkampf();
        partialUpdatedWettkampf.setId(wettkampf.getId());

        partialUpdatedWettkampf.name(UPDATED_NAME).anzahlPassen(UPDATED_ANZAHL_PASSEN).finalRunde(UPDATED_FINAL_RUNDE);

        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWettkampf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWettkampf))
            )
            .andExpect(status().isOk());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(DEFAULT_JAHR);
        assertThat(testWettkampf.getAnzahlRunden()).isEqualTo(DEFAULT_ANZAHL_RUNDEN);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(UPDATED_ANZAHL_PASSEN);
        assertThat(testWettkampf.getFinalRunde()).isEqualTo(UPDATED_FINAL_RUNDE);
        assertThat(testWettkampf.getFinalVorbereitung()).isEqualTo(DEFAULT_FINAL_VORBEREITUNG);
        assertThat(testWettkampf.getAnzahlFinalteilnehmer()).isEqualTo(DEFAULT_ANZAHL_FINALTEILNEHMER);
        assertThat(testWettkampf.getAnzahlPassenFinal()).isEqualTo(DEFAULT_ANZAHL_PASSEN_FINAL);
        assertThat(testWettkampf.getAnzahlTeam()).isEqualTo(DEFAULT_ANZAHL_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    void fullUpdateWettkampfWithPatch() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();

        // Update the wettkampf using partial update
        Wettkampf partialUpdatedWettkampf = new Wettkampf();
        partialUpdatedWettkampf.setId(wettkampf.getId());

        partialUpdatedWettkampf
            .name(UPDATED_NAME)
            .jahr(UPDATED_JAHR)
            .anzahlRunden(UPDATED_ANZAHL_RUNDEN)
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .finalRunde(UPDATED_FINAL_RUNDE)
            .finalVorbereitung(UPDATED_FINAL_VORBEREITUNG)
            .anzahlFinalteilnehmer(UPDATED_ANZAHL_FINALTEILNEHMER)
            .anzahlPassenFinal(UPDATED_ANZAHL_PASSEN_FINAL)
            .anzahlTeam(UPDATED_ANZAHL_TEAM)
            .template(UPDATED_TEMPLATE);

        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWettkampf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWettkampf))
            )
            .andExpect(status().isOk());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(UPDATED_JAHR);
        assertThat(testWettkampf.getAnzahlRunden()).isEqualTo(UPDATED_ANZAHL_RUNDEN);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(UPDATED_ANZAHL_PASSEN);
        assertThat(testWettkampf.getFinalRunde()).isEqualTo(UPDATED_FINAL_RUNDE);
        assertThat(testWettkampf.getFinalVorbereitung()).isEqualTo(UPDATED_FINAL_VORBEREITUNG);
        assertThat(testWettkampf.getAnzahlFinalteilnehmer()).isEqualTo(UPDATED_ANZAHL_FINALTEILNEHMER);
        assertThat(testWettkampf.getAnzahlPassenFinal()).isEqualTo(UPDATED_ANZAHL_PASSEN_FINAL);
        assertThat(testWettkampf.getAnzahlTeam()).isEqualTo(UPDATED_ANZAHL_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void patchNonExistingWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wettkampfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // Create the Wettkampf
        WettkampfDTO wettkampfDTO = wettkampfMapper.toDto(wettkampf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wettkampfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWettkampf() throws Exception {
        // Initialize the database
        wettkampfRepository.saveAndFlush(wettkampf);

        int databaseSizeBeforeDelete = wettkampfRepository.findAll().size();

        // Delete the wettkampf
        restWettkampfMockMvc
            .perform(delete(ENTITY_API_URL_ID, wettkampf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
