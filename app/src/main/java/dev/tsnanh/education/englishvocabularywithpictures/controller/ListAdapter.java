package dev.tsnanh.education.englishvocabularywithpictures.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.model.Categories;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Categories> arrayList;

    public ListAdapter(Context context, ArrayList<Categories> arrayList) {
        this.context = context.getApplicationContext();
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
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
            v = inflater.inflate(R.layout.item_listview_main, viewGroup, false);

            ViewHolder holder = new ViewHolder();
            holder.textView = v.findViewById(R.id.item_list_lbl_title);
            holder.imageView = v.findViewById(R.id.item_list_img_category);

            v.setTag(holder);
        }

        Categories categories = arrayList.get(i);

        ViewHolder holder = (ViewHolder) v.getTag();

        holder.textView.setText(categories.getTitle());
        Glide.with(context).load(Config.SERVER_IMAGE_CAT_FOLDER + categories.getIntroImage()).centerCrop().placeholder(R.drawable.loading_spinner).into(holder.imageView);
        Log.e("URL", Config.SERVER_IMAGE_CAT_FOLDER + categories.getIntroImage());
//        v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 550));
        return v;
    }
}
