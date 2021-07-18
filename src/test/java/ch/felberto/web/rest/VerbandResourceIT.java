package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
import ch.felberto.service.criteria.VerbandCriteria;
import ch.felberto.service.dto.VerbandDTO;
import ch.felberto.service.mapper.VerbandMapper;
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
 * Integration tests for the {@link VerbandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerbandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verbands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VerbandRepository verbandRepository;

    @Autowired
    private VerbandMapper verbandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerbandMockMvc;

    private Verband verband;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verband createEntity(EntityManager em) {
        Verband verband = new Verband().name(DEFAULT_NAME);
        return verband;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verband createUpdatedEntity(EntityManager em) {
        Verband verband = new Verband().name(UPDATED_NAME);
        return verband;
    }

    @BeforeEach
    public void initTest() {
        verband = createEntity(em);
    }

    @Test
    @Transactional
    void createVerband() throws Exception {
        int databaseSizeBeforeCreate = verbandRepository.findAll().size();
        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);
        restVerbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verbandDTO)))
            .andExpect(status().isCreated());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeCreate + 1);
        Verband testVerband = verbandList.get(verbandList.size() - 1);
        assertThat(testVerband.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createVerbandWithExistingId() throws Exception {
        // Create the Verband with an existing ID
        verband.setId(1L);
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        int databaseSizeBeforeCreate = verbandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verbandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = verbandRepository.findAll().size();
        // set the field null
        verband.setName(null);

        // Create the Verband, which fails.
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        restVerbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verbandDTO)))
            .andExpect(status().isBadRequest());

        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVerbands() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verband.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getVerband() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get the verband
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL_ID, verband.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verband.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getVerbandsByIdFiltering() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        Long id = verband.getId();

        defaultVerbandShouldBeFound("id.equals=" + id);
        defaultVerbandShouldNotBeFound("id.notEquals=" + id);

        defaultVerbandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVerbandShouldNotBeFound("id.greaterThan=" + id);

        defaultVerbandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVerbandShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVerbandsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name equals to DEFAULT_NAME
        defaultVerbandShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the verbandList where name equals to UPDATED_NAME
        defaultVerbandShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVerbandsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name not equals to DEFAULT_NAME
        defaultVerbandShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the verbandList where name not equals to UPDATED_NAME
        defaultVerbandShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVerbandsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVerbandShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the verbandList where name equals to UPDATED_NAME
        defaultVerbandShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVerbandsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name is not null
        defaultVerbandShouldBeFound("name.specified=true");

        // Get all the verbandList where name is null
        defaultVerbandShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVerbandsByNameContainsSomething() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name contains DEFAULT_NAME
        defaultVerbandShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the verbandList where name contains UPDATED_NAME
        defaultVerbandShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVerbandsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        // Get all the verbandList where name does not contain DEFAULT_NAME
        defaultVerbandShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the verbandList where name does not contain UPDATED_NAME
        defaultVerbandShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVerbandShouldBeFound(String filter) throws Exception {
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verband.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVerbandShouldNotBeFound(String filter) throws Exception {
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVerbandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVerband() throws Exception {
        // Get the verband
        restVerbandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVerband() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();

        // Update the verband
        Verband updatedVerband = verbandRepository.findById(verband.getId()).get();
        // Disconnect from session so that the updates on updatedVerband are not directly saved in db
        em.detach(updatedVerband);
        updatedVerband.name(UPDATED_NAME);
        VerbandDTO verbandDTO = verbandMapper.toDto(updatedVerband);

        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verbandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isOk());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
        Verband testVerband = verbandList.get(verbandList.size() - 1);
        assertThat(testVerband.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verbandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verbandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerbandWithPatch() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();

        // Update the verband using partial update
        Verband partialUpdatedVerband = new Verband();
        partialUpdatedVerband.setId(verband.getId());

        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerband.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVerband))
            )
            .andExpect(status().isOk());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
        Verband testVerband = verbandList.get(verbandList.size() - 1);
        assertThat(testVerband.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateVerbandWithPatch() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();

        // Update the verband using partial update
        Verband partialUpdatedVerband = new Verband();
        partialUpdatedVerband.setId(verband.getId());

        partialUpdatedVerband.name(UPDATED_NAME);

        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerband.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVerband))
            )
            .andExpect(status().isOk());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
        Verband testVerband = verbandList.get(verbandList.size() - 1);
        assertThat(testVerband.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verbandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerband() throws Exception {
        int databaseSizeBeforeUpdate = verbandRepository.findAll().size();
        verband.setId(count.incrementAndGet());

        // Create the Verband
        VerbandDTO verbandDTO = verbandMapper.toDto(verband);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(verbandDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerband() throws Exception {
        // Initialize the database
        verbandRepository.saveAndFlush(verband);

        int databaseSizeBeforeDelete = verbandRepository.findAll().size();

        // Delete the verband
        restVerbandMockMvc
            .perform(delete(ENTITY_API_URL_ID, verband.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
