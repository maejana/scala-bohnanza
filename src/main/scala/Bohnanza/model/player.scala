package Bohnanza.model

import javax.smartcardio.Card
import scala.collection.mutable.ArrayBuffer
import Bohnanza.controller.playerState

case class player(name: String, hand: ArrayBuffer[card]) {
  val playerName = name
  var playerHand = hand
  var plantfield1 = ArrayBuffer[card]()
  var plantfield2 = ArrayBuffer[card]()
  var plantfield3 = ArrayBuffer[card]()
  var gold = 0
  var plays : playerState.State = playerState.dontPlays()
}
