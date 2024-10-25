package Test

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Test.B1

class ScalaTest extends AnyWordSpec with Matchers {
  "B1" should {
    "initPlayer" in {
      val playerName = "TestPlayer"
      val result = B1.initPlayer(playerName)
      result should include(playerName)
      result should include regex ("((?:Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten),\\s){4}(Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten)")
    }

    "initGame" in {
      val input = new java.io.ByteArrayInputStream("2\nPlayer1\nPlayer2\n".getBytes)
      Console.withIn(input) {
        val result = B1.initGame
        result should include("Player1")
        result should include("Player2")
      }
    }
    "weightedRandom" in {
      val result = B1.weightedRandom()
      result should include regex ("(Blaue|Feuer|Sau|Brech|Soja|Augen|Rote|Garten)")
    }
  }
}