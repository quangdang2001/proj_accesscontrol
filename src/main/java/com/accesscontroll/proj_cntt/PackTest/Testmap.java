package com.accesscontroll.proj_cntt.PackTest;


import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;
import com.accesscontroll.proj_cntt.utils.ObjectModelUtil;
import com.accesscontroll.proj_cntt.utils.UserUtil;

public class Testmap {
    public static void main(String[] args) {

        ObjectModel file1 = new ObjectModel();
        file1.setObjectName("f1");
        file1.setLocation("D:\\");
        file1.setType("folder");

        ObjectModel file11 = new ObjectModel();
        file11.setObjectName("f1.1");
        file11.setLocation(file1.getLocation()+file1.getObjectName()+"\\");
        file11.setType("folder");

        ObjectModel file12 = new ObjectModel();
        file12.setObjectName("f1.2");
        file12.setLocation(file1.getLocation()+file1.getObjectName()+"\\");
        file12.setType("folder");

        ObjectModelUtil.createObject(file1);

        ObjectModelUtil.createObject(file11);

        ObjectModelUtil.createObject(file12);

        //ObjectModelUtil.deleteObject(file1);

        User user1 = new User();
        user1.setUserName("user1");
        ACM.addUser2CL(user1);
        User user2 = new User();
        user2.setUserName("user2");
        ACM.addUser2CL(user2);

        UserUtil.addUser(user1);
        UserUtil.addUser(user2);

        ACM.addObject2ACL(file1);
        ACM.addObject2ACL(file11);
        ACM.addObject2ACL(file12);


        ACM.addUser2CL(user1);
        ACM.addUser2CL(user2);


        ACM.setPermission(file1,user1,"rwx");
        ACM.setPermission(file11,user1,"rw");
        ACM.setPermission(file12,user2,"rx");
        ACM.setPermission(file11,user2,"rx");


        Data data1 = new Data();
        data1.saveData();
        data1.write(data1);
        data1 = data1.read();
//        ACM.setACL(data1.getACL());
//        System.out.println(ACM.getACL());
        System.out.println(data1.getListObject());
        System.out.println(data1.getListUser());
        ACM.printACM();
    }
}
