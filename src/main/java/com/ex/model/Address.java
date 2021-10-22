package com.ex.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String firstLine;
    private String secondLine;
    private String city;
    private String state;
    private String zip;

    public Address() {

    }

    public Address(int id, String firstLine, String secondLine, String city, String state, String zip) {
        this.id = id;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ",\"firstLine\":\"" + firstLine + "\"" +
                ",\"secondLine\":\"" + secondLine + "\"" +
                ",\"city\":\"" + city + "\"" +
                ",\"state\":\"" + state + "\"" +
                ",\"zip\":\"" + zip + "\"" +
                '}';
    }
}
