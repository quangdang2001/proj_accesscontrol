package com.accesscontroll.proj_cntt.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class GroupUser implements Serializable {
    private String groupName;
    private List<User> users = new ArrayList<>();

    public static HashSet<GroupUser> groupUsers= new HashSet<>();

    public GroupUser(String groupName, List<User> users) {
        this.groupName = groupName;
        this.users = users;
    }

    public GroupUser(String groupName) {
        this.groupName = groupName;
    }

    public GroupUser() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static HashSet<GroupUser> getGroupUsers() {
        return groupUsers;
    }
}
