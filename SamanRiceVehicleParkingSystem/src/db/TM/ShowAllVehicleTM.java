package db.TM;

import module.DeliveryVehicle;

public class ShowAllVehicleTM extends DeliveryVehicle {
    private int indexNo;

    public ShowAllVehicleTM(int indexNo, String number, String type, int maximumWeight, int numberOfPassengers) {
        super(number, type, maximumWeight, numberOfPassengers);
        this.indexNo = indexNo;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }
}
