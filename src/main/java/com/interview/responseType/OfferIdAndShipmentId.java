package com.interview.responseType;

public class OfferIdAndShipmentId {
    private Long offerId;
    private Long shipmentId;

    public OfferIdAndShipmentId(Long offerId, Long shipmentId) {
        this.offerId = offerId;
        this.shipmentId = shipmentId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
