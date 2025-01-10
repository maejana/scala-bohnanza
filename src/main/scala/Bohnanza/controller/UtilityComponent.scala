package Bohnanza.controller

import Bohnanza.model.modelBase.card
import Bohnanza.model.modelBase.player
import scala.collection.mutable.ArrayBuffer
trait UtilityComponent {
  def plantInfo(): card
  def plantPreperation(player: Option[player]): String
  def findCardWithname(name: String): card
  def plantDrawnCard(player: Option[player]): Unit
  def emptyPlantfieldNr(player: Option[player]): Int
  def selectCardToPlant(cards: ArrayBuffer[card]): card
  def findCardId(player: player, card: card): Int
  def selectPlayer(): Option[player]
  def isPlantable(player:Option[player], bean: card): Boolean
  def chooseOrEmpty(playerID: Option[player], card: card): Int
  def plant1or2(playingPlayer: Option[player]): Int
  def plantAllSelectedCards(plantCount: Integer): Unit
  def plant1or2ThreadInterrupt(): Unit
  def returnGoldValue(plantfield: ArrayBuffer[card]): Int
  def checkPlantAmount(card: card, plantfield: ArrayBuffer[card]): Int
  def weightedRandom(): card
  def plantSelectString(player: Option[player]): String
  def deletePlayerBecauseBug(): Unit

  def plant(cards: card, playerID: Option[player]): Unit = {}

  def harvest(field: Int): Unit = {}

}

