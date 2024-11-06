package Test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.regex


class ScalaTest extends AnyWordSpec with Matchers {
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
    "weightedRandom" in {
      val result = controller.UIlogic.weightedRandom(weights = Array(20, 18, 16, 14, 12, 10, 8, 6))
      result should include regex ("(Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten)")
    }
    "gameUpdate" in {
    }
    "plantSelected" in{
      
    }
  }
}

// noch testen Update gamelogic plant gameupdate