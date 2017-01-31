package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 1/23/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public WordAdapter(Activity context, ArrayList<Word> words,int  colorResourceId){
        super(context,0,words);
        mColorResourceId=colorResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listitemview =convertView;
        if (listitemview==null){
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        TextView miwoktext = (TextView)listitemview.findViewById(R.id.miwok_text_view);
        miwoktext.setText(currentWord.getMiwokAns());

        TextView engText = (TextView)listitemview.findViewById(R.id.default_text_view);
        engText.setText(currentWord.getEnglishAns());

        ImageView icon = (ImageView)listitemview.findViewById(R.id.image);
        if (currentWord.hasImage()){
            icon.setImageResource(currentWord.getImageId());
            icon.setVisibility(View.VISIBLE);
        }
        else {
            icon.setVisibility(View.GONE);
        }

        View textContainer = listitemview.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);



        return listitemview;
    }
}

