package com.accesscontroll.proj_cntt.utils;


import com.accesscontroll.proj_cntt.model.GroupUser;
import com.accesscontroll.proj_cntt.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupUserUtil {

    // Kiem tra Group da ton tai hay chua
    private static boolean checkExitsGroup(String groupName) {

        for (GroupUser tempGrName : GroupUser.getGroupUsers()) {
            if (tempGrName.getGroupName().equals(groupName)) return true;
        }
        return false;
    }

    private static GroupUser findGroupByName(String groupName) {
        for (GroupUser temp : GroupUser.getGroupUsers()) {
            if (temp.getGroupName().equals(groupName)) {
                return temp;
            }
        }
        return null;
    }

    // Kiem tra User da ton tai trong group
    private static boolean checkExitsUserInGroup(String groupName, User user) {
        GroupUser groupUser = findGroupByName(groupName);
        if (groupUser.getUsers() != null) {
            for (User tempUser : groupUser.getUsers()) {
                if (user == tempUser) return true;
            }
        }
        return false;
    }

    public static void addGroup(String groupName) {
        if (!GroupUserUtil.checkExitsGroup(groupName)) {
            GroupUser.groupUsers.add(new GroupUser(groupName));
        }
        else System.out.println("The group already exists");
    }

    public static void addUserGroup(User user, String groupName) {
        if (!checkExitsGroup(groupName)) {
            GroupUserUtil.addGroup(groupName);
            findGroupByName(groupName).getUsers().add(user);
        }
        else if (!checkExitsUserInGroup(groupName, user)) {
            GroupUser groupUser = findGroupByName(groupName);
            List<User> listUser = groupUser.getUsers();
            if (listUser == null) listUser = new ArrayList<>();
            listUser.add(user);
            groupUser.setUsers(listUser);
        } else System.out.println("The user already exists");
    }

    public static void removeGroup(String groupName) {
        if (checkExitsGroup(groupName)) {
            GroupUser.groupUsers.remove(findGroupByName(groupName));
        } else System.out.println("Not have group");
    }

    public static void removeUserGroup(String groupName, User user) {
        if (checkExitsUserInGroup(groupName, user)) {
            findGroupByName(groupName).getUsers().remove(user);
        } else System.out.println("Not have user in group");
    }
}
