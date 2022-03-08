package contoller.subViewContoller;

import contoller.MainPageContoller;
import contoller.subViewContoller.aditionalFormContoller.EditeUserNamePasswordFormCotoller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Loder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageFormContoller implements Loder {
    public ComboBox cmbxChoos;
    public AnchorPane tblTable;
    private static Stage stage;

    public void initialize() throws IOException {
        cmbxChoos.getItems().setAll("In Parking","On Delivery");
        cmbxChoos.getSelectionModel().select(0);
        loadTable();

        cmbxChoos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) {
                try {
                    loadTable();
                } catch (IOException e) {}
            }
        });
    }


    public void addVehicleOnAction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/AddVehicle.fxml");
    }

    public void addDriverOnAction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/AddDriver.fxml");
    }
    public void showparkingOnaction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/adishanal/ParkingSlotForm2.fxml");
    }
    public void editeDeleteVehicleOnAction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/adishanal/EditeDeleteVehicleForm.fxml");
    }

    public void editeDeletDriverOnAction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/adishanal/EditeDeleteDriverForm.fxml");
    }

    public void changUserOnAction(MouseEvent mouseEvent) throws IOException {
        EditeUserNamePasswordFormCotoller.managePageStage = (Stage) cmbxChoos.getScene().getWindow();
        pageLoad("../view/subView/adishanal/EditeUserNamePasswordForm.fxml");
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Log Out?...", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {

        Stage newstage = (Stage) cmbxChoos.getScene().getWindow();
        newstage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(MainPageContoller.class.getResource("../view/MainPage.fxml"))));
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

            closeLarstStage();
    }
    }
    public void SummaryOnAction(ActionEvent actionEvent) throws IOException {
        pageLoad("../view/subView/summary/SamaryForm.fxml");
    }

    //-------private methodes------

    private void closeLarstStage() {
        try {
            stage.close();
        }catch (NullPointerException e){}
    }

    private void loadTable() throws IOException {
        if(cmbxChoos.getSelectionModel().getSelectedItem().toString().equals("In Parking")){
            load("../view/subView/tableview/Parking.fxml");
        }else{
            load("../view/subView/tableview/Delevary.fxml");
        }
    }


    @Override
    public void load(String location) throws IOException {
        closeLarstStage();
        tblTable.getChildren().clear();
        Parent load = FXMLLoader.load(MainPageContoller.class.getResource(location));
        tblTable.getChildren().setAll(load);
    }

    private void pageLoad(String location) throws IOException {
        closeLarstStage();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(MainPageContoller.class.getResource(location))));
        stage.initStyle(StageStyle.UNDECORATED);
        ManageFormContoller.stage = stage;
        stage.centerOnScreen();
        stage.show();
    }



}
