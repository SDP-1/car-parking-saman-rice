package contoller.subViewContoller.aditionalFormContoller;

import db.TM.ShowAllVehicleTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowAllVehicleFormContoller {
    public TableColumn clmNo;
    public TableColumn clmVehicleNumber;
    public TableColumn clmVehicleType;
    public TableColumn clmMaxiumWeight;
    public TableColumn clmNoOfPasengers;
    public TextField txtSearch;
    public TableView tblVehicles;

    public void initialize() throws SQLException, ClassNotFoundException {
        clmNo.setCellValueFactory(new PropertyValueFactory<>("indexNo"));
        clmVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        clmVehicleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmMaxiumWeight.setCellValueFactory(new PropertyValueFactory<>("maximumWeight"));
        clmNoOfPasengers.setCellValueFactory(new PropertyValueFactory<>("numberOfPassengers"));

        fill();
    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage st = (Stage) txtSearch.getScene().getWindow();
        st.close();
    }

    public void keypress(KeyEvent keyEvent) {
        txtSearch.requestFocus();
    }

    private void fill() throws SQLException, ClassNotFoundException {
        ObservableList<ShowAllVehicleTM> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle");
        int no =1;

        while (resultSet.next()){
            observableList.add(new ShowAllVehicleTM(
                    no,
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4)
            ));
            no++;
        }

        //----------tbl search codes----!!!----------------

        FilteredList<ShowAllVehicleTM> filteredList = new FilteredList<>(observableList, b->true);
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(vehicle -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowecase =  newValue.toLowerCase();

                if(vehicle.getNumber().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(vehicle.getType().toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(String.valueOf(vehicle.getMaximumWeight()).toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(String.valueOf(vehicle.getNumberOfPassengers()).toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }
                return false;
            });

            int no2=1;
            for (int i = 0; i <filteredList.size(); i++) {
                filteredList.get(i).setIndexNo(no2++);
            }
        }));


        SortedList<ShowAllVehicleTM> sortedlist = new SortedList<>(filteredList);
        sortedlist.comparatorProperty().bind(tblVehicles.comparatorProperty());
        tblVehicles.setItems(sortedlist);

        //-------------------tbl search code END!----------------
    }
}
