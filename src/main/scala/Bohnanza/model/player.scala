package Bohnanza.model

import javax.smartcardio.Card
import scala.collection.mutable.ArrayBuffer
import Bohnanza.controller.playerState
import Bohnanza.controller.playerState.State

case class player(name: String, hand: ArrayBuffer[card]) {
  var playerName = name // eigentlich val
  var playerHand = hand
  var plantfield1 = ArrayBuffer[card]()
  var plantfield2 = ArrayBuffer[card]()
  var plantfield3 = ArrayBuffer[card]()
  var gold = 0
  var state: State = playerState.DontPlays()
  var lastMethodUsed = ""

  // beide Methoden für Undo
  def restore(newState: player): Unit = {
    this.playerName = newState.playerName
    this.playerHand = newState.playerHand.clone()
    this.plantfield1 = newState.plantfield1.clone()
    this.plantfield2 = newState.plantfield2.clone()
    this.plantfield3 = newState.plantfield3.clone()
    this.gold = newState.gold
    this.state = newState.state
  }
  def copyState(): player = {
    val newPlayer = player(
      this.playerName,
      this.playerHand.clone()
    )
    newPlayer.plantfield1 = this.plantfield1.clone()
    newPlayer.plantfield2 = this.plantfield2.clone()
    newPlayer.plantfield3 = this.plantfield3.clone()
    newPlayer.gold = this.gold
    newPlayer.state = this.state
    newPlayer

  }
}
