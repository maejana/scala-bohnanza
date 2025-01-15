package test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.model.{modelBase, *}
import Bohnanza.controller.*
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.model
import Bohnanza.model.modelBase.{FactoryP, card, dynamicGamedata, player}

import scala.collection.mutable.ArrayBuffer

object gamedata {
  val cards: ArrayBuffer[card] = ArrayBuffer()
  val players: ArrayBuffer[player] = ArrayBuffer()
}

class UtilityTest extends AnyWordSpec with Matchers {

  // Test data setup
  val testPlayerName = "TestPlayer"
  val testPlayer: player = FactoryP.PlayerFactory().createPlayer(testPlayerName, ArrayBuffer(card("bean", 1, Array(1))))
  dynamicGamedata.playingPlayer = Some(testPlayer)
  val testCard: card = card("bean", 1, Array(1))
  val testCard2: card = card("TestBean2", 2, Array(2))

  "Utility" should {
    
    "find empty plantfield number correctly" in {
      val player = testPlayer.copy()

      // Test empty fields
      Utility().emptyPlantfieldNr(dynamicGamedata.playingPlayer) shouldEqual 1
    }

    "find card ID correctly" in {
      val player = testPlayer.copy()
      player.playerHand :+= testCard

      val result = Utility().findCardId(player, testCard) shouldEqual 1

      // Test non-existent card
      val nonExistentResult = Utility().findCardId(player, testCard2)
      nonExistentResult shouldEqual -1
    }

    "check if bean is plantable correctly" in {
      val player = testPlayer.copy()

      // Test empty fields
      Utility().isPlantable(dynamicGamedata.playingPlayer, testCard) shouldBe true
    }

    "select player correctly" when {
      "return the correct player when given a valid index" in {
        // Setup test data
        modelBase.dynamicGamedata.players = ArrayBuffer(player("TestPlayer",ArrayBuffer(testCard)), player("Bob",ArrayBuffer(testCard)))

        // Test selecting a valid index
        val selectedPlayer = Utility().selectPlayer()
        dynamicGamedata.playingPlayer shouldEqual Some(player("TestPlayer",ArrayBuffer(testCard)))
      }

      /*
      "given invalid player index" in {
        gamedata.players.clear()

        an[IndexOutOfBoundsException] should be thrownBy {
          Utility().selectPlayer(99)
        }
      }
      */
    }

    "find card with name correctly" when {
      "card doesn't exist" in {
        gamedata.cards.clear()

        val result = Utility().findCardWithName("NonExistentBean")
        result should not be null
      }
    }

    "plant cards correctly" in {

    }
  }

}

