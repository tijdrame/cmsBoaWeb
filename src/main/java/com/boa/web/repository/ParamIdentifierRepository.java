package com.boa.web.repository;

import com.boa.web.domain.ParamIdentifier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParamIdentifier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamIdentifierRepository extends JpaRepository<ParamIdentifier, Long> {
}
