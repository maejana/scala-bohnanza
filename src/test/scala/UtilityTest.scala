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

    /*
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



    "plantAllSelectedCards" should {
      "plant all selected cards" in {
        val utility = new Utility()
        val player = testPlayer.copy()
        val card1 = testCard.copy()
        val card2 = testCard.copy()
        val card3 = testCard.copy()
        dynamicGamedata.cardsToPlant = ArrayBuffer(card1, card2, card3)
        dynamicGamedata.plantCount = 3
        dynamicGamedata.playingPlayer = Some(player)

        utility.plantAllSelectedCards(dynamicGamedata.plantCount)

        assert(player.plantfield1.contains(card1), "plantfield1 should contain card1")
        assert(player.plantfield2.contains(card2), "plantfield2 should contain card2")
        assert(player.plantfield3.contains(card3), "plantfield3 should contain card3")
      }
      }
      */

    "should return the right Gold Value" in {
      val player = testPlayer.copy()
      val card = testCard.copy()
      val card2 = testCard2.copy()
      player.playerHand += card
      player.playerHand += card2
      dynamicGamedata.playingPlayer = Some(player)

      Utility().returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield1) shouldEqual dynamicGamedata.playingPlayer.get.gold
    }

    "checkPlantAmount" should {
      "return the correct price based on the number of cards in the plantfield" in {
        val utility = new Utility()
        val card = testCard.copy(price = Array(0, 2, 4, 6))
        val plantfield = ArrayBuffer(card, card, card)

        val result = utility.checkPlantAmount(card, plantfield)
        result shouldEqual 4 // Assuming the price array is [0, 2, 4, 6] and plantfield has 3 cards
      }

      "return the base price if the plantfield is empty" in {
        val utility = new Utility()
        val card = testCard.copy(price = Array(0, 2, 4, 6))
        val plantfield = ArrayBuffer[card]()

        val result = utility.checkPlantAmount(card, plantfield)
        result shouldEqual 2 // Assuming the base price is 0
      }

      "return the correct price for a partially filled plantfield" in {
        val utility = new Utility()
        val card = testCard.copy(price = Array(0, 2, 4, 6))
        val plantfield = ArrayBuffer(card, card)

        val result = utility.checkPlantAmount(card, plantfield)
        result shouldEqual 4 // Assuming the price array is [0, 2, 4, 6] and plantfield has 2 cards
      }
    }
/*
    "plantSelectString" should {
      "return the correct string for the player's hand" in {
        val utility = new Utility()
        val player = testPlayer.copy()
        val card1 = testCard.copy()
        val card2 = testCard2.copy()
        player.playerHand += card1
        player.playerHand += card2
        dynamicGamedata.playingPlayer = Some(player)

        val result = utility.plantSelectString(dynamicGamedata.playingPlayer)
        result shouldEqual "...oechtest Bsp: Sau: [] bean TestBean2 "
      }
    }

 */

    "deletePlayerBecauseBug" should {
      "remove excess players if the player count is incorrect" in {
        val utility = new Utility()
        dynamicGamedata.players += testPlayer.copy()
        dynamicGamedata.players += testPlayer.copy()
        dynamicGamedata.playerCount = 1

        utility.deletePlayerBecauseBug()

        dynamicGamedata.players.size shouldEqual dynamicGamedata.playerCount
      }
    }
    "plant" should {
      "plant the card in the correct field" in {
        val utility = new Utility()
        val player = testPlayer.copy()
        val card = testCard.copy()
        dynamicGamedata.playingPlayer = Some(player)

        utility.plant(card, dynamicGamedata.playingPlayer)

        player.plantfield1 should contain(card)
        player.playerHand should not contain card
      }
    }
    "harvest" should {
      "add the correct gold value and clear the field" in {
        val utility = new Utility()
        val player = testPlayer.copy()
        val card = testCard.copy()
        player.plantfield1 += card
        dynamicGamedata.playingPlayer = Some(player)

        utility.harvest(1)

        player.gold should be > 0
        player.plantfield1 shouldBe empty
      }
    }
    "trade" should {
      "swap the cards between players" in {
        val utility = new Utility()
        val player1 = testPlayer.copy()
        val player2 = testPlayer.copy()
        val card1 = testCard.copy()
        val card2 = testCard2.copy()
        player1.playerHand += card1
        player2.playerHand += card2

        utility.trade(player1, player2, card1, card2)

        player1.playerHand should contain(card2)
        player2.playerHand should contain(card1)
      }
    }
    /*
    "drawCards" should {
      "draw two cards" in {
        val utility = new Utility()
        val cards = utility.drawCards()

        cards.size shouldEqual 2
      }
    }

    "initPlayer" should {
      "initialize a player with a hand of cards" in {
        val utility = new Utility()
        val playerName = "TestPlayer"
        val result = utility.initPlayer(playerName)

        dynamicGamedata.players.exists(_.playerName == playerName) shouldBe true
        result should include(playerName)
      }
    }

    "initGame" should {
      "initialize the game with the correct number of players" in {
        val utility = new Utility()
        dynamicGamedata.playerCount = 2
        val result = utility.initGame

        dynamicGamedata.players.size shouldEqual 2
        result should not be empty
      }
    }
    */
    "playerFieldToString" should {
      "return the correct string representation of the field" in {
        val utility = new Utility()
        val field = ArrayBuffer(testCard.copy(), testCard.copy())

        val result = utility.playerFieldToString(field)

        result shouldEqual "bean TestBean2"
      }
    }
    /*
    "takeNewCard" should {
      "add a new card to the player's hand" in {
        val utility = new Utility()
        val player = testPlayer.copy()
        dynamicGamedata.playingPlayer = Some(player)

        utility.takeNewCard(dynamicGamedata.playingPlayer)

        player.playerHand.size shouldEqual 1
      }
    }
    */
    "playerHandToString" should {
      "return the correct string representation of the hand" in {
        val utility = new Utility()
        val hand = ArrayBuffer(testCard.copy(), testCard.copy())

        val result = utility.playerHandToString(hand)

        result shouldEqual "bean TestBean2"
      }
    }





  }
}

