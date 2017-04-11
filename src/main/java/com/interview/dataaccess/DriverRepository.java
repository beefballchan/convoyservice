package com.interview.dataaccess;

import com.interview.domain.Driver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select d from Driver d left join d.offers o where d.capacity >= ?1 group by d.id order by count(o) ASC")
    List<Driver> findAllEligibleDrivers(int capacity, Pageable pageable);
}
