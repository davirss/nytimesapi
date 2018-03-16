package com.drss.nytimesarticles.model.article;

import com.drss.nytimesarticles.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davir on 15/03/2018.
 */

public class Doc extends BaseModel{

    @SerializedName("docs")
    @Expose
    List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
