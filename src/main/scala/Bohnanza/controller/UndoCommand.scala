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

  object PlantBeanCommand extends Command {
    private val stateStack: mutable.Stack[model.player] = mutable.Stack()
    private val redoStack: mutable.Stack[model.player] = mutable.Stack()

    override def doStep(player: player): Unit = {
      stateStack.push(player.copyState()) // Capture the entire state
    }

    override def undoStep(player: player): Unit = {
      if (!stateStack.isEmpty) {
        redoStack.push(stateStack.pop())
        player.restore(stateStack.pop()) // Restore the entire state
      }
      println(model.fieldBuilder.buildGrowingFieldStr(player))
      println(model.gamedata.undoSuccessful)
      matchState()
    }

    def matchState(): Unit = {
      model.gamedata.playingPlayer.lastMethodUsed match {
        case "plant1or2" => println(model.gamedata.plantAmountQuestion)
          println(model.gameDataFunc.playerHandToString(model.gamedata.playingPlayer.playerHand))
          model.gamedata.plantCount = Utility.plant1or2(model.gamedata.playingPlayer)
          Utility.plantAllSelectedCards(model.gamedata.plantCount)
          UndoCommand.PlantBeanCommand.doStep(model.gamedata.playingPlayer)
        case "handle" => model.gamedata.drawnCards.foreach(card => println(card.beanName))
          println(model.gamedata.drawCardText)
          plantAmount.selectStrategy().execute(model.gamedata.drawnCards, model.gamedata.playingPlayer)
          println(model.fieldBuilder.buildGrowingFieldStr(model.gamedata.playingPlayer))
          playerState.handle(model.gamedata.playingPlayer)
      }
    }


      override def redoStep(player: player): Unit = {
        if (redoStack.nonEmpty) {
          stateStack.push(player.copyState())
          player.restore(redoStack.pop())
        }
        println(model.gamedata.redoSuccessful)
      }
    }
  }


// TODO: Reparieren, dann so dass man nur M als Buchstabe eingeben kann und sonst Fehler angezeigt wird



