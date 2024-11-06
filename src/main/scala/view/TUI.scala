package Test.view

import Test.{controller, model}

object TUI {
  def setUpTUI(): Unit = {
    println(model.gamedata.welcome)
    println(model.gamedata.playerCountQuestion)
    println(controller.UIlogic.initGame)
  }
}