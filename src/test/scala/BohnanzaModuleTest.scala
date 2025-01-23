package test

import Bohnanza.controller.State
import Bohnanza.model.modelBase.player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

class StateTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "State" should "handle a player and return a new state" in {
    val mockPlayer = mock[player]
    val mockState = mock[State]
    val state = new State {
      override def handle(player: Option[player]): State = mockState
      override def stateToString(): String = "TestState"
    }

    val result = state.handle(Some(mockPlayer))
    result should be(mockState)
  }

  it should "return the correct state string" in {
    val state = new State {
      override def handle(player: Option[player]): State = this
      override def stateToString(): String = "TestState"
    }

    val result = state.stateToString()
    result should be("TestState")
  }
}