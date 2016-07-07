package com.mnandanuri.mytodoapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by mnandanuri on 7/1/2016.
 */

public class ToDoAppAdater extends ArrayAdapter<ToDoDetails> {

    public ToDoAppAdater(Context context, ArrayList<ToDoDetails> tasks) {

        super(context,android.R.layout.simple_list_item_1,tasks);

    }



}
