import contoller.LodingpageFormContoller;
import contoller.subViewContoller.LoginPageContoller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class MainPageInitiyalizer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        LodingpageFormContoller.aClass =getClass();
        URL resource = getClass().getResource("view/LodingpageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }
}
