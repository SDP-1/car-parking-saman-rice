package contoller.subViewContoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import contoller.MainPageContoller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.CrudUtil;
import util.Loder;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginPageContoller implements Loder {
    public Label lblHide;
    
    public AnchorPane loginPane;

    public static Stage mainPageStage;
    public TextField txtUserName;
    public PasswordField pwdPassword;


    public void usernameEnterPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            pwdPassword.requestFocus();
        }
    }

    public void enterKeyPressed(KeyEvent keyEvent) throws IOException, SQLException, ClassNotFoundException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) login();
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage) loginPane.getScene().getWindow();
        stage.close();
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        login();
    }

    public void closeClick(MouseEvent mouseEvent) {
        close();
    }

    private void close(){
        Stage stage =(Stage) loginPane.getScene().getWindow();
        stage.close();
    }

    public void hidePassword(MouseEvent mouseEvent) {
        Image img = new Image("Image/noHide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        pwdPassword.setText(pwdPassword.getPromptText());
        pwdPassword.setPromptText("");
        pwdPassword.setDisable(false);

        pwdPassword.requestFocus();
    }

    public void showPassword(MouseEvent mouseEvent) {
        Image img = new Image("Image/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        lblHide.setGraphic(view);

        pwdPassword.setPromptText(pwdPassword.getText());
        pwdPassword.setText("");
        pwdPassword.setDisable(true);
    }

    @Override
    public void load(String location) throws IOException {
        Stage newstage2 = (Stage) loginPane.getScene().getWindow();
        newstage2.close();
        mainPageStage.close();


        Stage stage = new Stage();
        URL resource = MainPageContoller.class.getResource(location);
        Parent load = FXMLLoader.load(resource);
        stage.setScene(new Scene(load));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();

    }

//--------private methodes-------

    int attemps = 5;
    private void login() throws IOException, SQLException, ClassNotFoundException {
        attemps--;
        if(attemps >=0) {
            ResultSet resultSet = CrudUtil.excecute("SELECT * FROM user WHERE rowNumber=1");
            if(resultSet.next()) {
                if (txtUserName.getText().equals(resultSet.getString(2)) && pwdPassword.getText().equals(resultSet.getString(3))) {
                    load("../view/subView/ManageForm.fxml");
                    return;
                }
            }
            if(attemps > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong username or password.\nCan be tried "+ attemps+" times.");
                alert.setTitle("Somthing Worng");
                alert.show();
                return;
            }else if(attemps==0){
                Alert alert = new Alert(Alert.AlertType.WARNING, "try other time");
                alert.setTitle("Faill login");
                Optional<ButtonType> buttonType = alert.showAndWait();
                alert.show();
                alert.close();
                close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "try other time");
            alert.setTitle("Faill login");
            Optional<ButtonType> buttonType = alert.showAndWait();
            alert.show();
            alert.close();
            close();
        }
    }


}
