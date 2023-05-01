package models;

import enums.StatusOfTechnicalInspection;
import enums.TypeOfTechnicalInspection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class TechnicalInspection {
    private int id;
    private LocalDate dateOfInspection;
    private Vehicle vehicle;
    private Customer customer;
    private TypeOfTechnicalInspection typeOfTechnicalInspection;
    private StatusOfTechnicalInspection statusOfTechnicalInspection;
    private ArrayList<Employee> employees = new ArrayList<>();
    private String engineType, engineTact, typeOfFuel, typeOfGearbox;
    private String comment;
    private boolean valid;
    private double price, width, length, height;
    private int placesToSit, placesToStand, placesToLieDown;

    public TechnicalInspection() {
    }


    public TechnicalInspection(int id, LocalDate dateOfInspection, Vehicle vehicle, Customer customer, String typeOfTechnicalInspection, String statusOfTechnicalInspection) {
            this.id = id;
            this.dateOfInspection = dateOfInspection;
            this.vehicle = vehicle;
            this.customer = customer;
            this.typeOfTechnicalInspection = TypeOfTechnicalInspection.valueOf(typeOfTechnicalInspection);
            this.statusOfTechnicalInspection = StatusOfTechnicalInspection.valueOf(statusOfTechnicalInspection);
    }

    public int getPlacesToSit() {
        return placesToSit;
    }

    public void setPlacesToSit(int placesToSit) {
        this.placesToSit = placesToSit;
    }

    public int getPlacesToStand() {
        return placesToStand;
    }

    public void setPlacesToStand(int placesToStand) {
        this.placesToStand = placesToStand;
    }

    public int getPlacesToLieDown() {
        return placesToLieDown;
    }

    public void setPlacesToLieDown(int placesToLieDown) {
        this.placesToLieDown = placesToLieDown;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineTact() {
        return engineTact;
    }

    public void setEngineTact(String engineTact) {
        this.engineTact = engineTact;
    }

    public String getTypeOfFuel() {
        return typeOfFuel;
    }

    public void setTypeOfFuel(String typeOfFuel) {
        this.typeOfFuel = typeOfFuel;
    }

    public String getTypeOfGearbox() {
        return typeOfGearbox;
    }

    public void setTypeOfGearbox(String typeOfGearbox) {
        this.typeOfGearbox = typeOfGearbox;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfInspection() {
        return dateOfInspection;
    }

    public void setDateOfInspection(LocalDate dateOfInspection) {
        this.dateOfInspection = dateOfInspection;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTypeOfTechnicalInspection(TypeOfTechnicalInspection typeOfTechnicalInspection) {
        this.typeOfTechnicalInspection = typeOfTechnicalInspection;
    }

    public void setStatusOfTechnicalInspection(StatusOfTechnicalInspection statusOfTechnicalInspection) {
        this.statusOfTechnicalInspection = statusOfTechnicalInspection;
    }

    public String getTypeOfTechnicalInspection() {
        return typeOfTechnicalInspection.toString();
    }

    public void setVrstaTehnickogPregleda(String vrstaTehnickogPregleda) {
        this.typeOfTechnicalInspection = TypeOfTechnicalInspection.valueOf(vrstaTehnickogPregleda);
    }

    public String getStatusOfTechnicalInspection() {
        return statusOfTechnicalInspection.toString();
    }

    public void setStatusTehnickogPregleda(String statusTehnickogPregleda) {
        this.statusOfTechnicalInspection = StatusOfTechnicalInspection.valueOf(statusTehnickogPregleda);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalInspection that = (TechnicalInspection) o;
        return vehicle.getId() == that.vehicle.getId() && Objects.equals(dateOfInspection, that.dateOfInspection);

    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfInspection, vehicle.getId());
    }

    @Override
    public String toString() {
        return "TechnicalInspection{" +
                "dateOfInspection=" + dateOfInspection +
                ", vehicle=" + vehicle +
                ", customer=" + customer +
                ", typeOfTechnicalInspection=" + typeOfTechnicalInspection +
                ", statusOfTechnicalInspection=" + statusOfTechnicalInspection +
                '}';
    }

}
