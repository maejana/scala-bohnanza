package Bohnanza.controller.controllerBase

import Bohnanza.model
import Bohnanza.model.modelTrait
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.gameDataFunc.{drawCards, initGame, playerHandToString}
import Bohnanza.model.modelBase.{dynamicGamedata, fieldBuilder, gameDataFunc, gamedata}

object GameUpdate {
  def gameUpdate(): String = {
    var z = false
    var i = 0
    var round = 1
    val gameUpdateLog = new StringBuilder
    val playerCount = dynamicGamedata.playerCount
    while (round <= 5) {
      //Start of Round
      Utility.selectPlayer()
      println(modelBase.dynamicGamedata.playingPlayer.get.playerName)
      playerState.handle(modelBase.dynamicGamedata.playingPlayer)
      println(gamedata.plantAmountQuestion)
      println(playerHandToString(modelBase.dynamicGamedata.playingPlayer.get.playerHand))
      modelBase.dynamicGamedata.plantCount = Utility.plant1or2(modelBase.dynamicGamedata.playingPlayer)
      UndoCommand.PlantBeanCommand.doStep(modelBase.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern
      //planting
      Utility.plantAllSelectedCards(modelBase.dynamicGamedata.plantCount)
      UndoCommand.PlantBeanCommand.doStep(modelBase.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern
      println(fieldBuilder.buildGrowingFieldStr(modelBase.dynamicGamedata.playingPlayer))
      //Trade or plant 2 Cards
      modelBase.dynamicGamedata.drawnCards = drawCards()
      modelBase.dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
      println(modelBase.gamedata.drawCardText)
      plantAmount.selectStrategy().execute(modelBase.dynamicGamedata.drawnCards, modelBase.dynamicGamedata.playingPlayer)
      println(modelBase.fieldBuilder.buildGrowingFieldStr(modelBase.dynamicGamedata.playingPlayer))
      playerState.handle(modelBase.dynamicGamedata.playingPlayer)

      UndoCommand.PlantBeanCommand.doStep(modelBase.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern

      round += 1
      i = 0
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
  def gameSetup(): String = {
    val s = new StringBuilder()
    s.append(modelBase.gamedata.welcome)
    s.append(modelBase.gamedata.menu)
    s.append("\n")
    s.append(modelBase.gamedata.playerCountQuestion)
    s.toString()
  }
  def gameStart(): String = {
    val s = new StringBuilder()
    model.modelBase.dynamicGamedata.plant1or2 = 0
    s.append(initGame)
    s.append("\n\n")
    s.toString()
  }
}