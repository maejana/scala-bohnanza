package Test

import Test.controller.GameUpdate
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.regex
import Test.controller.UIlogic
import Test.model
import Test.model.player
import Test.view



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
        model.card("Blaue", 20),
        model.card("Feuer", 18),
        model.card("Sau", 16),
        model.card("Brech", 14),
        model.card("Soja", 12),
        model.card("Augen", 10),
        model.card("Rote", 8),
        model.card("Garten", 6)
      )

      // Call the method
      val result = UIlogic.weightedRandom()

      // Check if the result is a valid card name
      val validCardNames = model.gamedata.cards.map(_.beanName)
      assert(validCardNames.contains(result))

      // Check if the weight of the returned card is decremented
      val cardIndex = validCardNames.indexOf(result)
      assert(model.gamedata.cards(cardIndex).weightCount == (20 - 1))
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
    test("isPlantable should return true if the bean is in the player's hand and can be planted") {
      val mockPlayer = player(
        playerName = "TestPlayer",
        hand = Array("Bean1", "Bean2", "Bean3"),
        growingFieldText = "",
        plantfield1 = "Bean1",
        plantfield2 = "",
        plantfield3 = ""
      )

      assert(UIlogic.isPlantable(mockPlayer, "Bean1"))
      assert(UIlogic.isPlantable(mockPlayer, "Bean2"))
      assert(UIlogic.isPlantable(mockPlayer, "Bean3"))
    }

    test("isPlantable should return false if the bean is not in the player's hand") {
      val mockPlayer = player(
        name = "TestPlayer",
        hand = Array("Bean1", "Bean2", "Bean3"),
        //growingFieldText = "",
        field = "Bean1",
        //plantfield2 = "",
        //plantfield3 = ""
      )

      assert(!UIlogic.isPlantable(mockPlayer, "Bean4"))
    }

    test("isPlantable should return true if the bean is not in the fields but there is an empty field") {
      val mockPlayer = player(
        name = "TestPlayer",
        hand = Array("Bean1", "Bean2", "Bean3"),
//        growingFieldText = "",
//        plantfield1 = "",
//        plantfield2 = "",
//        plantfield3 = ""
      )

      assert(UIlogic.isPlantable(mockPlayer, "Bean1"))
    }

    "isPlantable" in {
      val mockPlayer = player(
        name = "TestPlayer",
        hand = Array("Bean1", "Bean2", "Bean3"),
        growingFieldText = "",
        player.plantField1 = "Bean4",
        player.plantField2 = "Bean5",
        player.plantField3 = "Bean6"
      )

      assert(!UIlogic.isPlantable(mockPlayer, "Bean1"))
    }


  }
}



// noch testen Update gamelogic, plant, gameupdate, isPlantable, plantInfo, plantPreperation, 