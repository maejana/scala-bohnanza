package Bohnanza.controller

import Bohnanza.model
import Bohnanza.controller
import Bohnanza.model.{card, player}
import Bohnanza.view


import scala.collection.mutable.ArrayBuffer

object plantAmount {
  trait Strategy {
    def execute(cards: ArrayBuffer[model.card], player: Option[model.player]): Boolean
  }

  class Strategy1 extends Strategy {
    override def execute(cards: ArrayBuffer[model.card], player: Option[model.player]): Boolean = {
      true
    }
  }

  class Strategy2 extends Strategy {
    override def execute(cards: ArrayBuffer[model.card], player: Option[model.player]): Boolean = {
      println(model.gamedata.drawnCardName)
      Utility.plantDrawnCard(player, Utility.selectCardToPlant(cards))
      true
    }
  }

  class Strategy3 extends Strategy {
    override def execute(cards: ArrayBuffer[model.card], player: Option[model.player]): Boolean = {
      Utility.plantDrawnCard(player, cards(0))
      Utility.plantDrawnCard(player, cards(1))
      true
    }
  }
  private class StrategyRETRY extends Strategy{
    override def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean = {
      view.handleInput.handleInputF(player)
      false
    }
  }
  def selectStrategy(): Strategy = {
    var validInput = false
    var strategy: Strategy = StrategyRETRY()
    while (!validInput) {
      view.playerInput.keyListener() // Abfrage einer neuen Eingabe
      val nr = model.dynamicGamedata.plant1or2
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
