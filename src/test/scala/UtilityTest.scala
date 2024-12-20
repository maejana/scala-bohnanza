package test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.model.{modelBase, *}
import Bohnanza.controller.*
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.controller.controllerBase.Utility.selectPlayer
import Bohnanza.model
import Bohnanza.model.modelBase.{card, player}

import scala.collection.mutable.ArrayBuffer

object gamedata {
  val cards: ArrayBuffer[card] = ArrayBuffer()
  val players: ArrayBuffer[player] = ArrayBuffer()
}

class UtilityTest extends AnyWordSpec with Matchers {

  // Test data setup
  val testPlayer: player = player("TestPlayer", ArrayBuffer())
  val testCard: card = card("bean", 1, ArrayBuffer(1))
  val testCard2: card = card("TestBean2", 2, ArrayBuffer(2))

  "Utility" should {
    "find empty plantfield number correctly" in {
      val player = testPlayer.copy()

      // Test empty fields
      Utility.emptyPlantfieldNr(player) shouldEqual 1
    }

    "find card ID correctly" in {
      val player = testPlayer.copy()
      player.playerHand :+= testCard

      val result = Utility.findCardId(player, testCard)
      result shouldEqual 0

      // Test non-existent card
      val nonExistentResult = Utility.findCardId(player, testCard2)
      nonExistentResult shouldEqual -1
    }

    "check if bean is plantable correctly" in {
      val player = testPlayer.copy()

      // Test empty fields
      Utility.isPlantable(player, testCard) shouldBe true
    }

    "select player correctly" when {
      "return the correct player when given a valid index" in {
        // Setup test data
        modelBase.gamedata.players = ArrayBuffer(player("TestPlayer",ArrayBuffer(testCard)), player("Bob",ArrayBuffer(testCard)))

        // Test selecting a valid index
        val selectedPlayer = selectPlayer(1)
        selectedPlayer.name shouldEqual "Bob"
      }


      "given invalid player index" in {
        gamedata.players.clear()

        an[IndexOutOfBoundsException] should be thrownBy {
          Utility.selectPlayer(999)
        }
      }
    }

    "find card with name correctly" when {
      "card doesn't exist" in {
        gamedata.cards.clear()

        val result = Utility.findCardWithName("NonExistentBean")
        result shouldBe null
      }
    }
  }

}