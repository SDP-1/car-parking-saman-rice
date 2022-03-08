package module.park;

import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LorriePark extends VehicalPark implements Park {

    public LorriePark(String vehicleNumber, String parkTime) {
        this.vehicleNumber = vehicleNumber;
        this.parkTime = parkTime;
    }

    @Override
    public String park() throws SQLException, ClassNotFoundException {

//        ResultSet res  = CrudUtil.excecute("SELECT * FROM parkingspase " +
//                "WHERE parkSlot Like '5' " +
//                "&& parkSlot Like '6' " +
//                "&& parkSlot Like '7' " +
//                "&& parkSlot Like '8' " +
//                "&& parkSlot Like '9' " +
//                "&& parkSlot Like '10' " +
//                "&& parkSlot Like '11' ");

//        if(!res.next()){

            int[] lory = {5,6,7,8,9,10,11};
            for (int i = 0; i <lory.length; i++) {
                ResultSet res = CrudUtil.excecute("SELECT * FROM parkingspase WHERE parkSlot LIKE " + "'" + lory[i] + "'");
                if(!(res.next())){
                    CrudUtil.excecute("INSERT INTO parkingspase VALUES ("+"'"+vehicleNumber+"'"+",'Cargo Lorry',"+"'"+lory[i]+"'"+","+"'"+parkTime+"'"+")");
                    return String.valueOf(lory[i]);
                }
            }
//        }
      //  int[] lory = {5,6,7,8,9,10,11};
//        for (int number : lory) {
//            if (ParkingSpase.parkingSpace[number - 1] == null) {
//                ParkingSpase.parkingSpace[number - 1] = new module.Park(vehicleNumber, "Cargo Lorry",number, parkTime);
//                return String.valueOf(number);
//            }
//        }
        new Alert(Alert.AlertType.ERROR, "Cargo Lorry Parkings Are Full...").show();
        return "--";
    }
}
