package com.interview.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "driver")
public class Driver {

    @OneToMany(mappedBy = "driver")
    private Set<Offer> offers = ConcurrentHashMap.newKeySet();

    @Id
    @GeneratedValue
    private Long id;

    private int capacity;

    public Driver() {
    }

    public Driver(int capacity) {
        super();
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public Long getId() {
        return id;
    }

    public Set<Offer> getOffers() {
        return offers;
    }
}
