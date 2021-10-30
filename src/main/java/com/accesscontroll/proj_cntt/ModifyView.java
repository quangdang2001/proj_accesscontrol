package com.accesscontroll.proj_cntt;

import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyView implements Initializable {
    @FXML
    private TreeView treeView;
    @FXML
    private ListView listUser;
    @FXML
    private CheckBox btnRead;
    @FXML
    private CheckBox btnWrite;
    @FXML
    private CheckBox btnExecute;

    TreeItem<String> rootItem = new TreeItem<>("Files",
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folders.png")))));
    ObjectModel objectModelSelected;
    User userSelected;
    HashMap<User,String> userAccessSelected;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String rootPath="D:\\";
        Data data = new Data();
        data = data.read();
        data.getAllData();
        TreeItem<String> item = new TreeItem<>();
        ArrayList<String> objName= new ArrayList<>();
        for(ObjectModel obj : ObjectModel.getListObject()){
            if (obj.getLocation().equals(rootPath)){
                if (obj.getType().equals("folder")){
                    item = new TreeItem<>(obj.getLocation()+obj.getObjectName()
                            ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folders.png")))));
                    rootItem.getChildren().add(item);
                }else {
                    item = new TreeItem<>(obj.getLocation()+obj.getObjectName()
                            ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("files.png")))));
                    rootItem.getChildren().add(item);
                }

            }
        }

        ArrayList<String> containerList = new ArrayList<>();

        for (User user : User.listUser){
            listUser.getItems().add(user.getUserName());
        }

        treeView.setRoot(rootItem);
    }

    public void selectTreeItem(MouseEvent mouseEvent) {
        clearBtn();
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item!=null) {
            String value = item.getValue();
            int size = item.getChildren().size();
            if (size == 0) {
                for (ObjectModel obj : ObjectModel.getListObject()) {
                    if (obj.getLocation().equals(value+"\\")) {
                        if (obj.getType().equals("folder"))
                            item.getChildren().add(new TreeItem<String>(obj.getLocation() + obj.getObjectName()
                                    ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folders.png"))))));
                        else
                            item.getChildren().add(new TreeItem<String>(obj.getLocation() + obj.getObjectName()
                                    ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("files.png"))))));
                    }
                }
            }
            objectModelSelected = null;
            for (ObjectModel tempObj : ObjectModel.getListObject()){
                if (value.equals(tempObj.getLocation()+tempObj.getObjectName())){
                    objectModelSelected=tempObj;
                    break;
                }
            }
        }

    }

    public void selectListView(MouseEvent mouseEvent) {
        try {
            clearBtn();
            String user;
            user = (String) listUser.getSelectionModel().getSelectedItem();
            for (User tempUser : User.listUser) {
                if (tempUser.getUserName().equals(user)) {
                    userSelected = tempUser;
                    setBtn(ACM.getACL().get(objectModelSelected).get(userSelected));
                    break;
                }

            }
        } catch (Exception e){
            System.out.println("");
        }


    }

    public void switchToView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource( "show-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String getPermission(){
        String per="";
        if (btnRead.isSelected()) per+="r";
        if (btnWrite.isSelected()) per+="w";
        if (btnExecute.isSelected()) per+="x";
        return per;
    }

    public void Save(ActionEvent event) {
        try {
            ACM.setPermission(objectModelSelected,userSelected,getPermission());
            Data data = new Data();
            data.saveData();
            data.write(data);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save");
            alert.setHeaderText(null);
            alert.setContentText("Successfully!");
            alert.showAndWait();
            System.out.println("Done");
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save");
            alert.setHeaderText(null);
            alert.setContentText("Error!");
            alert.showAndWait();
        }

    }
    private void setBtn(String permission){
        if (permission==null) {
            clearBtn();
        }else {
            if (permission.contains("r")) btnRead.setSelected(true);
            if (permission.contains("w")) btnWrite.setSelected(true);
            if (permission.contains("x")) btnExecute.setSelected(true);
        }
    }
    private void clearBtn(){
        btnWrite.setSelected(false);
        btnRead.setSelected(false);
        btnExecute.setSelected(false);
    }
}
