import sbt._
import sbt.Keys._
import ExternalDependencies._
import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.PathList
import sbtavro.SbtAvro._



object Builds extends Build {
  val mergeStrategy = assemblyMergeStrategy in assembly := {
    case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }

  private val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "com.htvu",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.7"),
    scalaVersion := "2.10.4",
    resolvers := RepositoryResolvers.allResolvers,
    libraryDependencies := Seq(joda)
  )

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = buildSettings
  ) aggregate(kafkaProducer, streaming)

  lazy val kafkaProducer = Project(
    id = "kafka-producer",
    base = file("producer"),
    settings = buildSettings ++ Seq(
      libraryDependencies += kafka,
      libraryDependencies += twitter4j
    )

  ).dependsOn(common)

  lazy val common = Project(
    id = "common",
    base = file("common"),
    settings = buildSettings ++  Seq(
    )
  )

  (stringType in avroConfig) := "String"

  lazy val streaming = Project(
    id = "spark-streaming",
    base = file("streaming"),
    settings = buildSettings ++ Seq(
      libraryDependencies += spark,
      libraryDependencies += sparkStreaming,
      libraryDependencies += sparkSql,
      libraryDependencies += sparkStreamingKafka,
      libraryDependencies += twitter4j,
      libraryDependencies += parquet,
      libraryDependencies += parquetAvro
    )
  ).settings(mergeStrategy:_*)
    .settings(sbtavro.SbtAvro.avroSettings : _*)
    .settings((sourceDirectory in avroConfig) := file("streaming/src/main/resources/avro"))
    .settings((stringType in avroConfig) := "String")
}