package com.leet5.ecommerce.model.vo;

public enum ShipmentStatus {
    PENDING("Pending"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    FAILED("Failed"),
    RETURNED("Returned");

    private final String displayName;

    ShipmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
