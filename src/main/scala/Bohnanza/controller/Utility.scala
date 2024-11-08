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


    if (isPlantable(player, plantCard) && player.playerHand.contains(plantCard.beanName)) {
      gamelogic.plant(plantCard,  player)
      gameUpdateLog.append(s"${player.name} pflanzt $plantCard\n")
      gameUpdateLog.toString
    } else {
      model.gamedata.errorBeanNotInHand
      model.gamedata.errorPlantingField
      plantPreperation(player)
    }
  }
  def findCardWithName(name: String): model.card = {
    for (i <- 1 to model.gamedata.cards.length){
      if (model.gamedata.cards(i-1).beanName == name ){
        return model.gamedata.cards(i-1)
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
    if (player.plantfield1 == ""){
      return 1
    }else if (player.plantfield2 == ""){
      return 2
    }else if( player.plantfield3 == ""){
      return 3
    }else return -1
  }
  def plantOrTrade(cards: ArrayBuffer[model.card], player: model.player): Unit = {
      println(model.gamedata.drawCardText)
      val keepCardNr = UIlogic.keyListener()
      keepCardNr match
        case 0 => //gamelogic.trade()
        case 1 => //trade 1 und plant 1
        case 2 => plantDrawnCard(player, cards(0))
                  plantDrawnCard(player, cards(1))
    }

  def findCardId(player: model.player, card: model.card): Int = {
      player.playerHand.indexOf(card.beanName)
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
    else if (player.plantfield1 == "" || player.plantfield2 == "" || player.plantfield3 == "") {
      return true
    }
    else {
      return false
    }

  }

  def chooseOrEmpty(playerID: model.player, card: model.card): Int ={
    if(playerID.field == card.beanName) {
      println(model.gamedata.newOrOldField)
      val fieldChoosen = UIlogic.keyListener()
      fieldChoosen
    } else {
      emptyPlantfieldNr(playerID)
    }
  }

}
