package com.drss.nytimesarticles.model.article;

import com.drss.nytimesarticles.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by davir on 16/03/2018.
 */

class Headline extends BaseModel{

    @SerializedName("main")
    @Expose
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
