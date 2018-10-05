import sbt._

object Dependencies {
  lazy val flinkBatch =
    "org.apache.flink" %% "flink-scala" % "1.6.1" :: "org.apache.flink" %% "flink-clients" % "1.6.1" :: Nil
}
