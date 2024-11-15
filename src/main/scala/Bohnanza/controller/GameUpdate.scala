package Bohnanza.controller

import Bohnanza.model

object GameUpdate {
  def gameUpdate(): String = {
    var i = 0
    var p = 0
    var round = 1
    val gameUpdateLog = new StringBuilder
    var plantCount = 0
    val playerCount = model.gamedata.players.size
    while (round <= 5) {
      //Start of Game
      val playingPlayer = Utility.selectPlayer(p)
      println(model.gamedata.plantAmountQuestion)
      println(model.gameDataFunc.playerHandToString(playingPlayer.playerHand))
      plantCount = Utility.plant1or2(playingPlayer)
      //planting
      println("i außerhalb der while: " + i)
      while (i < plantCount) {
        println("i in while : "+ i)
        if(!Utility.plantPreperation(playingPlayer).equals("")){
          i += 1
        }
      }
      println(model.gameDataFunc.buildGrowingFieldStr(playingPlayer))
      //Trade or plant 2 Cards
      model.gamedata.drawnCards = model.gameDataFunc.drawCards()
      model.gamedata.drawnCards.foreach(card => println(card.beanName))
      Utility.plantOrTrade(model.gamedata.drawnCards, playingPlayer)
      println(model.gameDataFunc.buildGrowingFieldStr(playingPlayer))

      round += 1
      if(p == playerCount-1 || playerCount == 1){
        p = 0
      }else{
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
    s.toString()
  }
}
