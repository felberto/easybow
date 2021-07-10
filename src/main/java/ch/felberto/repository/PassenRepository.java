package ch.felberto.repository;

import ch.felberto.domain.Passen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Passen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassenRepository extends JpaRepository<Passen, Long> {}
