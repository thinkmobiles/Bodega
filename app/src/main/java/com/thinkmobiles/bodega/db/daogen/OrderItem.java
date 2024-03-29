package com.thinkmobiles.bodega.db.daogen;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ORDER_ITEM".
 */
public class OrderItem {

    private Long id;
    /** Not-null value. */
    private String name;
    private String pdf;
    private String icon;
    private long customerId;

    public OrderItem() {
    }

    public OrderItem(Long id) {
        this.id = id;
    }

    public OrderItem(Long id, String name, String pdf, String icon, long customerId) {
        this.id = id;
        this.name = name;
        this.pdf = pdf;
        this.icon = icon;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

}
