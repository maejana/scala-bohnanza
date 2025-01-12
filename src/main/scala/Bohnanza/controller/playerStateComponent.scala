package Bohnanza.controller
import Bohnanza.model.modelBase.player

trait playerStateComponent {
  //def handle(player: Option[player]): State
  def handle(player: Option[player]): Unit
}
