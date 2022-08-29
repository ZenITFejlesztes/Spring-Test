package com.example.demo.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CDSContractItems {

    @JsonProperty("crdf5_productname")
    String productName;
    @JsonProperty("crdf5_customer")
    String customer;
    @JsonProperty("crdf5_applieddevizatype@OData.Community.Display.V1.FormattedValue")
    String appliedDevizaType;
    @JsonProperty("crdf5_quamtity")
    int quantity;
    @JsonProperty("crdf5_contracteurprice_base")
    int eurPrice;
    @JsonProperty("crdf5_contracthufprice_base")
    int hufPrice;
    @JsonProperty("crdf5_contractid")
    int contractNumber;
    @JsonProperty("crdf5_validfrom")
    Date validFrom;
    @JsonProperty("crdf5_validtill")
    Date validTill;

    String priceString;
    String priceStringAll;


    public String getPriceStringCalc(){
        try {
            if (appliedDevizaType.equals("HUF"))
                return hufPrice + " Ft";
            else
                return eurPrice + " €";
        }catch (Exception ex)
        {
            System.out.println(ex);
            return "";
        }


    }

    public String getPriceStringCalcAll(){
        try {
        if (appliedDevizaType.equals("HUF"))
            return hufPrice*quantity + " Ft";
        else
            return eurPrice*quantity + " €";
        }catch (Exception ex)
        {
            System.out.println(ex);
            return "";
        }
    }

    public String getPriceStringAll() {
        return priceStringAll;
    }

    public void setPriceStringAll(String priceStringAll) {
        this.priceStringAll = priceStringAll;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAppliedDevizaType() {
        return appliedDevizaType;
    }

    public void setAppliedDevizaType(String appliedDevizaType) {
        this.appliedDevizaType = appliedDevizaType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEurPrice() {
        return eurPrice;
    }

    public void setEurPrice(int eurPrice) {
        this.eurPrice = eurPrice;
    }

    public int getHufPrice() {
        return hufPrice;
    }

    public void setHufPrice(int hufPrice) {
        this.hufPrice = hufPrice;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTillm() {
        return validTill;
    }

    public void setValidTillm(Date validTillm) {
        this.validTill = validTillm;
    }

    public CDSContractItems() {
    }

    public CDSContractItems(String productName, String customer, String appliedDevizaType, int quantity, int eurPrice, int hufPrice, int contractNumber, Date validFrom, Date validTillm) {
        this.productName = productName;
        this.customer = customer;
        this.appliedDevizaType = appliedDevizaType;
        this.quantity = quantity;
        this.eurPrice = eurPrice;
        this.hufPrice = hufPrice;
        this.contractNumber = contractNumber;
        this.validFrom = validFrom;
        this.validTill = validTillm;
    }
}
