package com.waysolutions.apsfamisanar.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sevenheaven.segmentcontrol.SegmentControl;
import com.waysolutions.apsfamisanar.MainActivity;
import com.waysolutions.apsfamisanar.R;
import com.waysolutions.apsfamisanar.SingletonVolley;
import com.waysolutions.apsfamisanar.dialoges.DialogeInfo;
import com.waysolutions.apsfamisanar.model.App;
import com.waysolutions.apsfamisanar.model.Data;
import com.waysolutions.apsfamisanar.model.controller.CtrCategory;
import com.waysolutions.apsfamisanar.tools.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 11/23/17.
 */

public class FragmentSplash extends Fragment {

    public static final String  NAME = "FragmentSplash";

    //
    Activity ctx;

    private final int SPLASH_DISPLAY_LENGTH = 1500;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_splash, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goFragmentAppsAnimator();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }




}
