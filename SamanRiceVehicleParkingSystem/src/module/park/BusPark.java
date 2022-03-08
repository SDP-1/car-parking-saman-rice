package module.park;

import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusPark extends VehicalPark implements Park {

    public BusPark(String vehicleNumber, String parkTime) {
        this.vehicleNumber = vehicleNumber;
        this.parkTime = parkTime;
    }

    @Override
    public String  park() throws SQLException, ClassNotFoundException {

        ResultSet res  = CrudUtil.excecute("SELECT * FROM parkingspase WHERE parkSlot Like '14'");

        if(!(res.next())){
            CrudUtil.excecute("INSERT INTO parkingspase VALUES ("+"'"+vehicleNumber+"'"+",'Bus','14',"+"'"+parkTime+"'"+")");
            return String.valueOf(14);
        } else {
            new Alert(Alert.AlertType.ERROR, "Bus Parkings Are Full...").show();
            return "--";
        }
    }
}
