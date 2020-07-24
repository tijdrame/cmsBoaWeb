package com.boa.web.repository;

import com.boa.web.domain.TypeIdentif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeIdentif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIdentifRepository extends JpaRepository<TypeIdentif, Long> {
}
