package com.example.shoplink;

public class ModelSupplier
{
    private String supplierId, email, suppName, businessName, contactNo, address, businessRegNo, description, password;

    public ModelSupplier(String supplierId, String email, String suppName,
                         String businessName, String contactNo, String address,
                         String businessRegNo, String description, String password) {
        this.supplierId = supplierId;
        this.email = email;
        this.suppName = suppName;
        this.businessName = businessName;
        this.contactNo = contactNo;
        this.address = address;
        this.businessRegNo = businessRegNo;
        this.description = description;
        this.password = password;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessRegNo() {
        return businessRegNo;
    }

    public void setBusinessRegNo(String businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public ModelSupplier()
    {

    }
}
