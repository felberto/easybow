package ch.felberto.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.felberto.IntegrationTest;
import ch.felberto.domain.Passen;
import ch.felberto.repository.PassenRepository;
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
 * Integration tests for the {@link PassenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassenResourceIT {

    private static final Integer DEFAULT_P_1 = 0;
    private static final Integer UPDATED_P_1 = 1;

    private static final Integer DEFAULT_P_2 = 0;
    private static final Integer UPDATED_P_2 = 1;

    private static final Integer DEFAULT_P_3 = 0;
    private static final Integer UPDATED_P_3 = 1;

    private static final Integer DEFAULT_P_4 = 0;
    private static final Integer UPDATED_P_4 = 1;

    private static final Integer DEFAULT_P_5 = 0;
    private static final Integer UPDATED_P_5 = 1;

    private static final Integer DEFAULT_P_6 = 0;
    private static final Integer UPDATED_P_6 = 1;

    private static final Integer DEFAULT_P_7 = 0;
    private static final Integer UPDATED_P_7 = 1;

    private static final Integer DEFAULT_P_8 = 0;
    private static final Integer UPDATED_P_8 = 1;

    private static final Integer DEFAULT_P_9 = 0;
    private static final Integer UPDATED_P_9 = 1;

    private static final Integer DEFAULT_P_10 = 0;
    private static final Integer UPDATED_P_10 = 1;

    private static final Integer DEFAULT_RESULTAT = 1;
    private static final Integer UPDATED_RESULTAT = 2;

    private static final String ENTITY_API_URL = "/api/passens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassenRepository passenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassenMockMvc;

    private Passen passen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passen createEntity(EntityManager em) {
        Passen passen = new Passen()
            .p1(DEFAULT_P_1)
            .p2(DEFAULT_P_2)
            .p3(DEFAULT_P_3)
            .p4(DEFAULT_P_4)
            .p5(DEFAULT_P_5)
            .p6(DEFAULT_P_6)
            .p7(DEFAULT_P_7)
            .p8(DEFAULT_P_8)
            .p9(DEFAULT_P_9)
            .p10(DEFAULT_P_10)
            .resultat(DEFAULT_RESULTAT);
        return passen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passen createUpdatedEntity(EntityManager em) {
        Passen passen = new Passen()
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);
        return passen;
    }

    @BeforeEach
    public void initTest() {
        passen = createEntity(em);
    }

    @Test
    @Transactional
    void createPassen() throws Exception {
        int databaseSizeBeforeCreate = passenRepository.findAll().size();
        // Create the Passen
        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isCreated());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeCreate + 1);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(DEFAULT_P_1);
        assertThat(testPassen.getp2()).isEqualTo(DEFAULT_P_2);
        assertThat(testPassen.getp3()).isEqualTo(DEFAULT_P_3);
        assertThat(testPassen.getp4()).isEqualTo(DEFAULT_P_4);
        assertThat(testPassen.getp5()).isEqualTo(DEFAULT_P_5);
        assertThat(testPassen.getp6()).isEqualTo(DEFAULT_P_6);
        assertThat(testPassen.getp7()).isEqualTo(DEFAULT_P_7);
        assertThat(testPassen.getp8()).isEqualTo(DEFAULT_P_8);
        assertThat(testPassen.getp9()).isEqualTo(DEFAULT_P_9);
        assertThat(testPassen.getp10()).isEqualTo(DEFAULT_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(DEFAULT_RESULTAT);
    }

    @Test
    @Transactional
    void createPassenWithExistingId() throws Exception {
        // Create the Passen with an existing ID
        passen.setId(1L);

        int databaseSizeBeforeCreate = passenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkp1IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp1(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp2IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp2(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp3IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp3(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp4IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp4(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp5IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp5(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp6IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp6(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp7IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp7(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp8IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp8(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp9IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp9(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkp10IsRequired() throws Exception {
        int databaseSizeBeforeTest = passenRepository.findAll().size();
        // set the field null
        passen.setp10(null);

        // Create the Passen, which fails.

        restPassenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isBadRequest());

        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPassens() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get all the passenList
        restPassenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passen.getId().intValue())))
            .andExpect(jsonPath("$.[*].p1").value(hasItem(DEFAULT_P_1)))
            .andExpect(jsonPath("$.[*].p2").value(hasItem(DEFAULT_P_2)))
            .andExpect(jsonPath("$.[*].p3").value(hasItem(DEFAULT_P_3)))
            .andExpect(jsonPath("$.[*].p4").value(hasItem(DEFAULT_P_4)))
            .andExpect(jsonPath("$.[*].p5").value(hasItem(DEFAULT_P_5)))
            .andExpect(jsonPath("$.[*].p6").value(hasItem(DEFAULT_P_6)))
            .andExpect(jsonPath("$.[*].p7").value(hasItem(DEFAULT_P_7)))
            .andExpect(jsonPath("$.[*].p8").value(hasItem(DEFAULT_P_8)))
            .andExpect(jsonPath("$.[*].p9").value(hasItem(DEFAULT_P_9)))
            .andExpect(jsonPath("$.[*].p10").value(hasItem(DEFAULT_P_10)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)));
    }

    @Test
    @Transactional
    void getPassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        // Get the passen
        restPassenMockMvc
            .perform(get(ENTITY_API_URL_ID, passen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passen.getId().intValue()))
            .andExpect(jsonPath("$.p1").value(DEFAULT_P_1))
            .andExpect(jsonPath("$.p2").value(DEFAULT_P_2))
            .andExpect(jsonPath("$.p3").value(DEFAULT_P_3))
            .andExpect(jsonPath("$.p4").value(DEFAULT_P_4))
            .andExpect(jsonPath("$.p5").value(DEFAULT_P_5))
            .andExpect(jsonPath("$.p6").value(DEFAULT_P_6))
            .andExpect(jsonPath("$.p7").value(DEFAULT_P_7))
            .andExpect(jsonPath("$.p8").value(DEFAULT_P_8))
            .andExpect(jsonPath("$.p9").value(DEFAULT_P_9))
            .andExpect(jsonPath("$.p10").value(DEFAULT_P_10))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT));
    }

    @Test
    @Transactional
    void getNonExistingPassen() throws Exception {
        // Get the passen
        restPassenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen
        Passen updatedPassen = passenRepository.findById(passen.getId()).get();
        // Disconnect from session so that the updates on updatedPassen are not directly saved in db
        em.detach(updatedPassen);
        updatedPassen
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);

        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPassen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPassen))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(UPDATED_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(UPDATED_P_7);
        assertThat(testPassen.getp8()).isEqualTo(UPDATED_P_8);
        assertThat(testPassen.getp9()).isEqualTo(UPDATED_P_9);
        assertThat(testPassen.getp10()).isEqualTo(UPDATED_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void putNonExistingPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassenWithPatch() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen using partial update
        Passen partialUpdatedPassen = new Passen();
        partialUpdatedPassen.setId(passen.getId());

        partialUpdatedPassen.p1(UPDATED_P_1).p2(UPDATED_P_2).p3(UPDATED_P_3).p4(UPDATED_P_4).p6(UPDATED_P_6).resultat(UPDATED_RESULTAT);

        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassen))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(DEFAULT_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(DEFAULT_P_7);
        assertThat(testPassen.getp8()).isEqualTo(DEFAULT_P_8);
        assertThat(testPassen.getp9()).isEqualTo(DEFAULT_P_9);
        assertThat(testPassen.getp10()).isEqualTo(DEFAULT_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void fullUpdatePassenWithPatch() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeUpdate = passenRepository.findAll().size();

        // Update the passen using partial update
        Passen partialUpdatedPassen = new Passen();
        partialUpdatedPassen.setId(passen.getId());

        partialUpdatedPassen
            .p1(UPDATED_P_1)
            .p2(UPDATED_P_2)
            .p3(UPDATED_P_3)
            .p4(UPDATED_P_4)
            .p5(UPDATED_P_5)
            .p6(UPDATED_P_6)
            .p7(UPDATED_P_7)
            .p8(UPDATED_P_8)
            .p9(UPDATED_P_9)
            .p10(UPDATED_P_10)
            .resultat(UPDATED_RESULTAT);

        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassen))
            )
            .andExpect(status().isOk());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
        Passen testPassen = passenList.get(passenList.size() - 1);
        assertThat(testPassen.getp1()).isEqualTo(UPDATED_P_1);
        assertThat(testPassen.getp2()).isEqualTo(UPDATED_P_2);
        assertThat(testPassen.getp3()).isEqualTo(UPDATED_P_3);
        assertThat(testPassen.getp4()).isEqualTo(UPDATED_P_4);
        assertThat(testPassen.getp5()).isEqualTo(UPDATED_P_5);
        assertThat(testPassen.getp6()).isEqualTo(UPDATED_P_6);
        assertThat(testPassen.getp7()).isEqualTo(UPDATED_P_7);
        assertThat(testPassen.getp8()).isEqualTo(UPDATED_P_8);
        assertThat(testPassen.getp9()).isEqualTo(UPDATED_P_9);
        assertThat(testPassen.getp10()).isEqualTo(UPDATED_P_10);
        assertThat(testPassen.getResultat()).isEqualTo(UPDATED_RESULTAT);
    }

    @Test
    @Transactional
    void patchNonExistingPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassen() throws Exception {
        int databaseSizeBeforeUpdate = passenRepository.findAll().size();
        passen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(passen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passen in the database
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassen() throws Exception {
        // Initialize the database
        passenRepository.saveAndFlush(passen);

        int databaseSizeBeforeDelete = passenRepository.findAll().size();

        // Delete the passen
        restPassenMockMvc
            .perform(delete(ENTITY_API_URL_ID, passen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passen> passenList = passenRepository.findAll();
        assertThat(passenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
