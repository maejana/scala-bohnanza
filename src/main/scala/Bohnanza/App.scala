package Bohnanza

import Bohnanza.view.viewBase
import Bohnanza.view.viewBase.FXGUi.start
import Bohnanza.view.viewBase.{FXGUi, TUI}
import scalafx.application.Platform

object App {
  @main def run: Unit = {
    new Thread(() => {
      FXGUi.main(Array.empty)
    }).start()
    TUI.setUpTUI()
    viewBase.TUI.startGame()
  }

}