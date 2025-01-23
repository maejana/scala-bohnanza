import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.controller.{Strategy, UndoCommandComponent, UtilityComponent, plantAmountComponent, playerStateComponent}
import Bohnanza.model.modelBase.{FactoryP, card, dynamicGamedata, gamedata, player}
import Bohnanza.controller.controllerBase.{GameUpdate, Utility}

import scala.collection.mutable.ArrayBuffer

class GameUpdateTest extends AnyFlatSpec with Matchers {
/*
  "gameUpdate" should "run the game update process and return the log" in {
    val utility = Utility()

    val plantAmount = new plantAmountComponent {
      override def selectStrategy(): Strategy = new Strategy() {
        override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = true
      }
    }

    val playerState = new playerStateComponent {
      override def handle(player: Option[player]): Unit = {}
    }

    val undoCommand = new UndoCommandComponent {
      override def doStep(player: Option[player]): Unit = {}

      override def undoStep(player: Option[player]): Unit = {}

      override def redoStep(player: Option[player]): Unit = {}

      override def matchState(): Unit = {}
    }

    val gameUpdate = new GameUpdate(utility, plantAmount, playerState, undoCommand)

    val testPlayer = FactoryP.PlayerFactory().createPlayer("testPlayerName", ArrayBuffer(new card("Bean1", 1, Array(1))))
    dynamicGamedata.players = ArrayBuffer(testPlayer)
    dynamicGamedata.playingPlayer = Some(testPlayer)
    dynamicGamedata.playerCount = 1

    val result = gameUpdate.gameUpdate()
    result should include("testPlayerName")
    result should include("Bean1")
    result should include("Bean2")
  }
  */

  "gameSetup" should "return the setup string" in {
    val utility = Utility()

    val plantAmount = new plantAmountComponent {
      override def selectStrategy(): Strategy = new Strategy() {
        override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = true
      }
    }

    val playerState = new playerStateComponent {
      override def handle(player: Option[player]): Unit = {}
    }

    val undoCommand = new UndoCommandComponent {
      override def doStep(player: Option[player]): Unit = {}

      override def undoStep(player: Option[player]): Unit = {}

      override def redoStep(player: Option[player]): Unit = {}

      override def matchState(): Unit = {}
    }

    val gameUpdate = new GameUpdate(utility, plantAmount, playerState, undoCommand)

    val result = gameUpdate.gameSetup()
    result should include(gamedata.welcome)
    result should include(gamedata.menu)
    result should include(gamedata.playerCountQuestion)
  }
/*
  "gameStart" should "initialize the game and return the start string" in {
    val utility = Utility()

    val plantAmount = new plantAmountComponent {
      override def selectStrategy(): Strategy = new Strategy() {
        override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = true
      }
    }

    val playerState = new playerStateComponent {
      override def handle(player: Option[player]): Unit = {}
    }

    val undoCommand = new UndoCommandComponent {
      override def doStep(player: Option[player]): Unit = {}
      override def undoStep(player: Option[player]): Unit = {}
      override def redoStep(player: Option[player]): Unit = {}

      override def matchState(): Unit = {}
    }

    val gameUpdate = new GameUpdate(utility, plantAmount, playerState, undoCommand)

    val result = gameUpdate.gameStart()
    result should include("Game Initialized")
    dynamicGamedata.plant1or2 shouldEqual 0
  }

 */
}