package com.mnandanuri.mytodoapp;

/**
 * Created by mnandanuri on 6/30/2016.
 */
public class ToDoDetails {


    public String taskName;



    public ToDoDetails(String taskName) {

        this.taskName = taskName;
    }
    @Override
    public String toString() {
        return getTaskName(taskName); // You can add anything else like maybe getDrinkType()
    }
    public static String getTaskName(String taskName) {
        return  taskName;
    }
}


