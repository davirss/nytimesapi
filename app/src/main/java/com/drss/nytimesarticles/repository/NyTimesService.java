package com.drss.nytimesarticles.repository;

import com.drss.nytimesarticles.BuildConfig;
import com.drss.nytimesarticles.model.article.Article;
import com.drss.nytimesarticles.model.article.QueryArticleResponse;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by davir on 15/03/2018.
 */

public interface NyTimesService {


    @GET("mostpopular/v2/mostviewed/{section}/{time-period}.json?api-key=" + BuildConfig.NYT_APIKEY)
    Call<MostViewedArticleResponse> getMostViewed(@Path("section") String section,
                                                  @Path("time-period") String TimePeriod);

    @GET("search/v2/articlesearch.json?api-key=" + BuildConfig.NYT_APIKEY + "&")
    Call<QueryArticleResponse> getArticles(@Query("q") String term,
                                           @Query("fl") String fields,
                                           @Query("page") Integer page,
                                           @Query("facet_filter") Boolean facetFilter,
                                           @Query("facet_field") String facet_field);
}
