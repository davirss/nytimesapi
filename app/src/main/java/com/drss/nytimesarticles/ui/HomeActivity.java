package com.drss.nytimesarticles.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drss.nytimesarticles.R;
import com.drss.nytimesarticles.business.MostViewedArticleBusiness;
import com.drss.nytimesarticles.business.QueryArticleBusiness;
import com.drss.nytimesarticles.model.DisplayArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleMedia;
import com.drss.nytimesarticles.model.mostviewedarticle.MediaMetadata;

import java.util.List;

public class HomeActivity extends Activity implements MostViewedArticleBusiness.MostViewedArticlesContract, QueryArticleBusiness.QueryArticleBusinessContract {


    private RecyclerView rvArticles;
    private TextView title;
    private EditText queryEditText;
    private MostViewedArticleBusiness mostViewedArticleBusiness;
    private QueryArticleBusiness queryArticleBusiness;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mostViewedArticleBusiness = new MostViewedArticleBusiness();
        queryArticleBusiness = new QueryArticleBusiness();

        rvArticles = findViewById(R.id.rv_articles);
        title = findViewById(R.id.tv_title);
        queryEditText = findViewById(R.id.et_search);

        queryEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                queryArticleBusiness.queryArticles(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        articleAdapter = new ArticleAdapter(this);
        rvArticles.setAdapter(articleAdapter);
        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        rvArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mostViewedArticleBusiness.attach(this);
        queryArticleBusiness.attach(this);
        mostViewedArticleBusiness.searchMostViewdArticles();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mostViewedArticleBusiness.attach(null);
        queryArticleBusiness.attach(null);
    }

    @Override
    public void displayMostViewedArticles(List<DisplayArticle> mostViewedArticles) {
        Log.d("ARTICLES",  "" + mostViewedArticles.size());
        articleAdapter.updateArticleList(mostViewedArticles);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayMostViewedArticlesError(String error) {
        Log.d("ARTICLES", error);
    }

    @Override
    public void displayQueryArticles(List<DisplayArticle> displayArticles) {
        articleAdapter.updateArticleList(displayArticles);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayQueryArticleError(String error) {
        Log.d("ARTICLES", error);
    }


    class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder>{

        private final Context mContext;
        private List<DisplayArticle> mArticleList;

        public ArticleAdapter(Context context){
            this.mContext = context;
        }

        public void updateArticleList(List<DisplayArticle> mostViewedArticleList){
            this.mArticleList = mostViewedArticleList;
        }

        public boolean canDisplayMoreArticles(){
            return mArticleList != null && mArticleList.size() > 0;
        }

        @NonNull
        @Override
        public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ArticleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
            if(position == getItemCount()){

            } else {
                DisplayArticle currMostViewedArticle = mArticleList.get(position);
                holder.title.setText(currMostViewedArticle.getTitle());
                holder.publishDate.setText(currMostViewedArticle.getPublishDate());
                holder.articleAbstract.setText(currMostViewedArticle.getArticleAbstract());
                if(!TextUtils.isEmpty(currMostViewedArticle.getThumbnailUrl())){
                    holder.articleImage.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(currMostViewedArticle.getThumbnailUrl())
                            .into(holder.articleImage);
                } else {
                    holder.articleImage.setVisibility(View.GONE);
                }

            }
        }

        @Override
        public int getItemCount() {
            if(mArticleList == null){
                return 0;
            }
            return mArticleList.size();
        }
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

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
}
