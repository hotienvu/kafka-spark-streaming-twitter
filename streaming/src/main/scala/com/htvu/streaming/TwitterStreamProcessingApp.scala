package com.htvu.streaming

import com.htvu.streaming.TwitterStreamProcessingConf._
import com.htvu.streaming.tweet.{SerializableTweet, Tweet}
import kafka.serializer.StringDecoder
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import parquet.avro.{AvroParquetOutputFormat, AvroWriteSupport}
import parquet.hadoop.ParquetOutputFormat
import twitter4j.TwitterObjectFactory


object TwitterStreamProcessingApp {

  def main (args: Array[String]) {
    val conf = new SparkConf().setMaster(SPARK_MASTER).setAppName(APPLICATION_NAME)
    val ssc = new StreamingContext(conf, Seconds(1))

    val kafkaParams = Map("metadata.broker.list" -> KAFKA_BROKERS)
    val directKafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, KAFKA_TOPICS)

    val rawTweets = directKafkaStream.map(_._2)

    rawTweets.window(Seconds(10), Seconds(10)).saveAsTextFiles(s"$HDFS_ROOT/user/$HDFS_USER/tweets/raw/")

    val tweets = rawTweets.map(TwitterObjectFactory.createStatus).map(SerializableTweet(_))


    val job = new Job()
    ParquetOutputFormat.setWriteSupportClass(job, classOf[AvroWriteSupport])
    AvroParquetOutputFormat.setSchema(job, Tweet.SCHEMA$)

    val outputDir = s"$HDFS_ROOT/user/$HDFS_USER/tweets/parquet/"
    tweets.map((null, _)).window(Seconds(10), Seconds(10)).foreachRDD((rdd, ts) => {
      val ms = ts.milliseconds
      rdd.saveAsNewAPIHadoopFile(outputDir + s"ts=$ms/", classOf[Void], classOf[Tweet], classOf[ParquetOutputFormat[Tweet]], job.getConfiguration)
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
