ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "Bohnanza"
  )

libraryDependencies ++= Seq(
  "org.mockito" % "mockito-core" % "5.4.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.16" % Test
)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"

libraryDependencies += "org.jline" % "jline" % "3.21.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
// ScalaFX Abhängigkeit
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"


// Optionally, specify ScalaTest or another test framework for better integration
coverageEnabled := true
//fork:=true