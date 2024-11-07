package Test.controller

import Test.model

object GameUpdate {
  def gameUpdate(): String = {
    var i = 0
    var p = 0
    var round = 1
    val gameUpdateLog = new StringBuilder
    val plantCount = 0
    while (round <= 10) {
      val playingPlayer = Utility.selectPlayer(p)
      println(model.gamedata.plantAmountQuestion)
      Utility.plant1or2(playingPlayer)
      while (i < plantCount) {
        Utility.plantPreperation(playingPlayer)
      }
      println(UIlogic.buildGrowingFieldStr(playingPlayer))
      println(gamelogic.drawCards())
      Utility.keepOrTrade()
      
      round += 1
      p += 1
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
}
