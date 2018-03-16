package com.drss.nytimesarticles.model.article;


import com.drss.nytimesarticles.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by davir on 15/03/2018.
 */
public class Multimedia extends BaseModel {

    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("url")
    @Expose
    private String url;

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}