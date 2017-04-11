package com.interview.dataaccess;

import com.interview.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
