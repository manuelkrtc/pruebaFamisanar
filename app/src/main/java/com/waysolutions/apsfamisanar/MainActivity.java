package com.waysolutions.apsfamisanar;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.waysolutions.apsfamisanar.fragments.FragmentApps;
import com.waysolutions.apsfamisanar.fragments.FragmentSplash;
import com.waysolutions.apsfamisanar.model.App;
import com.waysolutions.apsfamisanar.model.controller.CtrCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    CtrCategory ctrCategory;

    MainActivity    thisActivity = this;
    ArrayList<App>  apps;

    FragmentTransaction fragmentTransaction;

    //View
    ViewGroup fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = (ViewGroup) findViewById(R.id.rlFragment);
        ctrCategory = new CtrCategory(thisActivity);

        goFragmentSplash();
    }

    public CtrCategory getCtrCategory() {
        return ctrCategory;
    }

    //--------------------------------------- goFragments ------------------------------------------
    public void goFragmentApps(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, new FragmentApps());
        fragmentTransaction.addToBackStack(FragmentApps.NAME);
        fragmentTransaction.commit();
    }

    public void goFragmentAppsAnimator(){
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.fade_out, R.animator.fade_out);
        fragmentTransaction.add(R.id.rlFragment, new FragmentApps());
        fragmentTransaction.addToBackStack(FragmentApps.NAME);
        fragmentTransaction.commit();
    }

    public void goFragmentSplash(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragment, new FragmentSplash());
        fragmentTransaction.addToBackStack(FragmentSplash.NAME);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
