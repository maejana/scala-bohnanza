//package Bohnanza.controller
//
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.mockito.MockitoSugar
//import Bohnanza.model._
//import scala.collection.mutable.ArrayBuffer
//
//class GameLogicTest extends AnyWordSpec with Matchers with MockitoSugar {
//
//  // Test setup
//  val testCard1 = BeanCard("TestBean1", 1)
//  val testCard2 = BeanCard("TestBean2", 2)
//  val testPlayer1 = Player("Player1")
//  val testPlayer2 = Player("Player2")
//
//  "GameLogic" should {
//    "plant cards correctly in different fields" when {
//      "planting in field 1" in {
//        val player = testPlayer1.copy()
//        val mockUtility = mock[Utility.type]
//        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(1)
//
//        gamelogic.plant(testCard1, player)
//        player.plantfield1 should include(testCard1.beanName)
//      }
//
//      "planting in field 2" in {
//        val player = testPlayer1.copy()
//        val mockUtility = mock[Utility.type]
//        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(2)
//
//        gamelogic.plant(testCard1, player)
//        player.plantfield2 should include(testCard1.beanName)
//      }
//
//      "planting in field 3" in {
//        val player = testPlayer1.copy()
//        val mockUtility = mock[Utility.type]
//        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(3)
//
//        gamelogic.plant(testCard1, player)
//        player.plantfield3 should include(testCard1.beanName)
//      }
//
//      "handling no available fields" in {
//        val player = testPlayer1.copy()
//        val mockUtility = mock[Utility.type]
//        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(-1)
//
//        gamelogic.plant(testCard1, player)
//        player.plantfield1 shouldBe empty
//        player.plantfield2 shouldBe empty
//        player.plantfield3 shouldBe empty
//      }
//    }
//
//    "handle harvesting correctly" in {
//      // Test harvesting from different fields
//      val result1 = gamelogic.harvest(1)
//      result1 shouldBe ""  // Current implementation returns empty string
//
//      // You might want to expand this once harvest is implemented
//      // val expectedGold = calculateExpectedGold(field1Contents)
//      // result1 shouldBe expectedGold
//    }
//
//    "calculate bohnometer values correctly" in {
//      // Test bohnometer calculation
//      val result = gamelogic.bohnometer(testCard1)
//      result shouldBe 0  // Current implementation returns 0
//
//      // You might want to expand this once bohnometer is implemented
//      // val expectedValue = calculateExpectedValue(testCard1)
//      // result shouldBe expectedValue
//    }
//
//    "handle trading between players correctly" in {
//      // Setup players with cards
//      val player1 = testPlayer1.copy()
//      val player2 = testPlayer2.copy()
//
//      player1.playerHand += testCard1.beanName
//      player2.playerHand += testCard2.beanName
//
//      val mockUtility = mock[Utility.type]
//      when(mockUtility.findCardId(player1, testCard1)).thenReturn(0)
//      when(mockUtility.findCardId(player2, testCard2)).thenReturn(0)
//
//      // Execute trade
//      gamelogic.trade(player1, player2, testCard1, testCard2)
//
//      // Verify trade results
//      player1.playerHand should contain(testCard2.beanName)
//      player2.playerHand should contain(testCard1.beanName)
//    }
//
//    "draw cards correctly" in {
//      // Mock UIlogic.weightedRandom
//      val mockUILogic = mock[UIlogic.type]
//      when(mockUILogic.weightedRandom()).thenReturn(testCard1, testCard2)
//
//      // Test drawing cards
//      val drawnCards = gamelogic.drawCards()
//
//      drawnCards.length shouldBe 2
//      drawnCards should contain(testCard1)
//      drawnCards should contain(testCard2)
//    }
//
//    "handle edge cases" when {
//      "trading with invalid card indices" in {
//        val player1 = testPlayer1.copy()
//        val player2 = testPlayer2.copy()
//
//        // Test trading with non-existent cards
//        an [IndexOutOfBoundsException] should be thrownBy {
//          gamelogic.trade(player1, player2, testCard1, testCard2)
//        }
//      }
//
//      "planting in invalid fields" in {
//        val player = testPlayer1.copy()
//        val mockUtility = mock[Utility.type]
//        when(mockUtility.chooseOrEmpty(player, testCard1)).thenReturn(4) // Invalid field number
//
//        // Should handle invalid field number gracefully
//        noException should be thrownBy {
//          gamelogic.plant(testCard1, player)
//        }
//      }
//
//      "drawing cards with empty deck" in {
//        val mockUILogic = mock[UIlogic.type]
//        when(mockUILogic.weightedRandom()).thenReturn(null)
//
//        val drawnCards = gamelogic.drawCards()
//        drawnCards.length shouldBe 2  // Should still return correct number of cards
//      }
//    }
//  }
//
//  // Helper functions
//  def createPlayerWithCards(name: String, cards: String*): Player = {
//    val player = Player(name)
//    cards.foreach(card => player.playerHand += card)
//    player
//  }
//
//  def verifyFieldContents(player: Player, fieldNumber: Int, expectedCard: String): Unit = {
//    val field = fieldNumber match {
//      case 1 => player.plantfield1
//      case 2 => player.plantfield2
//      case 3 => player.plantfield3
//    }
//    field should include(expectedCard)
//  }
//}