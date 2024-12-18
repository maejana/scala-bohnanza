 package Bohnanza.controller

import Bohnanza.model.{card, player}
import Bohnanza.controller
import Bohnanza.model.player
import Bohnanza.model

import scala.collection.mutable
import scala.collection.mutable.Stack
object UndoCommand {
  trait Command {
    def doStep(player: Option[player]): Unit

    def undoStep(player: Option[player]): Unit

    def redoStep(player: Option[player]): Unit
  }

  object PlantBeanCommand extends Command {
    private val stateStack: mutable.Stack[model.player] = mutable.Stack()
    private val redoStack: mutable.Stack[model.player] = mutable.Stack()

    override def doStep(player: Option[player]): Unit = {
      stateStack.push(player.get.copyState()) // Capture the entire state
    }

    override def undoStep(player: Option[player]): Unit = {
      if (!stateStack.isEmpty) {
        redoStack.push(stateStack.pop())
        player.get.restore(stateStack.pop()) // Restore the entire state
      }
      println(model.fieldBuilder.buildGrowingFieldStr(player))
      println(model.gamedata.undoSuccessful)
      matchState()
    }

    def matchState(): Unit = {
      model.dynamicGamedata.playingPlayer.get.lastMethodUsed match {
        case "plant1or2" => println(model.gamedata.plantAmountQuestion)
          println(model.gameDataFunc.playerHandToString(model.dynamicGamedata.playingPlayer.get.playerHand))
          model.dynamicGamedata.plantCount = Utility.plant1or2(model.dynamicGamedata.playingPlayer)
          Utility.plantAllSelectedCards(model.dynamicGamedata.plantCount)
          UndoCommand.PlantBeanCommand.doStep(model.dynamicGamedata.playingPlayer)
        case "handle" => model.dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
          println(model.gamedata.drawCardText)
          plantAmount.selectStrategy().execute(model.dynamicGamedata.drawnCards, model.dynamicGamedata.playingPlayer)
          println(model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer))
          playerState.handle(model.dynamicGamedata.playingPlayer)
      }
    }


      override def redoStep(player: Option[player]): Unit = {
        if (redoStack.nonEmpty) {
          stateStack.push(player.get.copyState())
          player.get.restore(redoStack.pop())
        }
        println(model.fieldBuilder.buildGrowingFieldStr(player))
        println(model.gamedata.redoSuccessful)
        matchState()
      }

    }
  }