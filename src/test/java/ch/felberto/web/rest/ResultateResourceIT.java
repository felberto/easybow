package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
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
        Resultate resultate = new Resultate().runde(DEFAULT_RUNDE);
        return resultate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultate createUpdatedEntity(EntityManager em) {
        Resultate resultate = new Resultate().runde(UPDATED_RUNDE);
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
            .andExpect(jsonPath("$.[*].runde").value(hasItem(DEFAULT_RUNDE)));
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
            .andExpect(jsonPath("$.runde").value(DEFAULT_RUNDE));
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
        updatedResultate.runde(UPDATED_RUNDE);
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

        partialUpdatedResultate.runde(UPDATED_RUNDE);

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

        partialUpdatedResultate.runde(UPDATED_RUNDE);

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
