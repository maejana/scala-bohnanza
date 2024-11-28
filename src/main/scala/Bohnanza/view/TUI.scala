package Bohnanza.view

import Bohnanza.{controller, model}

object TUI {
  def displayMenu(): Unit = {
    println("U. Undo")
    println("R. Redo")
    println("E. Exit")
  }
  def setUpTUI(): Unit = {
    println(controller.GameUpdate.gameSetup())
    println(controller.GameUpdate.gameStart())
  }
  def startGame(): Unit ={
    println(controller.GameUpdate.gameUpdate())
  }
}