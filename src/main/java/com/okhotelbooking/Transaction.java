package com.okhotelbooking;

public class Transaction {
    private String name;
    private String ic;
    private String room;
    private String checkin;
    private String checkout;
    private String totaldate;
    private String price;
    private String payment;

    public String getName() {
        return name;
    }

    public Transaction(String name, String ic, String room, String checkin, String checkout, String totaldate, String price, String payment) {
        this.name = name;
        this.ic = ic;
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
        this.totaldate = totaldate;
        this.price = price;
        this.payment = payment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
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

    public String getTotaldate() {
        return totaldate;
    }

    public void setTotaldate(String totaldate) {
        this.totaldate = totaldate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


}
