package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase.{Utility, playerState}
import Bohnanza.model.modelBase.{card, dynamicGamedata, player}
import Bohnanza.controller.State
import Bohnanza.view.PlantBeanInView
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import javafx.embed.swing.JFXPanel

import scala.collection.mutable.ArrayBuffer

class PlantBeanCTest extends AnyFlatSpec with Matchers with MockitoSugar {

  // Helper method to initialize JavaFX toolkit
  def initToolkit(): Unit = {
    new JFXPanel() // Initializes the JavaFX toolkit
  }

  "PlantNone" should "select a player and handle player state" in {
    initToolkit() // Initialize JavaFX toolkit

    val utilityMock = mock[Utility]
    val playerStateMock = mock[State]
    val playerMock = mock[player]

    dynamicGamedata.playingPlayer = Some(playerMock)

    PlantBeanC.PlantNone().plantBean()

    verify(utilityMock, times(0)).selectPlayer()
    verify(playerStateMock, times(0)).handle(dynamicGamedata.playingPlayer)
  }

  "PlantOne" should "clear cardsToPlant and plant one card if plantable" in {
    initToolkit() // Initialize JavaFX toolkit

    val utilityMock = mock[Utility]
    val playerMock = mock[player]
    val cardMock = mock[card]

    dynamicGamedata.playingPlayer = Some(playerMock)
    when(playerMock.playerHand).thenReturn(ArrayBuffer(cardMock))
    when(playerMock.plantfield1).thenReturn(ArrayBuffer.empty[card]) // Ensure plantfield1 is initialized
    when(utilityMock.isPlantable(Some(playerMock), cardMock)).thenReturn(true)

    PlantBeanC.PlantOne().plantBean()

    dynamicGamedata.cardsToPlant should contain only cardMock
    verify(utilityMock, times(1)).plantPreperation(Some(playerMock))
  }

  "PlantTwo" should "clear cardsToPlant and plant two cards if both are plantable" in {
    initToolkit() // Initialize JavaFX toolkit

    val utilityMock = mock[Utility]
    val playerMock = mock[player]
    val cardMock1 = mock[card]
    val cardMock2 = mock[card]

    dynamicGamedata.playingPlayer = Some(playerMock)
    when(playerMock.playerHand).thenReturn(ArrayBuffer(cardMock1, cardMock2))
    when(playerMock.plantfield1).thenReturn(ArrayBuffer.empty[card]) // Ensure plantfield1 is initialized
    when(utilityMock.isPlantable(Some(playerMock), cardMock1)).thenReturn(true)
    when(utilityMock.isPlantable(Some(playerMock), cardMock2)).thenReturn(true)

    PlantBeanC.PlantTwo().plantBean()

    dynamicGamedata.cardsToPlant should contain inOrder (cardMock1, cardMock2)
    verify(utilityMock, times(1)).plant(cardMock1, Some(playerMock))
    verify(utilityMock, times(1)).plant(cardMock2, Some(playerMock))
  }

  "plantBean" should "call the correct PlantBeanInView based on count" in {
    initToolkit() // Initialize JavaFX toolkit

    val plantNoneMock = mock[PlantBeanInView]
    val plantOneMock = mock[PlantBeanInView]
    val plantTwoMock = mock[PlantBeanInView]

    PlantBeanC.plantBean(0)
    verify(plantNoneMock, times(1)).plantBean()

    PlantBeanC.plantBean(1)
    verify(plantOneMock, times(1)).plantBean()

    PlantBeanC.plantBean(2)
    verify(plantTwoMock, times(1)).plantBean()
  }
}