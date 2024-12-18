package Bohnanza.view

import Bohnanza.model.player
import Bohnanza.model.card
import Bohnanza.controller.UndoCommand
import Bohnanza.controller.UndoCommand.PlantBeanCommand
import Bohnanza.model
import Bohnanza.view.playerInput.keyListener

object handleInput {
  def handleInputF(player: Option[player]): Unit = {
    TUI.displayMenu()
    val input = scala.io.StdIn.readLine().strip()
    input match {
      case "0" => keyListener()
      case "1" => keyListener()
      case "2" => keyListener()
      case "U" =>
        // Undo the last action
        PlantBeanCommand.undoStep(player)
      case "R" =>
        // Redo the last undone action
        PlantBeanCommand.redoStep(player)
      case "E" =>
        // Exit the application
        println(model.gamedata.exiting)
        System.exit(0)
      case "M" =>
        // Display the menu
        TUI.displayMenu()
        keyListener()
      case _ =>
        println(model.gamedata.keineKorrekteNR)
        keyListener()
    }
  }
}
