//package Bohnanza.controller
//
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import org.scalamock.scalatest.MockFactory
//import Bohnanza.model._
//
//class GameUpdateTest extends AnyWordSpec with Matchers with MockFactory {
//
//  "GameUpdate" should {
//    "correctly execute a full game round" in {
//      // Create mocks
//      val mockUtility = mock[Utility.type]
//      val mockUILogic = mock[UIlogic.type]
//      val mockGameLogic = mock[gamelogic.type]
//
//      // Test player
//      val testPlayer = Player("TestPlayer")
//
//      // Mock card for testing
//      val testCard = BeanCard("TestBean", 1)
//      val testCards = List(testCard, testCard)
//
//      // Setup expectations
//      (mockUtility.selectPlayer _).expects(*).returning(testPlayer).repeat(10)
//      (mockUtility.plant1or2 _).expects(testPlayer).returning(1).repeat(10)
//      (mockUtility.plantPreperation _).expects(testPlayer).returning(()).repeat(10)
//      (mockUILogic.buildGrowingFieldStr _).expects(testPlayer).returning("Test Field String").repeat(10)
//      (mockGameLogic.drawCards _).expects().returning(testCards).repeat(10)
//      (mockUtility.plantOrTrade _).expects(testCards, testPlayer).returning(()).repeat(10)
//
//      // Execute
//      val result = GameUpdate.gameUpdate()
//
//      // The result should be a non-empty string (game log)
//      result should not be empty
//    }
//
//    "handle plant counts correctly" in {
//      // Create mocks
//      val mockUtility = mock[Utility.type]
//      val mockUILogic = mock[UIlogic.type]
//      val mockGameLogic = mock[gamelogic.type]
//
//      // Test with different plant counts
//      val testPlayer = Player("TestPlayer")
//
//      // Setup expectations for 2 plants
//      (mockUtility.selectPlayer _).expects(*).returning(testPlayer).repeat(10)
//      (mockUtility.plant1or2 _).expects(testPlayer).returning(2).repeat(10)
//      (mockUtility.plantPreperation _).expects(testPlayer).returning(()).repeat(20) // 2 plants * 10 rounds
//      (mockUILogic.buildGrowingFieldStr _).expects(testPlayer).returning("Test Field String").repeat(20)
//      (mockGameLogic.drawCards _).expects().returning(List()).repeat(10)
//      (mockUtility.plantOrTrade _).expects(List(), testPlayer).returning(()).repeat(10)
//
//      // Execute
//      GameUpdate.gameUpdate()
//    }
//
//    "complete exactly 10 rounds" in {
//      // Create mocks
//      val mockUtility = mock[Utility.type]
//      val mockUILogic = mock[UIlogic.type]
//      val mockGameLogic = mock[gamelogic.type]
//
//      // Test player
//      val testPlayer = Player("TestPlayer")
//
//      // Setup expectations for exactly 10 rounds
//      (mockUtility.selectPlayer _).expects(*).returning(testPlayer).repeat(10)
//      (mockUtility.plant1or2 _).expects(testPlayer).returning(1).repeat(10)
//      (mockUtility.plantPreperation _).expects(testPlayer).returning(()).repeat(10)
//      (mockUILogic.buildGrowingFieldStr _).expects(testPlayer).returning("Test Field String").repeat(10)
//      (mockGameLogic.drawCards _).expects().returning(List()).repeat(10)
//      (mockUtility.plantOrTrade _).expects(List(), testPlayer).returning(()).repeat(10)
//
//      // Execute
//      GameUpdate.gameUpdate()
//    }
//
//    "handle errors gracefully" in {
//      // Create mocks
//      val mockUtility = mock[Utility.type]
//      val mockUILogic = mock[UIlogic.type]
//      val mockGameLogic = mock[gamelogic.type]
//
//      // Test player
//      val testPlayer = Player("TestPlayer")
//
//      // Setup mock to throw exception
//      (mockUtility.selectPlayer _).expects(*).returning(testPlayer)
//      (mockUtility.plant1or2 _).expects(testPlayer).throwing(new RuntimeException("Test Exception"))
//
//      // Execute and verify exception handling
//      noException should be thrownBy GameUpdate.gameUpdate()
//    }
//  }
//}