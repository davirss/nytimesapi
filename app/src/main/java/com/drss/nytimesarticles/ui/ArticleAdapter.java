package com.drss.nytimesarticles.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drss.nytimesarticles.R;
import com.drss.nytimesarticles.model.DisplayArticle;

import java.util.List;

/**
 * Created by davir on 16/03/2018.
 */
class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ARTICLE_TYPE = 0;
    private static final int MORE_ARTICLE_TYPE = 1;


    private final Context mContext;
    private List<DisplayArticle> mArticleList;
    private Boolean displayLoading = false;

    public ArticleAdapter(Context context) {
        this.mContext = context;
    }

    public void updateArticleList(List<DisplayArticle> mostViewedArticleList) {
        this.mArticleList = mostViewedArticleList;
    }

    public void displayLoading(Boolean displayLoading) {
        this.displayLoading = displayLoading;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return MORE_ARTICLE_TYPE;
        } else {
            return ARTICLE_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case ARTICLE_TYPE:
                return new ArticleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false));
            case MORE_ARTICLE_TYPE:
                return new LoadingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_listend, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == MORE_ARTICLE_TYPE) {
            if(!displayLoading){
                ((LoadingViewHolder) holder).mProgress.setVisibility(View.GONE);
            } else {
                ((LoadingViewHolder) holder).mProgress.setVisibility(View.VISIBLE);
            }
            return;
        }

        ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
        DisplayArticle currMostViewedArticle = mArticleList.get(position);
        Log.d("RV", currMostViewedArticle.toString());
        articleViewHolder.title.setText(currMostViewedArticle.getTitle());
        articleViewHolder.publishDate.setText(currMostViewedArticle.getPublishDate());
        articleViewHolder.articleAbstract.setText(currMostViewedArticle.getArticleAbstract());
        if (!TextUtils.isEmpty(currMostViewedArticle.getThumbnailUrl())) {
            articleViewHolder.articleImage.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(currMostViewedArticle.getThumbnailUrl())
                    .into(articleViewHolder.articleImage);
        } else {
            articleViewHolder.articleImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mArticleList == null) {
            return 1;
        }
        return mArticleList.size() + 1;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView articleAbstract;
        TextView publishDate;
        ImageView articleImage;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            articleAbstract = itemView.findViewById(R.id.tv_abstract);
            publishDate = itemView.findViewById(R.id.tv_publishDate);
            articleImage = itemView.findViewById(R.id.iv_picture);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgress;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            mProgress = itemView.findViewById(R.id.pb_loading);
        }
    }
}
