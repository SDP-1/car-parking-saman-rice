package module.summary;

public class ParkingAndOnDeliverySummary {

    //parking
    private String vehicleNumber;
    private String vehicalType;
    private String parkingSlot;
    private String driverName;
    private String parkTime;

    //Delivaty
    private String driverName2;
    private String delivaryTime;

    public ParkingAndOnDeliverySummary() {
    }

    public ParkingAndOnDeliverySummary(String vehicleNumber, String vehicalType, String parkingSlot, String driverName, String parkTime, String driverName2, String delivaryTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicalType = vehicalType;
        this.parkingSlot = parkingSlot;
        this.driverName = driverName;
        this.parkTime = parkTime;
        this.driverName2 = driverName2;
        this.delivaryTime = delivaryTime;
    }

    public ParkingAndOnDeliverySummary(String vehicleNumber, String vehicalType, String parkingSlot, String driverName, String parkTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicalType = vehicalType;
        this.parkingSlot = parkingSlot;
        this.driverName = driverName;
        this.parkTime = parkTime;
    }

    public ParkingAndOnDeliverySummary(String driverName2, String delivaryTime) {
        this.driverName2 = driverName2;
        this.delivaryTime = delivaryTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicalType() {
        return vehicalType;
    }

    public void setVehicalType(String vehicalType) {
        this.vehicalType = vehicalType;
    }

    public String getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(String parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getParkTime() {
        return parkTime;
    }

    public void setParkTime(String parkTime) {
        this.parkTime = parkTime;
    }

    public String getDriverName2() {
        return driverName2;
    }

    public void setDriverName2(String driverName2) {
        this.driverName2 = driverName2;
    }

    public String getDelivaryTime() {
        return delivaryTime;
    }

    public void setDelivaryTime(String delivaryTime) {
        this.delivaryTime = delivaryTime;
    }

    @Override
    public String toString() {
        return "ParkingAndOnDeliverySummary{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicalType='" + vehicalType + '\'' +
                ", parkingSlot='" + parkingSlot + '\'' +
                ", driverName='" + driverName + '\'' +
                ", parkTime='" + parkTime + '\'' +
                ", driverName2='" + driverName2 + '\'' +
                ", delivaryTime='" + delivaryTime + '\'' +
                '}';
    }
}
