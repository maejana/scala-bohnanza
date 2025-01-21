package Bohnanza.model

import Bohnanza.model.modelBase.{card, player}

import scala.collection.mutable.ArrayBuffer
import Bohnanza.controller.State
import play.api.libs.json.JsObject

import scala.xml.Elem

trait FileIOTrait {
  def loadDynamicGamedata(file: String): Unit
  def findPlayerWithName(str: String): player
  def loadPlayers(file: String): ArrayBuffer[player]
  def load(): Unit
  def save(Players: ArrayBuffer[player]): Unit
  def playerToFile(Player: player): Elem
  def dynamicGamedateToFile(): Elem
  def dynamicGamedateToFileJ(): JsObject
  def ArrayBufferToString(plantfield: ArrayBuffer[card]): String
  def toStringArray(Player: player): ArrayBuffer[String]
  def StringToArrayBuffer(str: String): ArrayBuffer[card]
  def stringToCard(str: String): card
  def newPlayer(PlayerName: String, Playerhand: ArrayBuffer[card], Plantfield1: ArrayBuffer[card], Plantfield2: ArrayBuffer[card], Plantfield3: ArrayBuffer[card], Gold: Int, State: State, LastMethodUSsed: String): player

  def playerToFileJ(player: player): JsObject

}
