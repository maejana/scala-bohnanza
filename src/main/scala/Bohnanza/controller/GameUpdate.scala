package Bohnanza.controller

import Bohnanza.model
import Bohnanza.view.TUI
import Bohnanza.view

object GameUpdate {
  def gameUpdate(): String = {
    var z = false
    var i = 0
    var p = 0
    var round = 1
    val gameUpdateLog = new StringBuilder
    val playerCount = model.dynamicGamedata.playerCount
    while (round <= 5) {
      //Start of Round
      model.dynamicGamedata.playingPlayer = Utility.selectPlayer(p)
      println(model.dynamicGamedata.playingPlayer.playerName)
      playerState.handle(model.dynamicGamedata.playingPlayer)
      println(model.gamedata.plantAmountQuestion)
      println(model.gameDataFunc.playerHandToString(model.dynamicGamedata.playingPlayer.playerHand))
      model.dynamicGamedata.plantCount = Utility.plant1or2(model.dynamicGamedata.playingPlayer)
      UndoCommand.PlantBeanCommand.doStep(model.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern
      //planting
      Utility.plantAllSelectedCards(model.dynamicGamedata.plantCount)
      UndoCommand.PlantBeanCommand.doStep(model.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern
      println(model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer))
      //Trade or plant 2 Cards
      model.dynamicGamedata.drawnCards = model.gameDataFunc.drawCards()
      model.dynamicGamedata.drawnCards.foreach(card => println(card.beanName))
      println(model.gamedata.drawCardText)
      plantAmount.selectStrategy().execute(model.dynamicGamedata.drawnCards, model.dynamicGamedata.playingPlayer)
      println(model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer))
      playerState.handle(model.dynamicGamedata.playingPlayer)

      UndoCommand.PlantBeanCommand.doStep(model.dynamicGamedata.playingPlayer) // Für Undo immer Status speichern

      round += 1
      i = 0
      if (p == playerCount - 1 || playerCount == 1) {
        p = 0
      } else {
        p += 1
      }
      i = 0
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
  def gameSetup(): String = {
    val s = new StringBuilder()
    s.append(model.gamedata.welcome)
    s.append(model.gamedata.menu)
    s.append("\n")
    s.append(model.gamedata.playerCountQuestion)
    s.toString()
  }
  def gameStart(): String = {
    val s = new StringBuilder()
    s.append(model.gameDataFunc.initGame)
    s.append("\n\n")
    s.toString()
  }
}