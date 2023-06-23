package com.okhotelbooking;

public class Room {
    public String RoomID;

    public String Status;

    public Room(String roomID, String status) {
        RoomID = roomID;
        Status = status;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}

