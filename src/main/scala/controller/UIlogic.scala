package Test.controller

import Test.model

import scala.language.postfixOps
import scala.util.Random

object UIlogic {
  def weightedRandom(weights: Array[Int]): String = {
    val hand = model.gamedata.beans
    val cumulativeWeights = weights.scanLeft(0)(_ + _).tail
    val totalWeight = cumulativeWeights.last

    val rand = Random.nextInt(totalWeight)

    val index = cumulativeWeights.indexWhere(_ > rand)
    hand(index)
  }
  def initPlayer(name: String): String = {
    val playerName = name
    var growingFieldText: String =
      s"""
          ${playerName}:
             Field 1:

             Field 2:

             Field 3:


          """
    val weights = model.gamedata.weights
    var hand: Array[String] = Array("","","","","")
    for (i <- 1 to 5) {
      hand(i-1) = weightedRandom(weights)
      growingFieldText += hand(i-1) + ", "
    }
    growingFieldText += weightedRandom(weights)
    model.gamedata.players += model.player(playerName,hand,growingFieldText)
    growingFieldText
  }
  def initGame: String = {
    var str = ""
    val playerCount = scala.io.StdIn.readInt()
    val playernames: Array[String] = new Array[String](playerCount)
    println("Namen eingeben:")
    for (i <- 1 to playerCount) {

      playernames(i - 1) = scala.io.StdIn.readLine()
    }

    for (i <- 1 to playerCount) {
      str += initPlayer(playernames(i - 1))
    }
    str
  }

  def gameUpdate(): String = {
    var i = 0
    var round = 1
    var gameUpdateLog = new StringBuilder

    while (round <= 1) {
      val playingPlayer: model.player = model.gamedata.players(i)


      // Spieler kann eine Karte wählen
      println(plantSelectString(i))

      var bool = true
      while (bool) {
        val Line = scala.io.StdIn.readLine().split(" ", 2)
        val plantCard = Line(0)
        val fieldNr = Line(1).toInt

        if (playingPlayer.hand.contains(plantCard)) {
          gamelogic.plant(plantCard, fieldNr, playingPlayer)
          gameUpdateLog.append(s"${playingPlayer.name} pflanzt $plantCard auf Feld $fieldNr\n")
          bool = false
        } else {
          println(model.gamedata.errorBeanNotInHand + "\n")
        }
      }

      round += 1
    }

    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }


  private def plantSelectString(i : Int): String={
    val playingPlayer: model.player = model.gamedata.players(i)
    var s :String = ""
    s += playingPlayer.playerName + ":"
    s += model.gamedata.selectPlantCard + ":"
    s +=playingPlayer.hand.mkString("", ", ", "")
    s
  }
}
