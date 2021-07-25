package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
import ch.felberto.service.criteria.VerbandCriteria;
import ch.felberto.service.dto.VerbandDTO;
import ch.felberto.service.mapper.VerbandMapper;
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
 * Service for executing complex queries for {@link Verband} entities in the database.
 * The main input is a {@link VerbandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VerbandDTO} or a {@link Page} of {@link VerbandDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VerbandQueryService extends QueryService<Verband> {

    private final Logger log = LoggerFactory.getLogger(VerbandQueryService.class);

    private final VerbandRepository verbandRepository;

    private final VerbandMapper verbandMapper;

    public VerbandQueryService(VerbandRepository verbandRepository, VerbandMapper verbandMapper) {
        this.verbandRepository = verbandRepository;
        this.verbandMapper = verbandMapper;
    }

    /**
     * Return a {@link List} of {@link VerbandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VerbandDTO> findByCriteria(VerbandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Verband> specification = createSpecification(criteria);
        return verbandMapper.toDto(verbandRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VerbandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VerbandDTO> findByCriteria(VerbandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Verband> specification = createSpecification(criteria);
        return verbandRepository.findAll(specification, page).map(verbandMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VerbandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Verband> specification = createSpecification(criteria);
        return verbandRepository.count(specification);
    }

    /**
     * Function to convert {@link VerbandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Verband> createSpecification(VerbandCriteria criteria) {
        Specification<Verband> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Verband_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Verband_.name));
            }
        }
        return specification;
    }
}