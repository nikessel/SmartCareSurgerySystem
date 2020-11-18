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
    String postcode;
    String town;
    String addressLine1;
    String addressLine2;

    public Address(String postcode, String town, String addressLine1, String addressLine2) {
        this.postcode = postcode;
        this.town = town;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public Address(String postcode, String town, String addressLine1) {
        this.postcode = postcode;
        this.town = town;
        this.addressLine1 = addressLine1;
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

    @Override
    public String toString() {
        return "Address{" + "postcode=" + postcode + ", town=" + town + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + '}';
    }
    
    
}
