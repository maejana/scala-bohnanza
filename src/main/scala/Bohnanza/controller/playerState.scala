package Bohnanza.controller

import Bohnanza.model

object playerState {

  trait State {
    def handle(player: model.player): State
  }

  case class Plays() extends State {
    override def handle(player: model.player): State = {
      println(s"${player.playerName} is playing.")
      DontPlays() // Transition to DontPlays state
    }
  }

  case class DontPlays() extends State {
    override def handle(player: model.player): State = {
      println(s"${player.playerName} is not playing.")
      Plays()// Transition to Plays state
    }
  }

  var state: State = Plays() // Initial state

  def handle(player: model.player): Unit = {
    state = state.handle(player)
  }
}