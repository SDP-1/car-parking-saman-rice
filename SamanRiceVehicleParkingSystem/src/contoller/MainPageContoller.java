package contoller;

import com.jfoenix.controls.JFXButton;
import contoller.subViewContoller.LoginPageContoller;
import contoller.subViewContoller.aditionalFormContoller.EditeUserNamePasswordFormCotoller;
import contoller.subViewContoller.aditionalFormContoller.ParkingSlotFormContoller;
import db.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lib.ComboBoxAutoComplete;
import module.Delivery;
import module.DeliveryVehicle;
import module.Driver;
import module.park.BusPark;
import module.park.LorriePark;
import module.park.Park;
import module.park.VanPark;
import module.summary.ParkingAndOnDeliverySummary;
import util.CrudUtil;
import util.Loder;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;


public class MainPageContoller implements Loder {

    public static AnchorPane parkingSlotViewPanel;
    private static Stage logiPageStage;
    public Label lblSystemDate;
    public Label lblSystemTime;
    public ComboBox cmbxSelectVehicle;
    public ComboBox cmbxSelectDriver;
    public Label lblVehicleType;
    public JFXButton btnOnDillivery;
    public JFXButton btnParkVehicle;
    public Label lblParkingSlotNumber;
    public JFXButton btnClear;
    public JFXButton btnManagementLogin;
    public Label lblLastSlotNo;
    public Label lblcountDown;
    int timeSeconds = 10;

