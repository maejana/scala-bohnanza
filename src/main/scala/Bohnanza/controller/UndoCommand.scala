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
      model.dynamicGamedata.playingPlayer.lastMethodUsed match {
        case "plant1or2" => println(model.gamedata.plantAmountQuestion)
          println(model.gameDataFunc.playerHandToString(model.dynamicGamedata.playingPlayer.playerHand))
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


      override def redoStep(player: player): Unit = {
        if (redoStack.nonEmpty) {
          stateStack.push(player.copyState())
          player.restore(redoStack.pop())
        }
        println(model.fieldBuilder.buildGrowingFieldStr(player))
        println(model.gamedata.redoSuccessful)
        matchState()
      }

    }
  }


// TODO: Reparieren, dann so dass man nur M als Buchstabe eingeben kann und sonst Fehler angezeigt wird



