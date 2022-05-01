package ch.felberto.service;

import ch.felberto.domain.*; // for static metamodels
import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.criteria.WettkampfCriteria;
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
 * Service for executing complex queries for {@link Wettkampf} entities in the database.
 * The main input is a {@link WettkampfCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Wettkampf} or a {@link Page} of {@link Wettkampf} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WettkampfQueryService extends QueryService<Wettkampf> {

    private final Logger log = LoggerFactory.getLogger(WettkampfQueryService.class);

    private final WettkampfRepository wettkampfRepository;

    public WettkampfQueryService(WettkampfRepository wettkampfRepository) {
        this.wettkampfRepository = wettkampfRepository;
    }

    /**
     * Return a {@link List} of {@link Wettkampf} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Wettkampf> findByCriteria(WettkampfCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Wettkampf> specification = createSpecification(criteria);
        return wettkampfRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Wettkampf} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Wettkampf> findByCriteria(WettkampfCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wettkampf> specification = createSpecification(criteria);
        return wettkampfRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WettkampfCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Wettkampf> specification = createSpecification(criteria);
        return wettkampfRepository.count(specification);
    }

    /**
     * Function to convert {@link WettkampfCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Wettkampf> createSpecification(WettkampfCriteria criteria) {
        Specification<Wettkampf> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Wettkampf_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Wettkampf_.name));
            }
            if (criteria.getJahr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJahr(), Wettkampf_.jahr));
            }
            if (criteria.getAnzahlRunden() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnzahlRunden(), Wettkampf_.anzahlRunden));
            }
            if (criteria.getAnzahlPassen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnzahlPassen(), Wettkampf_.anzahlPassen));
            }
            if (criteria.getFinalRunde() != null) {
                specification = specification.and(buildSpecification(criteria.getFinalRunde(), Wettkampf_.finalRunde));
            }
            if (criteria.getFinalVorbereitung() != null) {
                specification = specification.and(buildSpecification(criteria.getFinalVorbereitung(), Wettkampf_.finalVorbereitung));
            }
            if (criteria.getAnzahlFinalteilnehmer() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAnzahlFinalteilnehmer(), Wettkampf_.anzahlFinalteilnehmer));
            }
            if (criteria.getAnzahlPassenFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnzahlPassenFinal(), Wettkampf_.anzahlPassenFinal));
            }
            if (criteria.getAnzahlTeam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnzahlTeam(), Wettkampf_.anzahlTeam));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplate(), Wettkampf_.template));
            }
        }
        return specification;
    }
}
