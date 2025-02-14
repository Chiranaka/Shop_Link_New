package com.example.shoplink;

public class ModelProduct {

    private String productId;
    private String productName;
    private String supplyPrize;
    private String maxSellingPrize;
    private String ShipFeePerOrder;
    private String productQuality;
    private String miniQuantity;
    private String maxQuantity;
    private String description;
    private String imageUrl;

    private String productCode;


    public ModelProduct(String productId, String productName, String supplyPrize, String maxSellingPrize,
                        String shipFeePerOrder, String productQuality, String miniQuantity, String maxQuantity,
                        String description, String imageUrl, String productCode) {
        this.productId = productId;
        this.productName = productName;
        this.supplyPrize = supplyPrize;
        this.maxSellingPrize = maxSellingPrize;
        ShipFeePerOrder = shipFeePerOrder;
        this.productQuality = productQuality;
        this.miniQuantity = miniQuantity;
        this.maxQuantity = maxQuantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.productCode = productCode;
    }

    public ModelProduct(String productId, String productName, String supplyPrize, String maxSellingPrize,
                        String shipFeePerOrder, String productQuality, String miniQuantity, String maxQuantity,
                        String description,String productCode) {
        this.productId = productId;
        this.productName = productName;
        this.supplyPrize = supplyPrize;
        this.maxSellingPrize = maxSellingPrize;
        ShipFeePerOrder = shipFeePerOrder;
        this.productQuality = productQuality;
        this.miniQuantity = miniQuantity;
        this.maxQuantity = maxQuantity;
        this.description = description;
        this.productCode = productCode;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
