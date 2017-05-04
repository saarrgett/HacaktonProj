package com.example.rachamim.hacaktonproj.Model;

/**
 * Created by Rachamim on 5/2/17.
 */

public class User {
    private String email;
    private String phone;

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
    private String blocked;
    private String blocking;
    private String otherUserId;

    public User(String mail,String phone, String licensePlate, String date, String blocked, String blocking, String otherUserId) {
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
        this.blocked = "false";
        this.blocking = "false";
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
