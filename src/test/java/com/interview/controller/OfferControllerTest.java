package com.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.ConvoyServiceApplication;
import com.interview.ConvoyServiceApplicationTests;
import com.interview.domain.Driver;
import com.interview.domain.Shipment;
import com.interview.requestType.OfferStatus;
import com.interview.responseType.OfferIdAndDriverId;
import com.interview.responseType.ShipmentIdAndPendingOffers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvoyServiceApplication.class)
@WebAppConfiguration
public class OfferControllerTest extends ConvoyServiceApplicationTests {

    private ShipmentIdAndPendingOffers shipmentIdAndPendingOffers;

    @Before
    public void setup() throws Exception {
        super.setup();
        for (int i = 0; i < 10; i++) {
            String driverJson = json(new Driver(50));
            mockMvc.perform(post("/driver").contentType(contentType).content(driverJson))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType));
        }

        String driverJson = json(new Driver(10));
        mockMvc.perform(post("/driver").contentType(contentType).content(driverJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        String shipmentJson = json(new Shipment(50));
        MvcResult result = mockMvc.perform(post("/shipment").contentType(contentType).content(shipmentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.offerAndDrivers", hasSize(10)))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        shipmentIdAndPendingOffers = objectMapper.readValue(result.getResponse().getContentAsString(), ShipmentIdAndPendingOffers.class);

    }

    @Test
    public void shouldGetShipmentWithPassOffer() throws Exception {
        OfferIdAndDriverId passOfferIdAndDriverId = shipmentIdAndPendingOffers.getOfferAndDrivers().get(9);
        Long passOfferId = passOfferIdAndDriverId.getOfferId();
        String passJson = json(new OfferStatus("PASS"));
        mockMvc.perform(put("/offer/" + passOfferId).contentType(contentType).content(passJson));

        mockMvc.perform(get("/shipment/" + shipmentIdAndPendingOffers.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    public void shouldGetShipmentWithActiveOffer() throws Exception {
        OfferIdAndDriverId passOfferIdAndDriverId = shipmentIdAndPendingOffers.getOfferAndDrivers().get(9);
        Long passOfferId = passOfferIdAndDriverId.getOfferId();
        String passJson = json(new OfferStatus("PASS"));
        mockMvc.perform(put("/offer/" + passOfferId).contentType(contentType).content(passJson));


        OfferIdAndDriverId offerIdAndDriverId = shipmentIdAndPendingOffers.getOfferAndDrivers().get(0);
        Long offerId = offerIdAndDriverId.getOfferId();
        Long driverId = offerIdAndDriverId.getDriverId();

        String acceptJson = json(new OfferStatus("ACCEPT"));
        mockMvc.perform(put("/offer/" + offerId).contentType(contentType).content(acceptJson));

        mockMvc.perform(get("/driver/" + driverId)).andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/shipment/" + shipmentIdAndPendingOffers.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
