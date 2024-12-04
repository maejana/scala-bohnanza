package Bohnanza.view

import Bohnanza.{controller, model}

object TUI {
  def displayMenu(): Unit = {
    println(model.gamedata.Undo)
    println(model.gamedata.Redo)
    println(model.gamedata.Exit)
  }
  def setUpTUI(): Unit = {
    println(controller.GameUpdate.gameSetup())
    println(controller.GameUpdate.gameStart())
  }
  def startGame(): Unit ={
    println(controller.GameUpdate.gameUpdate())
  }
}