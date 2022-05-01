package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
import ch.felberto.service.criteria.GruppenCriteria;
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
 * Service for executing complex queries for {@link Gruppen} entities in the database.
 * The main input is a {@link GruppenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Gruppen} or a {@link Page} of {@link Gruppen} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GruppenQueryService extends QueryService<Gruppen> {

    private final Logger log = LoggerFactory.getLogger(GruppenQueryService.class);

    private final GruppenRepository gruppenRepository;

    public GruppenQueryService(GruppenRepository gruppenRepository) {
        this.gruppenRepository = gruppenRepository;
    }

    /**
     * Return a {@link List} of {@link Gruppen} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Gruppen> findByCriteria(GruppenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Gruppen> specification = createSpecification(criteria);
        return gruppenRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Gruppen} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Gruppen> findByCriteria(GruppenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Gruppen> specification = createSpecification(criteria);
        return gruppenRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GruppenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Gruppen> specification = createSpecification(criteria);
        return gruppenRepository.count(specification);
    }

    /**
     * Function to convert {@link GruppenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Gruppen> createSpecification(GruppenCriteria criteria) {
        Specification<Gruppen> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Gruppen_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Gruppen_.name));
            }
        }
        return specification;
    }
}
