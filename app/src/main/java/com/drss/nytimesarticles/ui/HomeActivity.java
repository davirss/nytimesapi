package com.drss.nytimesarticles.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.drss.nytimesarticles.R;
import com.drss.nytimesarticles.business.MostViewedArticleBusiness;
import com.drss.nytimesarticles.business.QueryArticleBusiness;
import com.drss.nytimesarticles.model.DisplayArticle;

import java.util.List;

public class HomeActivity extends Activity implements MostViewedArticleBusiness.MostViewedArticlesContract, QueryArticleBusiness.QueryArticleBusinessContract {


    private RecyclerView rvArticles;
    private TextView title;
    private EditText queryEditText;
    private MostViewedArticleBusiness mostViewedArticleBusiness;
    private QueryArticleBusiness queryArticleBusiness;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager mLayoutManager;

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
        mLayoutManager = new LinearLayoutManager(this);
        rvArticles.setLayoutManager(mLayoutManager);
        rvArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(TextUtils.isEmpty(queryEditText.getText().toString())){
                   return;
                }
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    queryArticleBusiness.loadMoreArticles();
                }
            }
        });
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
        title.setText(R.string.most_viewed_articles);
        articleAdapter.displayLoading(false);
        articleAdapter.updateArticleList(mostViewedArticles);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayMostViewedArticlesError(String error) {
        Log.d("ARTICLES", error);
    }

    @Override
    public void displayQueryArticles(List<DisplayArticle> displayArticles) {
        title.setText(getString(R.string.articles_containing).concat(queryEditText.getText().toString()).concat("'"));
        articleAdapter.updateArticleList(displayArticles);
        articleAdapter.displayLoading(true);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayQueryArticleError(String error) {
        Log.d("ARTICLES", error);
    }


}
