package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase.UndoCommand
import Bohnanza.model
import Bohnanza.model.modelBase.{card, gamedata, player}
import Bohnanza.model.modelBase
import playerInput.keyListener
import Bohnanza.controller.UtilityComponent
import Bohnanza.controller.plantAmountComponent
import Bohnanza.controller.playerStateComponent
import Bohnanza.controller.UndoCommandComponent
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.controller.controllerBase.{playerState, plantAmount}

object handleInput {
  def handleInputF(player: Option[player]): Unit = {
    val utility = Utility()
    val PlantAmount: plantAmountComponent = plantAmount(utility)
    val PlayerState: playerStateComponent = playerState()
    TUI.displayMenu()
    val input = scala.io.StdIn.readLine().strip()
    input match {
      case "0" => keyListener()
      case "1" => keyListener()
      case "2" => keyListener()
      case "U" =>
        // Undo the last action
        UndoCommand(utility, PlantAmount, PlayerState).PlantBeanCommand.undoStep(player)
      case "R" =>
        // Redo the last undone action
        UndoCommand(utility, PlantAmount, PlayerState).PlantBeanCommand.redoStep(player)
      case "E" =>
        // Exit the application
        println(gamedata.exiting)
        System.exit(0)
      case "M" =>
        // Display the menu
        TUI.displayMenu()
        keyListener()
      case _ =>
        println(modelBase.gamedata.keineKorrekteNR)
        keyListener()
    }
  }
}
