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
        val Line = scala.io.StdIn.readLine().split(" ", 2)
        val plantCard = Line(0)
        val fieldNrStr = Line(1)
        Utility.plantInfo()

        if (UIlogic.isPlantable(playingPlayer, plantCard) && (fieldNrStr == "1" || fieldNrStr == "2" || fieldNrStr == "3")) {
          val fieldNr = fieldNrStr.toInt
          gamelogic.plant(plantCard, fieldNr, playingPlayer)
          gameUpdateLog.append(s"${playingPlayer.name} pflanzt $plantCard auf Feld $fieldNr\n")
          i += 1
        } else {
          println(model.gamedata.errorBeanNotInHand)
          println(model.gamedata.errorPlantingField + "\n")
        }
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
