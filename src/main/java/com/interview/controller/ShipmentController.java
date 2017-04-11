package com.interview.controller;

import com.interview.domain.Shipment;
import com.interview.responseType.OfferIdAndDriverId;
import com.interview.responseType.ShipmentIdAndPendingOffers;
import com.interview.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShipmentController {
    private ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/shipment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShipmentIdAndPendingOffers> addShipment(@RequestBody Shipment shipment) {

        Shipment saveShipment = shipmentService.saveShipment(shipment);
        List<OfferIdAndDriverId> pendingOffers = shipmentService.createShipmentAssociatedOffers(saveShipment);
        return new ResponseEntity<>(new ShipmentIdAndPendingOffers(saveShipment.getId(), pendingOffers), HttpStatus.OK);
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<List> getShipment(@PathVariable Long shipmentId) {
        List<OfferIdAndDriverId> responseList = shipmentService.getOffersByShipment(shipmentId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }
}
