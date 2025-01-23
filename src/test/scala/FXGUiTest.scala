package test

import javafx.stage.Stage
import org.junit.jupiter.api.{BeforeEach, Test}
import org.testfx.framework.junit5.ApplicationTest
import org.testfx.api.FxAssert
import org.testfx.matcher.control.LabeledMatchers
import Bohnanza.view.viewBase.FXGUi
import scalafx.application.JFXApp3.PrimaryStage

class FXGUiTest extends ApplicationTest {

  override def start(stage: Stage): Unit = {
    val primaryStage = new PrimaryStage()
    primaryStage.initOwner(stage)
    FXGUi.stage = primaryStage
    FXGUi.start()
  }

  @BeforeEach
  def setUp(): Unit = {
    // Set up any necessary preconditions here
  }

  @Test
  def shouldContainWelcomeLabel(): Unit = {
    FxAssert.verifyThat(".label", LabeledMatchers.hasText("Willkommen zu Bohnanza!"))
  }

  @Test
  def shouldChangeSceneOnPlayButtonClick(): Unit = {
    clickOn(".button:contains('Play')")
    FxAssert.verifyThat(".label", LabeledMatchers.hasText("Wie viele Spieler spielen?"))
  }

  @Test
  def shouldChangeSceneOnLoadButtonClick(): Unit = {
    clickOn(".button:contains('Spiel laden')")
    FxAssert.verifyThat(".label", LabeledMatchers.hasText("TestPlayer ist an der Reihe."))
  }
}