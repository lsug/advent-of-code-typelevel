import sbt._

object Dependencies {
  lazy val cats = "org.typelevel" %% "cats-core" % "2.1.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.1"
  lazy val fs2 = "co.fs2" %% "fs2-core" % "2.2.2"
  lazy val monocleCore =  "com.github.julien-truffaut" %%  "monocle-core"  % "2.0.0"
  lazy val monocleMacro =  "com.github.julien-truffaut" %%  "monocle-macro"  % "2.0.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
}
