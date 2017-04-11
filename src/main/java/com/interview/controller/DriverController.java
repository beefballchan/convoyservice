package com.interview.controller;

import com.interview.domain.Driver;
import com.interview.responseType.DriverId;
import com.interview.responseType.OfferIdAndShipmentId;
import com.interview.service.DriverService;
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
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.GET)
    public ResponseEntity<List> getDriver(@PathVariable Long driverId) {
        List<OfferIdAndShipmentId> offerIdAndShipmentIds = driverService.retrieveAcceptedOffersByDriver(driverId);
        return new ResponseEntity<>(offerIdAndShipmentIds, HttpStatus.OK);
    }


    @RequestMapping(value = "/driver", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverId> createDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverService.saveDriver(driver);
        return new ResponseEntity<>(new DriverId(savedDriver.getId()), HttpStatus.OK);
    }


}
