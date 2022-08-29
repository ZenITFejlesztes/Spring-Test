package com.example.demo.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpItems {
    @JsonProperty("AccountName")
    String AccountName;

    public SpItems(String accountName) {
        AccountName = accountName;
    }

    public SpItems() {
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }
}
