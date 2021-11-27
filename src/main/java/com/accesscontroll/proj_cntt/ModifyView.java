package com.accesscontroll.proj_cntt;

import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;
import com.accesscontroll.proj_cntt.utils.ObjectModelUtil;
import com.accesscontroll.proj_cntt.utils.UserUtil;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyView implements Initializable {
    @FXML
    public AnchorPane newScene;
    @FXML
    public TextField fileName;
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



    TreeItem<String> itemSelected;

    private String saveNew;
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

        if(User.listUser!=null){
            for (User user : User.listUser){
                listUser.getItems().add(user.getUserName());
            }
        }


        treeView.setRoot(rootItem);
    }

    public void selectTreeItem() {
        clearBtn();
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item!=null) {
            this.itemSelected=item;
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

    public void selectListView() {
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
            if (objectModelSelected.getType().equals("folder"))
            for(ObjectModel objectModel : ObjectModel.getListObject()){
                if (objectModel.getLocation().contains(objectModelSelected.getLocation()+objectModelSelected.getObjectName())){
                    ACM.setPermission(objectModel,userSelected,getPermission());
                }
            }

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

    public void newFolder(ActionEvent event) {
        if (objectModelSelected==null || objectModelSelected.getType().equals("file")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a folder");
            alert.showAndWait();
            return;
        }
        this.saveNew="folder";
        newScene.setVisible(true);
    }

    public void newFile(ActionEvent event) {

        if (objectModelSelected==null || objectModelSelected.getType().equals("file")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a folder");
            alert.showAndWait();
            return;
        }
        newScene.setVisible(true);
        this.saveNew="file";
    }

    public void saveNewFile(ActionEvent event) {
        String fileName = String.valueOf(this.fileName.getText());
        if (this.saveNew!=null && this.saveNew.equals("folder")){
            ObjectModel folder1 = new ObjectModel();
            folder1.setObjectName(fileName);
            folder1.setLocation(objectModelSelected.getLocation()+objectModelSelected.getObjectName()+"\\");
            folder1.setType("folder");
            boolean test = ObjectModelUtil.createObject(folder1);
            if (test){

                ACM.addObject2ACL(folder1);

                HashMap<User,String> userAccess;
                userAccess = ACM.getACL().get(objectModelSelected);
                for(User user : userAccess.keySet()){
                    ACM.getACL().get(folder1).put(user,userAccess.get(user));
                }

                itemSelected.getChildren().add(new TreeItem<String>(folder1.getLocation() + folder1.getObjectName()
                        ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("folders.png"))))));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Folder already exists");
                alert.showAndWait();
                return;
            }

        }else if (this.saveNew!=null && this.saveNew.equals("file")) {
            ObjectModel file121 = new ObjectModel();
            file121.setObjectName(fileName+".txt");
            file121.setLocation(objectModelSelected.getLocation()+objectModelSelected.getObjectName()+"\\");
            file121.setType("file");
            boolean test = ObjectModelUtil.createObject(file121);
            if (test){
                ACM.addObject2ACL(file121);

                HashMap<User,String> userAccess;
                userAccess = ACM.getACL().get(objectModelSelected);
                for(User user : userAccess.keySet()){
                    ACM.getACL().get(file121).put(user,userAccess.get(user));
                }

                itemSelected.getChildren().add(new TreeItem<String>(file121.getLocation() + file121.getObjectName()
                        ,new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("files.png"))))));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("File already exists");
                alert.showAndWait();
                return;
            }
        }else if (this.saveNew!=null && this.saveNew.equals("user")){
            User user1 = new User();
            user1.setUserName(fileName);
            UserUtil.addUser(user1);
            ACM.addUser2CL(user1);
            this.listUser.getItems().add(fileName);
        }

        saveData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save");
        alert.setHeaderText(null);
        alert.setContentText("Successfully!");
        alert.showAndWait();
    }

    public void cancelNewFile(ActionEvent event) {
        newScene.setVisible(false);
    }

    public void deleteFile(ActionEvent event) {
        if (objectModelSelected==null ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a file or folder");
            alert.showAndWait();
            return;
        }
        ObjectModelUtil.deleteObject(objectModelSelected);
        ObjectModelUtil.removeOject(objectModelSelected);
        ACM.removeObject(objectModelSelected);

        saveData();
        objectModelSelected=null;
        this.itemSelected.getParent().getChildren().removeAll(itemSelected);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Delete successfully!");
        alert.showAndWait();
    }

    public void newUser(ActionEvent event) {
        newScene.setVisible(true);
        this.saveNew="user";
    }

    public void deleteUser(ActionEvent event) {
        if (userSelected!=null){
            UserUtil.removeUser(userSelected.getUserName());
            listUser.getItems().remove(userSelected.getUserName());

            saveData();
            userSelected=null;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete");
            alert.setHeaderText(null);
            alert.setContentText("Delete successfully!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user");
            alert.showAndWait();
            return;
        }

    }

    private void saveData(){
        Data data1 = new Data();
        data1.saveData();
        data1.write(data1);
        this.fileName.clear();
    }
}
