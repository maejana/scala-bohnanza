package Bohnanza

import Bohnanza.model.ObserverData

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    view.TUI.startGame()

  }
}