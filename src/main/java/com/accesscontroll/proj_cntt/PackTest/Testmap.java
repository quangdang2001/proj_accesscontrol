package com.accesscontroll.proj_cntt.PackTest;


import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.Data;
import com.accesscontroll.proj_cntt.model.ObjectModel;
import com.accesscontroll.proj_cntt.model.User;
import com.accesscontroll.proj_cntt.utils.ObjectModelUtil;
import com.accesscontroll.proj_cntt.utils.UserUtil;

public class Testmap {
    public static void main(String[] args) {

        ObjectModel folder1 = new ObjectModel();
        folder1.setObjectName("Folder&File");
        folder1.setLocation("D:\\");
        folder1.setType("folder");
        ObjectModelUtil.createObject(folder1);
        ACM.addObject2ACL(folder1);

//        ObjectModel folder11 = new ObjectModel();
//        folder11.setObjectName("Folder1.1");
//        folder11.setLocation(folder1.getLocation()+folder1.getObjectName()+"\\");
//        folder11.setType("folder");
//        ObjectModelUtil.createObject(folder11);
//        ACM.addObject2ACL(folder11);
//
//        ObjectModel folder12 = new ObjectModel();
//        folder12.setObjectName("Folder1.2");
//        folder12.setLocation(folder1.getLocation()+folder1.getObjectName()+"\\");
//        folder12.setType("folder");
//        ObjectModelUtil.createObject(folder12);
//        ACM.addObject2ACL(folder12);
//
//        ObjectModel file13 = new ObjectModel();
//        file13.setObjectName("file1.3.txt");
//        file13.setLocation(folder1.getLocation()+folder1.getObjectName()+"\\");
//        file13.setType("file");
//        ObjectModelUtil.createObject(file13);
//        ACM.addObject2ACL(file13);
//
//        ObjectModel folder111 = new ObjectModel();
//        folder111.setObjectName("Folder1.1.1");
//        folder111.setLocation(folder11.getLocation()+folder11.getObjectName()+"\\");
//        folder111.setType("folder");
//        ObjectModelUtil.createObject(folder111);
//        ACM.addObject2ACL(folder111);
//
//        ObjectModel file112 = new ObjectModel();
//        file112.setObjectName("file1.1.1.txt");
//        file112.setLocation(folder11.getLocation()+folder11.getObjectName()+"\\");
//        file112.setType("file");
//        ObjectModelUtil.createObject(file112);
//        ACM.addObject2ACL(file112);
//
//        ObjectModel file121 = new ObjectModel();
//        file121.setObjectName("file1.2.1.txt");
//        file121.setLocation(folder12.getLocation()+folder12.getObjectName()+"\\");
//        file121.setType("file");
//        ObjectModelUtil.createObject(file121);
//        ACM.addObject2ACL(file121);
//
//        ObjectModel file122 = new ObjectModel();
//        file122.setObjectName("file1.2.2.txt");
//        file122.setLocation(folder12.getLocation()+folder12.getObjectName()+"\\");
//        file122.setType("file");
//        ObjectModelUtil.createObject(file122);
//        ACM.addObject2ACL(file122);
//
//
//
//
//
//
//
//        User user1 = new User();
//        user1.setUserName("John");
//        ACM.addUser2CL(user1);
//
//        User user2 = new User();
//        user2.setUserName("Marry");
//        ACM.addUser2CL(user2);
//
//        User user3 = new User();
//        user3.setUserName("Bob");
//        ACM.addUser2CL(user3);
//
//        UserUtil.addUser(user1);
//        UserUtil.addUser(user2);
//        UserUtil.addUser(user3);
//
//        ACM.addUser2CL(user1);
//        ACM.addUser2CL(user2);
//        ACM.addUser2CL(user3);


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
