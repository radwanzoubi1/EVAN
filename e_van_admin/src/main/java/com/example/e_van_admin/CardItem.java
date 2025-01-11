package com.example.e_van_admin;
public class CardItem {
    private String name;
    private String phone;
    private String service;
    private double cost;

    private Double lon;
    private Double lat;

    public String DocumentId ;

    public Double getLon() {
        return lon;
    }



    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public int getIndex() {
        return index;
    }

    private int index;
    public CardItem() {
        // Empty constructor
    }

    public CardItem(String name, String phone, String service,double cost) {
        this.name = name;
        this.phone = phone;
        this.service = service;
        this.cost = cost;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    // Constructor, getters, and setters
    public void setIndex(int index) {
        this.index = index;
    }


    public String getDocumentId() {
        return DocumentId;
    }
    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }
}

