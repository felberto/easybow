package ch.felberto.service;

import ch.felberto.domain.Series;
import ch.felberto.domain.Series_;
import ch.felberto.repository.SeriesRepository;
import ch.felberto.service.criteria.SeriesCriteria;
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
 * Service for executing complex queries for {@link Series} entities in the database.
 * The main input is a {@link SeriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Series} or a {@link Page} of {@link Series} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeriesQueryService extends QueryService<Series> {

    private final Logger log = LoggerFactory.getLogger(SeriesQueryService.class);

    private final SeriesRepository seriesRepository;

    public SeriesQueryService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    /**
     * Return a {@link List} of {@link Series} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Series> findByCriteria(SeriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Series} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Series> findByCriteria(SeriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SeriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Series> specification = createSpecification(criteria);
        return seriesRepository.count(specification);
    }

    /**
     * Function to convert {@link SeriesCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Series> createSpecification(SeriesCriteria criteria) {
        Specification<Series> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Series_.id));
            }
            if (criteria.getp1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp1(), Series_.p1));
            }
            if (criteria.getp2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp2(), Series_.p2));
            }
            if (criteria.getp3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp3(), Series_.p3));
            }
            if (criteria.getp4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp4(), Series_.p4));
            }
            if (criteria.getp5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp5(), Series_.p5));
            }
            if (criteria.getp6() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp6(), Series_.p6));
            }
            if (criteria.getp7() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp7(), Series_.p7));
            }
            if (criteria.getp8() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp8(), Series_.p8));
            }
            if (criteria.getp9() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp9(), Series_.p9));
            }
            if (criteria.getp10() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp10(), Series_.p10));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResult(), Series_.result));
            }
        }
        return specification;
    }
}
