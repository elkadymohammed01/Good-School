package com.example.easyschool.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class Save {

    private static Context context;

    private static String type , id;

    private static String Searching_Text;

    private static boolean show=false;

    private static Uri file=null;

    private static Activities activities;

    private static Drawable id_photo;

    private static User user;

    private static Task task;

    public static boolean isShow() {
        return show;
    }

    public static void setShow(boolean show) {
        Save.show = show;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Save.context = context;
    }

    public static Task getTask() {
        return task;
    }

    public static void setTask(Task task) {
        Save.task = task;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Save.user = user;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Save.id = id;
    }

    public static Drawable getId_photo() {
        return id_photo;
    }

    public static void setId_photo(Drawable id_photo) {
        Save.id_photo = id_photo;
    }

    public static Activities getActivities() {
        return activities;
    }

    public static void setActivities(Activities activities) {
        Save.activities = activities;
    }

    public static Uri getFile() {
        return file;
    }

    public static void setFile(Uri file) {
        Save.file = file;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Save.type = type;
    }

}
