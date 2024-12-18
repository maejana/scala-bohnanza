package Bohnanza.controller

import Bohnanza.model

object playerState {

  trait State {
    def handle(player: Option[model.player]): State
  }

  case class Plays() extends State {
    override def handle(player: Option[model.player]): State = {
      println(s"${player.get.playerName} ist an der Reihe.")
      DontPlays() // Transition to DontPlays state
    }
  }

  case class DontPlays() extends State {
    override def handle(player: Option[model.player]): State = {
      println(s"${player.get.playerName} hat Runde beendet.")
      Plays()// Transition to Plays state
    }
  }

  var state: State = Plays() // Initial state

  def handle(player: Option[model.player]): Unit = {
    state = state.handle(player)
    model.dynamicGamedata.playingPlayer.get.lastMethodUsed = "handle"
  }
}