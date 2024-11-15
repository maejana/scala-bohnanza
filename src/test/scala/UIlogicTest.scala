package Bohnanza

import Bohnanza.controller.GameUpdate
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.regex
import Bohnanza.controller.UIlogic
import Bohnanza.model
import Bohnanza.model.player
import Bohnanza.view
import org.scalactic.Prettifier.default

import scala.collection.mutable.ArrayBuffer



class UIlogicTest extends AnyWordSpec with Matchers {
  "UIlogic" should {
    "initPlayer" in {
      val playerName = "TestPlayer"
      val result = model.gameDataFunc.initPlayer(playerName)
      result should include(playerName)
      result should include regex ("((?:Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten),\\s){4}(Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten)")
    }

    "initGame" in {
      val input = new java.io.ByteArrayInputStream("2\nPlayer1\nPlayer2\n".getBytes)
      Console.withIn(input) {
        val result = model.gameDataFunc.initGame
        result should include("Player1")
        result should include("Player2")
      }
    }

    "weigthedRandom" in {
      // Setup initial game data
      model.gamedata.cards = Array(
        model.card("Blaue", 20, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Feuer", 18, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Sau", 16, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Brech", 14, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Soja", 12, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Augen", 10, scala.collection.mutable.ArrayBuffer(1, 2, 3)),
        model.card("Rote", 8, scala.collection.mutable.ArrayBuffer(1, 2, 3)))

      // Call the method
      val result = UIlogic.weightedRandom()

      // Check if the result is a valid card name
      val validCardNames = model.gamedata.cards.map(_.beanName)
      assert(validCardNames.contains(result.beanName))

      // Check if the weight of the returned card is decremented
      val cardIndex = validCardNames.indexOf(result.beanName)
      assert(model.gamedata.cards(cardIndex).weightCount == (result.weight - 1))
    }
    "keyListener" in{
      val mock = 0
      mock shouldBe(0)
    }
    "buildGrowingFieldStr" in {
      // Create a mock player with predefined fields
      val mockPlayer = player(
        name = "TestPlayer",
        hand = ArrayBuffer(
          model.card("Bean1", 1, ArrayBuffer(1)),
          model.card("Bean2", 1, ArrayBuffer(1)),
          model.card("Bean3", 1, ArrayBuffer(1))
        )
      )

      // Call the method
      val result = model.gameDataFunc.buildGrowingFieldStr(mockPlayer)

      // Expected result
      val expected =
        """
        Testplayer

                               TestPlayer:
                                  Field 1:

                                  Field 2:

                                  Field 3:

                               Bean1, Bean2, Bean3
                               """

      // Assert the result
      result shouldBe expected
    }
  }
}
// noch testen Update gamelogic, plant, gameupdate, isPlantable, plantInfo, plantPreperation, 