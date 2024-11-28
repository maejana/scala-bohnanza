package Bohnanza.model

import Bohnanza.view
import Bohnanza.controller
import Bohnanza.model.FactoryP

import scala.collection.mutable.ArrayBuffer
import Bohnanza.model.ObserverData
object gameDataFunc {
  def drawCards(): ArrayBuffer[card] = {
    val cardArray = ArrayBuffer[card]()
    for (i <- 1 to 2) {
      cardArray.addOne(controller.UIlogic.weightedRandom())
    }
    cardArray
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
    val weights = gamedata.weights
    val hand: ArrayBuffer[card] = ArrayBuffer()
    for (i <- 1 to 4) {
      hand.addOne(controller.UIlogic.weightedRandom())
      growingFieldText += hand(i - 1).beanName + ", "
    }
    
    hand.addOne(controller.UIlogic.weightedRandom())
    val newPlayer = FactoryP.PlayerFactory().createPlayer(playerName, hand) // Factory Pattern um Player zu erstellen
    growingFieldText += hand(4).beanName
    gamedata.players += newPlayer
    growingFieldText
  }
  def initGame: String = {
    var str = ""
    val playerCount = view.playerInput.playercount()
    val playernames: Array[String] = new Array[String](playerCount)
    println("Namen eingeben:")
    for (i <- 1 to playerCount) {
      playernames(i - 1) = view.playerInput.playername()
    }

    for (i <- 1 to playerCount) {
      str += initPlayer(playernames(i - 1))
    }
    str
  }
  def buildGrowingFieldStr(playingPlayer: player): String = {
    val growingFieldText: String =
      s"""
                                 ${playingPlayer.playerName}:
                                    Field 1:
                                 ${playerFieldToString(playingPlayer.plantfield1)}
                                    Field 2:
                                 ${playerFieldToString(playingPlayer.plantfield2)}
                                    Field 3:
                                 ${playerFieldToString(playingPlayer.plantfield3)}

                                 """
    growingFieldText
  }
  def playerFieldToString(field: ArrayBuffer[card]): String = {
    var s = ""
    field.foreach(card => s += card.beanName + " ")
    s
  }
  def takeNewCard(player: player, plantCard: card): Unit = {
    val index = player.playerHand.indexOf(plantCard)
    player.playerHand(index) = controller.UIlogic.weightedRandom()
  }
  def playerHandToString(hand: ArrayBuffer[card]): String = {
    var s = ""
    hand.foreach(card => s += card.beanName + " ")
    s
  }
}
