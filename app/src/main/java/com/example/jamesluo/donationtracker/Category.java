package com.example.jamesluo.donationtracker;

public enum Category {
    Clothing("Clothing"),
    Hat("Hat"),
    Kitchen("Kitchen"),
    Electronics("Electronics"),
    Household("Household"),
    Other("Other");
    private String value;
    Category(String letter) {
        value = letter;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
