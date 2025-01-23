package Bohnanza.controller.controllerBase

import Bohnanza.model.modelBase.{card, dynamicGamedata, player}
import Bohnanza.controller.UtilityComponent
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.model.modelBase.FactoryP
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

import scala.collection.mutable.ArrayBuffer

class InitPlayerTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "initPlayer" should "initialize a player with the given name and create a growing field text" in {
    // Mock necessary dependencies
    val utilityMock = mock[UtilityComponent]
    val dynamicGamedataMock = mock[dynamicGamedata.type]
    val playerFactoryMock = mock[FactoryP.PlayerFactory]

    // Set up a test player name
    val testPlayerName = "TestPlayer"

    // Set up mock behavior for random card generation
    val cardMock1 = mock[card]
    val cardMock2 = mock[card]
    val cardMock3 = mock[card]
    val cardMock4 = mock[card]
    val cardMock5 = mock[card]
    when(cardMock1.beanName).thenReturn("Bean1")
    when(cardMock2.beanName).thenReturn("Bean2")
    when(cardMock3.beanName).thenReturn("Bean3")
    when(cardMock4.beanName).thenReturn("Bean4")
    when(cardMock5.beanName).thenReturn("Bean5")

    // Mock the behavior of weightedRandom
    val weightedRandomMock = () => cardMock1 // Replace with mocked card behavior
    val handBuffer: ArrayBuffer[card] = ArrayBuffer(cardMock1, cardMock2, cardMock3, cardMock4, cardMock5)

    // Mock the player factory
    val testPlayer = mock[player]
    when(testPlayer.playerName).thenReturn(testPlayerName)
    when(playerFactoryMock.createPlayer(testPlayerName, handBuffer, ArrayBuffer.empty[card], ArrayBuffer.empty[card], ArrayBuffer.empty[card], 0, null)).thenReturn(testPlayer)

    // Mock dynamic gamedata behavior
    when(dynamicGamedataMock.players).thenReturn(ArrayBuffer.empty[player])

    // Test the initPlayer method
    val initPlayerMethod = new {
      def initPlayer(name: String): String = {
        val playerName = name
        var growingFieldText: String =
          s"""
              ${playerName}:
                 Field 1:

                 Field 2:

                 Field 3:


              """
        val hand: ArrayBuffer[card] = handBuffer.clone()
        for (i <- 1 to 4) {
          hand.addOne(weightedRandomMock())
          growingFieldText += hand(i - 1).beanName + ", "
        }
        hand.addOne(weightedRandomMock())
        val newPlayer = playerFactoryMock.createPlayer(playerName, hand, ArrayBuffer.empty[card], ArrayBuffer.empty[card], ArrayBuffer.empty[card], 0, null) // Factory Pattern to create Player
        if (!dynamicGamedataMock.players.isEmpty) {
          dynamicGamedataMock.players.toList.foreach((p: player) =>
            if (!p.playerName.equals(newPlayer.playerName)) {
              dynamicGamedataMock.players += newPlayer
            })
        }
        else dynamicGamedataMock.players += newPlayer
        growingFieldText
      }
    }

    val result = Utility().initPlayer(testPlayerName)

    // Verify results
    result should include("TestPlayer:")
    result should include("Field 1:")
    result should include("Field 2:")
    result should include("Field 3:")
    result should include("Bean1")
    result should include("Bean2")
    result should include("Bean3")
    result should include("Bean4")

    // Verify interactions
    verify(playerFactoryMock, times(1)).createPlayer(testPlayerName, handBuffer, ArrayBuffer.empty[card], ArrayBuffer.empty[card], ArrayBuffer.empty[card], 0, null)
    verify(dynamicGamedataMock.players, times(1))
  }
}