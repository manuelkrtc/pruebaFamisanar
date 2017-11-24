package com.waysolutions.apsfamisanar.dialoges;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.waysolutions.apsfamisanar.R;
import com.waysolutions.apsfamisanar.model.App;
import com.waysolutions.apsfamisanar.model.Data;
import com.waysolutions.apsfamisanar.tools.ImageLoader;


/**
 * Created by manuel on 7/20/17.
 */


@SuppressLint("ValidFragment")
public class DialogeInfo extends DialogFragment {

    Activity _ctx;

    Data _data;

    TextView tvLang;
    TextView tvSusc;
    TextView tvTitle;
    TextView tvSubTitle;
    TextView tvDescription;
    ImageView ivBackground;
    ImageLoader _imageL;


    @SuppressLint("ValidFragment")
    public DialogeInfo(Data data, Activity ctx){
        _ctx = ctx;
        _data = data;
        _imageL = new ImageLoader(_ctx);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = createView();
        AlertDialog.Builder builder = setViewBuilder(v);

        this.setCancelable(true);

        return builder.create();
    }

    private View createView(){
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_info, null);
        
        tvLang = (TextView)v.findViewById(R.id.tvLang);
        tvSusc = (TextView)v.findViewById(R.id.tvSusc);
        tvTitle = (TextView)v.findViewById(R.id.tvTitle);
        tvSubTitle = (TextView)v.findViewById(R.id.tvSubTitle);
        tvDescription = (TextView)v.findViewById(R.id.tvDescription);

        ivBackground = (ImageView) v.findViewById(R.id.ivBackground);
        
        return v;
    }

    private AlertDialog.Builder setViewBuilder(View v){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        tvLang.setText(_data.getLang());
        tvSusc.setText(String.valueOf(_data.getSubscribers()));
        tvTitle.setText(_data.getTitle());
        tvSubTitle.setText(_data.getHeader_title());
        tvDescription.setText(_data.getPublic_description());

        if(_data.getBanner_img()!=null)
            _imageL.loadAndDisplayImage(_data.getBanner_img(),ivBackground);
        

        return builder;
    }

}
