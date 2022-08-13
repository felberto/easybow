package ch.felberto.service;

import ch.felberto.domain.Association;
import ch.felberto.domain.Association_;
import ch.felberto.repository.AssociationRepository;
import ch.felberto.service.criteria.AssociationCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Association} entities in the database.
 * The main input is a {@link AssociationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Association} or a {@link Page} of {@link Association} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssociationQueryService extends QueryService<Association> {

    private final Logger log = LoggerFactory.getLogger(AssociationQueryService.class);

    private final AssociationRepository associationRepository;

    public AssociationQueryService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    /**
     * Return a {@link List} of {@link Association} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Association> findByCriteria(AssociationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Association> specification = createSpecification(criteria);
        return associationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Association} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Association> findByCriteria(AssociationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Association> specification = createSpecification(criteria);
        return associationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssociationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Association> specification = createSpecification(criteria);
        return associationRepository.count(specification);
    }

    /**
     * Function to convert {@link AssociationCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Association> createSpecification(AssociationCriteria criteria) {
        Specification<Association> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Association_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Association_.name));
            }
        }
        return specification;
    }
}
