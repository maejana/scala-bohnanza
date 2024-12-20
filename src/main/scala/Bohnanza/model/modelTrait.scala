package Bohnanza.model

import Bohnanza.model.modelBase.card
import Bohnanza.model.modelBase.player
import scala.collection.mutable.ArrayBuffer

trait modelTrait {
  def drawCards: ArrayBuffer[card]
  def initPlayer(name: String): String
  def initGame: String
  def playerFieldToString(field: ArrayBuffer[card]): String
  def takeNewCard(player: Option[player]): Unit
  def playerHandToString(hand: ArrayBuffer[card]): String
}
