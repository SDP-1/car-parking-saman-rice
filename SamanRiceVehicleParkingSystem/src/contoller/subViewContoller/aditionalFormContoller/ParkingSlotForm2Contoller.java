package contoller.subViewContoller.aditionalFormContoller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingSlotForm2Contoller {
    public Label lbl1;
    public Label lbl2;
    public Label lbl3;
    public Label lbl4;
    public Label lbl5;
    public Label lbl6;
    public Label lbl7;
    public Label lbl8;
    public Label lbl9;
    public Label lbl10;
    public Label lbl11;
    public Label lbl12;
    public Label lbl13;
    public Label lbl14;

    public void initialize() throws SQLException, ClassNotFoundException {

        setNomal();
        ResultSet resultSet = CrudUtil.excecute("SELECT * FROM parkingspase");
        while (resultSet.next()) {
                    switch (resultSet.getInt(3)) {
                        case 1:
                            lbl1.setStyle("-fx-background-color: Red");
                            break;
                        case 2:
                            lbl2.setStyle("-fx-background-color: Red");
                            break;
                        case 3:
                            lbl3.setStyle("-fx-background-color: Red");
                            break;
                        case 4:
                            lbl4.setStyle("-fx-background-color: Red");
                            break;
                        case 5:
                            lbl5.setStyle("-fx-background-color: Red");
                            break;
                        case 6:
                            lbl6.setStyle("-fx-background-color: Red");
                            break;
                        case 7:
                            lbl7.setStyle("-fx-background-color: Red");
                            break;
                        case 8:
                            lbl8.setStyle("-fx-background-color: Red");
                            break;
                        case 9:
                            lbl9.setStyle("-fx-background-color: Red");
                            break;
                        case 10:
                            lbl10.setStyle("-fx-background-color: Red");
                            break;
                        case 11:
                            lbl11.setStyle("-fx-background-color: Red");
                            break;
                        case 12:
                            lbl12.setStyle("-fx-background-color: Red");
                            break;
                        case 13:
                            lbl13.setStyle("-fx-background-color: Red");
                            break;
                        case 14:
                            lbl14.setStyle("-fx-background-color: Red");
                            break;
                    }
        }
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) lbl1.getScene().getWindow();
        stage.close();
    }

    private void setNomal(){
        lbl1.setStyle("-fx-background-color: LIGHTCYAN");
        lbl2.setStyle("-fx-background-color: LIGHTCYAN");
        lbl3.setStyle("-fx-background-color: LIGHTCYAN");
        lbl4.setStyle("-fx-background-color: LIGHTCYAN");
        lbl5.setStyle("-fx-background-color: \tLINEN");
        lbl6.setStyle("-fx-background-color: \tLINEN");
        lbl7.setStyle("-fx-background-color: \tLINEN");
        lbl8.setStyle("-fx-background-color: \tLINEN");
        lbl9.setStyle("-fx-background-color: \tLINEN");
        lbl10.setStyle("-fx-background-color: \tLINEN");
        lbl11.setStyle("-fx-background-color: \tLINEN");
        lbl12.setStyle("-fx-background-color: LIGHTCYAN");
        lbl13.setStyle("-fx-background-color: LIGHTCYAN");
        lbl14.setStyle("-fx-background-color: \tLIGHTGREEN");
    }
}