    public void initialize() throws SQLException, ClassNotFoundException {
        setSystemTime();
        setVahicle();
        setDrivesName();

        disable();

        cmbxSelectVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbxSelectVehicle.getSelectionModel().select(newValue);
        });
        cmbxSelectDriver.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbxSelectDriver.getSelectionModel().select(newValue);
        });

        new ComboBoxAutoComplete<>(cmbxSelectVehicle);
    }

    public void vehicleChangDetectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle WHERE number =?",
                    cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString()
            );
            if (resultSet.next()) {
                lblVehicleType.setText(resultSet.getString(2));
            }
        }catch (NullPointerException e){}
        enable();
    }

    public void selectDriverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        enable();
    }

    public void onDilliveryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd \t hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);

        CrudUtil.excecute("INSERT INTO delivery VALUES (?,?,?,?)",
                cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString(),
                lblVehicleType.getText(),
                cmbxSelectDriver.getSelectionModel().getSelectedItem().toString(),
                time
        );
        dilliverySummary(time);

        new Alert(Alert.AlertType.INFORMATION, "The addition to the registry is complete.\n" + "you're Welcome!").show();

        CrudUtil.excecute("DELETE FROM parkingspase WHERE vehicleNumber=?",
                cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString()
        );
        clear();
    }

    public void parkVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        timer();            //count down lable(lblCountdown)
        TenSecondClear();  //after 10s cler and update larst paking slot
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd \t hh:mm  a");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        disable();

        if (lblVehicleType.getText().equals("Bus")) {
            Park v1 = new BusPark(cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString(), time);
            String slot = v1.park();
            lblParkingSlotNumber.setText(slot);
            addSummary(slot, time);
            removeFromOnDelvery();
            return;
        }

        if (lblVehicleType.getText().equals("Van")) {
            Park v1 = new VanPark(cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString(), time);
            String slot = v1.park();
            lblParkingSlotNumber.setText(slot);
            addSummary(slot, time);
            removeFromOnDelvery();
            return;
        }

        if (lblVehicleType.getText().equals("Cargo Lorry")) {
            Park v1 = new LorriePark(cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString(), time);
            String slot = v1.park();
            lblParkingSlotNumber.setText(slot);
            addSummary(slot, time);
            removeFromOnDelvery();
            return;
        }
    }

    public void buttonClickDitect(MouseEvent mouseEvent) {
        disable();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        updateLastparkingLabel();
        clear();
    }

    public void managementLoginOnAction(ActionEvent actionEvent) throws IOException {
        LoginPageContoller.mainPageStage = (Stage) btnManagementLogin.getScene().getWindow();
        EditeUserNamePasswordFormCotoller.MainPageStage = LoginPageContoller.mainPageStage;
        try {
            logiPageStage.close();
        } catch (NullPointerException e) {
        }
        load("../view/subView/LoginPage.fxml");
    }

    @Override
    public void load(String location) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(location)));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        MainPageContoller.logiPageStage = stage;
        stage.show();
    }

    public void closeClick(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exsit ?...", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            Stage stage = (Stage) btnOnDillivery.getScene().getWindow();
            stage.close();
        }
    }

    public void selectVehicleEnterKeyPress(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (cmbxSelectVehicle.getSelectionModel().isEmpty()) {
                cmbxSelectVehicle.show();
            } else {
                cmbxSelectDriver.requestFocus();
                cmbxSelectDriver.show();
            }
        }

    }

    //-----private methodes-----

    public void driverEnterKeyPress(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            enable();
        }
        new ComboBoxAutoComplete<>(cmbxSelectDriver);
    }

    private void addSummary(String slot, String time) throws SQLException, ClassNotFoundException {
        CrudUtil.excecute("INSERT INTO parkingandondeliverysummary VALUES (?,?,?,?,?,?,?)",cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString(), lblVehicleType.getText(), slot, cmbxSelectDriver.getSelectionModel().getSelectedItem().toString(), time,"","");
    }

    private void dilliverySummary(String time) throws SQLException, ClassNotFoundException {

        CrudUtil.excecute("UPDATE parkingandondeliverysummary SET driverName2 = ? , delivaryTime =? WHERE vehicleNumber=? && driverName2 =''",
                cmbxSelectDriver.getSelectionModel().getSelectedItem().toString(),
                time,
                cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString()
        );

    }

    private void updateLastparkingLabel() {
        if (lblParkingSlotNumber.getText() != "") {
            lblLastSlotNo.setText(lblParkingSlotNumber.getText());
        }
    }

    private void clear() {
        cmbxSelectVehicle.getSelectionModel().clearSelection();
        cmbxSelectDriver.getSelectionModel().clearSelection();
        lblVehicleType.setText("");
        lblParkingSlotNumber.setText("");
        disable();
    }

    private void setSystemTime() {
        final DateFormat format = DateFormat.getInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler() {
            @Override
            public void handle(Event event) {
                final Calendar cal = Calendar.getInstance();
                lblSystemDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime()));
                lblSystemTime.setText(new SimpleDateFormat("hh:mm aa").format(Calendar.getInstance().getTime()));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setVahicle() throws SQLException, ClassNotFoundException {
        ObservableList<String> numbers = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM deliveryvehicle");
        while (resultSet.next()){
            numbers.add(resultSet.getString(1));
        }
        cmbxSelectVehicle.setItems(numbers);
    }

    private void setDrivesName() throws SQLException, ClassNotFoundException {
        ObservableList<String> names = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM driver");
        while (resultSet.next()){
            names.add(resultSet.getString(1));
        }
        cmbxSelectDriver.setItems(names);
    }

    private void enable() throws SQLException, ClassNotFoundException {
        if (!cmbxSelectDriver.getSelectionModel().isEmpty() && !cmbxSelectVehicle.getSelectionModel().isEmpty()) {

            ResultSet resultSet = CrudUtil.excecute("SELECT * FROM parkingspase WHERE vehicleNumber LIKE ?",
                    cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString()
                    );
            if(resultSet.next()){
                btnParkVehicle.setDisable(true);
                        btnOnDillivery.setDisable(false);
                        return;
            }

            btnOnDillivery.setDisable(true);
            btnParkVehicle.setDisable(false);
        }
    }

    private void disable() {
        btnParkVehicle.setDisable(true);
        btnOnDillivery.setDisable(true);
    }

    private void removeFromOnDelvery() throws SQLException, ClassNotFoundException {
         CrudUtil.excecute("DELETE FROM delivery WHERE vehicleNumber=?",
                 cmbxSelectVehicle.getSelectionModel().getSelectedItem().toString()
         );

    }

    public void inPress(MouseEvent mouseEvent) throws IOException {
        ParkingSlotFormContoller.number = lblParkingSlotNumber.getText();
        pakingpageLoad("../view/subView/adishanal/ParkingSlotForm.fxml");
    }

    public void outPress(MouseEvent mouseEvent) {
        Stage stage = (Stage) parkingSlotViewPanel.getScene().getWindow();
        stage.close();
    }

    public void pakingpageLoad(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(location))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    private void TenSecondClear() {
        Task<Void> task = task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {

                Thread.sleep(1000 * 10);

                return null;
            }
        };

        task.runningProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == false) {
                updateLastparkingLabel();
                clear();
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void timer() {
        cmbxSelectDriver.setDisable(true);
        cmbxSelectVehicle.setDisable(true);
        lblVehicleType.setDisable(true);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                // update timerLabel
                                lblcountDown.setText(String.valueOf(--timeSeconds));
                                if (timeSeconds <= 0) {
                                    timeline.stop();
                                    lblcountDown.setText("");
                                    timeSeconds = 10;
                                    cmbxSelectDriver.setDisable(false);
                                    cmbxSelectVehicle.setDisable(false);
                                    lblVehicleType.setDisable(false);
                                }
                            }
                        }));
        timeline.playFromStart();
    }
}