name := """Sellbook"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.webjars" % "bootstrap" % "2.3.1",
  "org.hibernate" % "hibernate-entitymanager" % "5.2.3.Final",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4"
)

// https://mvnrepository.com/artifact/org.mindrot/jbcrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"