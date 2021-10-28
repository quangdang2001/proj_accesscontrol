package com.accesscontroll.proj_cntt.utils;


import com.accesscontroll.proj_cntt.model.User;

import java.util.ArrayList;

public class UserUtil {

    public static void addUser(User user){
        if (User.listUser==null) User.listUser=new ArrayList<>();
        User.listUser.add(user);
        if (user.getGroupUser()!= null) {
            GroupUserUtil.addUserGroup(user,user.getGroupUser());
        }
    }
    public static void removeUser(String userName) {
        User user = findUserByName(userName);
        if (user!=null) {
            User.listUser.remove(user);
            if (user.getGroupUser() != null) {
                GroupUserUtil.removeUserGroup(user.getGroupUser(), user);
            }
        }
    }

    public static User findUserByName(String Name){
        for (User tempUser : User.listUser){
            if (tempUser.getUserName().equals(Name)){
                return tempUser;
            }
        }
        return null;
    }
}
