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

        Data data1 = new Data();
        data1.saveData();
        data1.write(data1);
        data1 = data1.read();

        System.out.println(data1.getListObject());
        System.out.println(data1.getListUser());
        ACM.printACM();
    }
}
