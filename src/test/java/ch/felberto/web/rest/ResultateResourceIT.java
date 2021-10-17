package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Gruppen;
import ch.felberto.domain.Passen;
import ch.felberto.domain.Resultate;
import ch.felberto.domain.Schuetze;
import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.service.criteria.ResultateCriteria;
import ch.felberto.service.dto.ResultateDTO;
import ch.felberto.service.mapper.ResultateMapper;
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
 * Integration tests for the {@link ResultateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultateResourceIT {

    private static final Integer DEFAULT_RUNDE = 1;
    private static final Integer UPDATED_RUNDE = 2;
    private static final Integer SMALLER_RUNDE = 1 - 1;

    private static final Integer DEFAULT_RESULTAT = 1;
    private static final Integer UPDATED_RESULTAT = 2;
    private static final Integer SMALLER_RESULTAT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/resultates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResultateRepository resultateRepository;

    @Autowired
    private ResultateMapper resultateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultateMockMvc;

    private Resultate resultate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultate createEntity(EntityManager em) {
        Resultate resultate = new Resultate().runde(DEFAULT_RUNDE).resultat(DEFAULT_RESULTAT);
        return resultate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultate createUpdatedEntity(EntityManager em) {
        Resultate resultate = new Resultate().runde(UPDATED_RUNDE).resultat(UPDATED_RESULTAT);
        return resultate;
    }

    @BeforeEach
    public void initTest() {
        resultate = createEntity(em);
    }

    @Test
    @Transactional
    void createResultate() throws Exception {
        int databaseSizeBeforeCreate = resultateRepository.findAll().size();
        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);
        restResultateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultateDTO)))
            .andExpect(status().isCreated());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeCreate + 1);
        Resultate testResultate = resultateList.get(resultateList.size() - 1);
        assertThat(testResultate.getRunde()).isEqualTo(DEFAULT_RUNDE);
        assertThat(testResultate.getResultat()).isEqualTo(DEFAULT_RESULTAT);
    }

    @Test
    @Transactional
    void createResultateWithExistingId() throws Exception {
        // Create the Resultate with an existing ID
        resultate.setId(1L);
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        int databaseSizeBeforeCreate = resultateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRundeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultateRepository.findAll().size();
        // set the field null
        resultate.setRunde(null);

        // Create the Resultate, which fails.
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        restResultateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultateDTO)))
            .andExpect(status().isBadRequest());

        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResultates() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList
        restResultateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultate.getId().intValue())))
            .andExpect(jsonPath("$.[*].runde").value(hasItem(DEFAULT_RUNDE)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)));
    }

    @Test
    @Transactional
    void getResultate() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get the resultate
        restResultateMockMvc
            .perform(get(ENTITY_API_URL_ID, resultate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultate.getId().intValue()))
            .andExpect(jsonPath("$.runde").value(DEFAULT_RUNDE))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT));
    }

    @Test
    @Transactional
    void getResultatesByIdFiltering() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        Long id = resultate.getId();

        defaultResultateShouldBeFound("id.equals=" + id);
        defaultResultateShouldNotBeFound("id.notEquals=" + id);

        defaultResultateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultateShouldNotBeFound("id.greaterThan=" + id);

        defaultResultateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde equals to DEFAULT_RUNDE
        defaultResultateShouldBeFound("runde.equals=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde equals to UPDATED_RUNDE
        defaultResultateShouldNotBeFound("runde.equals=" + UPDATED_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde not equals to DEFAULT_RUNDE
        defaultResultateShouldNotBeFound("runde.notEquals=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde not equals to UPDATED_RUNDE
        defaultResultateShouldBeFound("runde.notEquals=" + UPDATED_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsInShouldWork() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde in DEFAULT_RUNDE or UPDATED_RUNDE
        defaultResultateShouldBeFound("runde.in=" + DEFAULT_RUNDE + "," + UPDATED_RUNDE);

        // Get all the resultateList where runde equals to UPDATED_RUNDE
        defaultResultateShouldNotBeFound("runde.in=" + UPDATED_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde is not null
        defaultResultateShouldBeFound("runde.specified=true");

        // Get all the resultateList where runde is null
        defaultResultateShouldNotBeFound("runde.specified=false");
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde is greater than or equal to DEFAULT_RUNDE
        defaultResultateShouldBeFound("runde.greaterThanOrEqual=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde is greater than or equal to UPDATED_RUNDE
        defaultResultateShouldNotBeFound("runde.greaterThanOrEqual=" + UPDATED_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde is less than or equal to DEFAULT_RUNDE
        defaultResultateShouldBeFound("runde.lessThanOrEqual=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde is less than or equal to SMALLER_RUNDE
        defaultResultateShouldNotBeFound("runde.lessThanOrEqual=" + SMALLER_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde is less than DEFAULT_RUNDE
        defaultResultateShouldNotBeFound("runde.lessThan=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde is less than UPDATED_RUNDE
        defaultResultateShouldBeFound("runde.lessThan=" + UPDATED_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByRundeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where runde is greater than DEFAULT_RUNDE
        defaultResultateShouldNotBeFound("runde.greaterThan=" + DEFAULT_RUNDE);

        // Get all the resultateList where runde is greater than SMALLER_RUNDE
        defaultResultateShouldBeFound("runde.greaterThan=" + SMALLER_RUNDE);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat equals to DEFAULT_RESULTAT
        defaultResultateShouldBeFound("resultat.equals=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat equals to UPDATED_RESULTAT
        defaultResultateShouldNotBeFound("resultat.equals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat not equals to DEFAULT_RESULTAT
        defaultResultateShouldNotBeFound("resultat.notEquals=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat not equals to UPDATED_RESULTAT
        defaultResultateShouldBeFound("resultat.notEquals=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsInShouldWork() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat in DEFAULT_RESULTAT or UPDATED_RESULTAT
        defaultResultateShouldBeFound("resultat.in=" + DEFAULT_RESULTAT + "," + UPDATED_RESULTAT);

        // Get all the resultateList where resultat equals to UPDATED_RESULTAT
        defaultResultateShouldNotBeFound("resultat.in=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat is not null
        defaultResultateShouldBeFound("resultat.specified=true");

        // Get all the resultateList where resultat is null
        defaultResultateShouldNotBeFound("resultat.specified=false");
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat is greater than or equal to DEFAULT_RESULTAT
        defaultResultateShouldBeFound("resultat.greaterThanOrEqual=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat is greater than or equal to UPDATED_RESULTAT
        defaultResultateShouldNotBeFound("resultat.greaterThanOrEqual=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat is less than or equal to DEFAULT_RESULTAT
        defaultResultateShouldBeFound("resultat.lessThanOrEqual=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat is less than or equal to SMALLER_RESULTAT
        defaultResultateShouldNotBeFound("resultat.lessThanOrEqual=" + SMALLER_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsLessThanSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat is less than DEFAULT_RESULTAT
        defaultResultateShouldNotBeFound("resultat.lessThan=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat is less than UPDATED_RESULTAT
        defaultResultateShouldBeFound("resultat.lessThan=" + UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByResultatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        // Get all the resultateList where resultat is greater than DEFAULT_RESULTAT
        defaultResultateShouldNotBeFound("resultat.greaterThan=" + DEFAULT_RESULTAT);

        // Get all the resultateList where resultat is greater than SMALLER_RESULTAT
        defaultResultateShouldBeFound("resultat.greaterThan=" + SMALLER_RESULTAT);
    }

    @Test
    @Transactional
    void getAllResultatesByPasse1IsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Passen passe1 = PassenResourceIT.createEntity(em);
        em.persist(passe1);
        em.flush();
        resultate.setPasse1(passe1);
        resultateRepository.saveAndFlush(resultate);
        Long passe1Id = passe1.getId();

        // Get all the resultateList where passe1 equals to passe1Id
        defaultResultateShouldBeFound("passe1Id.equals=" + passe1Id);

        // Get all the resultateList where passe1 equals to (passe1Id + 1)
        defaultResultateShouldNotBeFound("passe1Id.equals=" + (passe1Id + 1));
    }

    @Test
    @Transactional
    void getAllResultatesByPasse2IsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Passen passe2 = PassenResourceIT.createEntity(em);
        em.persist(passe2);
        em.flush();
        resultate.setPasse2(passe2);
        resultateRepository.saveAndFlush(resultate);
        Long passe2Id = passe2.getId();

        // Get all the resultateList where passe2 equals to passe2Id
        defaultResultateShouldBeFound("passe2Id.equals=" + passe2Id);

        // Get all the resultateList where passe2 equals to (passe2Id + 1)
        defaultResultateShouldNotBeFound("passe2Id.equals=" + (passe2Id + 1));
    }

    @Test
    @Transactional
    void getAllResultatesByPasse3IsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Passen passe3 = PassenResourceIT.createEntity(em);
        em.persist(passe3);
        em.flush();
        resultate.setPasse3(passe3);
        resultateRepository.saveAndFlush(resultate);
        Long passe3Id = passe3.getId();

        // Get all the resultateList where passe3 equals to passe3Id
        defaultResultateShouldBeFound("passe3Id.equals=" + passe3Id);

        // Get all the resultateList where passe3 equals to (passe3Id + 1)
        defaultResultateShouldNotBeFound("passe3Id.equals=" + (passe3Id + 1));
    }

    @Test
    @Transactional
    void getAllResultatesByPasse4IsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Passen passe4 = PassenResourceIT.createEntity(em);
        em.persist(passe4);
        em.flush();
        resultate.setPasse4(passe4);
        resultateRepository.saveAndFlush(resultate);
        Long passe4Id = passe4.getId();

        // Get all the resultateList where passe4 equals to passe4Id
        defaultResultateShouldBeFound("passe4Id.equals=" + passe4Id);

        // Get all the resultateList where passe4 equals to (passe4Id + 1)
        defaultResultateShouldNotBeFound("passe4Id.equals=" + (passe4Id + 1));
    }

    @Test
    @Transactional
    void getAllResultatesByGruppeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Gruppen gruppe = GruppenResourceIT.createEntity(em);
        em.persist(gruppe);
        em.flush();
        resultate.setGruppe(gruppe);
        resultateRepository.saveAndFlush(resultate);
        Long gruppeId = gruppe.getId();

        // Get all the resultateList where gruppe equals to gruppeId
        defaultResultateShouldBeFound("gruppeId.equals=" + gruppeId);

        // Get all the resultateList where gruppe equals to (gruppeId + 1)
        defaultResultateShouldNotBeFound("gruppeId.equals=" + (gruppeId + 1));
    }

    @Test
    @Transactional
    void getAllResultatesBySchuetzeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Schuetze schuetze = SchuetzeResourceIT.createEntity(em);
        em.persist(schuetze);
        em.flush();
        resultate.setSchuetze(schuetze);
        resultateRepository.saveAndFlush(resultate);
        Long schuetzeId = schuetze.getId();

        // Get all the resultateList where schuetze equals to schuetzeId
        defaultResultateShouldBeFound("schuetzeId.equals=" + schuetzeId);

        // Get all the resultateList where schuetze equals to (schuetzeId + 1)
        defaultResultateShouldNotBeFound("schuetzeId.equals=" + (schuetzeId + 1));
    }

    @Test
    @Transactional
    void getAllResultatesByWettkampfIsEqualToSomething() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);
        Wettkampf wettkampf = WettkampfResourceIT.createEntity(em);
        em.persist(wettkampf);
        em.flush();
        resultate.setWettkampf(wettkampf);
        resultateRepository.saveAndFlush(resultate);
        Long wettkampfId = wettkampf.getId();

        // Get all the resultateList where wettkampf equals to wettkampfId
        defaultResultateShouldBeFound("wettkampfId.equals=" + wettkampfId);

        // Get all the resultateList where wettkampf equals to (wettkampfId + 1)
        defaultResultateShouldNotBeFound("wettkampfId.equals=" + (wettkampfId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultateShouldBeFound(String filter) throws Exception {
        restResultateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultate.getId().intValue())))
            .andExpect(jsonPath("$.[*].runde").value(hasItem(DEFAULT_RUNDE)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)));

        // Check, that the count call also returns 1
        restResultateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultateShouldNotBeFound(String filter) throws Exception {
        restResultateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResultate() throws Exception {
        // Get the resultate
        restResultateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResultate() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();

        // Update the resultate
        Resultate updatedResultate = resultateRepository.findById(resultate.getId()).get();
        // Disconnect from session so that the updates on updatedResultate are not directly saved in db
        em.detach(updatedResultate);
        updatedResultate.runde(UPDATED_RUNDE).resultat(UPDATED_RESULTAT);
        ResultateDTO resultateDTO = resultateMapper.toDto(updatedResultate);

        restResultateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
        Resultate testResultate = resultateList.get(resultateList.size() - 1);
        assertThat(testResultate.getRunde()).isEqualTo(UPDATED_RUNDE);
        assertThat(testResultate.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void putNonExistingResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultateWithPatch() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();

        // Update the resultate using partial update
        Resultate partialUpdatedResultate = new Resultate();
        partialUpdatedResultate.setId(resultate.getId());

        partialUpdatedResultate.runde(UPDATED_RUNDE).resultat(UPDATED_RESULTAT);

        restResultateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultate))
            )
            .andExpect(status().isOk());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
        Resultate testResultate = resultateList.get(resultateList.size() - 1);
        assertThat(testResultate.getRunde()).isEqualTo(UPDATED_RUNDE);
        assertThat(testResultate.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void fullUpdateResultateWithPatch() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();

        // Update the resultate using partial update
        Resultate partialUpdatedResultate = new Resultate();
        partialUpdatedResultate.setId(resultate.getId());

        partialUpdatedResultate.runde(UPDATED_RUNDE).resultat(UPDATED_RESULTAT);

        restResultateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultate))
            )
            .andExpect(status().isOk());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
        Resultate testResultate = resultateList.get(resultateList.size() - 1);
        assertThat(testResultate.getRunde()).isEqualTo(UPDATED_RUNDE);
        assertThat(testResultate.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void patchNonExistingResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultate() throws Exception {
        int databaseSizeBeforeUpdate = resultateRepository.findAll().size();
        resultate.setId(count.incrementAndGet());

        // Create the Resultate
        ResultateDTO resultateDTO = resultateMapper.toDto(resultate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resultateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resultate in the database
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultate() throws Exception {
        // Initialize the database
        resultateRepository.saveAndFlush(resultate);

        int databaseSizeBeforeDelete = resultateRepository.findAll().size();

        // Delete the resultate
        restResultateMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resultate> resultateList = resultateRepository.findAll();
        assertThat(resultateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
