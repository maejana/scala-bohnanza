package Bohnanza.controller

import Bohnanza.model.modelBase.player

trait State {
  def handle(player: Option[player]): State
  def stateToString(): String
}
