package Bohnanza.controller

import Bohnanza.model
import Bohnanza.controller
import Bohnanza.model.{card, player}
import Bohnanza.view

import scala.collection.mutable.ArrayBuffer

object plantAmount {
  trait Strategy {
    def execute(cards: ArrayBuffer[model.card], player: model.player): Unit
  }

  class Strategy1 extends Strategy {

    override def execute(cards: ArrayBuffer[model.card], player: model.player): Unit = {

    }
  }

  class Strategy2 extends Strategy {
    override def execute(cards: ArrayBuffer[model.card], player: model.player): Unit = {
      println(model.gamedata.drawnCardName)
      Utility.plantDrawnCard(player, Utility.selectCardToPlant(cards, player))

    }
  }

  class Strategy3 extends Strategy {
    override def execute(cards: ArrayBuffer[model.card], player: model.player): Unit = {
      Utility.plantDrawnCard(player, cards(0))
      Utility.plantDrawnCard(player, cards(1))
    }
  }
  private class StrategyRETRY extends Strategy{
    override def execute(cards: ArrayBuffer[card], player: player): Unit = {
      selectStrategy().execute(cards, player)
    }
  }
  private def selectStrategy(): Strategy = {
    val nr = view.playerInput.keyListener()
    if (nr == 0) new Strategy1
    else if (nr == 1) new Strategy2
    else if (nr == 2) new Strategy3
    else new StrategyRETRY
  }
  var strategy: Strategy = selectStrategy()
}
