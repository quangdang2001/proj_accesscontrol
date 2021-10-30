package com.accesscontroll.proj_cntt.model;



import com.accesscontroll.proj_cntt.acm.ACM;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Data implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<User> listUser;
    private HashSet<GroupUser> groupUsers;
    private ArrayList<ObjectModel> listObject;
    private HashMap<ObjectModel, HashMap<User,String>> ACL;
    private HashMap<User,HashMap<ObjectModel,String>> CL;

    public void saveData(){
        listUser = User.listUser;
        groupUsers = GroupUser.groupUsers;
        listObject = ObjectModel.listObject;
        ACL = ACM.getACL();
        CL = ACM.getCL();
    }
    public void getAllData(){
        User.listUser = listUser;
        GroupUser.groupUsers = groupUsers;
        ObjectModel.listObject = listObject;
        ACM.setACL(ACL);
        ACM.setCL(CL);
    }
    public void write(Data data) {  //ghi theo Object
        try {   // dat try cacth de tranh ngoai le khi tao va ghi File
            FileOutputStream f = new FileOutputStream("data.txt");   // tao file f tro den data.dat
            ObjectOutputStream oStream = new ObjectOutputStream(f); // dung de ghi theo Object vao file f
            oStream.writeObject(data);   // ghi data theo kieu Object vao file
            oStream.close();
        } catch (IOException e) {
            System.out.println("Error Write file");
        }
    }

    public Data read() {       // doc theo Object
        Data data = null;
        try {   // dat try cacth de tranh ngoai le khi tao va doc File
            FileInputStream f = new FileInputStream("data.txt"); // tao file f tro den data.dat
            ObjectInputStream inStream = new ObjectInputStream(f);  // dung de doc theo Object vao file f
            // dung inStream doc theo Object, ep kieu tra ve la data
            data = (Data) inStream.readObject();
            inStream.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (IOException e) {
            System.out.println("Error Read file");
        }
        return data;
    }
    public ArrayList<User> getListUser() {
        return listUser;
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public HashSet<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(HashSet<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public ArrayList<ObjectModel> getListObject() {
        return listObject;
    }

    public void setListObject(ArrayList<ObjectModel> listObject) {
        this.listObject = listObject;
    }

    public HashMap<ObjectModel, HashMap<User, String>> getACL() {
        return ACL;
    }

    public void setACL(HashMap<ObjectModel, HashMap<User, String>> ACL) {
        this.ACL = ACL;
    }

    public HashMap<User, HashMap<ObjectModel, String>> getCL() {
        return CL;
    }

    public void setCL(HashMap<User, HashMap<ObjectModel, String>> CL) {
        this.CL = CL;
    }
}
