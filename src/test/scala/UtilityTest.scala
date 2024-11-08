//package Bohnanza.controller
//
//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatest.matchers.should.Matchers
//import Bohnanza.model._
//import scala.collection.mutable.ArrayBuffer
//
//class UtilitySpec extends AnyWordSpec with Matchers {
//
//  // Test data setup
//  val testPlayer = Player("TestPlayer")
//  val testCard = BeanCard("TestBean", 1)
//  val testCard2 = BeanCard("TestBean2", 2)
//
//  "Utility" should {
//    "find empty plantfield number correctly" in {
//      val player = testPlayer.copy()
//
//      // Test empty fields
//      Utility.emptyPlantfieldNr(player) shouldEqual 1
//
//      // Test partially filled fields
//      player.plantfield1 = "Bean"
//      Utility.emptyPlantfieldNr(player) shouldEqual 2
//
//      // Test full fields
//      player.plantfield1 = "Bean1"
//      player.plantfield2 = "Bean2"
//      player.plantfield3 = "Bean3"
//      Utility.emptyPlantfieldNr(player) shouldEqual -1
//    }
//
//    "find card ID correctly" in {
//      val player = testPlayer.copy()
//      player.playerHand += testCard.beanName
//
//      val result = Utility.findCardId(player, testCard)
//      result shouldEqual 0
//
//      // Test non-existent card
//      val nonExistentResult = Utility.findCardId(player, testCard2)
//      nonExistentResult shouldEqual -1
//    }
//
//    "check if bean is plantable correctly" in {
//      val player = testPlayer.copy()
//
//      // Test empty fields
//      Utility.isPlantable(player, testCard) shouldBe true
//
//      // Test matching field
//      player.plantfield1 = testCard.beanName
//      Utility.isPlantable(player, testCard) shouldBe true
//
//      // Test full non-matching fields
//      val fullPlayer = testPlayer.copy()
//      fullPlayer.plantfield1 = "Bean1"
//      fullPlayer.plantfield2 = "Bean2"
//      fullPlayer.plantfield3 = "Bean3"
//      Utility.isPlantable(fullPlayer, testCard) shouldBe false
//    }
//
//    "select player correctly" when {
//      "given valid player index" in {
//        // Setup test data
//        model.gamedata.players.clear()
//        model.gamedata.players += testPlayer
//
//        val result = Utility.selectPlayer(0)
//        result shouldEqual testPlayer
//      }
//
//      "given invalid player index" in {
//        model.gamedata.players.clear()
//
//        an [IndexOutOfBoundsException] should be thrownBy {
//          Utility.selectPlayer(999)
//        }
//      }
//    }
//
//    "find card with name correctly" when {
//      "card exists" in {
//        // Setup test data
//        model.gamedata.cards.clear()
//        model.gamedata.cards += testCard
//
//        val result = Utility.findCardWithName(testCard.beanName)
//        result shouldEqual testCard
//      }
//
//      "card doesn't exist" in {
//        model.gamedata.cards.clear()
//
//        val result = Utility.findCardWithName("NonExistentBean")
//        result shouldBe null
//      }
//    }
//  }
//
//  // Helper function to create a player with specific field setup
//  def createPlayerWithFields(field1: String = "", field2: String = "", field3: String = ""): Player = {
//    val player = testPlayer.copy()
//    player.plantfield1 = field1
//    player.plantfield2 = field2
//    player.plantfield3 = field3
//    player
//  }
//}