package Bohnanza

import Bohnanza.view.viewBase
import Bohnanza.view.viewBase.FXGUi.start
import Bohnanza.view.viewBase.{FXGUi, TUI}
import scalafx.application.Platform
import Bohnanza.controller.controllerBase.*

object App {
  def initClasses(): Unit = {
    Utility()
  }
  
  @main def run: Unit = {
    new Thread(() => {
      FXGUi.main(Array.empty)
    }).start()
    TUI.setUpTUI()
    viewBase.TUI.startGame()
  }

}