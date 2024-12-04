package Bohnanza.view

import scalafx.application.JFXApp3
import scala.scene.Scene
import scala.scene.paint.Color._

object GUI extends JFXApp3{
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage{
      width = 600
      heigth = 600
      scene = new Scene{
        fill = White
      }
    }
  }
}
