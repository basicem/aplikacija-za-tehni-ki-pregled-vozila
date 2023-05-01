package models;

import enums.VehicleBrand;
import enums.VehicleType;

public class Vehicle {
    private int id;
    private VehicleType type;
    private VehicleBrand brand;
    private String model;
    private int yearOfProduction;
    private String registration;
    private String chassisNumber;
    private String color;
    private String colorType;

    public Vehicle() {
    }

    public Vehicle(int id, String type, String brand, String model, int yearOfProduction, String registration, String chassisNumber, String color, String colorType) {
        this.id = id;
        this.type = VehicleType.valueOf(type);
        this.brand = VehicleBrand.valueOf(brand);
        this.yearOfProduction = yearOfProduction;
        this.model = model;
        this.registration = registration;
        this.chassisNumber = chassisNumber;
        this.color = color;
        this.colorType = colorType;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = VehicleType.valueOf(type);
    }

    public String getBrand() {
        return brand.toString();
    }

    public void setBrand(String brand) {
        this.brand = VehicleBrand.valueOf(brand);
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return type + ", " + brand + ", " + model + ", " + registration;
    }
}
