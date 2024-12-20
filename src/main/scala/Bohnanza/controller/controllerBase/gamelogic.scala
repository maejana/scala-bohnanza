package Bohnanza.controller.controllerBase

import Bohnanza.controller.ControllerComponent
import Bohnanza.model
import Bohnanza.model.modelTrait
import Bohnanza.model.modelBase.{card, dynamicGamedata, player}


object gamelogic extends ControllerComponent {

  override def plant(cards: card, playerID: Option[player]): Unit = {
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

  override def harvest(field: Int): Unit = {
    field match{
      case 1 =>  dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(modelBase.dynamicGamedata.playingPlayer.get.plantfield1)
        modelBase.dynamicGamedata.playingPlayer.get.plantfield1.clear()
      case 2 =>   modelBase.dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(modelBase.dynamicGamedata.playingPlayer.get.plantfield2)
        modelBase.dynamicGamedata.playingPlayer.get.plantfield2.clear()
      case 3 => modelBase.dynamicGamedata.playingPlayer.get.gold += Utility.returnGoldValue(modelBase.dynamicGamedata.playingPlayer.get.plantfield3)
        modelBase.dynamicGamedata.playingPlayer.get.plantfield3.clear()
    }
  }

  def trade(playingPlayer : player, tradePartner : player, card1: card, card2: card): Unit ={
    val playerCard = playingPlayer.playerHand(Utility.findCardId(playingPlayer,card1))
    val traderCard = tradePartner.playerHand(Utility.findCardId(tradePartner,card2))
    playingPlayer.playerHand(Utility.findCardId(playingPlayer, card1)) = traderCard
    tradePartner.playerHand(Utility.findCardId(tradePartner, card2)) = playerCard
  }
}