package ch.felberto.repository;

import ch.felberto.domain.Verband;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Verband entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerbandRepository extends JpaRepository<Verband, Long>, JpaSpecificationExecutor<Verband> {
    Optional<Verband> findByName(String name);
}
