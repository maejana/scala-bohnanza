package Bohnanza.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import org.mockito.MockitoAnnotations
import Bohnanza.model
import Bohnanza.model.card
import Bohnanza.model.player

import scala.collection.mutable.ArrayBuffer

class GameUpdateTest extends AnyWordSpec with Matchers {
/*
  // Initialize Mockito
  MockitoAnnotations.openMocks(this)

  "GameUpdate" should {
    "correctly execute a full game round" in {
      // Create mocks
      val testPlayer = player("TestPlayer", ArrayBuffer())
      testPlayer.playerHand.addOne(card("Bean1",1,ArrayBuffer(1)))
      val gameData = model.gamedata
      gameData.drawnCards = ArrayBuffer(card("bean", 2, ArrayBuffer(1)))
      val input = new java.io.ByteArrayInputStream(s"1\nBean1\n1\nbean${model.gamedata.drawnCards(0)}\n".getBytes)
      Console.withIn(input) {
      val mockUtility = mock(classOf[Utility.type])
      val mockUILogic = mock(classOf[UIlogic.type])
      val mockGameLogic = mock(classOf[gamelogic.type])

      // Test player

      // Mock card for testing
      val testCard = card("bean", 1, scala.collection.mutable.ArrayBuffer(1))
      val testCards = ArrayBuffer(testCard, testCard)


      // Setup expectations
      when(mockUtility.selectPlayer(any())).thenReturn(testPlayer)
      when(mockUtility.plant1or2(testPlayer)).thenReturn(1)
      when(mockUtility.plantPreperation(testPlayer)).thenReturn("TestPlayer pflanzt bean\n")
      when(mockUILogic.buildGrowingFieldStr(testPlayer)).thenReturn("Test Field String")
      when(mockGameLogic.drawCards()).thenReturn(testCards)

      // Execute
      val result = GameUpdate.gameUpdate()

      // The result should be a non-empty string (game log)
      result should not be empty
        }
    }

  }

 */
}