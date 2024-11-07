package Test.controller

import Test.model

object Utility {

  def plantInfo(): Array[String] = {
    val Line = scala.io.StdIn.readLine().split(" ", 2)
    if (Line(1) == "1" || Line(1) == "2" || Line(1) == "3") {
      val fieldNrStr = Line(1)
      val plantCard = Line(0)
      val plantInfo: Array[String] = new Array[String](2)
      plantInfo(0) = plantCard
      plantInfo(1) = fieldNrStr
      plantInfo

    } else {
      val plantInfoError: Array[String] = new Array[String](2)
      plantInfoError(0) = model.gamedata.errorBeanNotInHand
      plantInfoError(1) = model.gamedata.errorPlantingField
      plantInfoError
    }

  }

  def plantPreperation(player: model.player): String = {
    val gameUpdateLog = new StringBuilder()
    val plantInfo: Array[String] = Utility.plantInfo()

    val plantCard = plantInfo(0)
    val fieldNrStr = plantInfo(1)

    if (UIlogic.isPlantable(player, plantCard)) {
      val fieldNr = fieldNrStr.toInt
      gamelogic.plant(plantCard, fieldNr, player)
      gameUpdateLog.append(s"${player.name} pflanzt $plantCard auf Feld $fieldNr\n")
      gameUpdateLog.toString
    } else {
      model.gamedata.errorBeanNotInHand
      model.gamedata.errorPlantingField
    }
  }
    def keepOrTrade(): Unit = {
      println(model.gamedata.drawCardText)
      val keepCardNr = UIlogic.keyListener()
      keepCardNr match
        case 0 =>
        case 1 =>
        case 2 =>
    }

    def findCardId(player: model.player, card: model.card): Int = {
      player.playerHand.indexOf(card.beanName)
    }

    def plant1or2(playingPlayer: model.player): Unit = {
      val Nr = UIlogic.keyListener()
      println(UIlogic.plantSelectString(playingPlayer))
    }

    def selectPlayer(p: Int): model.player = {
      val playingPlayer: model.player = model.gamedata.players(p)
      println(playingPlayer.playerName + ":\n")
      playingPlayer
    }
}
