package Bohnanza.controller

import Bohnanza.model

trait ControllerComponent {
  def plant(cards: model.card, playerID: Option[model.player]): Unit = {}

  def harvest(field: Int): Unit = {}

}
