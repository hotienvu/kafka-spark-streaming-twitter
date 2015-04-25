import sbt._

object RepositoryResolvers {
  val projectRootDir = "/"
  val allResolvers = Seq(
    DefaultMavenRepository,
    JavaNet1Repository,
    Classpaths.typesafeReleases,
    "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype OSS Repo" at "http://oss.sonatype.org/content/repositories/releases",
    "Sonatype OSS Repo (Snapshots)" at "http://oss.sonatype.org/content/repositories/snapshots",
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Maven Central" at "http://central.maven.org",
    "spray repo" at "http://repo.spray.io",
    "sbt-plugin-releases" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"
  )
}