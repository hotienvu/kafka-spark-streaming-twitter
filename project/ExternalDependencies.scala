import sbt._

object ExternalDependencies {
  val sparkVersion = "1.3.1"
  val spark = "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
  val sparkStreaming = "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided"
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
  val sparkStreamingKafka = "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion

  val kafkaVersion = "0.8.2.1"
  val kafka = "org.apache.kafka" %% "kafka" % kafkaVersion

  val twitter4jVersion = "4.0.3"
  val twitter4j = "org.twitter4j" % "twitter4j-stream" % twitter4jVersion

  val joda = "joda-time" % "joda-time" % "2.6"

  val avroVersion = "1.7.4"
  val avro = "org.apache.avro" % "avro" % avroVersion

  val parquetVersion = "1.6.0rc7"
  val parquet = "com.twitter" % "parquet-avro" % parquetVersion
  val parquetAvro = "com.twitter" % "parquet-avro" % parquetVersion
}
