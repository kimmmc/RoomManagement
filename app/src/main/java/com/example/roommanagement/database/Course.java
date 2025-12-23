package com.example.roommanagement.database;

public class Course {
    private int id;
    private String title;
    private String description;
    private String startDate;
    private int roomId;

    public Course() {
    }

    public Course(String title, String description, String startDate, int roomId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.roomId = roomId;
    }

    public Course(int id, String title, String description, String startDate, int roomId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
