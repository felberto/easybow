package ch.felberto.service;

import ch.felberto.domain.Athlete;
import ch.felberto.domain.Athlete_;
import ch.felberto.domain.Club_;
import ch.felberto.repository.AthleteRepository;
import ch.felberto.service.criteria.AthleteCriteria;
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
 * Service for executing complex queries for {@link ch.felberto.domain.Athlete} entities in the database.
 * The main input is a {@link AthleteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ch.felberto.domain.Athlete} or a {@link Page} of {@link ch.felberto.domain.Athlete} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AthleteQueryService extends QueryService<Athlete> {

    private final Logger log = LoggerFactory.getLogger(AthleteQueryService.class);

    private final AthleteRepository athleteRepository;

    public AthleteQueryService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * Return a {@link List} of {@link ch.felberto.domain.Athlete} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Athlete> findByCriteria(AthleteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ch.felberto.domain.Athlete} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Athlete> findByCriteria(AthleteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AthleteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.count(specification);
    }

    /**
     * Function to convert {@link AthleteCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Athlete> createSpecification(AthleteCriteria criteria) {
        Specification<Athlete> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Athlete_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Athlete_.name));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Athlete_.firstName));
            }
            if (criteria.getYearOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOfBirth(), Athlete_.yearOfBirth));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildSpecification(criteria.getNationality(), Athlete_.nationality));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Athlete_.gender));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getPosition(), Athlete_.position));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Athlete_.role));
            }
            if (criteria.getClubId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClubId(), root -> root.join(Athlete_.club, JoinType.LEFT).get(Club_.id))
                    );
            }
        }
        return specification;
    }
}
