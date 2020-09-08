package com.example.project3;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Module extends Application {
    public ArrayList<String> garrList = new ArrayList<>();
    public ArrayAdapter<String> garrAdp;
    public String gvalue_id;
    public String gvalue_title;

    public String getGvalue_id() { return gvalue_id; }
    public void setGvalue_id(String gvalue_id) { this.gvalue_id = gvalue_id; }
    public String getGvalue_title() { return gvalue_title; }
    public void setGvalue_title(String gvalue_title) { this.gvalue_title = gvalue_title; }

}
