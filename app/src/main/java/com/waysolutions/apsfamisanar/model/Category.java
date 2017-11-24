package com.waysolutions.apsfamisanar.model;

import java.util.ArrayList;

/**
 * Created by manuel on 11/23/17.
 */

public class Category {

    private String name;
    private ArrayList<App> appsFiltered = new ArrayList<>();

    //----------------------------------------------------------------------------------------------

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, App app) {
        this.name = name;
        appsFiltered.add(app);
    }

    public Category(String name, ArrayList<App> apps) {
        this.name = name;
        appsFiltered = apps;
    }

    //----------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppsFiltered(ArrayList<App> appsFiltered) {
        this.appsFiltered = appsFiltered;
    }

    public ArrayList<App> getAppsFiltered() {
        return appsFiltered;
    }
}
