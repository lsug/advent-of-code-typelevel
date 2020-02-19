import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "Advent Of Code",
    libraryDependencies ++= Seq(
      cats,
      catsEffect,
      fs2,
      monocleCore,
      monocleMacro,
      scalaTest % Test,
    )
  )
