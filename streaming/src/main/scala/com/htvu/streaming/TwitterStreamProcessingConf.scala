package com.htvu.streaming

import java.util.Properties

object TwitterStreamProcessingConf {
//  private val props = new Properties()
//  props.load(ClassLoader.getSystemResourceAsStream("streaming.properties"))

//  val SPARK_MASTER = props.getProperty("spark.master")
//  val APPLICATION_NAME = "TwitterStreamProcessing"
//  val KAFKA_BROKERS = props.getProperty("kafka.metadata.broker.list")
//  val KAFKA_TOPICS = props.getProperty("kafka.topics").trim().split(",").toSet
//  val HDFS_ROOT = props.getProperty("hdfsRoot")
//  val HDFS_USER = props.getProperty("hdfsUser")

  val SPARK_MASTER = "spark://c6401.ambari.apache.org:7077"
  val APPLICATION_NAME = "TwitterStreamProcessing"
  val KAFKA_BROKERS = "c6403.ambari.apache.org:6667,c6401.ambari.apache.org:6667"
  val KAFKA_TOPICS = Set("tweet")
  val HDFS_ROOT = "hdfs://c6401.ambari.apache.org:8020"
  val HDFS_USER = "spark"
}
