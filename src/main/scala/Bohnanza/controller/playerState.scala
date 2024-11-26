package Bohnanza.controller

import org.w3c.dom.events.Event
import Bohnanza.model

object playerState {
  trait Event
  case class playsEvent() extends Event
  case class dontPlaysEvent() extends Event

  trait State {
    def handle(p: model.player): State
  }
  case class plays() extends State{
    override def handle(p: model.player): State = {
      p.plays match
        case Plays: playsEvent => dontPlays()
        case DontPlays: dontPlaysEvent => plays()
    }
  }
  case class dontPlays() extends State{
    override def handle(p : model.player): State = {
      p.plays match
        case Plays: playsEvent => plays()
        case DontPlays: dontPlaysEvent => dontPlays()
    }
  }
  var state: State = dontPlays()
  def handle(p: model.player) = state = state.handle(p)
}
