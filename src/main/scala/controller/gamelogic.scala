package Test.controller

import Test.model
import scala.util.Random

object gamelogic {
  def weightedRandom(weights: Array[Int]): String = {
    val hand = model.gamedata.beans
    val cumulativeWeights = weights.scanLeft(0)(_ + _).tail
    val totalWeight = cumulativeWeights.last

    val rand = Random.nextInt(totalWeight)

    val index = cumulativeWeights.indexWhere(_ > rand)
    hand(index)
  }

  //val playerName = "Miesepetriger Mustafa Van Meckerich"
  def initPlayer(name: String): String = {
    val playerName = name
    var growingFieldText: String =
      s"""
          $playerName:
              ___________
             |     |     |
             |     |     |
             |_____|_____|
          """
    val weights = model.gamedata.weights
    for (i <- 1 to 4) {
      growingFieldText += weightedRandom(weights) + ", "
    }
    growingFieldText += weightedRandom(weights)
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
