package Bohnanza.controller

import Bohnanza.model
import Bohnanza.model.modelBase.{card, player}

trait ControllerComponent {
  def plant(cards: card, playerID: Option[player]): Unit = {}

  def harvest(field: Int): Unit = {}

}
