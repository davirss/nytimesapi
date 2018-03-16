package com.drss.nytimesarticles.util;

import com.bumptech.glide.Glide;
import com.drss.nytimesarticles.model.article.Article;
import com.drss.nytimesarticles.model.DisplayArticle;
import com.drss.nytimesarticles.model.article.Multimedia;
import com.drss.nytimesarticles.model.mostviewedarticle.MediaMetadata;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleMedia;

/**
 * Created by davir on 15/03/2018.
 */

public class DisplayArticleUtil {


    public static final String CDN_HOST = "https://static01.nyt.com/";

    public static DisplayArticle fromMostViewedArticle(MostViewedArticle mostViewedArticle){
        DisplayArticle displayArticle = new DisplayArticle();
        displayArticle.setArticleAbstract(mostViewedArticle.getAbstract());
        displayArticle.setTitle(mostViewedArticle.getTitle());
        displayArticle.setPublishDate(mostViewedArticle.getPublishedDate());
        if(mostViewedArticle.getMedia().size() > 0){
            for(MostViewedArticleMedia mostViewedArticleMedia : mostViewedArticle.getMedia()){
                if("image".equals(mostViewedArticleMedia.getType())){
                    for(MediaMetadata mediaMetadata : mostViewedArticleMedia.getMediaMetadata()){
                        if("Standard Thumbnail".equals(mediaMetadata.getFormat())){
                           displayArticle.setThumbnailUrl(mediaMetadata.getUrl());
                        }
                        break;
                    }
                    break;
                }
            }
        }

        return displayArticle;
    }

    public static DisplayArticle fromArticle(Article article){
        DisplayArticle displayArticle = new DisplayArticle();
        displayArticle.setTitle(article.getHeadline());
        displayArticle.setArticleAbstract(article.getSnippet());
        displayArticle.setPublishDate(article.getPubDate());
        for(Multimedia multimedia: article.getMultimedia()){
            if("thumbnail".equals(multimedia.getSubtype())){
                displayArticle.setThumbnailUrl(CDN_HOST.concat(multimedia.getUrl()));
                break;
            }
        }

        return displayArticle;

    }
}
