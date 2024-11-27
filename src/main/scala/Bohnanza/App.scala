package Bohnanza

import Bohnanza.model.ObserverData

object App {
  @main def run: Unit = {
    ObserverData.addObserver(model.CardObserver)
    view.TUI.setUpTUI()
    view.TUI.startGame()

  }
}