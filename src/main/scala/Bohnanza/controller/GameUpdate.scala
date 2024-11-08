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
    while (round <= 10) {
      //Start of Game
      val playingPlayer = Utility.selectPlayer(p)
      println(model.gamedata.plantAmountQuestion)
      plantCount = Utility.plant1or2(playingPlayer)
      //planting
      while (i < plantCount) {
        Utility.plantPreperation(playingPlayer)
        i +=1
      }
      println(UIlogic.buildGrowingFieldStr(playingPlayer))
      //Trade or plant 2 Cards
      val cards = gamelogic.drawCards()
      cards.foreach(card => println(card.beanName))
      Utility.plantOrTrade(cards, playingPlayer)
      println(UIlogic.buildGrowingFieldStr(playingPlayer))

      round += 1
      if(p == playerCount || playerCount == 1){
        p = 0
      }else{
        p += 1
      }
      i = 0
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
}
