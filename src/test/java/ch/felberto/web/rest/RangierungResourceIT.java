package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Rangierung;
import ch.felberto.domain.enumeration.Rangierungskriterien;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.service.dto.RangierungDTO;
import ch.felberto.service.mapper.RangierungMapper;
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
 * Integration tests for the {@link RangierungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RangierungResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Rangierungskriterien DEFAULT_RANGIERUNGSKRITERIEN = Rangierungskriterien.RESULTAT;
    private static final Rangierungskriterien UPDATED_RANGIERUNGSKRITERIEN = Rangierungskriterien.SERIE;

    private static final String ENTITY_API_URL = "/api/rangierungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RangierungRepository rangierungRepository;

    @Autowired
    private RangierungMapper rangierungMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRangierungMockMvc;

    private Rangierung rangierung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rangierung createEntity(EntityManager em) {
        Rangierung rangierung = new Rangierung().position(DEFAULT_POSITION).rangierungskriterien(DEFAULT_RANGIERUNGSKRITERIEN);
        return rangierung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rangierung createUpdatedEntity(EntityManager em) {
        Rangierung rangierung = new Rangierung().position(UPDATED_POSITION).rangierungskriterien(UPDATED_RANGIERUNGSKRITERIEN);
        return rangierung;
    }

    @BeforeEach
    public void initTest() {
        rangierung = createEntity(em);
    }

    @Test
    @Transactional
    void createRangierung() throws Exception {
        int databaseSizeBeforeCreate = rangierungRepository.findAll().size();
        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);
        restRangierungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rangierungDTO)))
            .andExpect(status().isCreated());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeCreate + 1);
        Rangierung testRangierung = rangierungList.get(rangierungList.size() - 1);
        assertThat(testRangierung.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testRangierung.getRangierungskriterien()).isEqualTo(DEFAULT_RANGIERUNGSKRITERIEN);
    }

    @Test
    @Transactional
    void createRangierungWithExistingId() throws Exception {
        // Create the Rangierung with an existing ID
        rangierung.setId(1L);
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        int databaseSizeBeforeCreate = rangierungRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRangierungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rangierungDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rangierungRepository.findAll().size();
        // set the field null
        rangierung.setPosition(null);

        // Create the Rangierung, which fails.
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        restRangierungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rangierungDTO)))
            .andExpect(status().isBadRequest());

        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRangierungskriterienIsRequired() throws Exception {
        int databaseSizeBeforeTest = rangierungRepository.findAll().size();
        // set the field null
        rangierung.setRangierungskriterien(null);

        // Create the Rangierung, which fails.
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        restRangierungMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rangierungDTO)))
            .andExpect(status().isBadRequest());

        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRangierungs() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        // Get all the rangierungList
        restRangierungMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rangierung.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].rangierungskriterien").value(hasItem(DEFAULT_RANGIERUNGSKRITERIEN.toString())));
    }

    @Test
    @Transactional
    void getRangierung() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        // Get the rangierung
        restRangierungMockMvc
            .perform(get(ENTITY_API_URL_ID, rangierung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rangierung.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.rangierungskriterien").value(DEFAULT_RANGIERUNGSKRITERIEN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRangierung() throws Exception {
        // Get the rangierung
        restRangierungMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRangierung() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();

        // Update the rangierung
        Rangierung updatedRangierung = rangierungRepository.findById(rangierung.getId()).get();
        // Disconnect from session so that the updates on updatedRangierung are not directly saved in db
        em.detach(updatedRangierung);
        updatedRangierung.position(UPDATED_POSITION).rangierungskriterien(UPDATED_RANGIERUNGSKRITERIEN);
        RangierungDTO rangierungDTO = rangierungMapper.toDto(updatedRangierung);

        restRangierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rangierungDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
        Rangierung testRangierung = rangierungList.get(rangierungList.size() - 1);
        assertThat(testRangierung.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRangierung.getRangierungskriterien()).isEqualTo(UPDATED_RANGIERUNGSKRITERIEN);
    }

    @Test
    @Transactional
    void putNonExistingRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rangierungDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rangierungDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRangierungWithPatch() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();

        // Update the rangierung using partial update
        Rangierung partialUpdatedRangierung = new Rangierung();
        partialUpdatedRangierung.setId(rangierung.getId());

        restRangierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRangierung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRangierung))
            )
            .andExpect(status().isOk());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
        Rangierung testRangierung = rangierungList.get(rangierungList.size() - 1);
        assertThat(testRangierung.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testRangierung.getRangierungskriterien()).isEqualTo(DEFAULT_RANGIERUNGSKRITERIEN);
    }

    @Test
    @Transactional
    void fullUpdateRangierungWithPatch() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();

        // Update the rangierung using partial update
        Rangierung partialUpdatedRangierung = new Rangierung();
        partialUpdatedRangierung.setId(rangierung.getId());

        partialUpdatedRangierung.position(UPDATED_POSITION).rangierungskriterien(UPDATED_RANGIERUNGSKRITERIEN);

        restRangierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRangierung.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRangierung))
            )
            .andExpect(status().isOk());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
        Rangierung testRangierung = rangierungList.get(rangierungList.size() - 1);
        assertThat(testRangierung.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRangierung.getRangierungskriterien()).isEqualTo(UPDATED_RANGIERUNGSKRITERIEN);
    }

    @Test
    @Transactional
    void patchNonExistingRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rangierungDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRangierung() throws Exception {
        int databaseSizeBeforeUpdate = rangierungRepository.findAll().size();
        rangierung.setId(count.incrementAndGet());

        // Create the Rangierung
        RangierungDTO rangierungDTO = rangierungMapper.toDto(rangierung);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRangierungMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rangierungDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rangierung in the database
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRangierung() throws Exception {
        // Initialize the database
        rangierungRepository.saveAndFlush(rangierung);

        int databaseSizeBeforeDelete = rangierungRepository.findAll().size();

        // Delete the rangierung
        restRangierungMockMvc
            .perform(delete(ENTITY_API_URL_ID, rangierung.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rangierung> rangierungList = rangierungRepository.findAll();
        assertThat(rangierungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
