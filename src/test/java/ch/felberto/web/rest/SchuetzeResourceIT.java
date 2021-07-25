package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Schuetze;
import ch.felberto.domain.Verein;
import ch.felberto.domain.enumeration.Stellung;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.criteria.SchuetzeCriteria;
import ch.felberto.service.dto.SchuetzeDTO;
import ch.felberto.service.mapper.SchuetzeMapper;
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
 * Integration tests for the {@link SchuetzeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchuetzeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_JAHRGANG = 1;
    private static final Integer UPDATED_JAHRGANG = 2;
    private static final Integer SMALLER_JAHRGANG = 1 - 1;

    private static final Stellung DEFAULT_STELLUNG = Stellung.FREI;
    private static final Stellung UPDATED_STELLUNG = Stellung.AUFGELEGT;

    private static final String ENTITY_API_URL = "/api/schuetzes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchuetzeRepository schuetzeRepository;

    @Autowired
    private SchuetzeMapper schuetzeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchuetzeMockMvc;

    private Schuetze schuetze;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schuetze createEntity(EntityManager em) {
        Schuetze schuetze = new Schuetze().name(DEFAULT_NAME).jahrgang(DEFAULT_JAHRGANG).stellung(DEFAULT_STELLUNG);
        return schuetze;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schuetze createUpdatedEntity(EntityManager em) {
        Schuetze schuetze = new Schuetze().name(UPDATED_NAME).jahrgang(UPDATED_JAHRGANG).stellung(UPDATED_STELLUNG);
        return schuetze;
    }

    @BeforeEach
    public void initTest() {
        schuetze = createEntity(em);
    }

    @Test
    @Transactional
    void createSchuetze() throws Exception {
        int databaseSizeBeforeCreate = schuetzeRepository.findAll().size();
        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);
        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isCreated());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeCreate + 1);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(DEFAULT_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(DEFAULT_STELLUNG);
    }

    @Test
    @Transactional
    void createSchuetzeWithExistingId() throws Exception {
        // Create the Schuetze with an existing ID
        schuetze.setId(1L);
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        int databaseSizeBeforeCreate = schuetzeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuetzeRepository.findAll().size();
        // set the field null
        schuetze.setName(null);

        // Create the Schuetze, which fails.
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isBadRequest());

        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJahrgangIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuetzeRepository.findAll().size();
        // set the field null
        schuetze.setJahrgang(null);

        // Create the Schuetze, which fails.
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isBadRequest());

        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStellungIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuetzeRepository.findAll().size();
        // set the field null
        schuetze.setStellung(null);

        // Create the Schuetze, which fails.
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isBadRequest());

        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSchuetzes() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schuetze.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jahrgang").value(hasItem(DEFAULT_JAHRGANG)))
            .andExpect(jsonPath("$.[*].stellung").value(hasItem(DEFAULT_STELLUNG.toString())));
    }

    @Test
    @Transactional
    void getSchuetze() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get the schuetze
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL_ID, schuetze.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schuetze.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.jahrgang").value(DEFAULT_JAHRGANG))
            .andExpect(jsonPath("$.stellung").value(DEFAULT_STELLUNG.toString()));
    }

    @Test
    @Transactional
    void getSchuetzesByIdFiltering() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        Long id = schuetze.getId();

        defaultSchuetzeShouldBeFound("id.equals=" + id);
        defaultSchuetzeShouldNotBeFound("id.notEquals=" + id);

        defaultSchuetzeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchuetzeShouldNotBeFound("id.greaterThan=" + id);

        defaultSchuetzeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchuetzeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name equals to DEFAULT_NAME
        defaultSchuetzeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the schuetzeList where name equals to UPDATED_NAME
        defaultSchuetzeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name not equals to DEFAULT_NAME
        defaultSchuetzeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the schuetzeList where name not equals to UPDATED_NAME
        defaultSchuetzeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSchuetzeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the schuetzeList where name equals to UPDATED_NAME
        defaultSchuetzeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name is not null
        defaultSchuetzeShouldBeFound("name.specified=true");

        // Get all the schuetzeList where name is null
        defaultSchuetzeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameContainsSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name contains DEFAULT_NAME
        defaultSchuetzeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the schuetzeList where name contains UPDATED_NAME
        defaultSchuetzeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchuetzesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where name does not contain DEFAULT_NAME
        defaultSchuetzeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the schuetzeList where name does not contain UPDATED_NAME
        defaultSchuetzeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang equals to DEFAULT_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.equals=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang equals to UPDATED_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.equals=" + UPDATED_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang not equals to DEFAULT_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.notEquals=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang not equals to UPDATED_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.notEquals=" + UPDATED_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsInShouldWork() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang in DEFAULT_JAHRGANG or UPDATED_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.in=" + DEFAULT_JAHRGANG + "," + UPDATED_JAHRGANG);

        // Get all the schuetzeList where jahrgang equals to UPDATED_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.in=" + UPDATED_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsNullOrNotNull() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang is not null
        defaultSchuetzeShouldBeFound("jahrgang.specified=true");

        // Get all the schuetzeList where jahrgang is null
        defaultSchuetzeShouldNotBeFound("jahrgang.specified=false");
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang is greater than or equal to DEFAULT_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.greaterThanOrEqual=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang is greater than or equal to UPDATED_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.greaterThanOrEqual=" + UPDATED_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang is less than or equal to DEFAULT_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.lessThanOrEqual=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang is less than or equal to SMALLER_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.lessThanOrEqual=" + SMALLER_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsLessThanSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang is less than DEFAULT_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.lessThan=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang is less than UPDATED_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.lessThan=" + UPDATED_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByJahrgangIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where jahrgang is greater than DEFAULT_JAHRGANG
        defaultSchuetzeShouldNotBeFound("jahrgang.greaterThan=" + DEFAULT_JAHRGANG);

        // Get all the schuetzeList where jahrgang is greater than SMALLER_JAHRGANG
        defaultSchuetzeShouldBeFound("jahrgang.greaterThan=" + SMALLER_JAHRGANG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByStellungIsEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where stellung equals to DEFAULT_STELLUNG
        defaultSchuetzeShouldBeFound("stellung.equals=" + DEFAULT_STELLUNG);

        // Get all the schuetzeList where stellung equals to UPDATED_STELLUNG
        defaultSchuetzeShouldNotBeFound("stellung.equals=" + UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByStellungIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where stellung not equals to DEFAULT_STELLUNG
        defaultSchuetzeShouldNotBeFound("stellung.notEquals=" + DEFAULT_STELLUNG);

        // Get all the schuetzeList where stellung not equals to UPDATED_STELLUNG
        defaultSchuetzeShouldBeFound("stellung.notEquals=" + UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByStellungIsInShouldWork() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where stellung in DEFAULT_STELLUNG or UPDATED_STELLUNG
        defaultSchuetzeShouldBeFound("stellung.in=" + DEFAULT_STELLUNG + "," + UPDATED_STELLUNG);

        // Get all the schuetzeList where stellung equals to UPDATED_STELLUNG
        defaultSchuetzeShouldNotBeFound("stellung.in=" + UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void getAllSchuetzesByStellungIsNullOrNotNull() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        // Get all the schuetzeList where stellung is not null
        defaultSchuetzeShouldBeFound("stellung.specified=true");

        // Get all the schuetzeList where stellung is null
        defaultSchuetzeShouldNotBeFound("stellung.specified=false");
    }

    @Test
    @Transactional
    void getAllSchuetzesByVereinIsEqualToSomething() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);
        Verein verein = VereinResourceIT.createEntity(em);
        em.persist(verein);
        em.flush();
        schuetze.setVerein(verein);
        schuetzeRepository.saveAndFlush(schuetze);
        Long vereinId = verein.getId();

        // Get all the schuetzeList where verein equals to vereinId
        defaultSchuetzeShouldBeFound("vereinId.equals=" + vereinId);

        // Get all the schuetzeList where verein equals to (vereinId + 1)
        defaultSchuetzeShouldNotBeFound("vereinId.equals=" + (vereinId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchuetzeShouldBeFound(String filter) throws Exception {
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schuetze.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].jahrgang").value(hasItem(DEFAULT_JAHRGANG)))
            .andExpect(jsonPath("$.[*].stellung").value(hasItem(DEFAULT_STELLUNG.toString())));

        // Check, that the count call also returns 1
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchuetzeShouldNotBeFound(String filter) throws Exception {
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchuetzeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSchuetze() throws Exception {
        // Get the schuetze
        restSchuetzeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchuetze() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();

        // Update the schuetze
        Schuetze updatedSchuetze = schuetzeRepository.findById(schuetze.getId()).get();
        // Disconnect from session so that the updates on updatedSchuetze are not directly saved in db
        em.detach(updatedSchuetze);
        updatedSchuetze.name(UPDATED_NAME).jahrgang(UPDATED_JAHRGANG).stellung(UPDATED_STELLUNG);
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(updatedSchuetze);

        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schuetzeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(UPDATED_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void putNonExistingSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schuetzeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetzeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchuetzeWithPatch() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();

        // Update the schuetze using partial update
        Schuetze partialUpdatedSchuetze = new Schuetze();
        partialUpdatedSchuetze.setId(schuetze.getId());

        partialUpdatedSchuetze.jahrgang(UPDATED_JAHRGANG);

        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchuetze.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchuetze))
            )
            .andExpect(status().isOk());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(UPDATED_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(DEFAULT_STELLUNG);
    }

    @Test
    @Transactional
    void fullUpdateSchuetzeWithPatch() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();

        // Update the schuetze using partial update
        Schuetze partialUpdatedSchuetze = new Schuetze();
        partialUpdatedSchuetze.setId(schuetze.getId());

        partialUpdatedSchuetze.name(UPDATED_NAME).jahrgang(UPDATED_JAHRGANG).stellung(UPDATED_STELLUNG);

        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchuetze.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchuetze))
            )
            .andExpect(status().isOk());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(UPDATED_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void patchNonExistingSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schuetzeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // Create the Schuetze
        SchuetzeDTO schuetzeDTO = schuetzeMapper.toDto(schuetze);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(schuetzeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchuetze() throws Exception {
        // Initialize the database
        schuetzeRepository.saveAndFlush(schuetze);

        int databaseSizeBeforeDelete = schuetzeRepository.findAll().size();

        // Delete the schuetze
        restSchuetzeMockMvc
            .perform(delete(ENTITY_API_URL_ID, schuetze.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
