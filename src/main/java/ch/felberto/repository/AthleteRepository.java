package ch.felberto.repository;

import ch.felberto.domain.Athlete;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long>, JpaSpecificationExecutor<Athlete> {
    @Override
    <S extends Athlete> List<S> findAll(Example<S> example);

    Boolean existsByName(String name);

    Boolean existsByNameAndFirstName(String name, String firstName);

    Optional<Athlete> findByNameAndFirstName(String name, String firstName);
}
