package Bohnanza.controller

import Bohnanza.model.modelBase.card
import Bohnanza.model.modelBase.player
import scala.collection.mutable.ArrayBuffer
trait UtilityComponent {
  def plantInfo(): card
  def plantPreperation(player: Option[player]): String
  def findCardWithName(name: String): card              // Test
  def plantDrawnCard(player: Option[player], card: card): Unit
  def emptyPlantfieldNr(player: Option[player]): Int            // Test
  def selectCardToPlant(cards: ArrayBuffer[card]): card
  def findCardId(player: player, card: card): Int
  def selectPlayer(): Option[player]                            // Test
  def isPlantable(player:Option[player], bean: card): Boolean
  def chooseOrEmpty(playerID: Option[player], card: card): Int
  def plant1or2(playingPlayer: Option[player]): Int
  def plantAllSelectedCards(plantCount: Int): Unit
  def plant1or2ThreadInterrupt(): Unit
  def returnGoldValue(plantfield: ArrayBuffer[card]): Int
  def checkPlantAmount(card: card, plantfield: ArrayBuffer[card]): Int
  def weightedRandom(): card
  def plantSelectString(player: Option[player]): String
  def deletePlayerBecauseBug(): Unit

  def plant(cards: card, playerID: Option[player]): Unit = {}
  def harvest(field: Int): Unit = {}

  def drawCards(): ArrayBuffer[card]
  def initPlayer(name: String): String
  def initGame: String
  def playerFieldToString(field: ArrayBuffer[card]): String
  def takeNewCard(player: Option[player]): Unit
  def playerHandToString(hand: ArrayBuffer[card]): String
}

