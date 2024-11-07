package Test.controller

import Test.model

import scala.collection.mutable.ArrayBuffer

object gamelogic {

  def plant(cards: String, fieldNr: Int, playerID: model.player): Unit ={
    fieldNr match {
      case 1 => playerID.plantfield1 += cards + "\n"
      case 2 => playerID.plantfield2 += cards + "\n"
      case 3 => playerID.plantfield3 += cards + "\n"
    }
  }


  def harvest(field: Int): String ={
    // angepflanze Bohnen erten und gegen Gold tauschen
    ""
  }
  
  def bohnometer(card :model.card ): Int ={
    // gibt für jede Karte an, wie viel Geld sie Wert ist
    
    0
  }

  def trade(playingPlayer : model.player, tradePartner : model.player, card1: model.card, card2: model.card): Unit ={
    val playerCard = playingPlayer.playerHand(Utility.findCardId(playingPlayer,card1))
    val traderCard = tradePartner.playerHand(Utility.findCardId(tradePartner,card2))
  }
  def drawCards(): String = {
    var cardArray = ArrayBuffer[String]()
    for (i <- 1 to 2) {
      cardArray.addOne(UIlogic.weightedRandom())
    }
    cardArray.mkString("", ",", "")
  }
}