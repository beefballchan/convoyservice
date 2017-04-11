package com.interview.responseType;

import java.util.List;

public class ShipmentIdAndPendingOffers {
    private Long id;
    private List<OfferIdAndDriverId> offerAndDrivers;

    public ShipmentIdAndPendingOffers(){}

    public ShipmentIdAndPendingOffers(Long id, List<OfferIdAndDriverId> offerAndDrivers) {
        this.id = id;
        this.offerAndDrivers = offerAndDrivers;
    }

    public Long getId() {
        return id;
    }

    public List<OfferIdAndDriverId> getOfferAndDrivers() {
        return offerAndDrivers;
    }

}
