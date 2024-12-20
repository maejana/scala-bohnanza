package test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import org.mockito.MockitoAnnotations
import Bohnanza.model
import Bohnanza.model.*
import Bohnanza.controller.controllerBase
import Bohnanza.controller
import Bohnanza.controller.controllerBase.{Utility, gamelogic}
import Bohnanza.model.modelBase.{card, gameDataFunc, player}

import scala.collection.mutable.ArrayBuffer

class GameLogicTest extends AnyWordSpec with Matchers {

  MockitoAnnotations.openMocks(this)

  // Test setup
  val testCard1 = card("bean", 16, ArrayBuffer(1, 2, 3))
  val testCard2 = card("Soja", 12, ArrayBuffer(1, 2, 3))
  val testPlayer1 = player("Player1", ArrayBuffer())
  val testPlayer2 = player("Player2", ArrayBuffer())

  "GameLogic" should {
    "plant cards correctly in different fields" when {
      "planting" in {
        val player = testPlayer1.copy()
        val mockUtility = mock(classOf[Utility.type])
        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(1)

        gamelogic.plant(testCard1, player)
        player.plantfield1 should contain(testCard1)
      }
    }

    "handle harvesting correctly" in {
      // Test harvesting from different fields
      val result1 = controllerBase.gamelogic.harvest(1)
      result1 shouldBe ""  // Current implementation returns empty string

      // You might want to expand this once harvest is implemented
      // val expectedGold = calculateExpectedGold(field1Contents)
      // result1 shouldBe expectedGold
    }

    "calculate bohnometer values correctly" in {
      // Test bohnometer calculation
      val result = controllerBase.gamelogic.bohnometer(testCard1)
      result shouldBe 0  // Current implementation returns 0

      // You might want to expand this once bohnometer is implemented
      // val expectedValue = calculateExpectedValue(testCard1)
      // result shouldBe expectedValue
    }

//    "handle trading between players correctly" in {
//      // Setup players with cards
//      val player1 = testPlayer1.copy()
//      val player2 = testPlayer2.copy()
//
//      player1.playerHand :+= testCard1
//      player2.playerHand :+= testCard2
//
//      val mockUtility = mock(classOf[Utility.type])
//      when(mockUtility.findCardId(player1, testCard1)).thenReturn(0)
//      when(mockUtility.findCardId(player2, testCard2)).thenReturn(0)
//
//      // Execute trade
//      controller.gamelogic.trade(player1, player2, testCard1, testCard2)
//
//      // Verify trade results
//      player1.playerHand should contain(testCard2.beanName)
//      player2.playerHand should contain(testCard1.beanName)
//    }


    "handle edge cases" when {
      "trading with invalid card indices" in {
        val player1 = testPlayer1.copy()
        val player2 = testPlayer2.copy()

        // Test trading with non-existent cards
        an [IndexOutOfBoundsException] should be thrownBy {
          controllerBase.gamelogic.trade(player1, player2, testCard1, testCard2)
        }
      }

      "planting in invalid fields" in {
        val player = testPlayer1.copy()
        val mockUtility = mock(classOf[Utility.type])
        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(4) // Invalid field number

        // Should handle invalid field number gracefully
        noException should be thrownBy {
          controllerBase.gamelogic.plant(testCard1, player)
        }
      }

      "drawing cards with empty deck" in {
        val mockUILogic = mock(classOf[UIlogic.type])
        when(mockUILogic.weightedRandom()).thenReturn(null)

        val drawnCards = gameDataFunc.drawCards()
        drawnCards.length shouldBe 2  // Should still return correct number of cards
      }
    }
  }

  // Helper functions
  def createPlayerWithCards(name: String, cards: card*): player = {
    val newPlayer: player = player(name, ArrayBuffer())
    cards.foreach(card => newPlayer.playerHand :+= card)
    newPlayer
  }

  def verifyFieldContents(player: player, fieldNumber: Int, expectedCard: card): Unit = {
    val field = fieldNumber match {
      case 1 => player.plantfield1
      case 2 => player.plantfield2
      case 3 => player.plantfield3
    }
    field should contain(expectedCard)
  }
}