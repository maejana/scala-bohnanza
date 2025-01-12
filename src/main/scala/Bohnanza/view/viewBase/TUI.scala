package Bohnanza.view.viewBase

import Bohnanza.controller.{UtilityComponent, controllerBase}
import Bohnanza.controller.controllerBase.{GameUpdate, Utility, plantAmount, playerState}
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.gamedata
import Bohnanza.{controller, model}
import Bohnanza.controller.controllerBase.UndoCommand
import Bohnanza.controller.UtilityComponent
import Bohnanza.controller.plantAmountComponent
import Bohnanza.controller.playerStateComponent
import Bohnanza.controller.UndoCommandComponent

object TUI {
  def displayMenu(): Unit = {
    println(gamedata.Undo)
    println(modelBase.gamedata.Redo)
    println(modelBase.gamedata.Exit)
  }
  def setUpTUI(): Unit = {
    val utility = Utility()
    val PlantAmount: plantAmountComponent = plantAmount(utility)
    val PlayerState: playerStateComponent = playerState()
    val undoCommand: UndoCommandComponent = UndoCommand(utility, PlantAmount, PlayerState)

    println(GameUpdate(utility, PlantAmount, PlayerState, undoCommand).gameSetup())
    println(controllerBase.GameUpdate(utility, PlantAmount, PlayerState, undoCommand).gameStart())
  }
  def startGame(): Unit ={
    val utility = Utility()
    val PlantAmount: plantAmountComponent = plantAmount(utility)
    val PlayerState: playerStateComponent = playerState()
    val undoCommand: UndoCommandComponent = UndoCommand(utility, PlantAmount, PlayerState)
    println(controllerBase.GameUpdate(utility, PlantAmount, PlayerState, undoCommand).gameUpdate())
  }
}