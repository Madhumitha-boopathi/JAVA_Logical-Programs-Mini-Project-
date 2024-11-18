package project;

import java.util.Date;

public class Customer {
    private int customerId;
    private String customerName;
    private String contactNo;
    private String email;
    private String address;
    private int loyaltyPoints;
    private Date lastPurchaseDate;

    // Constructor
    public Customer(int customerId, String customerName, String contactNo, String email, String address,
                    int loyaltyPoints, Date lastPurchaseDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.email = email;
        this.address = address;
        this.loyaltyPoints = loyaltyPoints;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }
}

