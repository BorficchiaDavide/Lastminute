name := "exercise 1 lastminute"

version := "0.1"

scalaVersion := "2.13.10"

val scalaLikeJDBCVersion = "3.3.5"
val sparkVersion = "3.2.3"
val scalatestVersion = "3.2.3"
libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalaLikeJDBCVersion,
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % scalaLikeJDBCVersion,
  "org.scalikejdbc" %% "scalikejdbc-config" % scalaLikeJDBCVersion,
  "org.xerial" % "sqlite-jdbc" % "3.40.0.0",
  "ch.qos.logback" % "logback-classic" % "1.2.+",
  "com.h2database" % "h2" % "1.4.+",
  "mysql" % "mysql-connector-java" % "5.1.46",
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.scalactic" %% "scalactic" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test,
  "com.github.tototoshi" %% "scala-csv" % "1.3.10"
)
