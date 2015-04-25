package com.htvu.producer

import java.util.Properties

import twitter4j.conf.ConfigurationBuilder

object TwitterProducerConfig {
  val props = new Properties()
  props.load(ClassLoader.getSystemResourceAsStream("producer.properties"))

  val KAFKA_TOPIC = props.getProperty("producer.topic")

  val producerProps = new Properties()
  List(
    "serializer.class",
    "metadata.broker.list",
    "request.required.acks"
  ) foreach(s => producerProps.put(s, props.get(s)))


  val cb = new ConfigurationBuilder()
  cb.setOAuthConsumerKey(props.getProperty("consumerKey"))
  cb.setOAuthConsumerSecret(props.getProperty("consumerSecret"))
  cb.setOAuthAccessToken(props.getProperty("accessToken"))
  cb.setOAuthAccessTokenSecret(props.getProperty("accessTokenSecret"))
  cb.setJSONStoreEnabled(true)
  cb.setIncludeEntitiesEnabled(true)

  val twitterStreamingConf = cb.build()
}
