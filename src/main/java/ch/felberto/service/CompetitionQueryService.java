package ch.felberto.service;

import ch.felberto.domain.Competition;
import ch.felberto.domain.Competition_;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.service.criteria.CompetitionCriteria;
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
 * Service for executing complex queries for {@link Competition} entities in the database.
 * The main input is a {@link CompetitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Competition} or a {@link Page} of {@link Competition} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompetitionQueryService extends QueryService<Competition> {

    private final Logger log = LoggerFactory.getLogger(CompetitionQueryService.class);

    private final CompetitionRepository competitionRepository;

    public CompetitionQueryService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    /**
     * Return a {@link List} of {@link Competition} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Competition> findByCriteria(CompetitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Competition} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Competition> findByCriteria(CompetitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompetitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionRepository.count(specification);
    }

    /**
     * Function to convert {@link CompetitionCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Competition> createSpecification(CompetitionCriteria criteria) {
        Specification<Competition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Competition_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Competition_.name));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), Competition_.year));
            }
            if (criteria.getNumberOfRounds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfRounds(), Competition_.numberOfRounds));
            }
            if (criteria.getNumberOfSeries() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfSeries(), Competition_.numberOfSeries));
            }
            if (criteria.getFinalRound() != null) {
                specification = specification.and(buildSpecification(criteria.getFinalRound(), Competition_.finalRound));
            }
            if (criteria.getFinalPreparation() != null) {
                specification = specification.and(buildSpecification(criteria.getFinalPreparation(), Competition_.finalPreparation));
            }
            if (criteria.getNumberOfFinalAthletes() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumberOfFinalAthletes(), Competition_.numberOfFinalAthletes));
            }
            if (criteria.getNumberOfFinalSeries() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumberOfFinalSeries(), Competition_.numberOfFinalSeries));
            }
            if (criteria.getTeamSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTeamSize(), Competition_.teamSize));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplate(), Competition_.template));
            }
        }
        return specification;
    }
}
