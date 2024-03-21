package com.example.assignment_and103_ph39920.Model;

public class MotorModel {
    private String _id,name,brand;
    private double price;
    private  int quantity;
    private  boolean status;

    public MotorModel() {
    }

    public MotorModel(String name, String brand, double price, int quantity, boolean status) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public MotorModel(String _id, String name, String brand, double price, int quantity, boolean status) {
        this._id = _id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
