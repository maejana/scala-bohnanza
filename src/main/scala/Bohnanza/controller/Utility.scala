package Bohnanza.controller

import Bohnanza.view
import Bohnanza.model
import Bohnanza.model.player
import Bohnanza.model.gameDataFunc

import scala.collection.mutable.ArrayBuffer

object Utility {

  def plantInfo(): model.card = {
    val cardname = scala.io.StdIn.readLine()
    findCardWithName(cardname)
  }
  def plantPreperation(player: model.player): String = {
    val gameUpdateLog = new StringBuilder()
    val plantCard: model.card = plantInfo()
    if(plantCard == null) {
      return ""
    }
    else if (isPlantable(player, plantCard) && player.playerHand.contains(plantCard)) {
      gamelogic.plant(plantCard, player)
      model.gameDataFunc.takeNewCard(player, plantCard)
      gameUpdateLog.append(s"${player.name} pflanzt $plantCard\n")
      gameUpdateLog.toString
    } else {
      ""
    }
  }
  def findCardWithName(name: String): model.card = {
    var i = 0
    for (i <- 0 until model.gamedata.cards.length){
      if (model.gamedata.cards(i).beanName == name ){
        return model.gamedata.cards(i)
      }
    }
    null
  }
  def plantDrawnCard(player: model.player, card: model.card): Unit = {
    if(isPlantable(player, card)){
      gamelogic.plant(card,player)
    }
  }
  def emptyPlantfieldNr(player: model.player): Int = {
    if (player.plantfield1.isEmpty){
      return 1
    }else if (player.plantfield2.isEmpty){
      return 2
    }else if( player.plantfield3.isEmpty){
      return 3
    }else return -1
  }
 
  def selectCardToPlant(cards : ArrayBuffer[model.card], player: model.player): model.card = {
    var bool = true
    while (bool){
      val cardToPlant = plantInfo()
      if (cards.contains(cardToPlant)) {
        bool = false
        return cardToPlant
      } else {
        bool = true
      }
    }
    null
  }
  def findCardId(player: model.player, card: model.card): Int = {
      player.playerHand.indexOf(card)
    }
  def selectPlayer(index: Int): player = {
    if (index >= 0 && index < model.gamedata.players.length) {
      model.gamedata.players(index)
    } else {
      throw new IndexOutOfBoundsException(s"$index is out of bounds (min 0, max ${model.gamedata.players.length - 1})")
    }
  }
  def isPlantable(player: model.player, bean: model.card): Boolean = {
    if (player.plantfield1.contains(bean.beanName) || player.plantfield2.contains(bean.beanName) || player.plantfield3.contains(bean.beanName)) {
      return true
    }
    else if (player.plantfield1.isEmpty || player.plantfield2.isEmpty || player.plantfield3.isEmpty) {
      return true
    }
    else {
      return false
    }
  }
  def chooseOrEmpty(playerID: model.player, card: model.card): Int ={
    if(playerID.plantfield1.contains(card)) {
      return 1
    } else if(playerID.plantfield2.contains(card)) {
      return 2
    } else if(playerID.plantfield3.contains(card)){
      return 3
    } else {
      emptyPlantfieldNr(playerID)
    }
  }
  def plant1or2(playingPlayer: player): Int = {
    val Nr = view.playerInput.keyListener()
    println(UIlogic.plantSelectString(playingPlayer))
    Nr
  }
}
