package ru.spbu.apmath.st033672


import collection.JavaConverters._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import SparkContext._
import com.google.gson._
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Main {

  def main(args: Array[String]) {

    val appName = "SparkTFIDF"
    val jars = List(SparkContext.jarOfObject(this).get)
    println(jars)
    val conf = new SparkConf().setAppName(appName).setJars(jars)


    val sc = new SparkContext(conf)

    //wordcount to couchdb
    //run(args, sc)


    run1(args, sc)

  }


  def run(args: Array[String], sc: SparkContext) {


    if (args.length < 2) {
      println(args.mkString(","))
      println("ERROR. Please, specify input and output directories.")
      return
    }


    val inputDir = args(0)
    val outputDir = args(1)
    println("Input directory: " + inputDir)
    println("Output directory: " + outputDir)


    var gson = new Gson()

    val ip = "217.197.2.6";
    val port = "5984";
    val dbName = "testdb";
    val userName = "admin";
    val userPassword = "admin";

    var writer = new CouchdbConnector(ip, port, dbName, userName, userPassword)

    val counts: java.util.List[WordFrequency] = sc
      .textFile(inputDir + "/*")
      .flatMap(line => line.split("\\W+"))
      .filter(word => !word.isEmpty())
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .map(it => new WordFrequency(it._1, it._2))
      .collect()
      .toSeq
      .asJava

    writer.writeBulk(counts);


    //
    // .map( wf => {
    // 	val gson = new Gson()
    // 	gson.toJson(wf)
    // } )
    // 	//val str = wf.toString() + "+"
    // 	//str
    // .saveAsTextFile(outputDir)

  }


  def run1(args: Array[String], sc: SparkContext) {

    if (args.length < 2) {
      println(args.mkString(","))
      println("ERROR. Please, specify input and output directories.")
      return
    }

    val inputDir = args(0)
    val outputDir = args(1)
    println("Input directory: " + inputDir)
    println("Output directory: " + outputDir)

    val docNum = sc.broadcast(18828)

    val rdd = sc
      .wholeTextFiles(inputDir)
      // (docName, Json)
      .flatMap((tuple) => {
          JavaCode.jsonsFromViewResponse(tuple._2).asScala
      })
      // (Article) -> [(word, (docName, tf))]
      .flatMap((article) => {
        val map = scala.collection.mutable.Map[String, Double]()
        var total = 0.0
        article.getText.split("[\\W_]+")
          .foreach((token) => {
            var word = token
            if( word.length > 2 ){
              word = word.toLowerCase()
              map += word -> (map.getOrElse(word, 0.0) + 1.0)
              total += 1.0
            }
          })
        var arr = new ArrayBuffer[(String, (String, Double))]()
        map.foreach(i => {
          arr += ((i._1, (article.getDocName, i._2 / total)))
        })
        arr
      })
      //(word, (docName, tf))
      .groupByKey()
      // (word, (docName, tf)) -> (word, [ (docName, tf) ])
      .flatMap(tuple => {
        for( i <- tuple._2 ) yield (i._1,(tuple._1, i._2.toDouble * docNum.value.toDouble/tuple._2.size.toDouble))
      })
      //(word, [ (docName, tf) ]) -> [ (docName,(word, tfidf)) ]
      .groupByKey()
      //(docName,(word, tfidf)) -> ( docName, [ word, tfidf ] )
      .map(tuple => {
        (tuple._1, tuple._2.mkString("[", ",", "]"))
      })
      .saveAsTextFile(outputDir)



    //
    // .map( wf => {
    // 	val gson = new Gson()
    // 	gson.toJson(wf)
    // } )
    // 	//val str = wf.toString() + "+"
    // 	//str
    // .saveAsTextFile(outputDir)

  }


}
