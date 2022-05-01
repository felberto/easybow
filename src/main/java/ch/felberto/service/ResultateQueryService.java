package ch.felberto.service;

import ch.felberto.domain.*;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.service.criteria.ResultateCriteria;
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
 * Service for executing complex queries for {@link Resultate} entities in the database.
 * The main input is a {@link ResultateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Resultate} or a {@link Page} of {@link Resultate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultateQueryService extends QueryService<Resultate> {

    private final Logger log = LoggerFactory.getLogger(ResultateQueryService.class);

    private final ResultateRepository resultateRepository;

    public ResultateQueryService(ResultateRepository resultateRepository) {
        this.resultateRepository = resultateRepository;
    }

    /**
     * Return a {@link List} of {@link Resultate} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Resultate> findByCriteria(ResultateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resultate> specification = createSpecification(criteria);
        return resultateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Resultate} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Resultate> findByCriteria(ResultateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resultate> specification = createSpecification(criteria);
        return resultateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resultate> specification = createSpecification(criteria);
        return resultateRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultateCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Resultate> createSpecification(ResultateCriteria criteria) {
        Specification<Resultate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Resultate_.id));
            }
            if (criteria.getRunde() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunde(), Resultate_.runde));
            }
            if (criteria.getResultat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResultat(), Resultate_.resultat));
            }
            if (criteria.getPasse1Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPasse1Id(), root -> root.join(Resultate_.passe1, JoinType.LEFT).get(Passen_.id))
                    );
            }
            if (criteria.getPasse2Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPasse2Id(), root -> root.join(Resultate_.passe2, JoinType.LEFT).get(Passen_.id))
                    );
            }
            if (criteria.getPasse3Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPasse3Id(), root -> root.join(Resultate_.passe3, JoinType.LEFT).get(Passen_.id))
                    );
            }
            if (criteria.getPasse4Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPasse4Id(), root -> root.join(Resultate_.passe4, JoinType.LEFT).get(Passen_.id))
                    );
            }
            if (criteria.getGruppeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getGruppeId(), root -> root.join(Resultate_.gruppe, JoinType.LEFT).get(Gruppen_.id))
                    );
            }
            if (criteria.getSchuetzeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSchuetzeId(),
                            root -> root.join(Resultate_.schuetze, JoinType.LEFT).get(Schuetze_.id)
                        )
                    );
            }
            if (criteria.getWettkampfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWettkampfId(),
                            root -> root.join(Resultate_.wettkampf, JoinType.LEFT).get(Wettkampf_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
