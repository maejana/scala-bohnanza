package Test.controller

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
      while (i < plantCount) {
        val Line = scala.io.StdIn.readLine().split(" ", 2)
        val plantCard = Line(0)
        val fieldNrStr = Line(1)

        if (playingPlayer.hand.contains(plantCard) && isPlantable(playingPlayer, plantCard) && (fieldNrStr == "1" || fieldNrStr == "2" || fieldNrStr == "3")) {
          val fieldNr = fieldNrStr.toInt
          gamelogic.plant(plantCard, fieldNr, playingPlayer)
          gameUpdateLog.append(s"${playingPlayer.name} pflanzt $plantCard auf Feld $fieldNr\n")
          i += 1
        } else {
          println(model.gamedata.errorBeanNotInHand)
          println(model.gamedata.errorPlantingField + "\n")
        }
      }
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
