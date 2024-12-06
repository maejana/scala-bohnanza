package Bohnanza.model

import Bohnanza.controller.SubjectTrait

import scala.collection.mutable.ArrayBuffer

object dynamicGamedata extends SubjectTrait {
  var players = ArrayBuffer[player]()
  var drawnCards: ArrayBuffer[card] = ArrayBuffer[card]()
  var playingPlayer: Bohnanza.model.player = null
  var plantCount = 0
  var playerNameBuffer = ArrayBuffer[String]()
  var playerCount = 0
  var readerThread: Thread = new Thread()
  var NameReaderThread: Thread = new Thread()
}
