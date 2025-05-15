package com.example.take_homeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.take_homeproject.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<Article> articleList;

    public NewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleText.setText(article.getTitle());
        holder.authorText.setText(article.getAuthor() != null ? article.getAuthor() : "Unknown");

        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, authorText;
        ImageView imageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            authorText = itemView.findViewById(R.id.authorText);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}