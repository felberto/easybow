package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Verband;
import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.criteria.VereinCriteria;
import ch.felberto.service.dto.VereinDTO;
import ch.felberto.service.mapper.VereinMapper;
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
 * Integration tests for the {@link VereinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VereinResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vereins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VereinRepository vereinRepository;

    @Autowired
    private VereinMapper vereinMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVereinMockMvc;

    private Verein verein;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verein createEntity(EntityManager em) {
        Verein verein = new Verein().name(DEFAULT_NAME);
        return verein;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verein createUpdatedEntity(EntityManager em) {
        Verein verein = new Verein().name(UPDATED_NAME);
        return verein;
    }

    @BeforeEach
    public void initTest() {
        verein = createEntity(em);
    }

    @Test
    @Transactional
    void createVerein() throws Exception {
        int databaseSizeBeforeCreate = vereinRepository.findAll().size();
        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);
        restVereinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereinDTO)))
            .andExpect(status().isCreated());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeCreate + 1);
        Verein testVerein = vereinList.get(vereinList.size() - 1);
        assertThat(testVerein.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createVereinWithExistingId() throws Exception {
        // Create the Verein with an existing ID
        verein.setId(1L);
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        int databaseSizeBeforeCreate = vereinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVereinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vereinRepository.findAll().size();
        // set the field null
        verein.setName(null);

        // Create the Verein, which fails.
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        restVereinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereinDTO)))
            .andExpect(status().isBadRequest());

        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVereins() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList
        restVereinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verein.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getVerein() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get the verein
        restVereinMockMvc
            .perform(get(ENTITY_API_URL_ID, verein.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verein.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getVereinsByIdFiltering() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        Long id = verein.getId();

        defaultVereinShouldBeFound("id.equals=" + id);
        defaultVereinShouldNotBeFound("id.notEquals=" + id);

        defaultVereinShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVereinShouldNotBeFound("id.greaterThan=" + id);

        defaultVereinShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVereinShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVereinsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name equals to DEFAULT_NAME
        defaultVereinShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vereinList where name equals to UPDATED_NAME
        defaultVereinShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVereinsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name not equals to DEFAULT_NAME
        defaultVereinShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the vereinList where name not equals to UPDATED_NAME
        defaultVereinShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVereinsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVereinShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vereinList where name equals to UPDATED_NAME
        defaultVereinShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVereinsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name is not null
        defaultVereinShouldBeFound("name.specified=true");

        // Get all the vereinList where name is null
        defaultVereinShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVereinsByNameContainsSomething() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name contains DEFAULT_NAME
        defaultVereinShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the vereinList where name contains UPDATED_NAME
        defaultVereinShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVereinsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        // Get all the vereinList where name does not contain DEFAULT_NAME
        defaultVereinShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the vereinList where name does not contain UPDATED_NAME
        defaultVereinShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVereinsByVerbandIsEqualToSomething() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);
        Verband verband = VerbandResourceIT.createEntity(em);
        em.persist(verband);
        em.flush();
        verein.setVerband(verband);
        vereinRepository.saveAndFlush(verein);
        Long verbandId = verband.getId();

        // Get all the vereinList where verband equals to verbandId
        defaultVereinShouldBeFound("verbandId.equals=" + verbandId);

        // Get all the vereinList where verband equals to (verbandId + 1)
        defaultVereinShouldNotBeFound("verbandId.equals=" + (verbandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVereinShouldBeFound(String filter) throws Exception {
        restVereinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verein.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restVereinMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVereinShouldNotBeFound(String filter) throws Exception {
        restVereinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVereinMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVerein() throws Exception {
        // Get the verein
        restVereinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVerein() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();

        // Update the verein
        Verein updatedVerein = vereinRepository.findById(verein.getId()).get();
        // Disconnect from session so that the updates on updatedVerein are not directly saved in db
        em.detach(updatedVerein);
        updatedVerein.name(UPDATED_NAME);
        VereinDTO vereinDTO = vereinMapper.toDto(updatedVerein);

        restVereinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vereinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isOk());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
        Verein testVerein = vereinList.get(vereinList.size() - 1);
        assertThat(testVerein.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vereinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vereinDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVereinWithPatch() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();

        // Update the verein using partial update
        Verein partialUpdatedVerein = new Verein();
        partialUpdatedVerein.setId(verein.getId());

        restVereinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerein.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVerein))
            )
            .andExpect(status().isOk());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
        Verein testVerein = vereinList.get(vereinList.size() - 1);
        assertThat(testVerein.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateVereinWithPatch() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();

        // Update the verein using partial update
        Verein partialUpdatedVerein = new Verein();
        partialUpdatedVerein.setId(verein.getId());

        partialUpdatedVerein.name(UPDATED_NAME);

        restVereinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerein.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVerein))
            )
            .andExpect(status().isOk());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
        Verein testVerein = vereinList.get(vereinList.size() - 1);
        assertThat(testVerein.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vereinDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerein() throws Exception {
        int databaseSizeBeforeUpdate = vereinRepository.findAll().size();
        verein.setId(count.incrementAndGet());

        // Create the Verein
        VereinDTO vereinDTO = vereinMapper.toDto(verein);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVereinMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vereinDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verein in the database
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerein() throws Exception {
        // Initialize the database
        vereinRepository.saveAndFlush(verein);

        int databaseSizeBeforeDelete = vereinRepository.findAll().size();

        // Delete the verein
        restVereinMockMvc
            .perform(delete(ENTITY_API_URL_ID, verein.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Verein> vereinList = vereinRepository.findAll();
        assertThat(vereinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
