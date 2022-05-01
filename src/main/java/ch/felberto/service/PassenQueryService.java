package ch.felberto.service;

import ch.felberto.domain.Passen;
import ch.felberto.domain.Passen_;
import ch.felberto.repository.PassenRepository;
import ch.felberto.service.criteria.PassenCriteria;
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
 * Service for executing complex queries for {@link Passen} entities in the database.
 * The main input is a {@link PassenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Passen} or a {@link Page} of {@link Passen} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PassenQueryService extends QueryService<Passen> {

    private final Logger log = LoggerFactory.getLogger(PassenQueryService.class);

    private final PassenRepository passenRepository;

    public PassenQueryService(PassenRepository passenRepository) {
        this.passenRepository = passenRepository;
    }

    /**
     * Return a {@link List} of {@link Passen} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Passen> findByCriteria(PassenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Passen> specification = createSpecification(criteria);
        return passenRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Passen} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Passen> findByCriteria(PassenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Passen> specification = createSpecification(criteria);
        return passenRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PassenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Passen> specification = createSpecification(criteria);
        return passenRepository.count(specification);
    }

    /**
     * Function to convert {@link PassenCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Passen> createSpecification(PassenCriteria criteria) {
        Specification<Passen> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Passen_.id));
            }
            if (criteria.getp1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp1(), Passen_.p1));
            }
            if (criteria.getp2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp2(), Passen_.p2));
            }
            if (criteria.getp3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp3(), Passen_.p3));
            }
            if (criteria.getp4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp4(), Passen_.p4));
            }
            if (criteria.getp5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp5(), Passen_.p5));
            }
            if (criteria.getp6() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp6(), Passen_.p6));
            }
            if (criteria.getp7() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp7(), Passen_.p7));
            }
            if (criteria.getp8() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp8(), Passen_.p8));
            }
            if (criteria.getp9() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp9(), Passen_.p9));
            }
            if (criteria.getp10() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getp10(), Passen_.p10));
            }
            if (criteria.getResultat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResultat(), Passen_.resultat));
            }
        }
        return specification;
    }
}
