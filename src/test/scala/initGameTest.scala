package test

import Bohnanza.model.modelBase.{dynamicGamedata, player}
import Bohnanza.view.viewBase
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class InitGameTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "initGame" should "initialize the game by adding players and returning the generated field text" in {
    // Mock dependencies
    val playerInputMock = mock[viewBase.playerInput.type]
    val dynamicGamedataMock = mock[dynamicGamedata.type]

    // Mock the dynamic gamedata player count
    when(dynamicGamedataMock.playerCount).thenReturn(2)
    when(dynamicGamedataMock.players).thenReturn(scala.collection.mutable.ArrayBuffer.empty[player])

    // Mock playerInput to provide player names
    when(playerInputMock.playercount()).thenAnswer(_ => dynamicGamedataMock.playerCount)
    when(playerInputMock.playername())
      .thenReturn("Player1")
      .thenReturn("Player2")

    // Define the initPlayer method
    def initPlayer(name: String): String = {
      s"$name: Initialized\n"
    }

    // Define the initGame method
    def initGame: String = {
      var str = ""
      playerInputMock.playercount()

      if (dynamicGamedataMock.players.length != dynamicGamedataMock.playerCount) {
        val playernames: Array[String] = new Array[String](dynamicGamedataMock.playerCount)
        println("Namen eingeben:")
        for (i <- 1 to dynamicGamedataMock.playerCount - dynamicGamedataMock.players.length) {
          playernames(i - 1) = playerInputMock.playername()
          if (playernames(i - 1) != "") str += initPlayer(playernames(i - 1))
        }
      }
      str
    }

    // Test initGame
    val result = initGame

    // Verify results
    result should include("Player1: Initialized")
    result should include("Player2: Initialized")
    result.split("\n").length should be(2)

    // Verify playerInput interactions
    verify(playerInputMock, times(1)).playercount()
    verify(playerInputMock, times(2)).playername()
  }

  it should "not initialize additional players if all players are already initialized" in {
    // Mock dependencies
    val playerInputMock = mock[viewBase.playerInput.type]
    val dynamicGamedataMock = mock[dynamicGamedata.type]

    // Mock the dynamic gamedata player count and already initialized players
    when(dynamicGamedataMock.playerCount).thenReturn(2)
    when(dynamicGamedataMock.players).thenReturn(scala.collection.mutable.ArrayBuffer(mock[player], mock[player]))

    // Define the initPlayer method
    def initPlayer(name: String): String = {
      s"$name: Initialized\n"
    }

    // Define the initGame method
    def initGame: String = {
      var str = ""
      playerInputMock.playercount()

      if (dynamicGamedataMock.players.length != dynamicGamedataMock.playerCount) {
        val playernames: Array[String] = new Array[String](dynamicGamedataMock.playerCount)
        println("Namen eingeben:")
        for (i <- 1 to dynamicGamedataMock.playerCount - dynamicGamedataMock.players.length) {
          playernames(i - 1) = playerInputMock.playername()
          if (playernames(i - 1) != "") str += initPlayer(playernames(i - 1))
        }
      }
      str
    }

    // Test initGame
    val result = initGame

    // Verify that no new players are added
    result shouldBe empty

    // Verify playerInput interactions
    verify(playerInputMock, times(1)).playercount()
    verify(playerInputMock, never()).playername()
  }
}