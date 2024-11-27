package Bohnanza.controller

import Bohnanza.model

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
      playerState.handle(model.gamedata.playingPlayer)
      //println(model.gamedata.playingPlayer.playerName + ": \n")
      println(model.gamedata.plantAmountQuestion)
      println(model.gameDataFunc.playerHandToString(model.gamedata.playingPlayer.playerHand))
      plantCount = Utility.plant1or2(model.gamedata.playingPlayer)
      //planting
      while (i < plantCount) {
        if(!Utility.plantPreperation(model.gamedata.playingPlayer).equals("")){
          i += 1
        }
        else {
          println(model.gamedata.keineKorrekteBohne)
        }
      }
      println(model.gameDataFunc.buildGrowingFieldStr(model.gamedata.playingPlayer))
      //Trade or plant 2 Cards
      model.gamedata.drawnCards = model.gameDataFunc.drawCards()
      model.gamedata.drawnCards.foreach(card => println(card.beanName))
      println(model.gamedata.drawCardText)
      plantAmount.selectStrategy().execute(model.gamedata.drawnCards, model.gamedata.playingPlayer)
      println(model.gameDataFunc.buildGrowingFieldStr(model.gamedata.playingPlayer))
      playerState.handle(model.gamedata.playingPlayer)

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