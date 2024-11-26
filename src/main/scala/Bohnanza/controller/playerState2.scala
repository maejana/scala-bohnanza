package Bohnanza.controller
import Bohnanza.controller.playerState

object playerState2 {
  var state = playerState.plays()
  def handle(e: playerState.Event)= {
    e match{
      case plays: playerState.playsEvent => state = plays
      case dontPlays: playerState.dontPlaysEvent => state = dontPlays
    }
    state
  }
}
