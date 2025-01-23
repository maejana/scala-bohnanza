package test

import Bohnanza.controller.{UndoCommandComponent, UtilityComponent, plantAmountComponent, playerStateComponent, Strategy}
import Bohnanza.model.modelBase.{card, dynamicGamedata, player}
import Bohnanza.controller.controllerBase.UndoCommand
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import scala.collection.mutable.ArrayBuffer

class UndoCommandTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "UndoCommand" should "perform doStep, undoStep, and redoStep correctly" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val undoCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock)
    val player = mock[player]
    val strategyMock = mock[Strategy]

    when(player.copyState()).thenReturn(player)
    doNothing().when(player).restore(any[player])
    when(plantAmountMock.selectStrategy()).thenReturn(strategyMock)
    doNothing().when(strategyMock).execute(any(), any())

    val plantBeanCommand = undoCommand.PlantBeanCommand

    plantBeanCommand.getStateStack.push(player)
    plantBeanCommand.doStep(Some(player))
    plantBeanCommand.getStateStack should have size 2

    plantBeanCommand.undoStep(Some(player))
    plantBeanCommand.getStateStack should have size 1
    plantBeanCommand.getRedoStack should have size 1

    plantBeanCommand.redoStep(Some(player))
    plantBeanCommand.getStateStack should have size 2
    plantBeanCommand.getRedoStack should have size 0
  }

  it should "call the appropriate matchState method based on the player's lastMethodUsed" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val dynamicGamedataMock = mock[dynamicGamedata.type]
    val playingPlayerMock = mock[player]

    when(dynamicGamedataMock.playingPlayer).thenReturn(Some(playingPlayerMock))
    when(playingPlayerMock.lastMethodUsed).thenReturn("plant1or2")

    val undoCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock)

    noException should be thrownBy undoCommand.PlantBeanCommand.matchState()

    verify(utilityMock, times(1)).playerHandToString(any())
    verify(utilityMock, times(1)).plant1or2(any())
    verify(utilityMock, times(1)).plantAllSelectedCards(any())
  }

  it should "handle the case where the player has no state to restore" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val undoCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock)
    val plantBeanCommand = undoCommand.PlantBeanCommand
    val player = mock[player]

    when(player.copyState()).thenReturn(player)
    doNothing().when(player).restore(any[player])

    plantBeanCommand.getStateStack.clear()
    plantBeanCommand.getRedoStack.clear()

    noException should be thrownBy plantBeanCommand.undoStep(Some(player))
    noException should be thrownBy plantBeanCommand.redoStep(Some(player))
  }

  "PlantBeanCommand" should "handle 'plant1or2' case correctly" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val playingPlayerMock = mock[player]
    when(dynamicGamedata.playingPlayer).thenReturn(Some(playingPlayerMock))
    when(playingPlayerMock.lastMethodUsed).thenReturn("plant1or2")

    when(utilityMock.playerHandToString(any())).thenReturn("PlayerHand")
    when(utilityMock.plant1or2(any())).thenReturn(2)
    doNothing().when(utilityMock).plantAllSelectedCards(any())

    val plantBeanCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock).PlantBeanCommand

    noException should be thrownBy plantBeanCommand.matchState()

    verify(utilityMock).playerHandToString(any())
    verify(utilityMock).plant1or2(any())
    verify(utilityMock).plantAllSelectedCards(any())
  }

  it should "handle 'handle' case correctly" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val playingPlayerMock = mock[player]
    val strategyMock = mock[Strategy]

    when(dynamicGamedata.playingPlayer).thenReturn(Some(playingPlayerMock))
    when(playingPlayerMock.lastMethodUsed).thenReturn("handle")
    when(plantAmountMock.selectStrategy()).thenReturn(strategyMock)
    doNothing().when(strategyMock).execute(any(), any())
    doNothing().when(playerStateMock).handle(any())

    val plantBeanCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock).PlantBeanCommand

    noException should be thrownBy plantBeanCommand.matchState()

    verify(plantAmountMock).selectStrategy()
    verify(strategyMock).execute(any(), any())
    verify(playerStateMock).handle(any())
  }


  "redoStep" should "restore the player's state from the redo stack" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]

    val undoCommand = new UndoCommand(utilityMock, plantAmountMock, playerStateMock)
    val playerMock = mock[player]

    when(playerMock.copyState()).thenReturn(playerMock)
    doNothing().when(playerMock).restore(any[player])

    val plantBeanCommand = undoCommand.PlantBeanCommand

    // Simulate a state change and push to redo stack
    plantBeanCommand.getStateStack.push(playerMock)
    plantBeanCommand.getRedoStack.push(playerMock)

    // Perform redoStep
    plantBeanCommand.redoStep(Some(playerMock))

    // Verify the state was restored from the redo stack
    verify(playerMock, times(1)).restore(any[player])
    plantBeanCommand.getStateStack should have size 1
    plantBeanCommand.getRedoStack should have size 0
  }
}