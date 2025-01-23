package Bohnanza.controller.controllerBase

import Bohnanza.model
import Bohnanza.controller.{plantAmountComponent, UtilityComponent, playerStateComponent}

import Bohnanza.model.modelBase

import Bohnanza.model.modelBase.{dynamicGamedata, fieldBuilder, gamedata}
import Bohnanza.controller.UtilityComponent
import Bohnanza.controller.UndoCommandComponent
import Bohnanza.controller.plantAmountComponent

import Bohnanza.controller.GameUpdateComponent


class GameUpdate(utility: UtilityComponent, plantAmount: plantAmountComponent, playerState: playerStateComponent, UndoCommand: UndoCommandComponent) extends GameUpdateComponent {

  override def gameUpdate(): String = {

    var z = false
    var i = 0
    var round = 1
    val gameUpdateLog = new StringBuilder
    val playerCount = dynamicGamedata.playerCount
    while (round <= 5) {
      //Start of Round
      utility.selectPlayer()
      println(dynamicGamedata.playingPlayer.get.playerName)
      playerState.handle(dynamicGamedata.playingPlayer)
      println(gamedata.plantAmountQuestion)
      println(utility.playerHandToString(dynamicGamedata.playingPlayer.get.playerHand))
      dynamicGamedata.plantCount = utility.plant1or2(dynamicGamedata.playingPlayer)
      UndoCommand.doStep(dynamicGamedata.playingPlayer) // Für Undo immer Status speichern
      //planting
      utility.plantAllSelectedCards(dynamicGamedata.plantCount)
      UndoCommand.doStep(dynamicGamedata.playingPlayer) // Für Undo immer Status speichern

      println(fieldBuilder(utility).buildGrowingFieldStr(dynamicGamedata.playingPlayer))
      //Trade or plant 2 Cards
      dynamicGamedata.drawnCards = utility.drawCards()
      dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
      println(gamedata.drawCardText)
      plantAmount.selectStrategy().execute(dynamicGamedata.drawnCards, dynamicGamedata.playingPlayer)
      println(fieldBuilder(utility).buildGrowingFieldStr(dynamicGamedata.playingPlayer))

      playerState.handle(dynamicGamedata.playingPlayer)

      UndoCommand.doStep(dynamicGamedata.playingPlayer) // Für Undo immer Status speichern

      round += 1
      i = 0
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
  override def gameSetup(): String = {
    val s = new StringBuilder()
    s.append(gamedata.welcome)
    s.append(gamedata.menu)
    s.append("\n")
    s.append(gamedata.playerCountQuestion)
    s.toString()
  }
  override def gameStart(): String = {
    val s = new StringBuilder()
    dynamicGamedata.plant1or2 = 0
    s.append(utility.initGame)
    s.append("\n\n")
    s.toString()
  }
}