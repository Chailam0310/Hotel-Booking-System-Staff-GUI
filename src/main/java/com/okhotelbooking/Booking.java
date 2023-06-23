package com.okhotelbooking;

public class Booking {
    private String name;
    private String email;
    private String gender;
    private String ic;
    private String phone;
    private String room;
    private String checkin;
    private String checkout;

    private String status;

    private String totaldate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getTotaldate() {
        return totaldate;
    }

    public void setTotaldate(String totaldate) {
        this.totaldate = totaldate;
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

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Booking(String name,String ic, String phone,String email, String gender, String room, String checkin, String checkout,String totaldate, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.ic = ic;
        this.phone = phone;
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
        this.totaldate = totaldate;
        this.status = status;
    }
}
