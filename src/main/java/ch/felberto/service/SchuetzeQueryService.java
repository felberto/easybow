package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Schuetze;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.criteria.SchuetzeCriteria;
import ch.felberto.service.dto.SchuetzeDTO;
import ch.felberto.service.mapper.SchuetzeMapper;
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
 * Service for executing complex queries for {@link Schuetze} entities in the database.
 * The main input is a {@link SchuetzeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchuetzeDTO} or a {@link Page} of {@link SchuetzeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchuetzeQueryService extends QueryService<Schuetze> {

    private final Logger log = LoggerFactory.getLogger(SchuetzeQueryService.class);

    private final SchuetzeRepository schuetzeRepository;

    private final SchuetzeMapper schuetzeMapper;

    public SchuetzeQueryService(SchuetzeRepository schuetzeRepository, SchuetzeMapper schuetzeMapper) {
        this.schuetzeRepository = schuetzeRepository;
        this.schuetzeMapper = schuetzeMapper;
    }

    /**
     * Return a {@link List} of {@link SchuetzeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchuetzeDTO> findByCriteria(SchuetzeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Schuetze> specification = createSpecification(criteria);
        return schuetzeMapper.toDto(schuetzeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchuetzeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchuetzeDTO> findByCriteria(SchuetzeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Schuetze> specification = createSpecification(criteria);
        return schuetzeRepository.findAll(specification, page).map(schuetzeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchuetzeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Schuetze> specification = createSpecification(criteria);
        return schuetzeRepository.count(specification);
    }

    /**
     * Function to convert {@link SchuetzeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Schuetze> createSpecification(SchuetzeCriteria criteria) {
        Specification<Schuetze> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Schuetze_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Schuetze_.name));
            }
            if (criteria.getJahrgang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJahrgang(), Schuetze_.jahrgang));
            }
            if (criteria.getStellung() != null) {
                specification = specification.and(buildSpecification(criteria.getStellung(), Schuetze_.stellung));
            }
            if (criteria.getVereinId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVereinId(), root -> root.join(Schuetze_.verein, JoinType.LEFT).get(Verein_.id))
                    );
            }
        }
        return specification;
    }
}
