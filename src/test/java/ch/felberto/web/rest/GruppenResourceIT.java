package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
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
 * Integration tests for the {@link GruppenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GruppenResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gruppens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GruppenRepository gruppenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGruppenMockMvc;

    private Gruppen gruppen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gruppen createEntity(EntityManager em) {
        Gruppen gruppen = new Gruppen().name(DEFAULT_NAME);
        return gruppen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gruppen createUpdatedEntity(EntityManager em) {
        Gruppen gruppen = new Gruppen().name(UPDATED_NAME);
        return gruppen;
    }

    @BeforeEach
    public void initTest() {
        gruppen = createEntity(em);
    }

    @Test
    @Transactional
    void createGruppen() throws Exception {
        int databaseSizeBeforeCreate = gruppenRepository.findAll().size();
        // Create the Gruppen
        restGruppenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppen)))
            .andExpect(status().isCreated());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeCreate + 1);
        Gruppen testGruppen = gruppenList.get(gruppenList.size() - 1);
        assertThat(testGruppen.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createGruppenWithExistingId() throws Exception {
        // Create the Gruppen with an existing ID
        gruppen.setId(1L);

        int databaseSizeBeforeCreate = gruppenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruppenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppen)))
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGruppens() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruppen.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getGruppen() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get the gruppen
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL_ID, gruppen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gruppen.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingGruppen() throws Exception {
        // Get the gruppen
        restGruppenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGruppen() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();

        // Update the gruppen
        Gruppen updatedGruppen = gruppenRepository.findById(gruppen.getId()).get();
        // Disconnect from session so that the updates on updatedGruppen are not directly saved in db
        em.detach(updatedGruppen);
        updatedGruppen.name(UPDATED_NAME);

        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGruppen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGruppen))
            )
            .andExpect(status().isOk());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
        Gruppen testGruppen = gruppenList.get(gruppenList.size() - 1);
        assertThat(testGruppen.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gruppen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gruppen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gruppen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGruppenWithPatch() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();

        // Update the gruppen using partial update
        Gruppen partialUpdatedGruppen = new Gruppen();
        partialUpdatedGruppen.setId(gruppen.getId());

        partialUpdatedGruppen.name(UPDATED_NAME);

        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGruppen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGruppen))
            )
            .andExpect(status().isOk());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
        Gruppen testGruppen = gruppenList.get(gruppenList.size() - 1);
        assertThat(testGruppen.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateGruppenWithPatch() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();

        // Update the gruppen using partial update
        Gruppen partialUpdatedGruppen = new Gruppen();
        partialUpdatedGruppen.setId(gruppen.getId());

        partialUpdatedGruppen.name(UPDATED_NAME);

        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGruppen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGruppen))
            )
            .andExpect(status().isOk());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
        Gruppen testGruppen = gruppenList.get(gruppenList.size() - 1);
        assertThat(testGruppen.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gruppen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gruppen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gruppen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGruppen() throws Exception {
        int databaseSizeBeforeUpdate = gruppenRepository.findAll().size();
        gruppen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gruppen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGruppen() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        int databaseSizeBeforeDelete = gruppenRepository.findAll().size();

        // Delete the gruppen
        restGruppenMockMvc
            .perform(delete(ENTITY_API_URL_ID, gruppen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
