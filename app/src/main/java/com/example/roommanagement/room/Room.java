package com.example.roommanagement.room;

public class Room {
    private String name;
    private String category;
    private int capacity;
    private String price;
    private boolean isHighlighted;

    public Room(String name, String category, int capacity, String price, boolean isHighlighted) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.price = price;
        this.isHighlighted = isHighlighted;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getPrice() {
        return price;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }
}
