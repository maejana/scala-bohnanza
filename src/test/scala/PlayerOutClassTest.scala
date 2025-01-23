import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import scalafx.scene.layout.VBox
import scalafx.scene.control.{Button, Label}
import Bohnanza.model.modelBase.{dynamicGamedata, player}
import Bohnanza.view.viewBase.PlayerOutClass

class PlayerOutClassTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "playerScene" should "create a VBox with player info, hand cards, plant fields, and undo button" in {
    val playerMock = mock[player]
    when(playerMock.playerName).thenReturn("TestPlayer")
    when(playerMock.gold).thenReturn(10)
    when(playerMock.playerHand).thenReturn(Seq.empty)
    when(playerMock.plantfield1).thenReturn(Seq.empty)
    when(playerMock.plantfield2).thenReturn(Seq.empty)
    when(playerMock.plantfield3).thenReturn(Seq.empty)
    dynamicGamedata.playingPlayer = Some(playerMock)

    val playerOut = new PlayerOutClass
    val scene = playerOut.playerScene

    scene shouldBe a [VBox]
    scene.children.size shouldEqual 2
  }

  "playerInfo" should "create an HBox with player name and gold labels" in {
    val playerMock = mock[player]
    when(playerMock.playerName).thenReturn("TestPlayer")
    when(playerMock.gold).thenReturn(10)
    dynamicGamedata.playingPlayer = Some(playerMock)

    val playerOut = new PlayerOutClass
    val info = playerOut.playerInfo

    info.children.size shouldEqual 2
    info.children.head shouldBe a [Label]
    info.children(1) shouldBe a [Label]
  }

  "handCards" should "create an HBox with hand cards" in {
    val playerMock = mock[player]
    when(playerMock.playerHand).thenReturn(Seq.empty)
    dynamicGamedata.playingPlayer = Some(playerMock)

    val playerOut = new PlayerOutClass
    val hand = playerOut.handCards

    hand.children shouldBe empty
  }

  "plantFields" should "create an HBox with plant fields based on field number" in {
    val playerOut = new PlayerOutClass

    val field1 = playerOut.plantFields(1)
    field1.children.size should be > 0

    val field2 = playerOut.plantFields(2)
    field2.children.size should be > 0

    val field3 = playerOut.plantFields(3)
    field3.children.size should be > 0

    val invalidField = playerOut.plantFields(4)
    invalidField.children.head shouldBe a [Label]
  }

  "undoButton" should "create a Button with 'Undo' text" in {
    val playerOut = new PlayerOutClass
    val button = playerOut.undoButton

    button shouldBe a [Button]
    button.text.value shouldEqual "Undo"
  }
}