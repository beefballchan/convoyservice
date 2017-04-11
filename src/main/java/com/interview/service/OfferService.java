package com.interview.service;

import com.interview.dataaccess.OfferRepository;
import com.interview.domain.Offer;
import com.interview.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public void acceptOffer(Long offerId) throws Exception {
        Offer offer = offerRepository.findOne(offerId);

        List<Offer> offers = offerRepository.findAllByShipmentId(offer.getShipment().getId());
        for (Offer pending : offers) {
            if (pending.getStatus() == Status.ACCEPT) {
                throw new Exception("offer was already accepted");
            }

            if (pending.getStatus() == Status.PASS) {
                continue;
            }

            if (pending.getId().equals(offerId)) {
                pending.setStatus(Status.ACCEPT);
            } else {
                pending.setStatus(Status.REVOKE);
            }
        }

        offerRepository.save(offers);
    }

    public void passOffer(Long offerId) throws Exception {
        Offer offer = offerRepository.findOne(offerId);

        if (offer.getStatus() != Status.PENDING) {
            throw new Exception("offer was already accepted");
        }

        offer.setStatus(Status.PASS);
        offerRepository.save(offer);
    }

}
