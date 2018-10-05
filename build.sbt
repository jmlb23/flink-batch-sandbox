import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    Seq(name := "flink-batch",
  version := "0.1",
  scalaVersion := "2.11.8")
  ),
  libraryDependencies ++= flinkBatch
)
