package ru.spbu.apmath.st033672;

import java.io.Serializable;

public class WordFrequency implements Serializable{

    private String word;
    private Integer frequency;

    public WordFrequency(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }

    public String toString(){
        return word + ": " + frequency;
    }

}
