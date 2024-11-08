package Bohnanza.controller

import Bohnanza.model

import scala.collection.mutable.ArrayBuffer

object gamelogic {

  def plant(cards: model.card, playerID: model.player): Unit = {
    
    val fieldNr = Utility.chooseOrEmpty(playerID, cards)
    fieldNr match {
      case 1 => playerID.plantfield1.addOne(cards)
      case 2 => playerID.plantfield2.addOne(cards)
      case 3 => playerID.plantfield3.addOne(cards)
      case -1 => println("Kein Feld frei zum anpflanzen")
      case _ => // No available fields
    }
  }

  def harvest(field: Int): String = {
    // angepflanze Bohnen erten und gegen Gold tauschen
    ""
  }

  def bohnometer(card: model.card): Int = {
    // gibt für jede Karte an, wie viel Geld sie Wert ist

    0
  }

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