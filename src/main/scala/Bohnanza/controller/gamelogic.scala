package Bohnanza.controller

import Bohnanza.model

import scala.collection.mutable.ArrayBuffer

object gamelogic {

  def plant(cards: model.card, playerID: model.player): Unit ={
    val fieldNr = Utility.emptyPlantfieldNr(playerID)
    fieldNr match {
      case 1 => playerID.plantfield1 += cards.beanName + "\n"
      case 2 => playerID.plantfield2 += cards.beanName + "\n"
      case 3 => playerID.plantfield3 += cards.beanName + "\n"
    }
  }

  /*
  def harvest(field: Int): String={

  }*/
  def trade(playingPlayer : model.player, tradePartner : model.player, card1: model.card, card2: model.card): Unit ={
    val playerCard = playingPlayer.playerHand(Utility.findCardId(playingPlayer,card1))
    val traderCard = tradePartner.playerHand(Utility.findCardId(tradePartner,card2))
    playingPlayer.playerHand(Utility.findCardId(playingPlayer, card1)) = traderCard
    tradePartner.playerHand(Utility.findCardId(tradePartner, card2)) = playerCard
  }
  def drawCards(): ArrayBuffer[model.card] ={
    var cardArray = ArrayBuffer[model.card]()
    for (i <- 1 to 2) {
      cardArray.addOne(UIlogic.weightedRandom())
    }
    cardArray
  }
}