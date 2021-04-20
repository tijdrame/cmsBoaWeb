package com.boa.web.repository;

import com.boa.web.domain.Institution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Institution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {}
