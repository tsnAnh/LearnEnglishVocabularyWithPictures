package com.tsnanh.education.learnenglishvocabularywithpictures.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

import java.util.ArrayList;

public class VocabularyCategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Vocabulary> arrayList;

    public VocabularyCategoryAdapter(Context context, ArrayList<Vocabulary> arrayList) {
        this.context = context.getApplicationContext();
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Vocabulary getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item_list_vocabulary_category, viewGroup, false);

            ViewHolder holder = new ViewHolder();


        }
    }

    class ViewHolder {
        RoundedImageView roundedImageView;
        TextView textView;
        ImageView btnFavorite;
    }
}
