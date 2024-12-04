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
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
libraryDependencies += "org.jline" % "jline" % "3.21.0"

libraryDependencies += "org.scalafx" %% "scalafx" % "23.0.1-R34"
// In build.sbt
libraryDependencies ++= Seq(
  "org.openjfx" % "javafx-controls" % "19",
  "org.openjfx" % "javafx-fxml" % "19"
)



// Enable the JavaFX modules required for your project
// You can modify these based on which parts of JavaFX you are using
javaOptions ++= Seq(
  "--module-path",
  "C:/User/hoppe/Documents/GitHub/scala-bohnanza/javafx-sdk-23.0.1/lib",
  "--add-modules",
  "javafx.controls,javafx.fxml"
)


// Optionally, specify ScalaTest or another test framework for better integration
coverageEnabled := true