package com.boa.web.repository;

import com.boa.web.domain.ParamFiliale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ParamFiliale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamFilialeRepository extends JpaRepository<ParamFiliale, Long> {
    ParamFiliale findByCodeFiliale(String code);
}
