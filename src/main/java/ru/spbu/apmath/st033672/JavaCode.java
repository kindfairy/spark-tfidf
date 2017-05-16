package ru.spbu.apmath.st033672;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 5/16/17.
 */
public class JavaCode {

    public static List<Article> jsonsFromViewResponse(String json){

        Gson gson = new Gson();

        ViewResponse viewResponse = gson.fromJson(json, ViewResponse.class);

        List<KeyValuePair<Article, Article>> keyValuePairs = viewResponse.getKeyValuePairs(Article.class, Article.class);
        List<Article> articles = new ArrayList<>();
        for( KeyValuePair<Article, Article> keyValuePair : keyValuePairs ){
            articles.add(keyValuePair.getValue());
        }

        return articles;
    }


}
