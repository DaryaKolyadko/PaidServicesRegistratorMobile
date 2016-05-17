package com.darya.paidserviceregistrator.entities;

/**
 * Created by USER on 13.05.2016.
 */
public class Service {
    private int id;
    private String name;

    public Service() {
    }

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
