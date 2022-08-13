package ch.felberto.service;

import ch.felberto.domain.Competition_;
import ch.felberto.domain.Ranking;
import ch.felberto.domain.Ranking_;
import ch.felberto.repository.RankingRepository;
import ch.felberto.service.criteria.RankingCriteria;
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
 * Service for executing complex queries for {@link Ranking} entities in the database.
 * The main input is a {@link RankingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ranking} or a {@link Page} of {@link Ranking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RankingQueryService extends QueryService<Ranking> {

    private final Logger log = LoggerFactory.getLogger(RankingQueryService.class);

    private final RankingRepository rankingRepository;

    public RankingQueryService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    /**
     * Return a {@link List} of {@link Ranking} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ranking> findByCriteria(RankingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ranking> specification = createSpecification(criteria);
        return rankingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ranking} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ranking> findByCriteria(RankingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ranking> specification = createSpecification(criteria);
        return rankingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RankingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ranking> specification = createSpecification(criteria);
        return rankingRepository.count(specification);
    }

    /**
     * Function to convert {@link RankingCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ranking> createSpecification(RankingCriteria criteria) {
        Specification<Ranking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ranking_.id));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), Ranking_.position));
            }
            if (criteria.getRankingCriteriaFilter() != null) {
                specification = specification.and(buildSpecification(criteria.getRankingCriteriaFilter(), Ranking_.rankingCriteria));
            }
            if (criteria.getWettkampfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWettkampfId(),
                            root -> root.join(Ranking_.competition, JoinType.LEFT).get(Competition_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
