package Bohnanza.model.modelBase

import Bohnanza.controller.controllerBase
import Bohnanza.controller.controllerBase.Utility
import Bohnanza.model.modelBase.{FactoryP, card, dynamicGamedata, gamedata}
import Bohnanza.model.modelBase
import Bohnanza.view.viewBase
import Bohnanza.view.viewBase.playerInput
import Bohnanza.{controller, model, view}

import java.io.{ByteArrayInputStream, InputStream}
import scala.collection.mutable.ArrayBuffer
object gameDataFunc {
  def drawCards(): ArrayBuffer[card] = {
    val cardArray = ArrayBuffer[card]()
    for (i <- 1 to 2) {
      cardArray.addOne(Utility.weightedRandom())
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
      hand.addOne(controllerBase.Utility.weightedRandom())
      growingFieldText += hand(i - 1).beanName + ", "
    }
    hand.addOne(controllerBase.Utility.weightedRandom())

    val newPlayer = FactoryP.PlayerFactory().createPlayer(playerName, hand) // Factory Pattern um Player zu erstellen
    //growingFieldText += hand(4).beanName
    if(!dynamicGamedata.players.isEmpty) {
      dynamicGamedata.players.toList.foreach((p: player) =>
        if (!p.playerName.equals(newPlayer.playerName)) {
          dynamicGamedata.players += newPlayer
        })
    }
    else dynamicGamedata.players += newPlayer
    growingFieldText
  }
  def initGame: String = {
    var str = ""
    playerInput.playercount()

    if(modelBase.dynamicGamedata.players.length != modelBase.dynamicGamedata.playerCount){
      val playernames: Array[String] = new Array[String](dynamicGamedata.playerCount)
      println("Namen eingeben:")
      for (i <- 1 to dynamicGamedata.playerCount-modelBase.dynamicGamedata.players.length) {
        playernames(i-1) = viewBase.playerInput.playername()
        //view.GUI.addPlayerViaTUI(playernames(i-1), i)
        if(playernames(i-1)!= "") str += initPlayer(playernames(i-1))
      }
    }
    str
  }
  def playerFieldToString(field: ArrayBuffer[card]): String = {
    var s = ""
    field.foreach(card => s += card.beanName + " ")
    s
  }
  def takeNewCard(player: Option[player]): Unit = {
    player.get.playerHand += controllerBase.Utility.weightedRandom()
  }
  def playerHandToString(hand: ArrayBuffer[card]): String = {
    var s = ""
    hand.foreach(card => s += card.beanName + " ")
    s
  }
}

