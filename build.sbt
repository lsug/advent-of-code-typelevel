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
    libs
      .add(name = "monocle", version = "1.6.0") //override versions
      .dependencies(
        "cats-core",
        "algebra",
        "fs2-core",
        "monocle-core",
        "kittens",
        "newtype"
      ),
    libs.testDependencies("scalatest"),
    addCompilerPlugins(libs, "kind-projector"),
    scalastyleFailOnWarning := true,
    scalastyleFailOnError := true
  )
