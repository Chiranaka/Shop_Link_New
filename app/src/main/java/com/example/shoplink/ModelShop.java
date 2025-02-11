package com.example.shoplink;

public class ModelShop {

    private String shopId, email, shopOwnerName, shopName, contactNo, address, shopRegNo, description, password;

    public ModelShop(String shopId, String email, String shopOwnerName, String shopName, String contactNo, String address, String shopRegNo, String description, String password) {
        this.shopId = shopId;
        this.email = email;
        this.shopOwnerName = shopOwnerName;
        this.shopName = shopName;
        this.contactNo = contactNo;
        this.address = address;
        this.shopRegNo = shopRegNo;
        this.description = description;
        this.password = password;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getShopRegNo() {
        return shopRegNo;
    }

    public void setShopRegNo(String shopRegNo) {
        this.shopRegNo = shopRegNo;
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
}
