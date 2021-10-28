package com.accesscontroll.proj_cntt.utils;


import com.accesscontroll.proj_cntt.acm.ACM;
import com.accesscontroll.proj_cntt.model.ObjectModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectModelUtil {

    public static void createObject(ObjectModel objectModel){
        String type = objectModel.getType();
        if (type.equals("file")){
            try {

                File file = new File(objectModel.getLocation()+objectModel.getObjectName());

                if (file.createNewFile()) {
                    System.out.println("File is created!");
                    addOject(objectModel);
                } else {
                    System.out.println("File already exists.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Path path = Paths.get(objectModel.getLocation()+objectModel.getObjectName());

            // Tạo đường dẫn thư mục duy nhất
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                    System.out.println("Create path = " + path + " successfully!");
                    addOject(objectModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Path = " + path + " is existed!");
            }
        }
    }

    public static void deleteObject(ObjectModel objectModel){
        if (findObjectByName(objectModel.getObjectName(),objectModel.getLocation())!=null) {
            deleteDir(new File(objectModel.getLocation() + "\\" + objectModel.getObjectName()));
        }
    }

    private static void deleteDir(File file) {

        // neu file la thu muc thi xoa het thu muc con va file cua no
        if (file.isDirectory()) {
            // liet ke tat ca thu muc va file
            String[] files = file.list();
            for (String child : files) {
                File childDir = new File(file, child);
                if (childDir.isDirectory()) {
                    // neu childDir la thu muc thi goi lai phuong thuc deleteDir()
                    deleteDir(childDir);
                } else {
                    // neu childDir la file thi xoa
                    childDir.delete();
                    ACM.removeObject(findObjectByName(childDir.getName(),childDir.getParent()));
                    System.out.println("File deleted "
                            + childDir.getAbsolutePath());
                }
            }
            // Check lai va xoa thu muc cha
            if (file.list().length == 0) {
                file.delete();
                ACM.removeObject(findObjectByName(file.getName(),file.getParent()));
                System.out.println("Folder deleted " + file.getAbsolutePath());
            }

        } else {
            // neu file la file thi xoa
            file.delete();
            ACM.removeObject(findObjectByName(file.getName(),file.getParent()));
            System.out.println("File deleted " + file.getAbsolutePath());
        }
    }

    public static void addOject(ObjectModel objectModel){
        ObjectModel.listObject.add(objectModel);
    }

    public static void removeOject(ObjectModel objectModel){
        if (ObjectModel.listObject.contains(objectModel)) {
            ObjectModel.listObject.remove(objectModel);
        }
    }

    public static ObjectModel findObjectByName(String Name, String location){
        for (ObjectModel tempObjectModel : ObjectModel.listObject){
            if (tempObjectModel.getObjectName().equals(Name) && tempObjectModel.getLocation().equals(location)){
                return tempObjectModel;
            }
        }
        return null;
    }
}
