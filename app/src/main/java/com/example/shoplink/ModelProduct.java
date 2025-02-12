package com.example.shoplink;

public class ModelProduct {

    private String  productName, supplyPrize, maxSellingPrize, ShipFeePerOrder, productQuality, miniQuantity, maxQuantity, description,imageUrl;

    public ModelProduct(String productName, String supplyPrize, String maxSellingPrize, String shipFeePerOrder, String productQuality, String miniQuantity, String maxQuantity, String description, String imageUrl) {

        this.productName = productName;
        this.supplyPrize = supplyPrize;
        this.maxSellingPrize = maxSellingPrize;
        this.ShipFeePerOrder = shipFeePerOrder;
        this.productQuality = productQuality;
        this.miniQuantity = miniQuantity;
        this.maxQuantity = maxQuantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplyPrize() {
        return supplyPrize;
    }

    public void setSupplyPrize(String supplyPrize) {
        this.supplyPrize = supplyPrize;
    }

    public String getMaxSellingPrize() {
        return maxSellingPrize;
    }

    public void setMaxSellingPrize(String maxSellingPrize) {
        this.maxSellingPrize = maxSellingPrize;
    }

    public String getShipFeePerOrder() {
        return ShipFeePerOrder;
    }

    public void setShipFeePerOrder(String shipFeePerOrder) {
        ShipFeePerOrder = shipFeePerOrder;
    }

    public String getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(String productQuality) {
        this.productQuality = productQuality;
    }

    public String getMiniQuantity() {
        return miniQuantity;
    }

    public void setMiniQuantity(String miniQuantity) {
        this.miniQuantity = miniQuantity;
    }

    public String getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(String maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
