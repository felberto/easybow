package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
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
        restVerbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verband)))
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

        int databaseSizeBeforeCreate = verbandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerbandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verband)))
            .andExpect(status().isBadRequest());

        // Validate the Verband in the database
        List<Verband> verbandList = verbandRepository.findAll();
        assertThat(verbandList).hasSize(databaseSizeBeforeCreate);
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

        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerband.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVerband))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verband.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(verband))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(verband))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(verband)))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verband.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(verband))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(verband))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerbandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(verband)))
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
