package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
import ch.felberto.service.criteria.GruppenCriteria;
import ch.felberto.service.dto.GruppenDTO;
import ch.felberto.service.mapper.GruppenMapper;
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
    private GruppenMapper gruppenMapper;

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
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);
        restGruppenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppenDTO)))
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
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        int databaseSizeBeforeCreate = gruppenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruppenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gruppen in the database
        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruppenRepository.findAll().size();
        // set the field null
        gruppen.setName(null);

        // Create the Gruppen, which fails.
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        restGruppenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppenDTO)))
            .andExpect(status().isBadRequest());

        List<Gruppen> gruppenList = gruppenRepository.findAll();
        assertThat(gruppenList).hasSize(databaseSizeBeforeTest);
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
    void getGruppensByIdFiltering() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        Long id = gruppen.getId();

        defaultGruppenShouldBeFound("id.equals=" + id);
        defaultGruppenShouldNotBeFound("id.notEquals=" + id);

        defaultGruppenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGruppenShouldNotBeFound("id.greaterThan=" + id);

        defaultGruppenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGruppenShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGruppensByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name equals to DEFAULT_NAME
        defaultGruppenShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the gruppenList where name equals to UPDATED_NAME
        defaultGruppenShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGruppensByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name not equals to DEFAULT_NAME
        defaultGruppenShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the gruppenList where name not equals to UPDATED_NAME
        defaultGruppenShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGruppensByNameIsInShouldWork() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGruppenShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the gruppenList where name equals to UPDATED_NAME
        defaultGruppenShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGruppensByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name is not null
        defaultGruppenShouldBeFound("name.specified=true");

        // Get all the gruppenList where name is null
        defaultGruppenShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllGruppensByNameContainsSomething() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name contains DEFAULT_NAME
        defaultGruppenShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the gruppenList where name contains UPDATED_NAME
        defaultGruppenShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGruppensByNameNotContainsSomething() throws Exception {
        // Initialize the database
        gruppenRepository.saveAndFlush(gruppen);

        // Get all the gruppenList where name does not contain DEFAULT_NAME
        defaultGruppenShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the gruppenList where name does not contain UPDATED_NAME
        defaultGruppenShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGruppenShouldBeFound(String filter) throws Exception {
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruppen.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGruppenShouldNotBeFound(String filter) throws Exception {
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGruppenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        GruppenDTO gruppenDTO = gruppenMapper.toDto(updatedGruppen);

        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gruppenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gruppenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gruppenDTO)))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gruppenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
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

        // Create the Gruppen
        GruppenDTO gruppenDTO = gruppenMapper.toDto(gruppen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGruppenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gruppenDTO))
            )
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
