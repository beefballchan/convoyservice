package com.interview.dataaccess;

import com.interview.domain.Offer;
import com.interview.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByDriverIdAndStatus(Long driverId, Status status);

    List<Offer> findAllByShipmentId(Long shipmentId);

    @Transactional
    <S extends Offer> List<S> save(Iterable<S> var1);
}
