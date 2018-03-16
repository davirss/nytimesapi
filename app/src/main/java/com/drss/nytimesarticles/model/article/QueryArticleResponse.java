package com.drss.nytimesarticles.model.article;

import com.drss.nytimesarticles.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davir on 15/03/2018.
 */

public class QueryArticleResponse extends BaseModel {

    @SerializedName("response")
    @Expose
    private Doc docs = null;

    public Doc getDocs() {
        return docs;
    }

    public void setDocs(Doc docs) {
        this.docs = docs;
    }

}
