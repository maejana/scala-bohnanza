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
    var plantCount = 0
    val playerCount = model.gamedata.players.size
    while (round <= 5) {
      //Start of Round
      model.gamedata.playingPlayer = Utility.selectPlayer(p)
      println(model.gamedata.playingPlayer.playerName + ": \n")
      println(model.gamedata.plantAmountQuestion)
      println(model.gameDataFunc.playerHandToString(model.gamedata.playingPlayer.playerHand))
      plantCount = Utility.plant1or2(model.gamedata.playingPlayer)
      UndoCommand.PlantBeanCommand(model.gamedata.playingPlayer).doStep(model.gamedata.playingPlayer) // Für Undo immer Status speichern
      //planting
      while (i < plantCount) {
        if(!Utility.plantPreperation(model.gamedata.playingPlayer).equals("")){
          i += 1
        }
        else {
          println(model.gamedata.keineKorrekteBohne)
        }
      }

      UndoCommand.PlantBeanCommand(model.gamedata.playingPlayer).doStep(model.gamedata.playingPlayer) // Für Undo immer Status speichern
      println(model.gameDataFunc.buildGrowingFieldStr(model.gamedata.playingPlayer))
      //Trade or plant 2 Cards
      model.gamedata.drawnCards = model.gameDataFunc.drawCards()
      model.gamedata.drawnCards.foreach(card => println(card.beanName))
      println(model.gamedata.drawCardText)
      plantAmount.selectStrategy().execute(model.gamedata.drawnCards, model.gamedata.playingPlayer)
      println(model.gameDataFunc.buildGrowingFieldStr(model.gamedata.playingPlayer))
      playerState.handle(model.gamedata.playingPlayer)

      UndoCommand.PlantBeanCommand(model.gamedata.playingPlayer).doStep(model.gamedata.playingPlayer) // Für Undo immer Status speichern

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
    s.toString()
  }
}