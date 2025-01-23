 package Bohnanza.controller.controllerBase

 import Bohnanza.controller.{UndoCommandComponent, UtilityComponent, plantAmountComponent, playerStateComponent}
 import Bohnanza.controller.controllerBase.{plantAmount, playerState}
 import Bohnanza.model.modelBase.{card, dynamicGamedata, fieldBuilder, gamedata, player}
 import Bohnanza.{controller, model}
 import Bohnanza.model.modelBase

 import scala.collection.mutable
 import scala.collection.mutable.Stack



class UndoCommand(utility: UtilityComponent, plantAmount: plantAmountComponent, playerState: playerStateComponent) extends UndoCommandComponent {


  trait Command {
    def doStep(player: Option[player]): Unit

    def undoStep(player: Option[player]): Unit

    def redoStep(player: Option[player]): Unit

  }

  object PlantBeanCommand extends Command, UndoCommandComponent {
    private val stateStack: mutable.Stack[player] = mutable.Stack()
    private val redoStack: mutable.Stack[player] = mutable.Stack()

    def getStateStack: mutable.Stack[player] = stateStack
    def getRedoStack: mutable.Stack[player] = redoStack

    override def doStep(player: Option[player]): Unit = {
      stateStack.push(player.get.copyState()) // Capture the entire state
    }

    override def undoStep(player: Option[player]): Unit = {
      if (!stateStack.isEmpty) {
        redoStack.push(stateStack.pop())
        player.get.restore(stateStack.pop()) // Restore the entire state
      }
      println(fieldBuilder(utility).buildGrowingFieldStr(player))
      println(gamedata.undoSuccessful)
      matchState()
    }

    override def matchState(): Unit = {
      dynamicGamedata.playingPlayer.get.lastMethodUsed match {
        case "plant1or2" => println(gamedata.plantAmountQuestion)
          println(utility.playerHandToString(dynamicGamedata.playingPlayer.get.playerHand))
          dynamicGamedata.plantCount = utility.plant1or2(dynamicGamedata.playingPlayer)
          utility.plantAllSelectedCards(dynamicGamedata.plantCount)
          PlantBeanCommand.doStep(dynamicGamedata.playingPlayer)
        case "handle" => dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
          println(gamedata.drawCardText)
          plantAmount.selectStrategy().execute(dynamicGamedata.drawnCards, dynamicGamedata.playingPlayer)
          println(fieldBuilder(utility).buildGrowingFieldStr(dynamicGamedata.playingPlayer))
          playerState.handle(dynamicGamedata.playingPlayer)
      }
    }


    override def redoStep(player: Option[player]): Unit = {
      if (redoStack.nonEmpty) {
        stateStack.push(player.get.copyState())
        player.get.restore(redoStack.pop())
      }
      println(fieldBuilder(utility).buildGrowingFieldStr(player))
      println(gamedata.redoSuccessful)
      matchState()
    }

  }

  override def doStep(player: Option[player]): Unit = {

  }

  override def matchState(): Unit = {


  }

  override def redoStep(player: Option[player]): Unit = {
    
  }

  override def undoStep(player: Option[player]): Unit = {
    
  }
}