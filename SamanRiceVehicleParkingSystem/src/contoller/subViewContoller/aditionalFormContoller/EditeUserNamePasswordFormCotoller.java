package contoller.subViewContoller.aditionalFormContoller;

import contoller.MainPageContoller;
import contoller.subViewContoller.LoginPageContoller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.CrudUtil;
import util.Loder;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class EditeUserNamePasswordFormCotoller {

    public TextField txtNewUserName;
    public PasswordField pwdOldPassword;
    public PasswordField pwdNewPassword;
    public PasswordField pwdReTypePassword;
    public static Stage managePageStage;
    public static Stage MainPageStage;
    public Label lblUsernameError;
    public Label lblNewPwdError;
    public Label lblReTypePwdError;
    Pattern c1 =Pattern.compile("^[a-zA-Z\\s]*$");

    public void initialize(){
        txtNewUserName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(c1.matcher(txtNewUserName.getText()).matches() && noSpaseLength(txtNewUserName) > 4)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblUsernameError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblUsernameError.setGraphic(view);
                }
            }
        });

        pwdNewPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(noSpaseLengthpwd(pwdNewPassword) > 4)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblNewPwdError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblNewPwdError.setGraphic(view);
                }
            }
        });

        pwdReTypePassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (!(pwdNewPassword.getText().equals(pwdReTypePassword.getText()) && noSpaseLengthpwd(pwdReTypePassword) > 4)) {
                    Image img = new Image("Image/problam.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblReTypePwdError.setGraphic(view);
                } else {
                    Image img = new Image("Image/ok.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(20);
                    view.setFitWidth(20);
                    lblReTypePwdError.setGraphic(view);
                }
            }
        });
    }

    public void closeClick(MouseEvent mouseEvent) {
        close();
    }

    private void close(){
        Stage stage = (Stage) txtNewUserName.getScene().getWindow();
        stage.close();
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        save();
    }



    private void save() throws IOException, SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM user WHERE rowNumber =1");
        String oldPwd = null;
        if(resultSet.next()){
            oldPwd = resultSet.getString(3);
        }

        if(!(c1.matcher(txtNewUserName.getText()).matches() && txtNewUserName.getText().length()>4)){
            new Alert(Alert.AlertType.ERROR,"Plese enter up 4 character Username.").showAndWait();
            return;
        }else if(!(oldPwd.equals(pwdOldPassword.getText()))){
            new Alert(Alert.AlertType.ERROR,"Wrong old Password!...").showAndWait();
            return;
        }else if(!(pwdNewPassword.getText().length()>=4)){
            new Alert(Alert.AlertType.ERROR,"Plese enter least  4 character password.").showAndWait();
            return;
        }else if(!(pwdReTypePassword.getText().equals(pwdNewPassword.getText()))){
            new Alert(Alert.AlertType.ERROR,"Password not same.").showAndWait();
            return;
        }else{
            CrudUtil.excecute("UPDATE user SET userName=? , password=? WHERE rowNumber=1",
                    txtNewUserName.getText(),
                    pwdNewPassword.getText()
                    );

            new Alert(Alert.AlertType.INFORMATION,"Password change successfully").showAndWait();
            close();
            managePageStage.close();
            Stage stage = load("../view/MainPage.fxml");
            LoginPageContoller.mainPageStage = stage;
            load("../view/subView/LoginPage.fxml");
        }
    }

    public Stage load(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(MainPageContoller.class.getResource(location))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
        return stage;
    }

    public void userNmaeEnterPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) pwdOldPassword.requestFocus();
    }

    public void oldPasswordEnterKeyPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) pwdNewPassword.requestFocus();
    }

    public void newPasswordEnterKeyPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) pwdReTypePassword.requestFocus();
    }

    public void reTypePasswordEnterKeyPress(KeyEvent keyEvent) throws IOException, SQLException, ClassNotFoundException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) save();
    }

    private int noSpaseLength(TextField f) {
        int count = 0;
        for (int i = 0; i < f.getText().length(); i++) {
            if (f.getText().charAt(i) != ' ') count++;
        }
        return count;
    }
    private int noSpaseLengthpwd(PasswordField p) {
        int count = 0;
        for (int i = 0; i < p.getText().length(); i++) {
            if (p.getText().charAt(i) != ' ') count++;
        }
        return count;
    }

//    private void clear(){
//        txtNewUserName.clear();
//        pwdNewPassword.clear();
//        pwdOldPassword.clear();
//        pwdReTypePassword.clear();
//    }
}
