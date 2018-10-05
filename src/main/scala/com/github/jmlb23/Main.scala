package com.github.jmlb23

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.scala._




object Main{

  def getStringFilePathOnResouces(str: String):String = Thread.currentThread().getContextClassLoader.getResource(str).getFile


  case class Movie(id: Int, name: String, generes: String)
  case class Ratings(userId: Int, movieId: Int, rate: Int, timestamp: String)
  case class MovieC( movieId: Int, count: Long = 1)

  def main(args: Array[String]): Unit = {



    val bEnv = ExecutionEnvironment.getExecutionEnvironment


    val movieData = bEnv.readCsvFile[Movie](getStringFilePathOnResouces("movies.dat"),fieldDelimiter = "::")
    val ratings = bEnv.readCsvFile[Ratings](getStringFilePathOnResouces("ratings.dat"),fieldDelimiter = "::")

    val countRatingsForeachMovie = ratings.map(x => MovieC(x.movieId)).groupBy(0).aggregate(Aggregations.SUM,1)
    val sumOfRatingsForeachMovie = ratings.map(x => MovieC(x.movieId,x.rate)).groupBy(0).aggregate(Aggregations.SUM,1)


    val mean = countRatingsForeachMovie
      .join(sumOfRatingsForeachMovie).where(0).equalTo(0).map(x => (x._1.movieId, x._2.count.toDouble / x._1.count.toDouble))


    val meanWithName = mean.join(movieData).where(0).equalTo(0).map(x => (x._2.id,x._2.name,x._2.generes,x._1._2))

    meanWithName.writeAsCsv(args(0),fieldDelimiter = "::")

    bEnv.execute()
  }
}
