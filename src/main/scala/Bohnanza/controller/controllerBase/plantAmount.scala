package Bohnanza.controller.controllerBase

import Bohnanza.model.modelBase.{card, dynamicGamedata, gamedata, player}
import Bohnanza.view.viewBase.{handleInput, playerInput}
import Bohnanza.{controller, model, view}

import Bohnanza.controller.plantAmountComponent
import scala.collection.mutable.ArrayBuffer

class plantAmount(utility: UtilityComponent, dynamicGamedata: dynamicGamedataComponent, gamedata: gamedataComponent) {

  trait Strategy {
    def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean
  }

  class Strategy1 extends Strategy {
    override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = {
      true
    }
  }

  class Strategy2 extends Strategy {
    override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = {
      println(gamedata.drawnCardName)
      utility.plantDrawnCard(player, utility.selectCardToPlant(cards))
      true
    }
  }

  class Strategy3 extends Strategy {
    override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = {
      utility.plantDrawnCard(player, cards(0))
      utility.plantDrawnCard(player, cards(1))
      true
    }
  }
  private class StrategyRETRY extends Strategy{
    override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = {
      handleInput.handleInputF(player)
      false
    }
  }
  override def selectStrategy(): Strategy = {
    var validInput = false
    var strategy: Strategy = StrategyRETRY()
    while (!validInput) {
      playerInput.keyListener() // Abfrage einer neuen Eingabe
      val nr = dynamicGamedata.plant1or2
        nr match {
        case 0 =>
          strategy = new Strategy1
          validInput = true // Schleife wird beendet
        case 1 =>
          strategy = new Strategy2
          validInput = true // Schleife wird beendet
        case 2 =>
          strategy = new Strategy3
          validInput = true // Schleife wird beendet
        case _ =>
          strategy = new StrategyRETRY
          validInput = false
      }
    }
    strategy
  }
}
