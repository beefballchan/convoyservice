package com.interview.service;

import com.interview.dataaccess.DriverRepository;
import com.interview.dataaccess.OfferRepository;
import com.interview.dataaccess.ShipmentRepository;
import com.interview.domain.Driver;
import com.interview.domain.Offer;
import com.interview.domain.Shipment;
import com.interview.domain.Status;
import com.interview.responseType.OfferIdAndDriverId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {
    private ShipmentRepository shipmentRepository;
    private OfferRepository offerRepository;
    private DriverRepository driverRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository,
                           OfferRepository offerRepository,
                           DriverRepository driverRepository) {
        this.shipmentRepository = shipmentRepository;
        this.offerRepository = offerRepository;
        this.driverRepository = driverRepository;
    }

    public Shipment saveShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public List<OfferIdAndDriverId> createShipmentAssociatedOffers(Shipment shipment) {
        List<Driver> eligibleDrivers = driverRepository.findAllEligibleDrivers(shipment.getCapacity(), new PageRequest(0, 10));
        List<Offer> offers = eligibleDrivers.stream().map(
                eligibleDriver -> new Offer(shipment, eligibleDriver, Status.PENDING)).collect(Collectors.toList());
        List<Offer> savedOffers = offerRepository.save(offers);

        return savedOffers.stream().map(
                savedOffer -> new OfferIdAndDriverId(savedOffer.getId(), savedOffer.getDriver().getId())).collect(
                Collectors.toList());
    }

    public List<OfferIdAndDriverId> getOffersByShipment(Long shipmentId) {
        List<Offer> offers = offerRepository.findAllByShipmentId(shipmentId);

        List<Offer> acceptedOffer = offers.stream().filter(offer -> offer.getStatus() == Status.ACCEPT).collect(
                Collectors.toList());

        List<OfferIdAndDriverId> responseList = new ArrayList<>();
        if (acceptedOffer.size() == 0) {
            responseList.addAll(offers.stream().map(
                    offer -> new OfferIdAndDriverId(offer.getId(), offer.getDriver().getId())).collect(
                    Collectors.toList()));
        } else {
            Offer theOffer = acceptedOffer.get(0);
            responseList.add(new OfferIdAndDriverId(theOffer.getId(), theOffer.getDriver().getId()));
        }

        return responseList;
    }
}
