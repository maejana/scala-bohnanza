 package Bohnanza.controller

import Bohnanza.model.{card, player}
import Bohnanza.controller
import Bohnanza.model.player
import Bohnanza.model

import scala.collection.mutable
import scala.collection.mutable.Stack
object UndoCommand {
  trait Command {
    def doStep(player: player): Unit

    def undoStep(player: player): Unit

    def redoStep(player: player): Unit
  }

  class PlantBeanCommand(player: player) extends Command {
    private val stateStack: mutable.Stack[player] = mutable.Stack()
    private val redoStack: mutable.Stack[player] = mutable.Stack()

    override def doStep(player: player): Unit = {
      stateStack.push(player.copyState()) // Capture the entire state


    }

    override def undoStep(player : player): Unit = { //Spieler wieder wechseln
      for(stack <- stateStack){
        println(stack)
      }
      if (!stateStack.isEmpty) {
        redoStack.push(stateStack.pop())
        player.restore(stateStack.pop()) // Restore the entire state
      }
        val growingField: String = model.gameDataFunc.buildGrowingFieldStr(player)
        print(s"$growingField")
    }

    override def redoStep(player: player): Unit = {
      if (redoStack.nonEmpty) {
        stateStack.push(player.copyState())
        player.restore(redoStack.pop())

      }
    }
  }
}

// TODO: Reparieren, dann so dass man nur M als Buchstabe eingeben kann und sonst Fehler angezeigt wird



