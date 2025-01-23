import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers._
import Bohnanza.controller.{Strategy, UndoCommandComponent, UtilityComponent, plantAmountComponent, playerStateComponent}
import Bohnanza.model.modelBase.{FactoryP, card, dynamicGamedata, gamedata, player, fieldBuilder}
import Bohnanza.controller.controllerBase.{GameUpdate, Utility, plantAmount, playerState, UndoCommand}

import scala.collection.mutable.ArrayBuffer

class GameUpdateTest extends AnyFlatSpec with Matchers {

  "gameUpdate" should "run the game loop and process player actions correctly" in {
    // Mock dependencies
    val utilityMock = mock(classOf[Utility])
    val dynamicGamedataMock = mock(classOf[dynamicGamedata.type])
    val playerStateMock = mock(classOf[playerStateComponent])
    val undoCommandMock = mock(classOf[UndoCommandComponent])
    val fieldBuilderMock = mock(classOf[fieldBuilder])
    val plantAmountMock = mock(classOf[plantAmountComponent])

    // Set up mocked methods and properties
    when(dynamicGamedataMock.playerCount).thenReturn(2)
    when(dynamicGamedataMock.playingPlayer).thenReturn(Some(mock(classOf[player])))

    when(utilityMock.selectPlayer()).thenReturn(Some(mock(classOf[player])))
    when(utilityMock.playerHandToString(any())).thenReturn("Player hand as string")
    when(utilityMock.plant1or2(any())).thenReturn(1)
    when(utilityMock.drawCards()).thenReturn(ArrayBuffer(mock(classOf[card]), mock(classOf[card])))

    val mockCard = mock(classOf[card])
    when(mockCard.beanName).thenReturn("MockBean")
    when(fieldBuilderMock.buildGrowingFieldStr(any())).thenReturn("Field: Mock State")
    when(plantAmountMock.selectStrategy()).thenReturn(new Strategy {
      override def execute(drawnCards: ArrayBuffer[card], player: Option[player]): Boolean = true
    })

    // Test subject
    val game = new GameUpdate(utilityMock, plantAmountMock, playerStateMock, undoCommandMock)

    // Argument captor for playerStateComponent.handle
    val playerCaptor = ArgumentCaptor.forClass(classOf[Option[player]])

    // Execute
    val result = game.gameUpdate()

    // Verify interactions
    verify(utilityMock, times(5)).selectPlayer()
    verify(playerStateMock, times(10)).handle(playerCaptor.capture())
    verify(utilityMock, times(5)).drawCards()
    verify(utilityMock, times(5)).plant1or2(any())
    verify(fieldBuilderMock, atLeastOnce()).buildGrowingFieldStr(any())

    // Check the captured arguments
    val capturedPlayers = playerCaptor.getAllValues
    capturedPlayers.forEach { playerOption =>
      playerOption should not be empty
      playerOption.get shouldBe a[player]
    }

    // Check the result
    result should not be empty
  }
}