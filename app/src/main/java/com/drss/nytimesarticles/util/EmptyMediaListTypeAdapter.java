package com.drss.nytimesarticles.util;

import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleMedia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davir on 16/03/2018.
 */

public class EmptyMediaListTypeAdapter implements JsonDeserializer<MostViewedArticle> {

    @Override
    public MostViewedArticle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //A API está retornando uma String vazia onde deveria ser uma lista.
        MostViewedArticle mostViewedArticle = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(json, MostViewedArticle.class);
        JsonElement mediaList = json.getAsJsonObject().get("media");

        if(mediaList.isJsonArray()){
            List<MostViewedArticleMedia> listMvam = context.deserialize(mediaList.getAsJsonArray(),
                    new TypeToken<List<MostViewedArticleMedia>>(){}.getType());
            mostViewedArticle.setMedia(listMvam);
        } else {
            mostViewedArticle.setMedia(Collections.<MostViewedArticleMedia>emptyList());
        }

        return mostViewedArticle;
    }
}
