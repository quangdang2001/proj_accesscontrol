package com.accesscontroll.proj_cntt;

import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.utils.ObjectModelUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create root Path
        ObjectModel folder1 = new ObjectModel();
        folder1.setObjectName("Folder&File");
        folder1.setLocation("D:\\");
        folder1.setType("folder");
        if (ObjectModelUtil.createObject(folder1)){
            ACM.addObject2ACL(folder1);
            Data data1 = new Data();
            data1.saveData();
            data1.write(data1);
        }


        Parent root = FXMLLoader.load(getClass().getResource("show-view.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Access Control");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}