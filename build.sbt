ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "Bohnanza"
  )
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
libraryDependencies += "org.jline" % "jline" % "3.21.0"

//libraryDependencies += "org.scalamock" %% "scalamock" % "5.2.0" % "test"


