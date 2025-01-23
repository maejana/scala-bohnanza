import Bohnanza.controller.State
import Bohnanza.model.modelBase.player
import Bohnanza.view.viewBase.playerInput
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

class initGameTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "initGame" should "initialize the game with the correct number of players and player names" in {
    val mockPlayer = mock[player]
    val mockState = mock[State]
    val state = new State {
      override def handle(player: Option[player]): State = mockState
      override def stateToString(): String = "TestState"
    }

    doNothing().when(playerInput).playercount()

    // Additional test setup and assertions
  }
}