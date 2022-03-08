package module.park;

import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VanPark extends VehicalPark implements Park {

    public VanPark(String vehicleNumber, String parkTime) {
        this.vehicleNumber = vehicleNumber;
        this.parkTime = parkTime;
    }

    @Override
    public String park() throws SQLException, ClassNotFoundException {
        int[] van = {1, 2, 3, 4, 13, 12};
        for (int i = 0; i <van.length; i++) {
            ResultSet res = CrudUtil.excecute("SELECT * FROM parkingspase WHERE parkSlot LIKE " + "'" + van[i] + "'");
            if(!(res.next())){
                CrudUtil.excecute("INSERT INTO parkingspase VALUES ("+"'"+vehicleNumber+"'"+",'Van',"+"'"+van[i]+"'"+","+"'"+parkTime+"'"+")");
                return String.valueOf(van[i]);
            }
        }
        new Alert(Alert.AlertType.ERROR, "Van Parkings Are Full...").show();
        return "--";
    }
}
