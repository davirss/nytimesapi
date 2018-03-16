package com.drss.nytimesarticles.business;

import com.drss.nytimesarticles.model.DisplayArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleResponse;
import com.drss.nytimesarticles.repository.NyTimesService;
import com.drss.nytimesarticles.util.DisplayArticleUtil;
import com.drss.nytimesarticles.util.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by davir on 15/03/2018.
 */

public class MostViewedArticleBusiness implements Callback<MostViewedArticleResponse> {

    public interface MostViewedArticlesContract {

        public void displayMostViewedArticles(List<DisplayArticle> mostViewedArticles);

        public void displayMostViewedArticlesError(String error);

    }


    private final NyTimesService nyTimesService;
    private MostViewedArticlesContract mostViwedArticlesContract;
    Call<MostViewedArticleResponse> currMostViewedArticleCall;

    public MostViewedArticleBusiness(){
         nyTimesService = ServiceGenerator.generateService(NyTimesService.class);

    }

    public void attach(MostViewedArticlesContract mostViewdArticlesContract){
        this.mostViwedArticlesContract = mostViewdArticlesContract;
    }

    public void searchMostViewdArticles(){
        currMostViewedArticleCall = nyTimesService.getMostViewed("all-sections", "1");
        currMostViewedArticleCall.enqueue(this);

    }

    List<DisplayArticle> mCurrentDisplayArticles = new ArrayList<DisplayArticle>();

    @Override
    public void onResponse(Call<MostViewedArticleResponse> call, Response<MostViewedArticleResponse> response) {
        if(mostViwedArticlesContract != null){
            List<MostViewedArticle> mostViewedArticles =  response.body().getResults();

            for(MostViewedArticle mostViewedArticle : mostViewedArticles){
                mCurrentDisplayArticles.add(DisplayArticleUtil.fromMostViewedArticle(mostViewedArticle));
            }

            mostViwedArticlesContract.displayMostViewedArticles(mCurrentDisplayArticles);
        }
    }

    @Override
    public void onFailure(Call<MostViewedArticleResponse> call, Throwable t) {
        if(mostViwedArticlesContract != null)
            mostViwedArticlesContract.displayMostViewedArticlesError("Erro");
    }
}
