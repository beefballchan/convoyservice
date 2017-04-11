package com.interview.responseType;


public class OfferIdAndDriverId {

    private Long offerId;
    private Long driverId;

    public OfferIdAndDriverId() {}

    public OfferIdAndDriverId(Long offerId, Long driverId) {
        this.offerId = offerId;
        this.driverId = driverId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getDriverId() {
        return driverId;
    }

}
