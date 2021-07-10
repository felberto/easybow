package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Schuetze;
import ch.felberto.domain.enumeration.Stellung;
import ch.felberto.repository.SchuetzeRepository;
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
 * Integration tests for the {@link SchuetzeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchuetzeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JAHRGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JAHRGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Stellung DEFAULT_STELLUNG = Stellung.FREI;
    private static final Stellung UPDATED_STELLUNG = Stellung.AUFGELEGT;

    private static final String ENTITY_API_URL = "/api/schuetzes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchuetzeRepository schuetzeRepository;

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
        Schuetze schuetze = new Schuetze()
            .name(DEFAULT_NAME)
            .vorname(DEFAULT_VORNAME)
            .jahrgang(DEFAULT_JAHRGANG)
            .stellung(DEFAULT_STELLUNG);
        return schuetze;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schuetze createUpdatedEntity(EntityManager em) {
        Schuetze schuetze = new Schuetze()
            .name(UPDATED_NAME)
            .vorname(UPDATED_VORNAME)
            .jahrgang(UPDATED_JAHRGANG)
            .stellung(UPDATED_STELLUNG);
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
        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetze)))
            .andExpect(status().isCreated());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeCreate + 1);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchuetze.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(DEFAULT_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(DEFAULT_STELLUNG);
    }

    @Test
    @Transactional
    void createSchuetzeWithExistingId() throws Exception {
        // Create the Schuetze with an existing ID
        schuetze.setId(1L);

        int databaseSizeBeforeCreate = schuetzeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchuetzeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetze)))
            .andExpect(status().isBadRequest());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeCreate);
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
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].jahrgang").value(hasItem(DEFAULT_JAHRGANG.toString())))
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
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.jahrgang").value(DEFAULT_JAHRGANG.toString()))
            .andExpect(jsonPath("$.stellung").value(DEFAULT_STELLUNG.toString()));
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
        updatedSchuetze.name(UPDATED_NAME).vorname(UPDATED_VORNAME).jahrgang(UPDATED_JAHRGANG).stellung(UPDATED_STELLUNG);

        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchuetze.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSchuetze))
            )
            .andExpect(status().isOk());

        // Validate the Schuetze in the database
        List<Schuetze> schuetzeList = schuetzeRepository.findAll();
        assertThat(schuetzeList).hasSize(databaseSizeBeforeUpdate);
        Schuetze testSchuetze = schuetzeList.get(schuetzeList.size() - 1);
        assertThat(testSchuetze.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchuetze.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(UPDATED_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void putNonExistingSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schuetze.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schuetze))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schuetze))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schuetze)))
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

        partialUpdatedSchuetze.vorname(UPDATED_VORNAME).stellung(UPDATED_STELLUNG);

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
        assertThat(testSchuetze.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(DEFAULT_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(UPDATED_STELLUNG);
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

        partialUpdatedSchuetze.name(UPDATED_NAME).vorname(UPDATED_VORNAME).jahrgang(UPDATED_JAHRGANG).stellung(UPDATED_STELLUNG);

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
        assertThat(testSchuetze.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testSchuetze.getJahrgang()).isEqualTo(UPDATED_JAHRGANG);
        assertThat(testSchuetze.getStellung()).isEqualTo(UPDATED_STELLUNG);
    }

    @Test
    @Transactional
    void patchNonExistingSchuetze() throws Exception {
        int databaseSizeBeforeUpdate = schuetzeRepository.findAll().size();
        schuetze.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schuetze.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schuetze))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schuetze))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchuetzeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(schuetze)))
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
