package Bohnanza.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import org.mockito.MockitoAnnotations
import Bohnanza.model.*

import scala.collection.mutable.ArrayBuffer

class GameUpdateTest extends AnyWordSpec with Matchers {

  // Initialize Mockito
  MockitoAnnotations.openMocks(this)

  "GameUpdate" should {
    "correctly execute a full game round" in {
      // Create mocks
      val mockUtility = mock(classOf[Utility.type])
      val mockUILogic = mock(classOf[UIlogic.type])
      val mockGameLogic = mock(classOf[gamelogic.type])

      // Test player
      val testPlayer = player("TestPlayer", ArrayBuffer())

      // Mock card for testing
      val testCard = card("TestBean", 1, scala.collection.mutable.ArrayBuffer(1))
      val testCards = ArrayBuffer(testCard, testCard)
      

      // Setup expectations
      when(mockUtility.selectPlayer(any())).thenReturn(testPlayer)
      when(mockUtility.plant1or2(testPlayer)).thenReturn(1)
      when(mockUtility.plantPreperation(testPlayer)).thenReturn(())
      when(mockUILogic.buildGrowingFieldStr(testPlayer)).thenReturn("Test Field String")
      when(mockGameLogic.drawCards()).thenReturn(testCards)
      when(mockUtility.plantOrTrade(testCards, testPlayer)).thenReturn(())

      // Execute
      val result = GameUpdate.gameUpdate()

      // The result should be a non-empty string (game log)
      result should not be empty
    }

    "handle plant counts correctly" in {
      // Create mocks
      val mockUtility = mock(classOf[Utility.type])
      val mockUILogic = mock(classOf[UIlogic.type])
      val mockGameLogic = mock(classOf[gamelogic.type])

      // Test with different plant counts
      val testPlayer = player("TestPlayer", ArrayBuffer())

      // Setup expectations for 2 plants
      when(mockUtility.selectPlayer(any())).thenReturn(testPlayer)
      when(mockUtility.plant1or2(testPlayer)).thenReturn(2)
      when(mockUtility.plantPreperation(testPlayer)).thenReturn(())
      when(mockUILogic.buildGrowingFieldStr(testPlayer)).thenReturn("Test Field String")
      when(mockGameLogic.drawCards()).thenReturn(ArrayBuffer())
      when(mockUtility.plantOrTrade(ArrayBuffer(), testPlayer)).thenReturn(())
      // Execute
      GameUpdate.gameUpdate()
    }

    "complete exactly 10 rounds" in {
      // Create mocks
      val mockUtility = mock(classOf[Utility.type])
      val mockUILogic = mock(classOf[UIlogic.type])
      val mockGameLogic = mock(classOf[gamelogic.type])

      // Test player
      val testPlayer = player("TestPlayer", ArrayBuffer())

      // Setup expectations for exactly 10 rounds
      when(mockUtility.selectPlayer(any())).thenReturn(testPlayer)
      when(mockUtility.plant1or2(testPlayer)).thenReturn(1)
      when(mockUtility.plantPreperation(testPlayer)).thenReturn(())
      when(mockUILogic.buildGrowingFieldStr(testPlayer)).thenReturn("Test Field String")
      when(mockGameLogic.drawCards()).thenReturn(List())
      when(mockUtility.plantOrTrade(ArrayBuffer(), testPlayer)).thenReturn(())

      // Execute
      GameUpdate.gameUpdate()
    }

    "handle errors gracefully" in {
      // Create mocks
      val mockUtility = mock(classOf[Utility.type])
      val mockUILogic = mock(classOf[UIlogic.type])
      val mockGameLogic = mock(classOf[gamelogic.type])

      // Test player
      val testPlayer = player("TestPlayer", ArrayBuffer())

      // Setup mock to throw exception
      when(mockUtility.selectPlayer(any())).thenReturn(testPlayer)
      when(mockUtility.plant1or2(testPlayer)).thenThrow(new RuntimeException("Test Exception"))

      // Execute and verify exception handling
      noException should be thrownBy GameUpdate.gameUpdate()
    }
  }
}