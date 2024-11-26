package Bohnanza.controller

import org.w3c.dom.events.Event
import Bohnanza.controller.playerState2

object playerState {
  trait Event
  case class playsEvent() extends Event
  case class dontPlaysEvent() extends Event

  playerState.handle(new playsEvent)
  playerState.handle(new dontPlaysEvent)

  playerState2.handle(playsEvent())
  playerState2.handle(dontPlaysEvent())
  trait State {
    def handle(e:Event): State
  }
  case class plays() extends State{
    override def handle(e: Event): State = {
      e match
        case plays: playsEvent => plays()
        case dontPlays: dontPlaysEvent => dontPlays()
    }
  }
  case class dontPlays() extends State{
    override def handle(e: Event): State = {
      e match
        case plays: playsEvent => plays()
        case dontPlays: dontPlaysEvent => dontPlays()
    }
  }
  var state: State = dontPlays()
  def handle(e: Event) = state = state.handle(e)
}
