package Bohnanza.model

import scala.collection.mutable.ArrayBuffer
import Bohnanza.model.modelBase.card
import Bohnanza.model.modelBase.player
trait gameDataFuncComponent {
  def drawCards(): Unit
  def initPlayer(name: String): String
  def initGame: String
  def playerFieldToString(field: ArrayBuffer[card]): String
  def takeNewCard(player: Option[player]): Unit
  def playerHandToString(hand: ArrayBuffer[card]): String
}
