package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase
import Bohnanza.controller.controllerBase.GameUpdate
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.gamedata
import Bohnanza.{controller, model}

object TUI {
  def displayMenu(): Unit = {
    println(gamedata.Undo)
    println(modelBase.gamedata.Redo)
    println(modelBase.gamedata.Exit)
  }
  def setUpTUI(): Unit = {
    println(GameUpdate.gameSetup())
    println(controllerBase.GameUpdate.gameStart())
  }
  def startGame(): Unit ={
    println(controllerBase.GameUpdate.gameUpdate())
  }
}