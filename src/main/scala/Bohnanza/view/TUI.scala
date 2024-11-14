package Bohnanza.view

import Bohnanza.{controller, model}

object TUI {
  def setUpTUI(): Unit = {
    println(model.gamedata.welcome)
    println(model.gamedata.playerCountQuestion)
    println(model.gameDataFunc.initGame)
  }
}