import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import Bohnanza.view.viewBase.handleInput
import Bohnanza.model.modelBase.{player, gamedata}
import Bohnanza.controller.controllerBase.{Utility, UndoCommand}
import Bohnanza.controller.{plantAmountComponent, playerStateComponent}

class handleInputTest extends AnyFlatSpec with Matchers with MockitoSugar {
/*
  "handleInputF" should "call the correct methods for input '0', '1', or '2'" in {
    val playerMock = mock[Option[player]]
    val keyListenerMock = mock[() => Unit]
    val utilityMock = mock[Utility]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]
    val undoCommandMock = mock[UndoCommand]

    // Mock the keyListener method
    when(keyListenerMock.apply()).thenReturn((): Unit)

    // Replace the original keyListener with the mock
    val handleInputClass = handleInput.getClass
    val keyListenerField = handleInputClass.getDeclaredField("keyListener")
    keyListenerField.setAccessible(true)
    keyListenerField.set(handleInput, keyListenerMock)

    // Test inputs '0', '1', and '2'
    Seq("0", "1", "2").foreach { input =>
      val inputMock = mock[() => String]
      when(inputMock.apply()).thenReturn(input)

      noException should be thrownBy handleInput.handleInputF(playerMock)
      verify(keyListenerMock, times(1)).apply()
    }
  }

  it should "undo the last action for input 'U'" in {
    val playerMock = mock[Option[player]]
    val utilityMock = mock[Utility]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]
    val undoCommandMock = mock[UndoCommand]

    when(undoCommandMock.PlantBeanCommand.undoStep(playerMock)).thenReturn((): Unit)

    val inputMock = mock[() => String]
    when(inputMock.apply()).thenReturn("U")

    noException should be thrownBy handleInput.handleInputF(playerMock)
    verify(undoCommandMock.PlantBeanCommand, times(1)).undoStep(playerMock)
  }

  it should "redo the last undone action for input 'R'" in {
    val playerMock = mock[Option[player]]
    val utilityMock = mock[Utility]
    val plantAmountMock = mock[plantAmountComponent]
    val playerStateMock = mock[playerStateComponent]
    val undoCommandMock = mock[UndoCommand]

    when(undoCommandMock.PlantBeanCommand.redoStep(playerMock)).thenReturn((): Unit)

    val inputMock = mock[() => String]
    when(inputMock.apply()).thenReturn("R")

    noException should be thrownBy handleInput.handleInputF(playerMock)
    verify(undoCommandMock.PlantBeanCommand, times(1)).redoStep(playerMock)
  }

  it should "exit the application for input 'E'" in {
    val playerMock = mock[Option[player]]
    val exitMock = mock[() => Unit]

    val inputMock = mock[() => String]
    when(inputMock.apply()).thenReturn("E")

    // Mock system exit
    val exitException = intercept[SecurityException] {
      handleInput.handleInputF(playerMock)
    }
    exitException.getMessage shouldBe "System.exit was called"
  }

  it should "display the menu for input 'M'" in {
    val playerMock = mock[Option[player]]

    // Mock the TUI.displayMenu method
    val displayMenuMock = mock[() => Unit]
    when(displayMenuMock.apply()).thenReturn((): Unit)

    val inputMock = mock[() => String]
    when(inputMock.apply()).thenReturn("M")

    noException should be thrownBy handleInput.handleInputF(playerMock)
    verify(displayMenuMock, times(1)).apply()
  }

  it should "handle incorrect input gracefully" in {
    val playerMock = mock[Option[player]]
    val inputMock = mock[() => String]

    when(inputMock.apply()).thenReturn("InvalidInput")
    noException should be thrownBy handleInput.handleInputF(playerMock)
  }

 */
}