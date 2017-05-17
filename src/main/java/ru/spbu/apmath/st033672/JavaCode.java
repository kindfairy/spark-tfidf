package ru.spbu.apmath.st033672;

import com.google.gson.Gson;

import java.io.IOException;
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


    public static List<Article> jsonsFromViewResponseFromCouchDB(int skip, int limit)throws IOException{

        String ip = "217.197.2.6";
        String port = "5984";
        String dbName = "articles";
        String userName = "admin";
        String userPassword = "admin";
        String ddName = "dd";
        String viewName = "alldocs";


        CouchdbConnector connector = new CouchdbConnector(ip, port, dbName, userName, userPassword);

        List<KeyValuePair<Article, Article>> keyValuePairs =
                connector.getView(Article.class, Article.class, ddName, viewName, false, skip, limit);
        List<Article> articles = new ArrayList<>();
        for( KeyValuePair<Article, Article> keyValuePair : keyValuePairs ){
            articles.add(keyValuePair.getValue());
        }

        return articles;
    }


}
