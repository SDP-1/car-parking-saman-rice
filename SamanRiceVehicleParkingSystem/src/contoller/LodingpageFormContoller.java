package contoller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Loder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LodingpageFormContoller implements Loder {
    public static Class aClass;
    public JFXProgressBar prgbarLoding;
    public Label lblLodingPresentage;

    public void initialize() throws IOException {
        prgbarLoding.setStyle("-fx-accent: red;");
        prosess();
    }

    ArrayList<File> listofFile =new ArrayList<>();
    private void prosess() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                File file1 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\contoller");
                File file2 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\db");
                File file3 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\Image");
                File file4 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\lib");
                File file5 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\module");
                File file6 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\util");
                File file7 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src\\view");
                File file8 = new File("D:\\GDSE60\\intellij IDEA\\Course Work (OOP)\\SamanRiceVehicleParkingSystem\\src");

                addToArrayList(file1.listFiles());
                addToArrayList(file2.listFiles());
                addToArrayList(file3.listFiles());
                addToArrayList(file4.listFiles());
                addToArrayList(file5.listFiles());
                addToArrayList(file6.listFiles());
                addToArrayList(file7.listFiles());
                addToArrayList(file8.listFiles());
            //    File[] listOfFile = file.listFiles();

                for (int i = 0; i <= listofFile.size(); i++) {
                    updateProgress(i, listofFile.size());
                  //  updateMessage(listOfFile[i].size());
                    Thread.sleep(100);
                }
                return null;
            }
        };

//       task.messageProperty().addListener(new ChangeListener<String>() {
//           @Override
//           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//              txt
//
//           }
//       });


        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue){
                int presentage =(int)Math.round((Double) newValue*100);
                lblLodingPresentage.setText(String.valueOf(presentage));
                if(presentage ==100)loadUi();
            }
        });

        prgbarLoding.progressProperty().unbind();
        prgbarLoding.progressProperty().bind(task.progressProperty());



        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void addToArrayList(File[] ar){
        for (int i = 0; i < ar.length; i++) {
            listofFile.add(ar[i]);
        }
    }

    private void loadUi() {
        try {
            load("view/MainPage.fxml");
            Stage stage = (Stage) lblLodingPresentage.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void load(String location) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(aClass.getResource(location)));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();

    }
}
