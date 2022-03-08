package contoller.subViewContoller.aditionalFormContoller;

import contoller.MainPageContoller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CrudUtil;
import util.Loder;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public class EditeDeleteVehicleFormContoller implements Loder {
    public TextField txtVehicleNumber;
    public TextField txtMaxWeight;
    public TextField txtNoOfPassengerss;
    public ComboBox cmbxVehicleType;
    public Button btnGetEdite;
    public Button btnSearch;
    public Button btnDelete;
    public Label lblVehicaleNameError;
    public Label lblWeightError;
    public Label lblnoFoPassengersError;

    Set<String> numbers = new HashSet<>();
    Pattern c1 = Pattern.compile("..*-[0-9]*");
    Pattern c2 = Pattern.compile("[0-9]*");
    Pattern c3 = Pattern.compile("[0-9]*");
    private AutoCompletionBinding<String> stringAutoCompletionBinding;

    public void initialize() throws SQLException, ClassNotFoundException {
        disable();
        cmbxVehicleType.getItems().setAll("Cargo Lorry", "Van", "Bus");
        cmbxVehicleType.getSelectionModel().select(0);
        addVehicleNumbers();
        entedChacks();
    }

    private void addVehicleNumbers() throws SQLException, ClassNotFoundException {
        numbers.clear();

//        for (DeliveryVehicle d : VehicaleDB.vehicles) {
//            numbers.add(d.getNumber());
//        }

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle");
        while (resultSet.next()){
            numbers.add(resultSet.getString(1));
        }

        stringAutoCompletionBinding = TextFields.bindAutoCompletion(txtVehicleNumber, numbers);
    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) txtMaxWeight.getScene().getWindow();
        stage.close();
    }

    public void getEditeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        chackandAdd();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "delete?..", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {

            try {
                ResultSet resultSet = CrudUtil.excecute("DELETE FROM deliveryvehicle WHERE number=?",
                        txtVehicleNumber.getText()
                );
            }catch (ClassCastException e){}
            new Alert(Alert.AlertType.INFORMATION, "Delete Sucses Full.").showAndWait();
                    clear();
                    labelClear();
                    addVehicleNumbers();
        }
    }

    public void vehicleNumberChangDitect(MouseEvent mouseEvent) {
        clear();
        labelClear();
    }

    public void vihicleNumberEnterKeyPressed(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
//        for (DeliveryVehicle d : VehicaleDB.vehicles) {
//            if (d.getNumber().equals(txtVehicleNumber.getText())) {
//                txtMaxWeight.setText(String.valueOf(d.getMaximumWeight()));
//                txtNoOfPassengerss.setText(String.valueOf(d.getNumberOfPassengers()));
//                if (d.getType().equals("Cargo Lorry")) {
//                    cmbxVehicleType.getSelectionModel().select(0);
//                } else if (d.getType().equals("Van")) {
//                    cmbxVehicleType.getSelectionModel().select(1);
//                } else {
//                    cmbxVehicleType.getSelectionModel().select(2);
//                }
//                enable();
//            }
//        }

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle WHERE number=?",
                txtVehicleNumber.getText()
        );
        if(resultSet.next()){
            txtMaxWeight.setText(resultSet.getString(3));
            txtNoOfPassengerss.setText(resultSet.getString(4));
            if (resultSet.getString(2).equals("Cargo Lorry")) {
                    cmbxVehicleType.getSelectionModel().select(0);
                } else if (resultSet.getString(2).equals("Van")) {
                    cmbxVehicleType.getSelectionModel().select(1);
                } else {
                    cmbxVehicleType.getSelectionModel().select(2);
                }

            enable();
        }

    }

    private void labelClear(){
        lblVehicaleNameError.setGraphic(null);
        lblnoFoPassengersError.setGraphic(null);
        lblWeightError.setGraphic(null);
    }

    private void clear() {
        txtVehicleNumber.clear();
        txtMaxWeight.clear();
        cmbxVehicleType.getSelectionModel().select(0);
        txtNoOfPassengerss.clear();
        disable();
    }

    private void disable() {
        txtMaxWeight.setDisable(true);
        cmbxVehicleType.setDisable(true);
        txtNoOfPassengerss.setDisable(true);
        btnDelete.setDisable(true);
        btnGetEdite.setDisable(true);
    }

    private void enable() {
        txtMaxWeight.setDisable(false);
        cmbxVehicleType.setDisable(false);
        txtNoOfPassengerss.setDisable(false);
        btnDelete.setDisable(false);
        btnGetEdite.setDisable(false);
    }

    private void chackandAdd() throws SQLException, ClassNotFoundException {


        if (!c1.matcher(txtVehicleNumber.getText()).matches() || txtVehicleNumber.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Vehicle Number is Invalide.").showAndWait();
        } else if (!(c2.matcher(txtMaxWeight.getText()).matches()) || txtMaxWeight.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Maximum Weight is Invalide.").showAndWait();
        } else if (!(Integer.parseInt(txtMaxWeight.getText()) <= 10000 && Integer.parseInt(txtMaxWeight.getText()) >= 0)) {
            new Alert(Alert.AlertType.ERROR, "Maximum Weight not posible.").showAndWait();
        } else if (!(c3.matcher(txtNoOfPassengerss.getText()).matches()) || txtNoOfPassengerss.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "No Of Passengerss is Invalide.").showAndWait();
        } else if (!(Integer.parseInt(txtNoOfPassengerss.getText()) <= 60 && Integer.parseInt(txtNoOfPassengerss.getText()) >= 0)) {
            new Alert(Alert.AlertType.ERROR, "No Of Passengerss not posible.").showAndWait();
        } else {
//            for (int i = 0; i < VehicaleDB.vehicles.size(); i++) {
//                if (VehicaleDB.vehicles.get(i).getNumber().equals(txtVehicleNumber.getText())) {
//                    VehicaleDB.vehicles.set(i, new DeliveryVehicle(
//                            txtVehicleNumber.getText(),
//                            cmbxVehicleType.getSelectionModel().getSelectedItem().toString(),
//                            Integer.parseInt(txtMaxWeight.getText()),
//                            Integer.parseInt(txtNoOfPassengerss.getText())
//                    ));
//
//                    new Alert(Alert.AlertType.INFORMATION, "Save Sucses Full.").showAndWait();
//                    clear();
//                    labelClear();
//                }
//            }

            try {
                ResultSet resultSet = CrudUtil.excecute("UPDATE deliveryvehicle SET type=?,maximumWeight=?,numberOfPassengers=? WHERE number=?",
                            cmbxVehicleType.getSelectionModel().getSelectedItem().toString(),
                            Integer.parseInt(txtMaxWeight.getText()),
                            Integer.parseInt(txtNoOfPassengerss.getText()),
                        txtVehicleNumber.getText()
                );
            }catch (ClassCastException e){}
            new Alert(Alert.AlertType.INFORMATION, "Save Sucses Full.").showAndWait();
            clear();
            labelClear();

        }
    }

    private void entedChacks() {
        txtVehicleNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c1.matcher(txtVehicleNumber.getText()).matches() && noSpaseLength(txtVehicleNumber) > 3)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblVehicaleNameError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblVehicaleNameError.setGraphic(view);
                }
            }
        });


        txtMaxWeight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                try {
                    if (!(c2.matcher(txtMaxWeight.getText()).matches() && (Integer.parseInt(txtMaxWeight.getText()) <= 10000 && Integer.parseInt(txtMaxWeight.getText()) >= 250) && noSpaseLength(txtMaxWeight) >= 3)) {
                        Image img = new Image("Image/problam.png");
                        ImageView view = new ImageView(img);
                        view.setFitHeight(20);
                        view.setFitWidth(20);
                        lblWeightError.setGraphic(view);
                    } else {
                        Image img = new Image("Image/ok.png");
                        ImageView view = new ImageView(img);
                        view.setFitHeight(20);
                        view.setFitWidth(20);
                        lblWeightError.setGraphic(view);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });


        txtNoOfPassengerss.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                try {
                    if (!((c3.matcher(txtNoOfPassengerss.getText()).matches()) && (Integer.parseInt(txtNoOfPassengerss.getText()) <= 60 && Integer.parseInt(txtMaxWeight.getText()) >= 0) && noSpaseLength(txtNoOfPassengerss) >= 1)) {
                        Image img = new Image("Image/problam.png");
                        ImageView view = new ImageView(img);
                        view.setFitHeight(20);
                        view.setFitWidth(20);
                        lblnoFoPassengersError.setGraphic(view);
                    } else {
                        Image img = new Image("Image/ok.png");
                        ImageView view = new ImageView(img);
                        view.setFitHeight(20);
                        view.setFitWidth(20);
                        lblnoFoPassengersError.setGraphic(view);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
    }

    private int noSpaseLength(TextField f) {
        int count = 0;
        for (int i = 0; i < f.getText().length(); i++) {
            if (f.getText().charAt(i) != ' ') count++;
        }
        return count;
    }


    @Override
    public void load(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(MainPageContoller.class.getResource(location))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    public void showAllWehiclesOnAction(ActionEvent actionEvent) throws IOException {
        load("../view/subView/adishanal/ShowAllVehicleForm.fxml");
    }
}
