package Bohnanza.view

import Bohnanza.model.player
import Bohnanza.model.card
import Bohnanza.controller.UndoCommand
import Bohnanza.controller.UndoCommand.PlantBeanCommand
import Bohnanza.model
import Bohnanza.view.playerInput.keyListener

object handleInput {
  def handleInputF(player: player): Unit = {
    TUI.displayMenu()
    val input = scala.io.StdIn.readLine().strip()
    input match {
      case "0" => keyListener()
      case "1" => keyListener()
      case "2" => keyListener()
      case "U" =>
        // Undo the last action
        PlantBeanCommand(player).undoStep(player)
        println("Undo successful.")
      case "R" =>
        // Redo the last undone action
        PlantBeanCommand(player).redoStep(player)
        println("Redo successful.")
      case "E" =>
        // Exit the application
        println("Exiting...")
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
