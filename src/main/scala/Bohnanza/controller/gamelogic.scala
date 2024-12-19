package Bohnanza.controller

import Bohnanza.model


object gamelogic {

  def plant(cards: model.card, playerID: Option[model.player]): Unit = {
    val fieldNr = Utility.chooseOrEmpty(playerID, cards)
    fieldNr match {
      case 1 => playerID.get.plantfield1.addOne(cards)
      case 2 => playerID.get.plantfield2.addOne(cards)
      case 3 => playerID.get.plantfield3.addOne(cards)
      case -1 => println("Kein Feld frei zum anpflanzen")
      case _ => // No available fields
    }
    playerID.get.playerHand -= cards
  }

  def harvest(field: Int): Unit = {
    field match{
      case 1 =>  model.dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(model.dynamicGamedata.playingPlayer.get.plantfield1)
        model.dynamicGamedata.playingPlayer.get.plantfield1.clear()
      case 2 =>   model.dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(model.dynamicGamedata.playingPlayer.get.plantfield2)
        model.dynamicGamedata.playingPlayer.get.plantfield2.clear()
      case 3 => model.dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(model.dynamicGamedata.playingPlayer.get.plantfield3)
        model.dynamicGamedata.playingPlayer.get.plantfield3.clear()
    }
  }

  def trade(playingPlayer : model.player, tradePartner : model.player, card1: model.card, card2: model.card): Unit ={
    val playerCard = playingPlayer.playerHand(Utility.findCardId(playingPlayer,card1))
    val traderCard = tradePartner.playerHand(Utility.findCardId(tradePartner,card2))
    playingPlayer.playerHand(Utility.findCardId(playingPlayer, card1)) = traderCard
    tradePartner.playerHand(Utility.findCardId(tradePartner, card2)) = playerCard
  }
}