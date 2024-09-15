package com.example.gymfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.ArticleDetail;

import java.util.List;

public class ArticleDetailAdapter extends BaseAdapter {

    private final Context context;
    private List<ArticleDetail> articleDetails;
    private final LayoutInflater inflater;

    public ArticleDetailAdapter(Context context, List<ArticleDetail> articleDetails) {
        this.context = context;
        this.articleDetails = articleDetails;
        this.inflater = LayoutInflater.from(context);
    }

    public void setArticleDetails(List<ArticleDetail> articleDetails) {
        this.articleDetails = articleDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articleDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return articleDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_article_detail, parent, false);
            holder = new ViewHolder();
            holder.txtHeader = convertView.findViewById(R.id.txtHeader);
            holder.txtContent = convertView.findViewById(R.id.txtContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArticleDetail articleDetail = articleDetails.get(position);
        holder.txtHeader.setText(articleDetail.getHeader());
        holder.txtContent.setText(articleDetail.getContent());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtHeader;
        TextView txtContent;
    }
}