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

public class EditeDeleteDriverFormContoller implements Loder {
    public Button btnGetEdite;
    public TextField txtDriverName;
    public TextField txtNICNumber;
    public TextField txtDrivingLicenNo;
    public TextArea txtAddress;
    public TextField txtContactANo;
    public Button btnSearch;
    public Button btnDelete;
    public Label lblDrivernameError;
    public Label lblNicError;
    public Label lblLicenNoError;
    public Label lblContactNoError;

    Set<String> names = new HashSet<>();
    Pattern c1 = Pattern.compile("^[a-zA-Z\\s]*$");
    Pattern c2 = Pattern.compile("[0-9]*");
    Pattern c3 = Pattern.compile("[0-9]*[Vv]");
    Pattern c4 = Pattern.compile("[a-zA-Z][0-9]*");
    Pattern c5 = Pattern.compile("0[0-9]*");
    private AutoCompletionBinding<String> stringAutoCompletionBinding;

    public void initialize() throws SQLException, ClassNotFoundException {
        addDriverNames();
        disable();
        entedChacks();
    }

    private void addDriverNames() throws SQLException, ClassNotFoundException {
        names.clear();

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM driver");
        while (resultSet.next()){
            names.add(resultSet.getString(1));
        }

        stringAutoCompletionBinding = TextFields.bindAutoCompletion(txtDriverName, names);
    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) txtAddress.getScene().getWindow();
        stage.close();
    }

    public void getEditeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        chackandAdd();
    }

    public void drivernameEditeDitect(MouseEvent mouseEvent)  {
       clear();
       labelClear();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void enterKeyPressed(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode() == KeyCode.ENTER) search();
    }

    private void search() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM driver WHERE name=?",
                txtDriverName.getText()
                );
        if(resultSet.next()){
            txtNICNumber.setText(resultSet.getString(2));
            txtDrivingLicenNo.setText(resultSet.getString(3));
            txtAddress.setText(resultSet.getString(4));
            txtContactANo.setText(resultSet.getString(5));

            enable();
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "delete?..", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {

            try {
                ResultSet resultSet = CrudUtil.excecute("DELETE FROM driver WHERE name=?",
                        txtDriverName.getText()
                );
            }catch (ClassCastException e){}
            new Alert(Alert.AlertType.INFORMATION, "Delete Sucses Full.").showAndWait();
                    clear();
                    labelClear();
                    addDriverNames();

        }
    }

    private void disable() {
        txtNICNumber.setDisable(true);
        txtDrivingLicenNo.setDisable(true);
        txtAddress.setDisable(true);
        txtContactANo.setDisable(true);
        btnDelete.setDisable(true);
        btnGetEdite.setDisable(true);
    }

    private void enable() {
        txtNICNumber.setDisable(false);
        txtDrivingLicenNo.setDisable(false);
        txtAddress.setDisable(false);
        txtContactANo.setDisable(false);
        btnDelete.setDisable(false);
        btnGetEdite.setDisable(false);
    }

    private void clear() {
        txtDriverName.clear();
        txtNICNumber.clear();
        txtDrivingLicenNo.clear();
        txtAddress.clear();
        txtContactANo.clear();
        disable();
    }

    private void labelClear(){
        lblContactNoError.setGraphic(null);
        lblDrivernameError.setGraphic(null);
        lblNicError.setGraphic(null);
        lblLicenNoError.setGraphic(null);
    }

    private void chackandAdd() throws SQLException, ClassNotFoundException {
        if (!((c1.matcher(txtDriverName.getText()).matches() || txtDriverName.getText().equals("")) && noSpaseLength(txtDriverName) > 4)) {
            new Alert(Alert.AlertType.ERROR, "Driver name Invalide.").showAndWait();
        } else if (!((c2.matcher(txtNICNumber.getText()).matches() || c3.matcher(txtNICNumber.getText()).matches()) && noSpaseLength(txtNICNumber) == 11)) {
            new Alert(Alert.AlertType.ERROR, "Invalide NIC.").showAndWait();
        } else if (!(c4.matcher(txtDrivingLicenNo.getText()).matches() && noSpaseLength(txtDrivingLicenNo) == 8)) {
            new Alert(Alert.AlertType.ERROR, "Invalide Driving License No.").showAndWait();
        } else if (!(c5.matcher(txtContactANo.getText()).matches() && noSpaseLength(txtContactANo) == 10)) {
            new Alert(Alert.AlertType.ERROR, "Invalide Contact No.").showAndWait();
        } else {

            try {
                ResultSet resultSet = CrudUtil.excecute("UPDATE driver SET NIC=?,drivingLicenseNo=?,address=?,contact=? WHERE name=?",
                        txtNICNumber.getText(),
                        txtDrivingLicenNo.getText(),
                        txtAddress.getText(),
                        txtContactANo.getText(),
                        txtDriverName.getText()
                );
            }catch (ClassCastException e){}
            new Alert(Alert.AlertType.INFORMATION, "Save Sucses Full.").showAndWait();
            clear();
            labelClear();
        }

    }

    private void entedChacks() {
        txtDriverName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!((c1.matcher(txtDriverName.getText()).matches() || txtDriverName.getText().equals("")) && noSpaseLength(txtDriverName) > 4)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblDrivernameError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblDrivernameError.setGraphic(view);
                }
            }
        });


        txtNICNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!((c2.matcher(txtNICNumber.getText()).matches() || c3.matcher(txtNICNumber.getText()).matches()) && noSpaseLength(txtNICNumber) == 11)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblNicError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblNicError.setGraphic(view);
                }
            }
        });

        txtDrivingLicenNo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c4.matcher(txtDrivingLicenNo.getText()).matches() && noSpaseLength(txtDrivingLicenNo) == 8)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblLicenNoError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblLicenNoError.setGraphic(view);
                }
            }
        });

        txtContactANo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c5.matcher(txtContactANo.getText()).matches() && noSpaseLength(txtContactANo) == 10)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblContactNoError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblContactNoError.setGraphic(view);
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

    public void showAllDrivesOnAction(ActionEvent actionEvent) throws IOException {
        load("../view/subView/adishanal/Show All DriverForm.fxml");
    }
}
