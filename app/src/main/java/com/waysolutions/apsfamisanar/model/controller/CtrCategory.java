package com.waysolutions.apsfamisanar.model.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.waysolutions.apsfamisanar.SingletonVolley;
import com.waysolutions.apsfamisanar.model.App;
import com.waysolutions.apsfamisanar.model.Category;
import com.waysolutions.apsfamisanar.tools.ToolsNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 11/23/17.
 */

public class CtrCategory {

    public static final String allCategory      = "All";
    public static final String withoutCategory  = "Without Category";

    ArrayList<Category> categories      = new ArrayList<>();
    ArrayList<String> categoriesNames   = new ArrayList<>();

    Activity _ctx;


    public CtrCategory(ArrayList<App> apps) {
        createCategories(apps);
    }

    public CtrCategory(Activity ctx) {
        _ctx = ctx;

        if(ToolsNetwork.isNetDisponible(ctx) && ToolsNetwork.isOnlineNet())
            getJson();
        else
            Toast.makeText(ctx,"connection error",Toast.LENGTH_LONG).show();
            restoreLocalApps(ctx);
    }

    public ArrayList<String> getCategoriesNames() {
        return categoriesNames;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    //----------------------------------------------------------------------------------------------
    private void getJson(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://www.reddit.com/reddits.json",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray= response.getJSONObject("data").getJSONArray("children");
                            ArrayList<App> apps = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<App>>() {}.getType());
                            saveLocalApps(apps);
                            createCategories(apps);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseLog", error.toString());
            }
        });
        SingletonVolley.getInstance(_ctx).addToRequestQueue(jsonObjectRequest);
    }
    //----------------------------------------------------------------------------------------------

    private void createCategories(ArrayList<App> apps){
        categories = new ArrayList<>();
        categoriesNames   = new ArrayList<>();

        //add base categories
        createCategory(allCategory, apps);
        createCategory(withoutCategory);

        for(int i = 0; i< apps.size(); i++){
            App app = apps.get(i);
            String categoryName = (app.getData().getAdvertiser_category()==null) ? withoutCategory: app.getData().getAdvertiser_category();
            if (!categoriesNames.contains(categoryName)){
                createCategory(categoryName, app);
            }else {
                int indexCategories = categoriesNames.indexOf(categoryName);
                categories.get(indexCategories).getAppsFiltered().add(app);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    private void createCategory(String nameCategory){
        categoriesNames.add(nameCategory);
        categories.add(new Category(nameCategory));
    }

    private void createCategory(String nameCategory, App app){
        categoriesNames.add(nameCategory);
        categories.add(new Category(nameCategory, app));
    }

    private void createCategory(String nameCategory, ArrayList<App> apps){
        categoriesNames.add(nameCategory);
        categories.add(new Category(nameCategory, apps));
    }

    //----------------------------------------------------------------------------------------------

    private void saveLocalApps(ArrayList<App> apps){
        Gson gson = new Gson();
        String json = gson.toJson(apps);

        SharedPreferences sharedPreferences = _ctx.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("apps", json);

        editor.commit();
    }

    private void restoreLocalApps(Activity ctx){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        String savedSession = sharedPreferences.getString("apps", null);

        if(savedSession != null)
            createCategories((ArrayList<App>) gson.fromJson(savedSession, new TypeToken<List<App>>() {}.getType()));
    }
}
