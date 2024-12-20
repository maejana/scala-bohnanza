 package Bohnanza.controller.controllerBase

 import Bohnanza.controller.controllerBase.{plantAmount, playerState}
 import Bohnanza.model.modelBase.{card, dynamicGamedata, fieldBuilder, gameDataFunc, gamedata, player}
 import Bohnanza.{controller, model}
 import Bohnanza.model.modelBase

 import scala.collection.mutable
 import scala.collection.mutable.Stack
object UndoCommand {
  trait Command {
    def doStep(player: Option[player]): Unit

    def undoStep(player: Option[player]): Unit

    def redoStep(player: Option[player]): Unit
  }

  object PlantBeanCommand extends Command {
    private val stateStack: mutable.Stack[player] = mutable.Stack()
    private val redoStack: mutable.Stack[player] = mutable.Stack()

    override def doStep(player: Option[player]): Unit = {
      stateStack.push(player.get.copyState()) // Capture the entire state
    }

    override def undoStep(player: Option[player]): Unit = {
      if (!stateStack.isEmpty) {
        redoStack.push(stateStack.pop())
        player.get.restore(stateStack.pop()) // Restore the entire state
      }
      println(fieldBuilder.buildGrowingFieldStr(player))
      println(gamedata.undoSuccessful)
      matchState()
    }

    def matchState(): Unit = {
      dynamicGamedata.playingPlayer.get.lastMethodUsed match {
        case "plant1or2" => println(modelBase.gamedata.plantAmountQuestion)
          println(gameDataFunc.playerHandToString(modelBase.dynamicGamedata.playingPlayer.get.playerHand))
          modelBase.dynamicGamedata.plantCount = Utility.plant1or2(modelBase.dynamicGamedata.playingPlayer)
          Utility.plantAllSelectedCards(modelBase.dynamicGamedata.plantCount)
          UndoCommand.PlantBeanCommand.doStep(modelBase.dynamicGamedata.playingPlayer)
        case "handle" => modelBase.dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
          println(modelBase.gamedata.drawCardText)
          plantAmount.selectStrategy().execute(modelBase.dynamicGamedata.drawnCards, modelBase.dynamicGamedata.playingPlayer)
          println(modelBase.fieldBuilder.buildGrowingFieldStr(modelBase.dynamicGamedata.playingPlayer))
          playerState.handle(modelBase.dynamicGamedata.playingPlayer)
      }
    }


      override def redoStep(player: Option[player]): Unit = {
        if (redoStack.nonEmpty) {
          stateStack.push(player.get.copyState())
          player.get.restore(redoStack.pop())
        }
        println(modelBase.fieldBuilder.buildGrowingFieldStr(player))
        println(modelBase.gamedata.redoSuccessful)
        matchState()
      }

    }
  }