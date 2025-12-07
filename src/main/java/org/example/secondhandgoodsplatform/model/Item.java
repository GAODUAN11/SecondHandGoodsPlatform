package org.example.secondhandgoodsplatform.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private double price;
    private int userId; // Foreign key to User
    private String ownerName; // Name of the user who posted the item

    public Item() {}

    public Item(String name, String description, double price, int userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    public Item(int id, String name, String description, double price, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}