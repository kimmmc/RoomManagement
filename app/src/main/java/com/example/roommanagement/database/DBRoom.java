package com.example.roommanagement.database;

public class DBRoom {
    private int id;
    private String name;
    private String category;
    private int capacity;
    private String price;
    private String imageUri;

    public DBRoom() {
    }

    public DBRoom(String name, String category, int capacity, String price, String imageUri) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.price = price;
        this.imageUri = imageUri;
    }

    public DBRoom(int id, String name, String category, int capacity, String price, String imageUri) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.price = price;
        this.imageUri = imageUri;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    // For spinner display
    @Override
    public String toString() {
        return name;
    }
}
