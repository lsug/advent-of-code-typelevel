ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val libs = org.typelevel.libraries

lazy val root = (project in file("."))
  .settings(
    name := "Advent Of Code",
    scalacOptions ++= Seq(
      "deprecation"
    ),
    libs.dependencies("cats-core"),
    libs.testDependencies("scalatest"),
    scalastyleFailOnWarning := true,
    scalastyleFailOnError := true,
    wartremoverErrors in (Compile, compile) ++= Warts
      .allBut(Wart.Equals, Wart.TraversableOps)
  )
