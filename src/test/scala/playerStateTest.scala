package Bohnanza.controller.controllerBase

import Bohnanza.model.modelBase.{dynamicGamedata, player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import scala.collection.mutable.ArrayBuffer
import Bohnanza.model.modelBase.card

class playerStateTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "DontPlays state" should "transition to Plays state when handle is called" in {
    val testPlayer = new player("TestPlayer", ArrayBuffer[card]())
    val stateMachine = new playerState()
    stateMachine.state = stateMachine.DontPlays()

    // Initial state should be DontPlays
    stateMachine.DontPlays().stateToString() should be("Plays")

    // Transition to Plays
    stateMachine.handle(Some(testPlayer))
    stateMachine.DontPlays().stateToString() should be("Plays")
  }

  "StringToState" should "convert 'DontPlays' string to DontPlays state" in {
    val stateMachine = new playerState()
    val state = stateMachine.StringToState("DontPlays")
    state.stateToString() should be("Plays")
  }

  it should "convert 'Plays' string to Plays state" in {
    val stateMachine = new playerState()
    val state = stateMachine.StringToState("Plays")
    state.stateToString() should be("DontPlays")
  }

}