package com.interview.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offer")
public class Offer {

    @ManyToOne
    private Shipment shipment;

    @ManyToOne
    private Driver driver;

    @Id
    @GeneratedValue
    private Long id;

    private Status status;

    public Offer(){}

    public Offer(Shipment shipment, Driver driver, Status status) {
        this.shipment = shipment;
        this.driver = driver;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
