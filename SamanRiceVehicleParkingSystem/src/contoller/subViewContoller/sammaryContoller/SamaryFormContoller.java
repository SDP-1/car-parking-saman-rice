package contoller.subViewContoller.sammaryContoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import module.summary.ParkingAndOnDeliverySummary;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SamaryFormContoller {
    public TableColumn clmSlot;
    public TableColumn clmVehicleNo;
    public TableColumn clmType;
    public TableColumn clmParkedDriverName;
    public TableColumn clmParkedTime;
    public TableColumn clmDiliveryDrivrName;
    public TableColumn clmDeliveryTime;
    public TextField txtSearch;
    public Button btnSearch;
    public TableView tblSummary;

    public void initialize() throws SQLException, ClassNotFoundException {
        //clmParkedTime.setSortType(TableColumn.SortType.DESCENDING);

        clmVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("vehicalType"));
        clmSlot.setCellValueFactory(new PropertyValueFactory<>("parkingSlot"));
        clmParkedDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        clmParkedTime.setCellValueFactory(new PropertyValueFactory<>("parkTime"));

        clmDiliveryDrivrName.setCellValueFactory(new PropertyValueFactory<>("driverName2"));
        clmDeliveryTime.setCellValueFactory(new PropertyValueFactory<>("delivaryTime"));

        load();
        tblSummary.setPlaceholder(new Label("Not found data !.."));
    }



    public void closeClick(MouseEvent mouseEvent) {
        Stage stage =(Stage) txtSearch.getScene().getWindow();
        stage.close();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
    private void load() throws SQLException, ClassNotFoundException {
        ObservableList<ParkingAndOnDeliverySummary> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.excecute("SELECT * from parkingandondeliverysummary");

        while (resultSet.next()){
            observableList.add(new ParkingAndOnDeliverySummary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }

        tblSummary.setItems(observableList);

        //----------tbl search codes----!!!----------------

        FilteredList<ParkingAndOnDeliverySummary> filteredList = new FilteredList<>(observableList,b->true);
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(parkingAndOnDeliverySummary -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowecase =  newValue.toLowerCase();

                if(parkingAndOnDeliverySummary.getVehicleNumber().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(parkingAndOnDeliverySummary.getVehicalType().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(parkingAndOnDeliverySummary.getParkingSlot().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(parkingAndOnDeliverySummary.getDriverName().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(parkingAndOnDeliverySummary.getParkTime().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }
                 if(parkingAndOnDeliverySummary.getDriverName2() != null){
                     if(parkingAndOnDeliverySummary.getDriverName2().toLowerCase().indexOf(lowecase) !=-1) {
                         return true;
                     }else if(parkingAndOnDeliverySummary.getDelivaryTime().toLowerCase().indexOf(lowecase) !=-1){
                         return true;
                     }
                }

                 return false;
            });
        }));

        SortedList<ParkingAndOnDeliverySummary> sortedlist = new SortedList<>(filteredList);
        sortedlist.comparatorProperty().bind(tblSummary.comparatorProperty());
        tblSummary.setItems(sortedlist);

        //-------------------tbl search code END!----------------
    }

    public void keypress(KeyEvent keyEvent) {
        txtSearch.requestFocus();
    }
}
