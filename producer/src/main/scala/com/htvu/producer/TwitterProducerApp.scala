package com.htvu.producer

object TwitterProducerApp {

  def main (args: Array[String]) {
    val twitterProducer = new TwitterProducer with StringKafkaProducer
    twitterProducer.start()
  }
}
