package Bohnanza.controller

import Bohnanza.model
import Bohnanza.controller
import Bohnanza.view

import scala.collection.mutable.ArrayBuffer

object plantAmount {
  trait Stragegy {
    def execute(): Unit

    def execute(cards: ArrayBuffer[model.card], player: model.player): Unit

  }

  class Strategy1 {

    override def execute(): Unit = {
      //gamelogic.trade()

    }
  }

  class Strategy2 {

    override def execute(cards: ArrayBuffer[model.card], player: model.player): Unit = {
      println(model.gamedata.drawnCardName)
      Utility.plantDrawnCard(player, Utility.selectCardToPlant(cards, player))

    }
  }

    class Strategy3 {
      override def execute(cards: ArrayBuffer[model.card], player: model.player): Unit = {
        Utility.plantDrawnCard(player, cards(0))
        Utility.plantDrawnCard(player, cards(1))
      }

    }


    var strategy = if (view.playerInput.keyListener() == 0) new Strategy1 else if (view.playerInput.keyListener() == 1) new Strategy2
    else if (view.playerInput.keyListener() == 2) new Strategy3 else (println("Fehlereingabe"))
  }
