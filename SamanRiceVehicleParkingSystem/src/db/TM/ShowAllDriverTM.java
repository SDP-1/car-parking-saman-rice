package db.TM;

import module.Driver;

public class ShowAllDriverTM extends Driver {
    private int indexNo;

    public ShowAllDriverTM(int indexNo, String name, String NIC, String drivingLicenseNo, String address, String contact) {
        super(name, NIC, drivingLicenseNo, address, contact);
        this.indexNo = indexNo;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }
}
