package com.example.roommanagement.database;

public class Student {
    private int id;
    private String name;
    private String email;
    private String gender; // "Male" or "Female"
    private int courseId;
    private String profilePicUri;

    public Student() {
    }

    public Student(String name, String email, String gender, int courseId, String profilePicUri) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.courseId = courseId;
        this.profilePicUri = profilePicUri;
    }

    public Student(int id, String name, String email, String gender, int courseId, String profilePicUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.courseId = courseId;
        this.profilePicUri = profilePicUri;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
    }
}
