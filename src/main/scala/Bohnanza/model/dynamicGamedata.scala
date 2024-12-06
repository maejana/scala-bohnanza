package Bohnanza.model

import Bohnanza.controller.SubjectTrait
import Bohnanza.model

import scala.collection.mutable.ArrayBuffer

object dynamicGamedata extends SubjectTrait {
  var players = ArrayBuffer[player]()
  var drawnCards: ArrayBuffer[card] = ArrayBuffer[card]()
  var playingPlayer: player = new player("", ArrayBuffer[card](card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue)))
  var plantCount = 0
  var playerNameBuffer = ArrayBuffer[String]()
  var playerCount = 0
  var readerThread: Thread = new Thread()
  var NameReaderThread: Thread = new Thread()
  var cardsToPlant = ArrayBuffer[card]()
  var playingPlayerID : Int = 0
}
