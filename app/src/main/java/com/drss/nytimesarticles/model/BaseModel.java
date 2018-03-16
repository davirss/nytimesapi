package com.drss.nytimesarticles.model;

import com.google.gson.Gson;

/**
 * Created by davir on 15/03/2018.
 */

public abstract class BaseModel {


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
