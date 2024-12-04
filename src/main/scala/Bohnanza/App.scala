package Bohnanza

import Bohnanza.model.ObserverData
import Bohnanza.view.GUI
import javafx.application.Application
import scalafx.application.JFXApp3

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    view.TUI.startGame()
  }
}