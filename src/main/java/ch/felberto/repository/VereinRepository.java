package ch.felberto.repository;

import ch.felberto.domain.Verein;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Verein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VereinRepository extends JpaRepository<Verein, Long>, JpaSpecificationExecutor<Verein> {
    Boolean existsByName(String name);

    Optional<Verein> findByName(String name);
}
