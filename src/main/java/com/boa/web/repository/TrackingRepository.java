package com.boa.web.repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.boa.web.domain.Tracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    Optional<List<Tracking>> findByRequestTrContainsAndEndPointTrContainsOrderByIdDesc(String idClient, String proxyCall);
}
