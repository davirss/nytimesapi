package com.drss.nytimesarticles.business;

import android.util.Log;

import com.drss.nytimesarticles.model.DisplayArticle;
import com.drss.nytimesarticles.model.article.Article;
import com.drss.nytimesarticles.model.article.QueryArticleResponse;
import com.drss.nytimesarticles.repository.NyTimesService;
import com.drss.nytimesarticles.ui.HomeActivity;
import com.drss.nytimesarticles.util.DisplayArticleUtil;
import com.drss.nytimesarticles.util.ServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davir on 15/03/2018.
 */
public class QueryArticleBusiness implements Callback<QueryArticleResponse>{

    private final NyTimesService nyTimesService;
    private QueryArticleBusinessContract queryContract;

    private Call<QueryArticleResponse> currQueryArticleCall;

    private List<DisplayArticle> displayArticles = new ArrayList<>();

    public QueryArticleBusiness(){
        nyTimesService = ServiceGenerator.generateService(NyTimesService.class);
    }

    public void attach(QueryArticleBusinessContract queryArticleBusinessContract) {
        this.queryContract = queryArticleBusinessContract;
    }

    public interface QueryArticleBusinessContract {

        void displayQueryArticles(List<DisplayArticle> displayArticles);

        void displayQueryArticleError(String error);
    }

    private Integer pagination = 0;

    private static final String FIELDS = "snippet,headline,multimedia,pub_date";
    private static final String FACET_FIELD = "type_of_material :\"article\"";
    private String lastSearchTerm = "";

    public void queryArticles(String term){
        if(currQueryArticleCall != null){
            currQueryArticleCall.cancel();
        }
        lastSearchTerm = term;
        pagination = 0;

        displayArticles.clear();
        if(queryContract != null){
            queryContract.displayQueryArticles(displayArticles);
        }

        currQueryArticleCall = nyTimesService.getArticles(term, FIELDS, pagination, true, FACET_FIELD);
        currQueryArticleCall.enqueue(this);
    }

    public void loadMoreArticles() {
        pagination += 1;
        currQueryArticleCall = nyTimesService.getArticles(lastSearchTerm,
                FIELDS, pagination, true, FACET_FIELD);
        currQueryArticleCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<QueryArticleResponse> call, Response<QueryArticleResponse> response) {
        if(response.code() != 200 || response.body() == null){
            if(queryContract != null){
                queryContract.displayQueryArticleError("Erro");
            }
            return;
        }

        for(Article article : response.body().getDocs().getArticleList()){
            displayArticles.add(DisplayArticleUtil.fromArticle(article));
        }

        if(queryContract != null) {
            queryContract.displayQueryArticles(displayArticles);
        }
    }

    @Override
    public void onFailure(Call<QueryArticleResponse> call, Throwable t) {
        if(queryContract != null){
            queryContract.displayQueryArticleError("Erro");
        }
    }
}
