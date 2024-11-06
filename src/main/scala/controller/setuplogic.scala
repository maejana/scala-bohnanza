package Test.controller

import Test.model

import scala.util.Random

object setuplogic {
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

}
