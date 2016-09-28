package com.klm.testcase.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klm.testcase.Objects.Flight;
import com.klm.testcase.Objects.News;
import com.klm.testcase.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Heitor Zanetti
 * 11/03/2016
 */
public class NewsAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    Context mContext;
    ArrayList<News> news;

    public NewsAdapter(Activity mContext, ArrayList<News> news) {
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.news = news;
    }

    static class ViewHolder{
        @Bind(R.id.title) TextView tvTitle;
        @Bind(R.id.author) TextView tvAuthor;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }
        else {
            convertView = mInflater.inflate(R.layout.news_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        News n = news.get(position);

        holder.tvTitle.setText(n.getTitle());
        holder.tvAuthor.setText(n.getAuthor());

        return convertView;

    }
}
