package Bohnanza.model

import Bohnanza.{controller, model, view}
import java.io.{ByteArrayInputStream, InputStream}
import scala.collection.mutable.ArrayBuffer
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
    view.playerInput.playercount()

    if(model.dynamicGamedata.players.length != model.dynamicGamedata.playerCount){
      val playernames: Array[String] = new Array[String](dynamicGamedata.playerCount)
      println("Namen eingeben:")
      for (i <- 1 to dynamicGamedata.playerCount-model.dynamicGamedata.players.length) {
        playernames(i-1) = view.playerInput.playername()
        view.GUI.addPlayerViaTUI(playernames(i-1), i)
        str += initPlayer(playernames(i-1))
      }
    }
    str
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

