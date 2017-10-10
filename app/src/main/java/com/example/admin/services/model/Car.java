package com.example.admin.services.model;

/**
 * Created by Admin on 10/9/2017.
 */

public class Car {

    String type;
    String make;
    String model;
    String color;
    int year;

    public Car(String type, String make, String model, String color, int year) {
        this.type = type;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
