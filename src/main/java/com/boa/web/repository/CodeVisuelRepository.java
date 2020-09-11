package com.boa.web.repository;

import java.util.Optional;

import com.boa.web.domain.CodeVisuel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CodeVisuel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeVisuelRepository extends JpaRepository<CodeVisuel, Long> {
    
    @Query("Select c from CodeVisuel c where :number > c.rangeLow and :number < c.rangeHigh")
    Optional<CodeVisuel> findBySearching(@Param("number") Long number);
}
