package module;

import java.time.LocalDateTime;

public class Park {
    private String vehicleNumber;
    private String Type;
    private int parkSlot;
    private String parkingTime;

    public Park(String vehicleNumber, String type, int parkSlot, String parkingTime) {
        this.vehicleNumber = vehicleNumber;
        Type = type;
        this.parkSlot = parkSlot;
        this.parkingTime = parkingTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getParkSlot() {
        return parkSlot;
    }

    public void setParkSlot(int parkSlot) {
        this.parkSlot = parkSlot;
    }

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }
}
