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



class UIlogicTest extends AnyWordSpec with Matchers {
  "UIlogic" should {
    "initPlayer" in {
      val playerName = "TestPlayer"
      val result = controller.UIlogic.initPlayer(playerName)
      result should include(playerName)
      result should include regex ("((?:Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten),\\s){4}(Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten)")
    }

    "initGame" in {
      val input = new java.io.ByteArrayInputStream("2\nPlayer1\nPlayer2\n".getBytes)
      Console.withIn(input) {
        val result = controller.UIlogic.initGame
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

    "buildGrowingFieldStr" in {
      // Create a mock player with predefined fields
      val mockPlayer = player(
        name = "TestPlayer",
        hand = Array("Bean1", "Bean2", "Bean3"),
        //growingFieldText = "",
        field = "Bean1",
        //field = "Bean2",
        //plantfield = "Bean3"
      )

      // Call the method
      val result = UIlogic.buildGrowingFieldStr(mockPlayer)

      // Expected result
      val expected =
        s"""
           |                               TestPlayer:
           |                                  Field 1:
           |                               Bean1
           |                                  Field 2:
           |                               Bean2
           |                                  Field 3:
           |                               Bean3
           |
           |                               """.stripMargin

      // Assert the result
      assert(result == expected)

    }
  }
}



// noch testen Update gamelogic, plant, gameupdate, isPlantable, plantInfo, plantPreperation, 