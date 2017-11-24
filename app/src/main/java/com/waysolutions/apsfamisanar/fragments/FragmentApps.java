package com.waysolutions.apsfamisanar.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.waysolutions.apsfamisanar.model.Category;
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

public class FragmentApps extends Fragment {

    public static final String  NAME = "FragmentApps";

    //
    MainActivity ctx;
    CtrCategory _ctrCategory;

    //View
    RecyclerView rvApps;
    SegmentControl segmentControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = (MainActivity) getActivity();
        _ctrCategory = ctx.getCtrCategory();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_apps, container, false);

        rvApps          = (RecyclerView) v.findViewById(R.id.rvApps);
        segmentControl  = (SegmentControl) v.findViewById(R.id.segmentControl);

        return v;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getJson()
        if(_ctrCategory.getCategories().size()>0){
            createRecycler(_ctrCategory.getCategories().get(0).getAppsFiltered());
            createViewCategories();
        }

    }

    //----------------------------------------------------------------------------------------------

    private void createRecycler(ArrayList<App> apps){
        rvApps.setHasFixedSize(true);
        MyAdapter adapaterRecicler = new MyAdapter(ctx, apps);
        rvApps.setAdapter(adapaterRecicler);
        rvApps.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));
    }

    //----------------------------------------------------------------------------------------------

    private void createViewCategories(){

        ArrayList<String> categoriesNames = _ctrCategory.getCategoriesNames();
        segmentControl.setText(categoriesNames.toArray(new String[categoriesNames.size()]));

        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int indexCategory) {
                rvApps.setHasFixedSize(true);
                MyAdapter adapaterRecicler = new MyAdapter(ctx, _ctrCategory.getCategories().get(indexCategory).getAppsFiltered());
                rvApps.setAdapter(adapaterRecicler);
                rvApps.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.VERTICAL,false));
            }
        });
    }


    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private Activity _ctx;
        private ArrayList<App> _apps;

        private ImageLoader     _imageL;

        public MyAdapter(Activity ctx, ArrayList<App> apps) {
            _ctx    = ctx;
            _apps   = apps;

            _imageL = new ImageLoader(ctx);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Data data   = _apps.get(position).getData();

            holder.tvTitle.setText(data.getTitle());
            holder.tvSubTitle.setText(data.getHeader_title());
            _imageL.loadAndDisplayCircledImage(data.getIcon_img(), holder.ivIconApp);


            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogeInfo dialogeInfo = new DialogeInfo(data, _ctx);
                    dialogeInfo.show(getFragmentManager(), null);

                    Animation rotate;
                    rotate = AnimationUtils.loadAnimation(_ctx, R.anim.girar);
                    rotate.reset();
                    holder.ivIconApp.startAnimation(rotate);
                }
            });


        }

        @Override
        public int getItemCount() {
            return _apps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView     tvTitle;
            public TextView     tvSubTitle;
            public ImageView    ivIconApp;
            public ViewGroup    parent;

            public ViewHolder(View itemView) {
                super(itemView);

                tvTitle     = (TextView)    itemView.findViewById(R.id.tvTitle);
                tvSubTitle  = (TextView)    itemView.findViewById(R.id.tvSubTitle);
                ivIconApp   = (ImageView) itemView.findViewById(R.id.ivIconApp);
                parent      = (ViewGroup) itemView.findViewById(R.id.parent);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_apps, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
            return vh;
        }
    }


}
