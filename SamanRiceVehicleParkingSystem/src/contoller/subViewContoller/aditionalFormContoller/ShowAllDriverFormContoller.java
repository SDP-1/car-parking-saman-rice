package contoller.subViewContoller.aditionalFormContoller;

import db.TM.ShowAllDriverTM;
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

public class ShowAllDriverFormContoller {
    public TableColumn clmAddress;
    public TableView tblDrivers;
    public TableColumn clmNo;
    public TableColumn clmDriverName;
    public TableColumn clmNic;
    public TableColumn clmDrivingLicenNo;
    public TableColumn clmContactNo;
    public TextField txtSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        clmNo.setCellValueFactory(new PropertyValueFactory<>("indexNo"));
        clmDriverName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmNic.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        clmDrivingLicenNo.setCellValueFactory(new PropertyValueFactory<>("drivingLicenseNo"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));

        fill();
    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) txtSearch.getScene().getWindow();
        stage.close();
    }

    public void keypress(KeyEvent keyEvent) {
      txtSearch.requestFocus();
    }

    private void fill() throws SQLException, ClassNotFoundException {
        ObservableList<ShowAllDriverTM> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM driver");
        int no =1;

        while (resultSet.next()){
            observableList.add(new ShowAllDriverTM(
                    no,
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
            no++;
        }

        //----------tbl search codes----!!!----------------

        FilteredList<ShowAllDriverTM> filteredList = new FilteredList<>(observableList, b->true);
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(driver -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowecase =  newValue.toLowerCase();

                if(driver.name.toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(driver.NIC.toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(driver.drivingLicenseNo.toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(driver.address.toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }else if(driver.contact.toLowerCase().indexOf(lowecase) !=-1){
                    return true;
                }
                return false;
            });

            int no2=1;
            for (int i = 0; i <filteredList.size(); i++) {
                filteredList.get(i).setIndexNo(no2++);
            }
        }));


        SortedList<ShowAllDriverTM> sortedlist = new SortedList<>(filteredList);
        sortedlist.comparatorProperty().bind(tblDrivers.comparatorProperty());
        tblDrivers.setItems(sortedlist);

        //-------------------tbl search code END!----------------
    }
}
