package com.example.demo.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customers {
    @JsonProperty("crdf5_customername")
    String customer;
    @JsonProperty("crdf5_customerbillingoidv3")
    int billingo;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getBillingo() {
        return billingo;
    }

    public void setBillingo(int billingo) {
        this.billingo = billingo;
    }

    public Customers(String customer, int billingo) {
        this.customer = customer;
        this.billingo = billingo;
    }
    public Customers() {
    }
}
