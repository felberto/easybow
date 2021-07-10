package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
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

    private static final Long DEFAULT_ANZAHL_PASSEN = 1L;
    private static final Long UPDATED_ANZAHL_PASSEN = 2L;

    private static final Long DEFAULT_TEAM = 1L;
    private static final Long UPDATED_TEAM = 2L;

    private static final Boolean DEFAULT_TEMPLATE = false;
    private static final Boolean UPDATED_TEMPLATE = true;

    private static final String ENTITY_API_URL = "/api/wettkampfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WettkampfRepository wettkampfRepository;

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
            .anzahlPassen(DEFAULT_ANZAHL_PASSEN)
            .team(DEFAULT_TEAM)
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
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .team(UPDATED_TEAM)
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
        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampf)))
            .andExpect(status().isCreated());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeCreate + 1);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(DEFAULT_JAHR);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(DEFAULT_ANZAHL_PASSEN);
        assertThat(testWettkampf.getTeam()).isEqualTo(DEFAULT_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    void createWettkampfWithExistingId() throws Exception {
        // Create the Wettkampf with an existing ID
        wettkampf.setId(1L);

        int databaseSizeBeforeCreate = wettkampfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWettkampfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampf)))
            .andExpect(status().isBadRequest());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeCreate);
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
            .andExpect(jsonPath("$.[*].anzahlPassen").value(hasItem(DEFAULT_ANZAHL_PASSEN.intValue())))
            .andExpect(jsonPath("$.[*].team").value(hasItem(DEFAULT_TEAM.intValue())))
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
            .andExpect(jsonPath("$.anzahlPassen").value(DEFAULT_ANZAHL_PASSEN.intValue()))
            .andExpect(jsonPath("$.team").value(DEFAULT_TEAM.intValue()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.booleanValue()));
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
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .team(UPDATED_TEAM)
            .template(UPDATED_TEMPLATE);

        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWettkampf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWettkampf))
            )
            .andExpect(status().isOk());

        // Validate the Wettkampf in the database
        List<Wettkampf> wettkampfList = wettkampfRepository.findAll();
        assertThat(wettkampfList).hasSize(databaseSizeBeforeUpdate);
        Wettkampf testWettkampf = wettkampfList.get(wettkampfList.size() - 1);
        assertThat(testWettkampf.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWettkampf.getJahr()).isEqualTo(UPDATED_JAHR);
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(UPDATED_ANZAHL_PASSEN);
        assertThat(testWettkampf.getTeam()).isEqualTo(UPDATED_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void putNonExistingWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wettkampf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wettkampf))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wettkampf))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wettkampf)))
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

        partialUpdatedWettkampf.name(UPDATED_NAME).team(UPDATED_TEAM).template(UPDATED_TEMPLATE);

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
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(DEFAULT_ANZAHL_PASSEN);
        assertThat(testWettkampf.getTeam()).isEqualTo(UPDATED_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
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
            .anzahlPassen(UPDATED_ANZAHL_PASSEN)
            .team(UPDATED_TEAM)
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
        assertThat(testWettkampf.getAnzahlPassen()).isEqualTo(UPDATED_ANZAHL_PASSEN);
        assertThat(testWettkampf.getTeam()).isEqualTo(UPDATED_TEAM);
        assertThat(testWettkampf.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void patchNonExistingWettkampf() throws Exception {
        int databaseSizeBeforeUpdate = wettkampfRepository.findAll().size();
        wettkampf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wettkampf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wettkampf))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wettkampf))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWettkampfMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wettkampf))
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
