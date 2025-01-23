import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import Bohnanza.controller.Strategy
import Bohnanza.controller.controllerBase.plantAmount
import Bohnanza.model.modelBase.{FactoryP, card, player}
import Bohnanza.controller.plantAmountComponent
import Bohnanza.model.modelBase.dynamicGamedata
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.view.viewBase.handleInput

import scala.collection.mutable.ArrayBuffer

class PlantAmountTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "Strategy1.excecute" should "return true" in {
    val utility = Utility()
    val plantAmount = new plantAmount(utility)
    val strategy = plantAmount.Strategy1()
    val player = FactoryP.PlayerFactory().createPlayer("testPlayer", ArrayBuffer(card("bean", 1, Array(1))))
    dynamicGamedata.playingPlayer = Some(player)
    val cards = ArrayBuffer(card("TestCard", 1, Array(1, 2, 3)))
    val result = strategy.execute(cards, dynamicGamedata.playingPlayer)
    result shouldEqual true
  }

  "Strategy2.excecute" should "return true" in {
    val utility = Utility()
    val plantAmount = new plantAmount(utility)
    val strategy = plantAmount.Strategy2()
    val player = FactoryP.PlayerFactory().createPlayer("testPlayer", ArrayBuffer(card("bean", 1, Array(1))))
    dynamicGamedata.playingPlayer = Some(player)
    val cards = ArrayBuffer(card("TestCard", 1, Array(1, 2, 3)))

    // Create a copy of the cards to avoid concurrent modification
    val cardsCopy = cards.clone()

    val result = strategy.execute(cardsCopy, dynamicGamedata.playingPlayer)
    result shouldEqual true
  }
  "Strategy3.excecute" should "return true" in {
    val utility = Utility()
    val plantAmount = new plantAmount(utility)
    val strategy = plantAmount.Strategy3()
    val player = FactoryP.PlayerFactory().createPlayer("testPlayer", ArrayBuffer(card("bean", 1, Array(1))))
    dynamicGamedata.playingPlayer = Some(player)
    val cards = ArrayBuffer(card("TestCard", 1, Array(1, 2, 3)), card("AnotherTestCard", 2, Array(4, 5, 6)))
    val result = strategy.execute(cards, dynamicGamedata.playingPlayer)
    result shouldEqual true
  }

  /*
  "StrategyRETRY.excecute" should "return false" in {
    val utility = Utility()
    val plantAmount = new plantAmount(utility)
    val strategy = plantAmount.StrategyRETRY()
    val player = FactoryP.PlayerFactory().createPlayer("testPlayer", ArrayBuffer(card("bean", 1, Array(1))))
    dynamicGamedata.playingPlayer = Some(player)
    val cards = ArrayBuffer(card("TestCard", 1, Array(1, 2, 3)))

    // Mock the handleInput
    val handleInputMock = mock[handleInput.type]
    doNothing().when(handleInputMock).handleInputF(dynamicGamedata.playingPlayer)

    // Inject the mock into the strategy if necessary
    // Assuming the strategy uses handleInput.handleInputF internally
    val result = strategy.execute(cards, dynamicGamedata.playingPlayer)
    result shouldEqual false

    // Verify that the mock was called
    verify(handleInputMock).handleInputF(dynamicGamedata.playingPlayer)
  }

   */
}