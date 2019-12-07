import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.example",
  organizationName := "yoshiyu",
  scalaVersion := "2.12.8",
  test in assembly := {}
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "spark-sample",
    assemblyOutputPath in assembly := file(s"out/${name.value}-${version.value}.jar"),
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.apache.spark" %% "spark-core" % "2.4.4" % "provided",
      "org.apache.spark" %% "spark-sql" % "2.4.4" % "provided",
      "org.apache.spark" %% "spark-hive" % "2.4.4" % "provided"
    )
  )
