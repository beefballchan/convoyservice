package com.interview.controller;

import com.interview.ConvoyServiceApplication;
import com.interview.ConvoyServiceApplicationTests;
import com.interview.domain.Driver;
import com.interview.domain.Shipment;
import com.interview.requestType.OfferStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvoyServiceApplication.class)
@WebAppConfiguration
public class DriverControllerTest extends ConvoyServiceApplicationTests {

    @Test
    public void shouldAddDriver() throws Exception {
        String driverJson = json(new Driver(50));
        mockMvc.perform(post("/driver").contentType(contentType).content(driverJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldReturnBadRequestWithNullContent() throws Exception {
        String driverJson = json(null);
        mockMvc.perform(post("/driver").contentType(contentType).content(driverJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnBadRequestWithNoContent() throws Exception {
        mockMvc.perform(post("/driver").contentType(contentType))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGetDriverWithNoActiveOffers() throws Exception {
        mockMvc.perform(get("/driver/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetDriverWithNonExistentId() throws Exception {
        mockMvc.perform(get("/driver/9999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnBadRequestWithInvalidDriverId() throws Exception {
        mockMvc.perform(get("/driver/invalid"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGetDriverWithActiveOffer() throws Exception {
        String shipmentJson = json(new Shipment(50));
        mockMvc.perform(post("/shipment").contentType(contentType).content(shipmentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.offerAndDrivers[0].offerId", is(1)));


        String statusJson = json(new OfferStatus("ACCEPT"));
        mockMvc.perform(put("/offer/1").contentType(contentType).content(statusJson));

        mockMvc.perform(get("/driver/1"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
