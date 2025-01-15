package Bohnanza.model.modelBase

import Bohnanza.controller.SubjectTrait
import Bohnanza.model
import Bohnanza.model.modelBase.card

import scala.collection.mutable.ArrayBuffer

object dynamicGamedata extends SubjectTrait {
  var players = ArrayBuffer[player]()
  var drawnCards: ArrayBuffer[card] = ArrayBuffer[card]()
  var playingPlayer: Option[player] = None
  var plantCount = 0
  var playerNameBuffer = ArrayBuffer[String]()
  var playerCount = 0
  var readerThread: Thread = new Thread()
  var readerThreadPlant1or2: Thread = new Thread()
  var NameReaderThread: Thread = new Thread()
  var cardsToPlant = ArrayBuffer[card]()
  var playingPlayerID : Int = 0
  var plant1or2 : Int = 1


  def ArrayBufferToString(buffer: ArrayBuffer[card]): String = {
    val str = new StringBuilder()
    for(card <- buffer){
      str.append(card.beanName)
      str.append(", ")
    }
    str.result()
  }
  //Todo: Methoden schreiben um jede einzelne Variable zu einem String zu konvertieren
  //Todo: Methoden schreiben um jede einzelne Variable aus einem String zu rekonstruieren
}
