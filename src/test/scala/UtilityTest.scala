package test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.model.{modelBase, *}
import Bohnanza.controller.*
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.model
import Bohnanza.model.modelBase.{FactoryP, card, dynamicGamedata, gamedata, player}
import org.scalatest.matchers.should.Matchers.exist.or
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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
        modelBase.dynamicGamedata.players = ArrayBuffer(player("TestPlayer", ArrayBuffer(testCard)), player("Bob", ArrayBuffer(testCard)))

        // Test selecting a valid index
        val selectedPlayer = Utility().selectPlayer()
        dynamicGamedata.playingPlayer shouldEqual Some(player("TestPlayer", ArrayBuffer(testCard)))
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

    /* "plant cards correctly" in {
      val player = testPlayer.copy()
      val card = testCard.copy()

      // Test empty fields
      Utility().plant(card, dynamicGamedata.playingPlayer)
      player.plantfield1 should contain(card)
    }

  */

    "give the right infos for planting" in {
      val player = testPlayer.copy()
      testPlayer.playerHand += testCard
      dynamicGamedata.cardsToPlant += dynamicGamedata.playingPlayer.get.playerHand(0)
      val plantedCard = dynamicGamedata.playingPlayer.get.playerHand(0)

      // Test empty fields
      Utility().plantPreperation(dynamicGamedata.playingPlayer) shouldEqual ""

    }
    "plantPreperation first case" in {
      val player = testPlayer.copy()
      val newCard = card(modelBase.gamedata.beans(2),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
      testPlayer.playerHand += testCard
      dynamicGamedata.cardsToPlant += dynamicGamedata.playingPlayer.get.playerHand(0)
      val plantedCard = dynamicGamedata.playingPlayer.get.playerHand(0)

      // Test empty fields
      Utility().plantPreperation(dynamicGamedata.playingPlayer) shouldEqual ""

    }
    "test Function plantDrawnCard" in {
      val player = dynamicGamedata.playingPlayer
      val card = testCard.copy()

      // Test empty fields
      Utility().plantDrawnCard(player, card)
      val cardInField1 = testPlayer.plantfield1.contains(card)
      val cardInField2 = testPlayer.plantfield2.contains(card)
      val cardInField3 = testPlayer.plantfield3.contains(card)
      val cardInHand = testPlayer.playerHand.contains(card)

      assert(cardInField1 || cardInField2 || cardInField3 || cardInHand, "The card should be in one of the plantfields")
    }

    "test Function selectCardToPlant" in {
      val player = dynamicGamedata.playingPlayer
      val newCard  =  modelBase.card(modelBase.gamedata.beans(0), modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
      dynamicGamedata.cardsToPlant += newCard
      Utility().selectCardToPlant(dynamicGamedata.cardsToPlant) shouldEqual newCard
    }

    "test Function chooseOrEmpty" in {
      val player = dynamicGamedata.playingPlayer
      val newCard  =  modelBase.card(modelBase.gamedata.beans(0), modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
      dynamicGamedata.cardsToPlant += newCard
      Utility().chooseOrEmpty(dynamicGamedata.playingPlayer, newCard) shouldEqual 2
    }

    "plant1or2" should {
      "process input 1 correctly" in {
        val utility = new Utility()
        val player = Some(testPlayer.copy(hand = ArrayBuffer(testCard.copy(), testCard.copy())))
        val inputProvider = () => 1

        val result = utility.plant1or2(player)
        result shouldBe 1
      }

      "process input 2 correctly" in {
        val utility = new Utility()
        val player = Some(testPlayer.copy(hand = ArrayBuffer(testCard.copy(), testCard.copy())))
        val inputProvider = () => 2

        dynamicGamedata.playingPlayer = player

        val result = utility.plant1or2(dynamicGamedata.playingPlayer)
        result shouldEqual 2
      }

      "retry on incorrect input" in {
        val utility = new Utility()
        val player = Some(testPlayer.copy())
        val inputs = List(0, 1).iterator
        val inputProvider = () => inputs.next()

        val result = utility.plant1or2(player)
        result shouldEqual 0
      }
    }




  }
}

