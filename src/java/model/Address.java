/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author niklas
 */
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String postcode;
    private String county;
    private String town;
    private String telephoneNumber;

    public Address(String addressLine1, String addressLine2, String postcode, String county, String town, String telephoneNumber) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postcode = postcode;
        this.county = county;
        this.town = town;
        this.telephoneNumber = telephoneNumber;
    }

    

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "Address{" + "addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", postcode=" + postcode + ", county=" + county + ", town=" + town + ", telephoneNumber=" + telephoneNumber + '}';
    }

}
