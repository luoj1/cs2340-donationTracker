package com.example.jamesluo.donationtracker;
/* get enum class for item category
 */
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
    /* set value for the category

     */
    public void setValue(String value) {
        this.value = value;
    }
    /* get the value of the category

     */
    public String getValue() {
        return value;
    }
}
