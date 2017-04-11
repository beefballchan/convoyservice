package com.interview.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "shipment")
public class Shipment {

    @OneToMany(mappedBy = "shipment")
    private Set<Offer> offers = ConcurrentHashMap.newKeySet();

    @Id
    @GeneratedValue
    private Long id;

    private int capacity;

    public Shipment() {}

    public Shipment(int capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<Offer> getOffers() {
        return offers;
    }
}
