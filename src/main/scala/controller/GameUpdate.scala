package Test.controller

import Test.Utility

object GameUpdate {
  def gameUpdate(): String = {
    var i = 0
    var p = 0
    var round = 1
    val gameUpdateLog = new StringBuilder

    while (round <= 10) {
      val playingPlayer: model.player = model.gamedata.players(p)
      println(playingPlayer.playerName + ":\n")
      println(model.gamedata.plantAmountQuestion)
      val plantCount = 0
      val Nr = UIlogic.keyListener()
      println(Nr)
      println(UIlogic.plantSelectString(playingPlayer))
      i = 0
      
      var growingFieldText: String =
        s"""
                           ${playingPlayer.playerName}:
                              Field 1:
                           ${playingPlayer.plantfield1}
                              Field 2:
                           ${playingPlayer.plantfield2}
                              Field 3:
                           ${playingPlayer.plantfield3}

                           """
      println(growingFieldText)
      println(gamelogic.drawCards().mkString("", ",", ""))
      println(model.gamedata.drawCardText)
      var keepCardNr = keyListener()
      round += 1
      p += 1
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
  
}
