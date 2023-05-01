package models;

import java.util.Objects;

public class Customer {
    	private int id;
    	private String firstName;
    	private String lastName;
    	private String address;
    	private String phoneNumber;

    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, String adress, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = adress;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer klijent = (Customer) o;
        return Objects.equals(firstName, klijent.firstName) &&
                Objects.equals(lastName, klijent.lastName) &&
                Objects.equals(address, klijent.address) &&
                Objects.equals(phoneNumber, klijent.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phoneNumber);
    }
}
