package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.criteria.VereinCriteria;
import ch.felberto.service.dto.VereinDTO;
import ch.felberto.service.mapper.VereinMapper;
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
 * Service for executing complex queries for {@link Verein} entities in the database.
 * The main input is a {@link VereinCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VereinDTO} or a {@link Page} of {@link VereinDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VereinQueryService extends QueryService<Verein> {

    private final Logger log = LoggerFactory.getLogger(VereinQueryService.class);

    private final VereinRepository vereinRepository;

    private final VereinMapper vereinMapper;

    public VereinQueryService(VereinRepository vereinRepository, VereinMapper vereinMapper) {
        this.vereinRepository = vereinRepository;
        this.vereinMapper = vereinMapper;
    }

    /**
     * Return a {@link List} of {@link VereinDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VereinDTO> findByCriteria(VereinCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinMapper.toDto(vereinRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VereinDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VereinDTO> findByCriteria(VereinCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinRepository.findAll(specification, page).map(vereinMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VereinCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Verein> specification = createSpecification(criteria);
        return vereinRepository.count(specification);
    }

    /**
     * Function to convert {@link VereinCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Verein> createSpecification(VereinCriteria criteria) {
        Specification<Verein> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Verein_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Verein_.name));
            }
            if (criteria.getVerbandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVerbandId(), root -> root.join(Verein_.verband, JoinType.LEFT).get(Verband_.id))
                    );
            }
        }
        return specification;
    }
}
