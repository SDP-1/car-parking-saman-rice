package contoller.subViewContoller;

import contoller.MainPageContoller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

public class AddDriverContoller implements Loder {

    public TextField txtNICNumber;
    public TextArea txtAddress;
    public TextField txtContactANo;
    public TextField txtDriverName;
    public TextField txtDrivingLicenNo;
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

    public void addDriverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        chack();
    }

    private void chack() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM driver");
        while (resultSet.next()){
            if (txtDriverName.getText().equals(resultSet.getString(1))) {
                new Alert(Alert.AlertType.ERROR, "Driver name allrady exsit...").showAndWait();
                return;
            }
        }

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
                ResultSet res = CrudUtil.excecute("INSERT INTO driver VALUES(?,?,?,?,?)",
                        txtDriverName.getText(),
                        txtNICNumber.getText(),
                        txtDrivingLicenNo.getText(),
                        txtAddress.getText(),
                        txtContactANo.getText()
                );
            }catch (ClassCastException e){}

            new Alert(Alert.AlertType.INFORMATION, "Save Sucses Full.").showAndWait();
            addDriverNames();
            clear();
            labelClear();
        }

    }

    public void closeClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) txtAddress.getScene().getWindow();
        stage.close();
    }

    private void entedChacks() {
        txtDriverName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c1.matcher(txtDriverName.getText()).matches() && noSpaseLength(txtDriverName) > 4)) {
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

    private void clear(){
        txtDriverName.clear();
        txtContactANo.clear();
        txtNICNumber.clear();
        txtAddress.clear();
        txtDrivingLicenNo.clear();
    }

    private void labelClear(){
        lblDrivernameError.setGraphic(null);
        lblContactNoError.setGraphic(null);
        lblNicError.setGraphic(null);
        lblLicenNoError.setGraphic(null);
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
