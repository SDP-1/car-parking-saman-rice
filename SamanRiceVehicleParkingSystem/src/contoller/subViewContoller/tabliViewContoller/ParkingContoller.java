package contoller.subViewContoller.tabliViewContoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import module.Park;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingContoller {
    public TableView tblParking;
    public TableColumn clmVehicleNumber;
    public TableColumn clmVehicleType;
    public TableColumn clmParkingSlot;
    public TableColumn clmParkedTime;


    public void initialize() throws SQLException, ClassNotFoundException {
        clmVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        clmVehicleType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        clmParkingSlot.setCellValueFactory(new PropertyValueFactory<>("parkSlot"));
        clmParkedTime.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));

        dataload();
        tblParking.setPlaceholder(new Label("No data !.."));
    }

    private void dataload() throws SQLException, ClassNotFoundException {
        ObservableList<Park> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.excecute("SELECT * from parkingspase");

        while (resultSet.next()){
            observableList.add(new Park(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            ));
        }

        tblParking.setItems(observableList);
    }
}
