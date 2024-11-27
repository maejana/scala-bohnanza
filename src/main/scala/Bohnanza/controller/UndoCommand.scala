/* package Bohnanza.controller

import Bohnanza.model.{card, player}
import Bohnanza.controller
import Bohnanza.model.player

object UndoCommand {
  trait Command {
    def doStep: Unit
    def undoStep: Unit
    def redoStep: Unit
  }

  class PlantBeanCommand(player: player, card: card) extends Command {
    private var previousState: player = _

    override def doStep: Unit = {
      previousState = player.copy() // Capture the entire state
      controller.Utility.isPlantable(player, card)
    }

    override def undoStep: Unit = {
      player.restore(previousState) // Restore the entire state
    }

    override def redoStep: Unit = {
      controller.Utility.isPlantable(player, card)
    }
  }
}

 */