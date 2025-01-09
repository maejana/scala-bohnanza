package Bohnanza.controller.controllerBase

import Bohnanza.model
import Bohnanza.model.modelBase.{dynamicGamedata, player}
import Bohnanza.controller.State
import Bohnanza.controller.playerStateComponent

class playerState extends playerStateComponent{

 

  case class Plays() extends State {
    override def handle(player: Option[player]): State = {
      println(s"${player.get.playerName} ist an der Reihe.")
      DontPlays() // Transition to DontPlays state
    }
  }

  case class DontPlays() extends State {
    override def handle(player: Option[player]): State = {
      println(s"${player.get.playerName} hat Runde beendet.")
      Plays()// Transition to Plays state
    }
  }

  var state: State = Plays() // Initial state

  override def handle(player: Option[player]): Unit = {
    state = state.handle(player)
    dynamicGamedata.playingPlayer.get.lastMethodUsed = "handle"
  }
}