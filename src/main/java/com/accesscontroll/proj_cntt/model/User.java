package com.accesscontroll.proj_cntt.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {


    private String userName;
    private String groupUser;

    public static ArrayList<User> listUser;

    public User(String userName, String groupUser) {
        this.userName = userName;

        this.groupUser = groupUser;
    }

    public User() {
    }

    public static void createUser(User user){
        listUser.add(user);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +

                ", groupUser='" + groupUser + '\'' +
                '}';
    }

    public ArrayList<User> getListUser() {
        return listUser;
    }
}
