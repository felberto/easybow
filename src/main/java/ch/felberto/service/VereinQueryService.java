package ch.felberto.service;

import ch.felberto.domain.Verband_;
import ch.felberto.domain.Verein;
import ch.felberto.domain.Verein_;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.criteria.VereinCriteria;
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
 * Service for executing complex queries for {@link Verein} entities in the database.
 * The main input is a {@link VereinCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Verein} or a {@link Page} of {@link Verein} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VereinQueryService extends QueryService<Verein> {

    private final Logger log = LoggerFactory.getLogger(VereinQueryService.class);

    private final VereinRepository vereinRepository;

    public VereinQueryService(VereinRepository vereinRepository) {
        this.vereinRepository = vereinRepository;
    }

    /**
     * Return a {@link List} of {@link Verein} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Verein> findByCriteria(VereinCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Verein} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Verein> findByCriteria(VereinCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VereinCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinRepository.count(specification);
    }

    /**
     * Function to convert {@link VereinCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Verein> createSpecification(VereinCriteria criteria) {
        Specification<Verein> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Verein_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Verein_.name));
            }
            if (criteria.getVerbandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVerbandId(), root -> root.join(Verein_.verband, JoinType.LEFT).get(Verband_.id))
                    );
            }
        }
        return specification;
    }
}
