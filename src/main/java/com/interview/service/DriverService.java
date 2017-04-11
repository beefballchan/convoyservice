package com.interview.service;

import com.interview.dataaccess.DriverRepository;
import com.interview.dataaccess.OfferRepository;
import com.interview.domain.Driver;
import com.interview.domain.Offer;
import com.interview.domain.Status;
import com.interview.responseType.OfferIdAndShipmentId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, OfferRepository offerRepository) {
        this.driverRepository = driverRepository;
        this.offerRepository = offerRepository;
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<OfferIdAndShipmentId> retrieveAcceptedOffersByDriver(Long driverId) {
        List<Offer> offers = offerRepository.findAllByDriverIdAndStatus(driverId, Status.ACCEPT);

        return offers.stream().map(
                offer -> new OfferIdAndShipmentId(offer.getId(), offer.getShipment().getId())).collect(
                Collectors.toList());
    }
}
