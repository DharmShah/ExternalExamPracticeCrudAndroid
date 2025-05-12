package com.example.externalexampractice.model;

public class User {
    private String name;
    private String email;
    private String gender;
    private String address;
    private String phone;
    private String password;
    private byte[] image; // To store the image as a byte array

    public User(String name, String email, String gender, String address, String phone, String password, byte[] image) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.image = image;
    }

    // Getters and Setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
