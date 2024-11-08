package Bohnanza.controller

import Bohnanza.model

import scala.collection.mutable.ArrayBuffer

object Utility {

  def plantInfo(): model.card = {
    val cardname = scala.io.StdIn.readLine()
    findCardWithName(cardname)
  }


  def plantPreperation(player: model.player): String = {
    val gameUpdateLog = new StringBuilder()
    val plantCard: model.card = Utility.plantInfo()
    if(plantCard == null){
      return ""
    }
    else if (isPlantable(player, plantCard) && player.playerHand.contains(plantCard)) {
      gamelogic.plant(plantCard,  player)
      takeNewCard(player, plantCard)
      gameUpdateLog.append(s"${player.name} pflanzt $plantCard\n")
      gameUpdateLog.toString
    } else {
      model.gamedata.errorBeanNotInHand
      model.gamedata.errorPlantingField
      plantPreperation(player)
    }
  }
  def takeNewCard(player: model.player, plantCard: model.card): Unit = {
    val index = player.playerHand.indexOf(plantCard)
    player.playerHand(index) = UIlogic.weightedRandom()
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
  def plantOrTrade(cards: ArrayBuffer[model.card], player: model.player): Unit = {
      println(model.gamedata.drawCardText)
      val keepCardNr = UIlogic.keyListener()
      keepCardNr match
        case 0 => //gamelogic.trade()
        case 1 => println(model.gamedata.drawnCardName)
                  plantDrawnCard(player, selectCardToPlant(cards, player))
        case 2 => plantDrawnCard(player, cards(0))
                  plantDrawnCard(player, cards(1))
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

  def plant1or2(playingPlayer: model.player): Int = {
      val Nr = UIlogic.keyListener()
      println(UIlogic.plantSelectString(playingPlayer))
      Nr
    }

  def selectPlayer(p: Int): model.player = {
      val playingPlayer: model.player = model.gamedata.players(p)
      println(playingPlayer.playerName + ":\n")
      playingPlayer
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
}
