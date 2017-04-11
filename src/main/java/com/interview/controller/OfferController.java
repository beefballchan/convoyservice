package com.interview.controller;

import com.interview.requestType.OfferStatus;
import com.interview.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    private OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @RequestMapping(value = "/offer/{offerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateOffer(@PathVariable Long offerId, @RequestBody OfferStatus offerStatus) {
        try {
            if (offerStatus.getStatus().equalsIgnoreCase("ACCEPT")) {
                offerService.acceptOffer(offerId);
            } else if (offerStatus.getStatus().equalsIgnoreCase("PASS")) {
                offerService.passOffer(offerId);
            } else {
                throw new Exception("invalid status");
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
