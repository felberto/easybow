package ch.felberto.service;

import ch.felberto.domain.*;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.criteria.ResultsCriteria;
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
 * Service for executing complex queries for {@link Results} entities in the database.
 * The main input is a {@link ResultsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Results} or a {@link Page} of {@link Results} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultsQueryService extends QueryService<Results> {

    private final Logger log = LoggerFactory.getLogger(ResultsQueryService.class);

    private final ResultsRepository resultsRepository;

    public ResultsQueryService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    /**
     * Return a {@link List} of {@link Results} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Results> findByCriteria(ResultsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Results> specification = createSpecification(criteria);
        return resultsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Results} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Results> findByCriteria(ResultsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Results> specification = createSpecification(criteria);
        return resultsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Results> specification = createSpecification(criteria);
        return resultsRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultsCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Results> createSpecification(ResultsCriteria criteria) {
        Specification<Results> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Results_.id));
            }
            if (criteria.getRound() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRound(), Results_.round));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResult(), Results_.result));
            }
            if (criteria.getSerie1Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSerie1Id(), root -> root.join(Results_.serie1, JoinType.LEFT).get(Series_.id))
                    );
            }
            if (criteria.getSerie2Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSerie2Id(), root -> root.join(Results_.serie2, JoinType.LEFT).get(Series_.id))
                    );
            }
            if (criteria.getSerie3Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSerie3Id(), root -> root.join(Results_.serie3, JoinType.LEFT).get(Series_.id))
                    );
            }
            if (criteria.getSerie4Id() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSerie4Id(), root -> root.join(Results_.serie4, JoinType.LEFT).get(Series_.id))
                    );
            }
            if (criteria.getGroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getGroupId(), root -> root.join(Results_.group, JoinType.LEFT).get(Group_.id))
                    );
            }
            if (criteria.getAthleteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAthleteId(), root -> root.join(Results_.athlete, JoinType.LEFT).get(Athlete_.id))
                    );
            }
            if (criteria.getCompetitionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompetitionId(),
                            root -> root.join(Results_.competition, JoinType.LEFT).get(Competition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
