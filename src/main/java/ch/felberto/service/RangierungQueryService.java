package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Rangierung;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.service.criteria.RangierungCriteria;
import ch.felberto.service.dto.RangierungDTO;
import ch.felberto.service.mapper.RangierungMapper;
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
 * Service for executing complex queries for {@link Rangierung} entities in the database.
 * The main input is a {@link RangierungCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RangierungDTO} or a {@link Page} of {@link RangierungDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RangierungQueryService extends QueryService<Rangierung> {

    private final Logger log = LoggerFactory.getLogger(RangierungQueryService.class);

    private final RangierungRepository rangierungRepository;

    private final RangierungMapper rangierungMapper;

    public RangierungQueryService(RangierungRepository rangierungRepository, RangierungMapper rangierungMapper) {
        this.rangierungRepository = rangierungRepository;
        this.rangierungMapper = rangierungMapper;
    }

    /**
     * Return a {@link List} of {@link RangierungDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RangierungDTO> findByCriteria(RangierungCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rangierung> specification = createSpecification(criteria);
        return rangierungMapper.toDto(rangierungRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RangierungDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RangierungDTO> findByCriteria(RangierungCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rangierung> specification = createSpecification(criteria);
        return rangierungRepository.findAll(specification, page).map(rangierungMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RangierungCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rangierung> specification = createSpecification(criteria);
        return rangierungRepository.count(specification);
    }

    /**
     * Function to convert {@link RangierungCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rangierung> createSpecification(RangierungCriteria criteria) {
        Specification<Rangierung> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rangierung_.id));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), Rangierung_.position));
            }
            if (criteria.getRangierungskriterien() != null) {
                specification = specification.and(buildSpecification(criteria.getRangierungskriterien(), Rangierung_.rangierungskriterien));
            }
            if (criteria.getWettkampfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWettkampfId(),
                            root -> root.join(Rangierung_.wettkampf, JoinType.LEFT).get(Wettkampf_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
