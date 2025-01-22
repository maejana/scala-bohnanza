import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.controller.Strategy
import Bohnanza.controller.controllerBase.plantAmount
import Bohnanza.model.modelBase.{card, player}
import Bohnanza.controller.plantAmountComponent

import scala.collection.mutable.ArrayBuffer

class PlantAmountTest extends AnyFlatSpec with Matchers {
  "selectStrategy for plantAmount" should "return the correct strategy" in {
    val plantAmount = new plantAmountComponent {
      override def selectStrategy(): Strategy = new Strategy() {
        override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = true
      }
    }
    val strategy = plantAmount.selectStrategy()
    strategy shouldBe a[Strategy]
  }
}