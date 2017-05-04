package com.example.rachamim.hacaktonproj.Model;

/**
 * Created by Rachamim on 5/2/17.
 */

public class User {
    private String email;
    private String phone;

    public User(){}

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String licensePlate;
    private String password;
    private String lastUpdate;
    private Boolean blocked;
    private Boolean blocking;
    private String otherUserId;
    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getBlocking() {
        return blocking;
    }

    public void setBlocking(Boolean blocking) {
        this.blocking = blocking;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public User(String mail, String phone, String licensePlate, String date, Boolean blocked, Boolean blocking, String otherUserId) {
        this.email = mail;
        this.phone = phone;
        this.licensePlate = licensePlate;
        this.lastUpdate = date;
        this.blocked = blocked;
        this.blocking = blocking;
        this.otherUserId = otherUserId;
    }

    public User(String mail, String password, String phone, String lastUpdate){
        this.email = mail;
        this.password = password;
        this.phone = phone;
        this.lastUpdate = lastUpdate;
        this.blocked = false;
        this.blocking = false;
        this.otherUserId = "-1";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public String getEmail() {
        return email;
    }


    public String getlastUpdate() {
        return lastUpdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setlastUpdate (String date) {
        this.lastUpdate = date;
    }
}
