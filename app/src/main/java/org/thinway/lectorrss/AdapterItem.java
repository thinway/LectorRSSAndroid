package org.thinway.lectorrss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thinway.lectorrss.model.News;

import java.util.ArrayList;

/**
 * Created by fdelgado on 25/1/18.
 */

public class AdapterItem extends BaseAdapter {

    private Activity activity;
    private ArrayList<News> newsList;

    public AdapterItem(Activity activity, ArrayList<News> newsList) {
        this.activity = activity;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if( convertView == null ){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.news_list_item, null);
        }

        News noticia = newsList.get(position);

        TextView title = v.findViewById(R.id.textViewTitle);
        title.setText(noticia.getTitle());

        TextView description = v.findViewById(R.id.textViewDescription);
        description.setText(noticia.getDescription());

        return v;
    }
}















