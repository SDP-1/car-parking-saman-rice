package contoller.subViewContoller.tabliViewContoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import module.Delivery;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DelevaryContoller {
    public TableView tblParking;
    public TableColumn clmVehicleNumber;
    public TableColumn clmVehicleType;
    public TableColumn clmDriverName;
    public TableColumn clmParkedTime;

    public void initialize() throws SQLException, ClassNotFoundException {
        clmVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        clmVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        clmDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        clmParkedTime.setCellValueFactory(new PropertyValueFactory<>("leftTime"));

        dataload();
        tblParking.setPlaceholder(new Label("No data !.."));
    }

    private void dataload() throws SQLException, ClassNotFoundException {
        ObservableList<Delivery> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.excecute("SELECT * from delivery");

        while (resultSet.next()){
            observableList.add(new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }

        tblParking.setItems(observableList);
    }
}
