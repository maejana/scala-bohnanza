package Bohnanza.controller

import Bohnanza.model

object playerState {

  trait State {
    def handle(player: model.player): State
  }

  case class Plays() extends State {
    override def handle(player: model.player): State = {
      println(s"${player.playerName} ist an der Reihe.")
      DontPlays() // Transition to DontPlays state
    }
  }

  case class DontPlays() extends State {
    override def handle(player: model.player): State = {
      println(s"${player.playerName} hat Runde beendet.")
      Plays()// Transition to Plays state
    }
  }

  var state: State = Plays() // Initial state

  def handle(player: model.player): Unit = {
    state = state.handle(player)
    model.dynamicGamedata.playingPlayer.lastMethodUsed = "handle"
  }
}