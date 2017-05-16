package ru.spbu.apmath.st033672;

import java.io.Serializable;

public class Article implements Serializable{

    private String _id;
    private String _rev;
    private String docName;
    private String text;

    public String getDocName() {
        return docName;
    }

    public String getText() {
        return text;
    }

    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "docName='" + docName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}