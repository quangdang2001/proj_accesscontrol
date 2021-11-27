package com.accesscontroll.proj_cntt;

import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;
import com.accesscontroll.proj_cntt.utils.ObjectModelUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ShowView implements Initializable {

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


    private Stage stage;
    private Scene scene;
    private Parent parent;

    TreeItem<String> rootItem = new TreeItem<>("Files",new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folders.png")))));
    ObjectModel objectModelSelected;
    HashMap<User,String> userAccessSelected;
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

        treeView.setRoot(rootItem);
    }

    @FXML
    private void selectTreeItem(){
        clearBtn();
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item!=null) {
            listUser.getItems().clear();
            String value = item.getValue();
            int size = item.getChildren().size();
            if (size==0) {
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
            userAccessSelected = ACM.getACL().get(objectModelSelected);
            if (userAccessSelected!=null) {
                Set<User> userName = userAccessSelected.keySet();
                for (User tempUser : userName) {
                    listUser.getItems().add(tempUser.getUserName());
                }
            }

        }

    }

    @FXML
    private void selectListView(){
        String user;
        user= (String) listUser.getSelectionModel().getSelectedItem();
        for (User tempUser : userAccessSelected.keySet()){
            if(tempUser.getUserName().equals(user)){
                setBtn(userAccessSelected.get(tempUser));
                break;
            }
        }
    }

    public void clearBtn(){
        btnWrite.setSelected(false);
        btnRead.setSelected(false);
        btnExecute.setSelected(false);
    }
    public void setBtn(String permission){
        clearBtn();
        if (permission.contains("r")) btnRead.setSelected(true);
        if (permission.contains("w")) btnWrite.setSelected(true);
        if (permission.contains("x")) btnExecute.setSelected(true);
    }


    public void switchToModify(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource( "modify-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
