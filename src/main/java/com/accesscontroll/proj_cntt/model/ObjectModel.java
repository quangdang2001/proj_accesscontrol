package com.accesscontroll.proj_cntt.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectModel implements Serializable {
    private String objectName;
    private String type;
    private String location;


    public static ArrayList<ObjectModel> listObject = new ArrayList<>();

    public ObjectModel(String objectName, String type, String location, User owner) {
        this.objectName = objectName;
        this.type = type;
        this.location = location;
    }

    public ObjectModel() {
    }

    public static ArrayList<ObjectModel> getListObject() {
        return listObject;
    }

    public static void setListObject(ArrayList<ObjectModel> listObject) {
        ObjectModel.listObject = listObject;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Object{" +
                "objectName='" + objectName + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
