package Bohnanza

import Bohnanza.view.FXGUi
import Bohnanza.view.FXGUi.start
import scalafx.application.Platform

object App {
  @main def run: Unit = {
    new Thread(() => {
      FXGUi.main(Array.empty)
    }).start()
    view.TUI.setUpTUI()
    view.TUI.startGame()
  }

}