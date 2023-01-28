name := "exercise 1 lastminute"

version := "0.1"

scalaVersion := "2.12.15"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"        % "4.0.+",
  "ch.qos.logback"  %  "logback-classic"    % "1.2.+",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "1.7.5",
  "org.xerial" % "sqlite-jdbc" % "3.40.0.0",
  "com.h2database"  %  "h2"                 % "1.4.+",
  
  "mysql"           %  "mysql-connector-java" % "5.1.46",
  "org.scalikejdbc" %% "scalikejdbc-config"   % "3.1.0"
)




libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.3"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % Test