package contoller.subViewContoller;

import contoller.MainPageContoller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.Set;
import java.util.regex.Pattern;

public class AddVehicleContoller implements Loder {
    public TextField txtVehicleNumber;
    public TextField txtMaxWeight;
    public TextField txtNoOfPassengerss;
    public ComboBox cmbxVehicleType;
    public Label lblVehicaleNameError;
    public Label lblWeightError;
    public Label lblnoFoPassengersError;

    Set<String> numbers = new HashSet<>();
    Pattern c1 = Pattern.compile("..*-[0-9]*");
    Pattern c2 = Pattern.compile("[0-9]*");
    Pattern c3 = Pattern.compile("[0-9]*");
    private AutoCompletionBinding<String> stringAutoCompletionBinding;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbxVehicleType.getItems().setAll("Cargo Lorry", "Van", "Bus");
        cmbxVehicleType.getSelectionModel().select(0);
        addVehicleNumbers();
        entedChacks();
    }

    private void addVehicleNumbers() throws SQLException, ClassNotFoundException {
        numbers.clear();
        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle");
        while (resultSet.next()){
            numbers.add(resultSet.getString(1));
        }

        stringAutoCompletionBinding = TextFields.bindAutoCompletion(txtVehicleNumber, numbers);
    }

    public void addVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        chack();
    }

    private void chack() throws SQLException, ClassNotFoundException {


        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle");
        while (resultSet.next()){
            numbers.add(resultSet.getString(1));
            if (resultSet.getString(1).equals(txtVehicleNumber.getText())) {
                new Alert(Alert.AlertType.ERROR, "Vehicle number allrady exsit...").showAndWait();
                return;
            }
        }


        if (!c1.matcher(txtVehicleNumber.getText()).matches() || txtVehicleNumber.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Vehicle Number is Invalide.").showAndWait();
        } else if (!(c2.matcher(txtMaxWeight.getText()).matches()) || txtMaxWeight.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Maximum Weight is Invalide.").showAndWait();
        } else if (!(Integer.parseInt(txtMaxWeight.getText()) <= 10000 && Integer.parseInt(txtMaxWeight.getText()) >= 250)) {
            new Alert(Alert.AlertType.ERROR, "Maximum Weight not posible.").showAndWait();
        } else if (!(c3.matcher(txtNoOfPassengerss.getText()).matches()) || txtNoOfPassengerss.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "No Of Passengerss is Invalide.").showAndWait();
        } else if (!(Integer.parseInt(txtNoOfPassengerss.getText()) <= 60 && Integer.parseInt(txtNoOfPassengerss.getText()) >= 0)) {
            new Alert(Alert.AlertType.ERROR, "No Of Passengerss not posible.").showAndWait();
        } else {
            try {
                ResultSet res = CrudUtil.excecute("INSERT INTO deliveryvehicle VALUES(?,?,?,?)",
                        txtVehicleNumber.getText(),
                        cmbxVehicleType.getSelectionModel().getSelectedItem().toString(),
                        Integer.parseInt(txtMaxWeight.getText()),
                        Integer.parseInt(txtNoOfPassengerss.getText())
                );
            }catch (ClassCastException e){}

//            VehicaleDB.vehicles.add(new DeliveryVehicle(
//                    txtVehicleNumber.getText(),
//                    cmbxVehicleType.getSelectionModel().getSelectedItem().toString(),
//                    Integer.parseInt(txtMaxWeight.getText()),
//                    Integer.parseInt(txtNoOfPassengerss.getText())
//            ));

            new Alert(Alert.AlertType.INFORMATION, "Save Sucses Full.").showAndWait();
            addVehicleNumbers();
            clear();
            labelClear();
        }

    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) txtMaxWeight.getScene().getWindow();
        stage.close();
    }

    private void entedChacks() {
        txtVehicleNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c1.matcher(txtVehicleNumber.getText()).matches() && noSpaseLength(txtVehicleNumber) >3)) {
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
                    if (!((c2.matcher(txtMaxWeight.getText()).matches())&& (Integer.parseInt(txtMaxWeight.getText()) <= 10000 && Integer.parseInt(txtMaxWeight.getText()) >= 250) && noSpaseLength(txtMaxWeight) >= 3)) {
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
                    }catch (NumberFormatException e){}
                }
            });



            txtNoOfPassengerss.textProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue != newValue) {
                    try {
                    if (!((c3.matcher(txtNoOfPassengerss.getText()).matches()) && (Integer.parseInt(txtNoOfPassengerss.getText()) <= 60 && Integer.parseInt(txtNoOfPassengerss.getText()) >= 0) && noSpaseLength(txtNoOfPassengerss) >= 1)) {
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
                    }catch (NumberFormatException e){}
                }
            });
    }

    private void clear(){
        txtVehicleNumber.clear();
        txtMaxWeight.clear();
        txtNoOfPassengerss.clear();
    }

    private void labelClear(){
        lblnoFoPassengersError.setGraphic(null);
        lblVehicaleNameError.setGraphic(null);
        lblWeightError.setGraphic(null);
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
}
