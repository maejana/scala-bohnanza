package Bohnanza.view

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.nio.file.Paths

object GUI {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[ScalaFXApp], args: _*)
  }
}

class ScalaFXApp extends Application {
  override def start(primaryStage: Stage): Unit = {
    // Lade die FXML-Datei
    val fxmlPath = "C:/Users/hoppe/Documents/GitHub/scala-bohnanza/GUItemplate.fxml"
    val fxmlLoader = new FXMLLoader(Paths.get(fxmlPath).toUri.toURL)

    // Root-Node aus der FXML laden
    val root: Pane = fxmlLoader.load()

    // Scene und Stage konfigurieren
    val scene = new Scene(root)
    primaryStage.setScene(scene)
    primaryStage.setTitle("Bohnanza")
    primaryStage.show()
  }
}
