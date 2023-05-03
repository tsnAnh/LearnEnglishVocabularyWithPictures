package dev.tsnanh.education.englishvocabularywithpictures.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import dev.tsnanh.education.englishvocabularywithpictures.R;
import dev.tsnanh.education.englishvocabularywithpictures.model.Vocabulary;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Vocabulary> arrayList;

    public SearchAdapter(Context context, ArrayList<Vocabulary> arrayList) {
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

        Vocabulary vocabulary = arrayList.get(i);

        ViewHolder holder = (ViewHolder) v.getTag();

        holder.textView.setText(vocabulary.getEn_us());
        Glide.with(context).load(Config.SERVER_IMAGE_FOLDER + vocabulary.getImage()).centerCrop().placeholder(R.drawable.loading_spinner).into(holder.imageView);
//        v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 550));
        return v;
    }

}
