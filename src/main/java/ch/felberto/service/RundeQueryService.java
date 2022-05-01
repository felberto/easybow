package ch.felberto.service;

import ch.felberto.domain.Runde;
import ch.felberto.domain.Runde_;
import ch.felberto.domain.Wettkampf_;
import ch.felberto.repository.RundeRepository;
import ch.felberto.service.criteria.RundeCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Runde} entities in the database.
 * The main input is a {@link RundeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Runde} or a {@link Page} of {@link Runde} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RundeQueryService extends QueryService<Runde> {

    private final Logger log = LoggerFactory.getLogger(RundeQueryService.class);

    private final RundeRepository rundeRepository;

    public RundeQueryService(RundeRepository rundeRepository) {
        this.rundeRepository = rundeRepository;
    }

    /**
     * Return a {@link List} of {@link Runde} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Runde> findByCriteria(RundeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Runde> specification = createSpecification(criteria);
        return rundeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Runde} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Runde> findByCriteria(RundeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Runde> specification = createSpecification(criteria);
        return rundeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RundeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Runde> specification = createSpecification(criteria);
        return rundeRepository.count(specification);
    }

    /**
     * Function to convert {@link RundeCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Runde> createSpecification(RundeCriteria criteria) {
        Specification<Runde> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Runde_.id));
            }
            if (criteria.getRunde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunde(), Runde_.runde));
            }
            if (criteria.getDatum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatum(), Runde_.datum));
            }
            if (criteria.getWettkampfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getWettkampfId(), root -> root.join(Runde_.wettkampf, JoinType.LEFT).get(Wettkampf_.id))
                    );
            }
        }
        return specification;
    }
}
