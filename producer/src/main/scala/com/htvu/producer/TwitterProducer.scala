package com.htvu.producer

import kafka.producer.{ProducerConfig, Producer}
import twitter4j.TwitterStreamFactory
import kafka.producer.KeyedMessage
import twitter4j._

class TwitterStreamListener(twitterStream: TwitterStream) extends StatusListener  {
  this: StringKafkaProducer =>

  val LOG = Logger.getLogger(classOf[TwitterStreamListener])

  def onStallWarning(stallWarning: StallWarning): Unit = {}

  def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

  def onScrubGeo(l: Long, l1: Long): Unit = {}

  def onStatus(status: Status): Unit = {
    val msg = new KeyedMessage[String, String](TwitterProducerConfig.KAFKA_TOPIC, TwitterObjectFactory.getRawJSON(status))
    producer.send(msg)
  }

  def onTrackLimitationNotice(i: Int): Unit = {}

  def onException(e: Exception): Unit = {
    LOG.info("Shutting down Twitter sample stream...")
    twitterStream.shutdown()
  }
}

trait StringKafkaProducer {
  val producer = new Producer[String, String](new ProducerConfig(TwitterProducerConfig.producerProps))
}

class TwitterProducer {
  this: StringKafkaProducer =>

  def start() = {
    val twitterStream = new TwitterStreamFactory(TwitterProducerConfig.twitterStreamingConf).getInstance()
    val listener = new TwitterStreamListener(twitterStream) with StringKafkaProducer
    twitterStream.addListener(listener)
    twitterStream.sample()
  }
}
