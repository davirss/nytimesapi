package com.drss.nytimesarticles.util;

import android.media.midi.MidiOutputPort;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.drss.nytimesarticles.model.article.Article;
import com.drss.nytimesarticles.model.DisplayArticle;
import com.drss.nytimesarticles.model.article.Multimedia;
import com.drss.nytimesarticles.model.mostviewedarticle.MediaMetadata;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticle;
import com.drss.nytimesarticles.model.mostviewedarticle.MostViewedArticleMedia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davir on 15/03/2018.
 */

public class DisplayArticleUtil {


    public static final String CDN_HOST = "https://static01.nyt.com/";
    private static final String ARTICLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String ARTICLE_DISPLAY_DATE_FORMAT = "dd/MM/yyyy";

    private static final String IMAGE_IDENTIFIER = "image";
    private static final String MV_THUMBNAIL_IDENTIFIER = "Standard Thumbnail";
    private static final String Q_THUMBNAIL_IDENTIFIER = "thumbnail";

    public static DisplayArticle fromMostViewedArticle(MostViewedArticle mostViewedArticle){
        DisplayArticle displayArticle = new DisplayArticle();
        displayArticle.setArticleAbstract(mostViewedArticle.getAbstract());
        displayArticle.setPublishDate(formatArticleDate(mostViewedArticle.getPublishedDate()));
        displayArticle.setTitle(mostViewedArticle.getTitle());
        if(mostViewedArticle.getMedia().size() > 0){
            for(MostViewedArticleMedia mostViewedArticleMedia : mostViewedArticle.getMedia()){
                if(IMAGE_IDENTIFIER.equals(mostViewedArticleMedia.getType())){
                    for(MediaMetadata mediaMetadata : mostViewedArticleMedia.getMediaMetadata()){
                        if(MV_THUMBNAIL_IDENTIFIER.equals(mediaMetadata.getFormat())){
                           displayArticle.setThumbnailUrl(mediaMetadata.getUrl());
                            break;
                        }
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
        displayArticle.setPublishDate(formatArticleDate(article.getPubDate()));

        for(Multimedia multimedia: article.getMultimedia()){
            if(Q_THUMBNAIL_IDENTIFIER.equals(multimedia.getSubtype())){
                displayArticle.setThumbnailUrl(CDN_HOST.concat(multimedia.getUrl()));
                break;
            }
        }

        return displayArticle;
    }

    public static String formatArticleDate(String dateString){
        try {
            SimpleDateFormat fromDate = new SimpleDateFormat(ARTICLE_DATE_FORMAT);
            SimpleDateFormat toDate = new SimpleDateFormat(ARTICLE_DISPLAY_DATE_FORMAT);
            Date date = fromDate.parse(dateString);
            return toDate.format(date);
        } catch (Exception e) {
            Log.e("DATE", e.getMessage());
            return dateString;
        }
    }
}
