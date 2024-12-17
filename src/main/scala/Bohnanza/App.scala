package Bohnanza

import Bohnanza.view.FXGUi
import scalafx.application.Platform

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    view.TUI.startGame()
  }
   Platform.runLater {
     FXGUi.start()
   }
}