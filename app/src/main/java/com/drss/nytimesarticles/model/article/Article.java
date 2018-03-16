package com.drss.nytimesarticles.model.article;

import com.drss.nytimesarticles.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davir on 15/03/2018.
 */

public class Article extends BaseModel{

    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getHeadline() {
        return headline.getMain();
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

}
