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
libraryDependencies ++= Seq(
  "org.junit.jupiter" % "junit-jupiter" % "5.10.1" % Test,
  "org.testfx" % "testfx-core" % "4.0.16-alpha" % Test,
  "org.testfx" % "testfx-junit5" % "4.0.16-alpha" % Test
)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"

libraryDependencies += "org.jline" % "jline" % "3.21.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
// ScalaFX Abhängigkeit
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"


libraryDependencies += "org.playframework" %% "play-json" % "3.0.4"

libraryDependencies += "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % Test




libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0"
mainClass in Compile := Some("Bohnanza.run")

// Optionally, specify ScalaTest or another test framework for better integration
coverageEnabled := true
//fork:=true