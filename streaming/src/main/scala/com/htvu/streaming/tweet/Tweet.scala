package com.htvu.streaming.tweet

import java.io._

import org.apache.avro.io.{DatumWriter, _}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}

object SerializableUser {
  def apply(user: twitter4j.User): SerializableUser =
    new SerializableUser(new User(
      new java.lang.Long(user.getId),
      user.getScreenName,
      user.getLang,
      new java.lang.Integer(user.getStatusesCount)))
}

case class SerializableUser() extends User with Serializable {
  private def setValues (user: User) {
    setId(user.getId)
    setLang(user.getLang)
    setScreenName(user.getScreenName)
    setStatusesCount(user.getStatusesCount)
  }

  def this (user: User) {
    this()
    setValues(user)
  }

  @throws (classOf[IOException] )
  private def writeObject (out: ObjectOutputStream) {
    val writer: DatumWriter[User] = new SpecificDatumWriter[User] (classOf[User] )
    val encoder: Encoder = EncoderFactory.get.binaryEncoder (out, null)
    writer.write (this, encoder)
    encoder.flush()
  }

  @throws (classOf[IOException] )
  @throws (classOf[ClassNotFoundException] )
  private def readObject (in: ObjectInputStream) {
    val reader: DatumReader[User] = new SpecificDatumReader[User] (classOf[User] )
    val decoder: Decoder = DecoderFactory.get.binaryDecoder (in, null)
    setValues (reader.read (null, decoder) )
  }

  @throws (classOf[ObjectStreamException] )
  private def readObjectNoData() {
  }
}

object SerializableTweet {
  def apply(status: twitter4j.Status): SerializableTweet =
    new SerializableTweet(new Tweet(
      new java.lang.Long(status.getId),
      status.getText,
      status.getCreatedAt.toString,
      SerializableUser(status.getUser)))
}

case class SerializableTweet() extends Tweet with Serializable {
  private def setValues (tweet: Tweet) {
    setId (tweet.getId)
    setText (tweet.getText)
    setUser (tweet.getUser)
    setCreatedAt (tweet.getCreatedAt)
  }

  def this (tweet: Tweet) {
    this ()
    setValues (tweet)
  }

  @throws (classOf[IOException] )
  private def writeObject (out: ObjectOutputStream) {
    val writer: DatumWriter[Tweet] = new SpecificDatumWriter[Tweet] (classOf[Tweet] )
    val encoder: Encoder = EncoderFactory.get.binaryEncoder (out, null)
    writer.write (this, encoder)
    encoder.flush()
  }

  @throws (classOf[IOException] )
  @throws (classOf[ClassNotFoundException] )
  private def readObject (in: ObjectInputStream) {
    val reader: DatumReader[Tweet] = new SpecificDatumReader[Tweet] (classOf[Tweet] )
    val decoder: Decoder = DecoderFactory.get.binaryDecoder (in, null)
    setValues (reader.read (null, decoder) )
  }

  @throws (classOf[ObjectStreamException] )
  private def readObjectNoData() {
  }
}
