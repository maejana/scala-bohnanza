package Bohnanza.view.viewBase

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import Bohnanza.controller.controllerBase.{GameUpdate, Utility, plantAmount, playerState}
import Bohnanza.controller.{UtilityComponent, plantAmountComponent, playerStateComponent}
import Bohnanza.model.modelBase.gamedata
import Bohnanza.controller.controllerBase.UndoCommand

class TUITest extends AnyFlatSpec with Matchers with MockitoSugar {


  "TUI" should "display the menu options correctly" in {
    val menuOutput = Console.withOut(new java.io.ByteArrayOutputStream()) {
      TUI.displayMenu()
    }
    val expectedOutput = s"${gamedata.Undo}\n${gamedata.Redo}\n${gamedata.Exit}\n"
    menuOutput.toString shouldEqual expectedOutput
  }



  /*
  it should "set up TUI and start the game setup process" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]
    val undoCommandMock = mock[UndoCommand]
    val gameUpdateMock = mock[GameUpdate]

    when(gameUpdateMock.gameSetup()).thenReturn("Game setup completed.")
    when(gameUpdateMock.gameStart()).thenReturn("Game started.")

    Console.withOut(new java.io.ByteArrayOutputStream()) {
      TUI.setUpTUI()
    }

    verify(gameUpdateMock, times(1)).gameSetup()
    verify(gameUpdateMock, times(1)).gameStart()
  }




  it should "start the game and execute game update" in {
    val utilityMock = mock[UtilityComponent]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]
    val undoCommandMock = mock[UndoCommand]
    val gameUpdateMock = mock[GameUpdate]

    when(gameUpdateMock.gameUpdate()).thenReturn("Game updated.")

    Console.withOut(new java.io.ByteArrayOutputStream()) {
      TUI.startGame()
    }

    verify(gameUpdateMock, times(1)).gameUpdate()
  }

   */


}
