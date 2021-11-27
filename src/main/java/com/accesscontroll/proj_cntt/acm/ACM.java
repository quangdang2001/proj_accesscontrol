package com.accesscontroll.proj_cntt.acm;



import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ACM {
    private HashMap<User,String> userAccess;

    private static HashMap<ObjectModel,HashMap<User,String>> ACL = new HashMap<>();
    private static HashMap<User,HashMap<ObjectModel,String>> CL = new HashMap<>();


    public static void setPermission(ObjectModel object, User user, String pemission){
            if ( ACL.containsKey(object)) {
                    ACL.get(object).put(user, pemission);
            }
            else System.out.println("Not have object");
            CL.get(user).put(object,pemission);

    }

    public static void printACM(){

        for (ObjectModel tempobj : ACM.getACL().keySet()){
            System.out.println(tempobj.getObjectName());
            for (User tempUser : ACM.getACL().get(tempobj).keySet()){
                System.out.print("  "+tempUser.getUserName()+ "  ");
                System.out.println(ACM.getACL().get(tempobj).get(tempUser));
            }
        }
    }

    public  static void removeObject(ObjectModel objectModel){
        ACL.remove(objectModel);
    }
    public static void removeUserForACL(User user){
        for (ObjectModel objectModel : ACM.getACL().keySet()){
            ACM.ACL.get(objectModel).remove(user);
        }
    }

    public static void removeUser(User user){
        CL.remove(user);
    }
    public static void addObject2ACL(ObjectModel objectModel){
        ACL.put(objectModel,new HashMap<>());
    }
    public static void addUser2CL(User user) {
        CL.put(user,new HashMap<>());
    }
    public static HashMap<ObjectModel, HashMap<User, String>> getACL() {
        return ACL;
    }
    public static HashMap<User, HashMap<ObjectModel, String>> getCL() {
        return CL;
    }

    public static void setACL(HashMap<ObjectModel, HashMap<User, String>> ACL) {
        ACM.ACL = ACL;
    }

    public static void setCL(HashMap<User, HashMap<ObjectModel, String>> CL) {
        ACM.CL = CL;
    }
}
